package com.evertvd.bienes.vista.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.csvreader.CsvReader;
import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.hilos.ThreadWriteData;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.Catalogo;
import com.evertvd.bienes.modelo.CentroCosto;
import com.evertvd.bienes.modelo.CuentaContable;
import com.evertvd.bienes.modelo.Departamento;
import com.evertvd.bienes.modelo.Empresa;
import com.evertvd.bienes.modelo.Historial;
import com.evertvd.bienes.modelo.Responsable;
import com.evertvd.bienes.modelo.Sede;
import com.evertvd.bienes.modelo.Total;
import com.evertvd.bienes.modelo.Ubicacion;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.modelo.dao.CatalogoDao;
import com.evertvd.bienes.modelo.dao.DepartamentoDao;
import com.evertvd.bienes.modelo.dao.EmpresaDao;
import com.evertvd.bienes.modelo.dao.SedeDao;
import com.evertvd.bienes.modelo.dao.UbicacionDao;
import com.evertvd.bienes.scannercode.MaterialBarcodeScanner;
import com.evertvd.bienes.scannercode.MaterialBarcodeScannerBuilder;
import com.evertvd.bienes.utils.Buscar;
import com.evertvd.bienes.vista.activitys.ActivoView;

import com.evertvd.bienes.vista.activitys.MainActivity;
import com.evertvd.bienes.vista.adapters.ActivoAdapter;
import com.evertvd.bienes.vista.dialogs.DialgoFilterData;
import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.google.android.gms.vision.barcode.Barcode;

import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Principal extends Fragment implements SearchView.OnQueryTextListener, DialgoFilterData.OnAddFilter, RadioGroup.OnCheckedChangeListener {
    private View view;

    public static final String BARCODE_KEY = "BARCODE";
    private ScaleGestureDetector mScaleDetector;
    private Barcode barcodeResult;
    private List<Activo>activoList;

    private MenuItem item;
    private LinearLayout layoutFondo;
    private TextView txtCargando;

    ProgressBar progressActivity;
    private static final String TAG = "MainActivity";
    private FastScroller fastScroller;
    private RadioGroup groupFiltro;
    private RadioButton rbnTodos, rbnEmpresa, rbnDepartamento, rbnCentro;


    ActivoAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager lManager;


    public Principal() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view= inflater.inflate(R.layout.fragment_principal, container, false);
        setHasOptionsMenu(true);

        groupFiltro = (RadioGroup)view.findViewById(R.id.rbnFiltro);
        rbnTodos= (RadioButton) groupFiltro.getChildAt(0);
        rbnTodos.setChecked(true);
        rbnDepartamento = (RadioButton) groupFiltro.getChildAt(1);
        rbnEmpresa = (RadioButton) groupFiltro.getChildAt(2);
        rbnCentro = (RadioButton) groupFiltro.getChildAt(3);
        groupFiltro.setOnCheckedChangeListener(this);

        progressActivity = (ProgressBar)view.findViewById(R.id.progressActivity);

        layoutFondo=(LinearLayout)view.findViewById(R.id.layoutFondo);
        txtCargando=(TextView)view.findViewById(R.id.txtCargando);

        activoList=Controller.getDaoSession().getActivoDao().queryBuilder().orderAsc(ActivoDao.Properties.Descripcion).list();
        //recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);


        final List<Total>totalList=Controller.getDaoSession().getTotalDao().loadAll();
       if(totalList.isEmpty() || totalList.get(0).getTotal()==0){
            groupFiltro.setVisibility(View.GONE);
            habilitarControles();
            progressActivity.setVisibility(View.VISIBLE);
        }else{
            progressActivity.setVisibility(View.GONE);
            groupFiltro.setVisibility(View.VISIBLE);
            ocultarControles();
        }

        /**/
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        fastScroller = (FastScroller)view.findViewById(R.id.fastscroll);
        recyclerView.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new ActivoAdapter(activoList, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        fastScroller.setRecyclerView(recyclerView);

        //inicializarAdapter(activoList);

        adapter.notifyDataSetChanged();

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                */
                startScan();
            }
        });

        return  view;
    }

    private void inicializarAdapter(final List<Activo>activoList){
        final List<Total>totalList=Controller.getDaoSession().getTotalDao().loadAll();
        if(totalList.isEmpty() || totalList.get(0).getTotal()==0){
            habilitarControles();
            //progressActivity.setVisibility(View.VISIBLE);
        }else{
            //progressActivity.setVisibility(View.GONE);
            ocultarControles();
        }

        adapter = new ActivoAdapter(activoList, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        fastScroller.setRecyclerView(recyclerView);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        FragmentManager fm = getActivity().getFragmentManager();
        DialgoFilterData dialogfilterData = new DialgoFilterData();
        Bundle bundle = new Bundle();
        switch (i){
            case R.id.rbnTodos:
                //activoList.clear();
                activoList=Controller.getDaoSession().getActivoDao().queryBuilder().orderAsc(ActivoDao.Properties.Descripcion).list();
                inicializarAdapter(activoList);
                break;

            case R.id.rbnDepartamento:
                //DialgoFilterData dialogfilterData = new DialgoFilterData();
                bundle.putInt("value", 1);
                //bundle.putString("CADENA", "Hola StackOverflow");
                dialogfilterData.setArguments(bundle);
                dialogfilterData.setTargetFragment(this, 0);
                dialogfilterData.setCancelable(false);
                dialogfilterData.show(fm, "dialog");
                break;

            case R.id.rbnEmpresa:
                /*
                DialogFragment dialogFragment = new SearchDepSedeUbic();
                dialogFragment.show(getFragmentManager(), "dialogoRegistrar");
                */
                bundle.putInt("value", 2);
                //bundle.putString("CADENA", "Hola StackOverflow");
                dialogfilterData.setArguments(bundle);
                dialogfilterData.setTargetFragment(this, 0);
                dialogfilterData.setCancelable(false);
                dialogfilterData.show(fm, "dialog");
                break;
            case R.id.rbnCentro:
                //DialgoFilterData dialogfilterData = new DialgoFilterData();
                bundle.putInt("value", 3);
                //bundle.putString("CADENA", "Hola StackOverflow");
                dialogfilterData.setArguments(bundle);
                dialogfilterData.setTargetFragment(this, 0);
                dialogfilterData.setCancelable(false);
                dialogfilterData.show(fm, "dialog");
                break;

        }

    }

    private void ocultarControles() {
        progressActivity.setVisibility(View.GONE);
        txtCargando.setVisibility(View.GONE);
        layoutFondo.setVisibility(View.GONE);
        //item.setEnabled(true);
    }

    private void habilitarControles(){
        //item.setEnabled(false);
        progressActivity.setVisibility(View.VISIBLE);
        txtCargando.setVisibility(View.VISIBLE);
        layoutFondo.setVisibility(View.VISIBLE);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);

        item = menu.findItem(R.id.action_buscar);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                //adapter.setFilterAnimated(activoList);
                //recyclerView.scrollToPosition(0);
                adapter.setFilter(activoList);//filtro sin animacion
                return true;
            }

        });

        //return true;

        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onQueryTextChange(String newText) {
        try {
            List<Activo> listaFiltrada = filter(activoList, newText);
            //aca se envÃ­a la nueva lista al recycler
            adapter.setFilter(listaFiltrada);
            //adapter.setFilterAnimated(listaFiltrada);
            //recyclerView.scrollToPosition(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Activo> filter(List<Activo> lista, String query) {
        List<Activo> listaFiltrada = new ArrayList<Activo>();
        try {

            //aca se recibe el parametro de busqueda
            query = query.toLowerCase();
            for (Activo activo : lista) {
                //busqueda por descripcion
                String queryDescripcion = activo.getDescripcion().toLowerCase();
                //busqueda por codigo
                String queryCodigo = String.valueOf(activo.getCodigo()).toLowerCase();
                if (queryDescripcion.contains(query)) {
                    listaFiltrada.add(activo);
                } else if (queryCodigo.contains(query)) {
                    listaFiltrada.add(activo);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaFiltrada;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //item.setEnabled(false);
        //MenuItem item1=item;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_importar) {
            getActivity().finish();
            /*Controller.getDaoSession().deleteAll(Total.class);
            Controller.getDaoSession().deleteAll(Empresa.class);
            Controller.getDaoSession().deleteAll(Departamento.class);
            Controller.getDaoSession().deleteAll(Sede.class);
            Controller.getDaoSession().deleteAll(Ubicacion.class);
            Controller.getDaoSession().deleteAll(Catalogo.class);
            Controller.getDaoSession().deleteAll(Responsable.class);
            Controller.getDaoSession().deleteAll(CentroCosto.class);
            Controller.getDaoSession().deleteAll(CuentaContable.class);*/
            Controller.getDaoSession().deleteAll(Activo.class);
            Controller.getDaoSession().deleteAll(Historial.class);
            //Controller.getDaoSession().deleteAll(ActivoAll.class);

            startActivity(new Intent(getActivity(),MainActivity.class));
            //startActivity(new Intent(this,MainActivity.class));
        }else if(id==R.id.action_exportar){
            //crear fragment export activo
            ThreadWriteData threadWriteData=new ThreadWriteData(getActivity());
            threadWriteData.start();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BARCODE_KEY, barcodeResult);
        super.onSaveInstanceState(outState);
    }
    private void startScan() {

        mScaleDetector = new ScaleGestureDetector(getActivity(), new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
            }
            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                return true;
            }
        });

        /**
         * Build a new MaterialBarcodeScanner
         */
        final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                .withActivity(getActivity())
                .withEnableAutoFocus(true)
                .withBleepEnabled(true)
                .withBackfacingCamera()
                .withCenterTracker()
                //.withCenterTracker(R.drawable.common_full_open_on_phone, R.drawable.common_google_signin_btn_icon_dark)
                .withText("Scaneando Código de Barras")
                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                    @Override
                    public void onResult(Barcode barcode) {
                        barcodeResult = barcode;
                        //String texto=barcode.rawValue;
                        //result.setText(barcode.rawValue);
                        //List<Activo> activoList= Buscar.buscarBarras(barcode.rawValue);
                        if(!Buscar.buscarBarras(barcode.rawValue).isEmpty()){
                            Intent intent=new Intent(getActivity(),ActivoView.class);
                            //barcode.rawValue
                            intent.putExtra("activo", Buscar.buscarBarras(barcode.rawValue).get(0).getCodigo());
                            //intent.putExtra("activo", (Serializable) activoList);
                            startActivity(intent);
                            //startActivity(new Intent(getActivity(),BarScanner.class));
                        }else{
                               List<Total> total=Controller.getDaoSession().getTotalDao().loadAll();
                                if(total.get(0).getTotal()!=0){
                                    progressActivity.setVisibility(View.GONE);
                                }
                                if(buscarBarraCSV(total.get(0).getRuta(),barcode.rawValue)!=null) {
                                    Toast.makeText(getActivity(),"C.Barras. "+barcode.rawValue+" se encontró en la data pero aun falta cargar la información al dispositivo. Vuelve a consultar en unos segundos",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getActivity(),"C.Barras. "+barcode.rawValue+" no ubicado",Toast.LENGTH_SHORT).show();
                                }
                        }
                    }
                })
                .build();
        materialBarcodeScanner.startScan();
    }

    public String buscarBarraCSV( String path, String scanbarra){
        String cBarra=null;
        try{
        CsvReader csvReader=new CsvReader(path);

        csvReader.readHeaders();
            while (csvReader.readRecord()) {
                String barra = csvReader.get("Cod. Barras");
                if ((barra.equalsIgnoreCase(scanbarra))) {
                    //Toast.makeText(getActivity(),"C.B. "+barcode.rawValue+" se encontró en la data pero aun falta cargar la información al dispositivo. Vuleve a consultar en unos segundos",Toast.LENGTH_SHORT).show();
                   cBarra=barra;
                    break;
                }
            }
        }catch(Exception e){
            }
        return cBarra;
    }





    @Override
    public void onAddFilterDepSedeUbic(Departamento departamento, Sede sede, Ubicacion ubicacion) {

        if((departamento==null && sede==null && ubicacion!=null)|| (departamento!=null && sede!=null && ubicacion!=null)){
            //solo ubicacion
            activoList=filterUbicacion(ubicacion);
        }else if(departamento==null && sede!=null & ubicacion!=null){
            //sede y ubicacion
            activoList=filterSedeUbicacion(sede, ubicacion);
        }else if(departamento!=null && sede==null & ubicacion==null){
            //solo departamento
            activoList=filterDeapartamento(departamento);
        }else if(departamento!=null&& sede!=null & ubicacion==null ){
            //departamento y sede
            activoList=filterDepartamentoSede(departamento,sede);
        }else if(departamento==null && sede!=null && ubicacion==null){
            //solo sede
            activoList=filterSede(sede);
        }else{
            //todos null
            rbnTodos.setChecked(true);
        }

            inicializarAdapter(activoList);

    }

    @Override
    public void onAddFilterEmpCatalogo(Empresa empresa, Catalogo catalogo) {
        if(empresa!=null && catalogo==null){
            activoList=filterEmpresa(empresa);
        }else if(empresa==null & catalogo!=null ){
            activoList=filterCatalogo(catalogo);
            //Toast.makeText(getActivity(),"Seleccion:"+catalogo.getCatalogo(),Toast.LENGTH_SHORT).show();
        }else if(empresa!=null & catalogo!=null){
            activoList=filterEmpresaCatalogo(empresa, catalogo);
            //Toast.makeText(getActivity(),"Empresa:"+empresa.getEmpresa()+" Catalogo:"+catalogo.getCatalogo(),Toast.LENGTH_SHORT).show();
        }else{
            //todos null
            rbnTodos.setChecked(true);
        }
        inicializarAdapter(activoList);
    }

    @Override
    public void onAddFilterCentroCosto(CentroCosto centroCosto) {
        if(centroCosto!=null){
            activoList=filterCentroCosto(centroCosto);
        }else{
            //todos null
            rbnTodos.setChecked(true);
        }
        inicializarAdapter(activoList);
    }

    @Override
    public void onButtonCancel() {
        rbnTodos.setChecked(true);
    }

    private List<Activo> filterSede(Sede object) {
        QueryBuilder<Activo> qb = Controller.getDaoSession().getActivoDao().queryBuilder().orderAsc(ActivoDao.Properties.Descripcion);
        Join ubicacion = qb.join(ActivoDao.Properties.Ubicacion_id, Ubicacion.class);
        Join sede = qb.join(ubicacion, UbicacionDao.Properties.Sede_id, Sede.class, SedeDao.Properties.Id);
        sede.where(SedeDao.Properties.Id.eq(object.getId()));
        List<Activo> list = qb.list();
        return list;
    }

    private List<Activo> filterDeapartamento(Departamento object){
        QueryBuilder<Activo> qb = Controller.getDaoSession().getActivoDao().queryBuilder().orderAsc(ActivoDao.Properties.Descripcion);
        Join ubicacion = qb.join(ActivoDao.Properties.Ubicacion_id, Ubicacion.class);
        Join sede = qb.join(ubicacion, UbicacionDao.Properties.Sede_id, Sede.class, SedeDao.Properties.Id);
        Join departamento = qb.join(sede, SedeDao.Properties.Departamento_id, Departamento.class, DepartamentoDao.Properties.Id);
        departamento.where(DepartamentoDao.Properties.Id.eq(object.getId()));
        List<Activo> list = qb.list();

        return list;
    }

    private List<Activo> filterSedeUbicacion(Sede object1, Ubicacion object2){
        QueryBuilder<Activo> qb = Controller.getDaoSession().getActivoDao().queryBuilder().orderAsc(ActivoDao.Properties.Descripcion);
        Join ubicacion = qb.join(ActivoDao.Properties.Ubicacion_id, Ubicacion.class).where(UbicacionDao.Properties.Id.eq(object2.getId()));
        Join sede = qb.join(ubicacion, UbicacionDao.Properties.Sede_id, Sede.class, SedeDao.Properties.Id);
        sede.where(SedeDao.Properties.Id.eq(object1.getId()));
        List<Activo> list = qb.list();
        return list;
    }

    private List<Activo> filterUbicacion(Ubicacion object){
        List<Activo>activos=Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Ubicacion_id.eq(object.getId())).orderAsc(ActivoDao.Properties.Descripcion).list();
        return activos;
    }

    private  List<Activo> filterDepartamentoSede(Departamento object1, Sede object2){
        QueryBuilder<Activo> qb = Controller.getDaoSession().getActivoDao().queryBuilder().orderAsc(ActivoDao.Properties.Descripcion);
        Join ubicacion = qb.join(ActivoDao.Properties.Ubicacion_id, Ubicacion.class);
        Join sede = qb.join(ubicacion, UbicacionDao.Properties.Sede_id, Sede.class, SedeDao.Properties.Id).where(SedeDao.Properties.Id.eq(object2.getId()));
        Join departamento = qb.join(sede, SedeDao.Properties.Departamento_id, Departamento.class, DepartamentoDao.Properties.Id);
        departamento.where(DepartamentoDao.Properties.Id.eq(object1.getId()));
        List<Activo> list = qb.list();

        return list;
    }

    private List<Activo>filterEmpresa(Empresa object){
        QueryBuilder<Activo> qb = Controller.getDaoSession().getActivoDao().queryBuilder().orderAsc(ActivoDao.Properties.Descripcion);
        Join catalogo = qb.join(ActivoDao.Properties.Catalogo_id, Catalogo.class);
        Join empresa = qb.join(catalogo, CatalogoDao.Properties.Empresa_id, Empresa.class, EmpresaDao.Properties.Id);
        empresa.where(EmpresaDao.Properties.Id.eq(object.getId()));
        List<Activo> list = qb.list();
        //List<Activo>list=Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Empresa_id.eq(object.getId())).orderAsc(ActivoDao.Properties.Descripcion).list();
        return list;

    }

    private List<Activo>filterCatalogo(Catalogo object){
        /*QueryBuilder<Activo> qb = Controller.getDaoSession().getActivoDao().queryBuilder().orderAsc(ActivoDao.Properties.Descripcion);
        Join catalogo = qb.join(ActivoDao.Properties.Catalogo_id, Catalogo.class);
        catalogo.where(CatalogoDao.Properties.Id.eq(object.getId()));
        List<Activo> list = qb.list();*/

       List<Catalogo>catalogoList=Controller.getDaoSession().getCatalogoDao().queryBuilder().where(CatalogoDao.Properties.Catalogo.eq(object.getCatalogo())).list();

        List<Activo>activoList1=new ArrayList<>();
        //recorrer y obtener todos los catalogos con el nombre del objeto devuelto
        for(int i=0;i<catalogoList.size();i++){
            List<Activo>list=Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Catalogo_id.eq(catalogoList.get(i).getId())).list();
            activoList1.addAll(list);//agregar a la lista activo todas las lista de catalagos por empresas
        }
        return activoList1;
    }

    private List<Activo>filterEmpresaCatalogo(Empresa object1, Catalogo object2){
        //buscar el catalogo con el nomcatalogo y nomempresa real
        //object2 es un valor devuelto de la posicion 0 de una lista desde el jdialog (puede pertenecer a cualquier empresa)
        Catalogo newCatalogo=Buscar.buscarCatalogo(object2.getCatalogo(),object1.getEmpresa());

        QueryBuilder<Activo> qb = Controller.getDaoSession().getActivoDao().queryBuilder().orderAsc(ActivoDao.Properties.Descripcion);
        Join catalogo = qb.join(ActivoDao.Properties.Catalogo_id, Catalogo.class);
        catalogo.where(CatalogoDao.Properties.Id.eq(newCatalogo.getId()));
        Join empresa = qb.join(catalogo, CatalogoDao.Properties.Empresa_id, Empresa.class, EmpresaDao.Properties.Id);
        empresa.where(EmpresaDao.Properties.Id.eq(object1.getId()));
        List<Activo> list = qb.list();
        return list;
    }

    private List<Activo>filterCentroCosto(CentroCosto object){
        List<Activo> list=Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Centrocosto_id.eq(object.getId())).orderAsc(ActivoDao.Properties.Descripcion).list();
        return list;
    }

}

