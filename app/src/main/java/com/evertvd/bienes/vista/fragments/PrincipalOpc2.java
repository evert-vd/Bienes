package com.evertvd.bienes.vista.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.csvreader.CsvReader;
import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.controlador.PaginationScrollListener;
import com.evertvd.bienes.controlador.RecyclerTouchListener;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.ActivoAll;
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
import com.evertvd.bienes.scannercode.MaterialBarcodeScanner;
import com.evertvd.bienes.scannercode.MaterialBarcodeScannerBuilder;
import com.evertvd.bienes.utils.Buscar;
import com.evertvd.bienes.vista.activitys.ActivoActivity;
import com.evertvd.bienes.vista.activitys.BarScanner;
import com.evertvd.bienes.vista.activitys.GrupoActivity;
import com.evertvd.bienes.vista.activitys.MainActivity;
import com.evertvd.bienes.vista.adapters.ActivoAdapterRV;
import com.evertvd.bienes.vista.adapters.PaginationAdapter;
import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrincipalOpc2 extends Fragment implements SearchView.OnQueryTextListener{
    private View view;

    public static final String BARCODE_KEY = "BARCODE";
    private ScaleGestureDetector mScaleDetector;
    private Barcode barcodeResult;
    private List<Activo>activoList;
    private RecyclerView recyclerView;
    private MenuItem item;
    private LinearLayout layoutFondo;
    private TextView txtCargando;

    ProgressBar progressActivity;
    private static final String TAG = "MainActivity";
    ActivoAdapterRV adapter;
    private FastScroller fastScroller;

    public PrincipalOpc2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view= inflater.inflate(R.layout.fragment_principal_opc2, container, false);
        setHasOptionsMenu(true);


        progressActivity = (ProgressBar)view.findViewById(R.id.progressActivity);
        fastScroller = (FastScroller)view.findViewById(R.id.fastscroll);
        layoutFondo=(LinearLayout)view.findViewById(R.id.layoutFondo);
        txtCargando=(TextView)view.findViewById(R.id.txtCargando);

        activoList=Controller.getDaoSession().getActivoDao().loadAll();
        final List<Total>totalList=Controller.getDaoSession().getTotalDao().loadAll();
        if(totalList.isEmpty() || totalList.get(0).getTotal()==0){
            habilitarControles();
            //progressActivity.setVisibility(View.VISIBLE);
        }else{
            //progressActivity.setVisibility(View.GONE);
            ocultarControles();
        }


        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);


        adapter = new ActivoAdapterRV(activoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Activo activo = activoList.get(position);
                if(totalList.get(0).getTotal()!=0){
                    Intent intent=new Intent(getActivity(),ActivoActivity.class);
                    intent.putExtra("activo",activo.getCodigo());
                    startActivity(intent);
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        fastScroller.setRecyclerView(recyclerView);

        //adapter.notifyDataSetChanged();

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
                //adapter.setFilter(productoList);
                return true;
            }
        });

        //return true;

        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        item.setEnabled(false);
        //MenuItem item1=item;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cargar) {
            getActivity().finish();
            Controller.getDaoSession().deleteAll(Total.class);
            Controller.getDaoSession().deleteAll(Empresa.class);
            Controller.getDaoSession().deleteAll(Departamento.class);
            Controller.getDaoSession().deleteAll(Sede.class);
            Controller.getDaoSession().deleteAll(Ubicacion.class);
            Controller.getDaoSession().deleteAll(Catalogo.class);
            Controller.getDaoSession().deleteAll(Responsable.class);
            Controller.getDaoSession().deleteAll(CentroCosto.class);
            Controller.getDaoSession().deleteAll(CuentaContable.class);
            Controller.getDaoSession().deleteAll(Activo.class);
            Controller.getDaoSession().deleteAll(Historial.class);
            Controller.getDaoSession().deleteAll(ActivoAll.class);
            startActivity(new Intent(getActivity(),MainActivity.class));
            //startActivity(new Intent(this,MainActivity.class));
        }else if(id==R.id.action_settings){
            startActivity(new Intent(getActivity(), GrupoActivity.class));
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
                .withText("Buscando Código de Barras")
                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                    @Override
                    public void onResult(Barcode barcode) {
                        barcodeResult = barcode;
                        //String texto=barcode.rawValue;
                        //result.setText(barcode.rawValue);
                        //List<Activo> activoList= Buscar.buscarBarras(barcode.rawValue);
                        if(!Buscar.buscarBarras(barcode.rawValue).isEmpty()){
                            Intent intent=new Intent(getActivity(),ActivoActivity.class);
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
                                    Toast.makeText(getActivity(),"C.B. "+barcode.rawValue+" se encontró en la data pero aun falta cargar la información al dispositivo. Vuelve a consultar en unos segundos",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getActivity(),"C.B. "+barcode.rawValue+" no ubicado",Toast.LENGTH_SHORT).show();
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}

