package com.evertvd.bienes.vista.fragments;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.Catalogo;
import com.evertvd.bienes.modelo.CentroCosto;
import com.evertvd.bienes.modelo.Departamento;
import com.evertvd.bienes.modelo.Historial;
import com.evertvd.bienes.modelo.Responsable;
import com.evertvd.bienes.modelo.Sede;
import com.evertvd.bienes.modelo.Ubicacion;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.modelo.dao.CatalogoDao;
import com.evertvd.bienes.utils.Buscar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditActivo extends Fragment implements View.OnClickListener {

    private View view;
    private String numActivo;
    private Activo activo;

    private AutoCompleteTextView txtDepartamento, txtCatalogo, txtSede, txtUbicacion, txtCentro, txtResponsable, txtPlaca, txtMarca, txtModelo;
    private EditText txtDescripcion, txtCodigo, txtBarras, txtOC, txtFactura, txtFecha, txtEmpresa, txtCodCentro, txtSerie;
    private FloatingActionButton fabGuardar;
    private List<String>camposModificados;


    public static final String TAG = "EditActivo";

    private EditActivo.FragmentIterationListener mCallback = null;
    public interface FragmentIterationListener{
        public void onFragmentIteration(Bundle parameters);
    }

    public static EditActivo newInstance(Bundle arguments){
        EditActivo editActivo = new EditActivo();
        if(arguments != null){
            editActivo.setArguments(arguments);
        }
        return editActivo;
    }

    public EditActivo() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mCallback = (EditActivo.FragmentIterationListener) activity;
        }catch(Exception ex){
            Log.e("ExampleFragment", "El Activity debe implementar la interfaz FragmentIterationListener");
        }
    }

    //El Fragment ha sido creado
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_edit_activo, container, false);
        camposModificados=new ArrayList<>();
        //numActivo=getActivity().getIntent().getExtras().getString("activo");
        activo= Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Codigo.eq(getActivity().getIntent().getExtras().getString("activo"))).unique();

        fabGuardar=(FloatingActionButton)view.findViewById(R.id.fabGuardar);
        fabGuardar.setOnClickListener(this);

        txtDescripcion =(EditText)view.findViewById(R.id.txtDescipcion);
        txtDescripcion.setText(activo.getDescripcion());

        txtCodigo=(EditText)view.findViewById(R.id.txtCodigo);
        txtCodigo.setText(activo.getCodigo());

        txtBarras=(EditText)view.findViewById(R.id.txtBarras);
        txtBarras.setText(activo.getCodigobarra());

        txtOC=(EditText)view.findViewById(R.id.txtOrdenCompra);
        txtOC.setText(activo.getOrdencompra());

        txtFactura=(EditText)view.findViewById(R.id.txtFactura);
        txtFactura.setText(activo.getFactura());

        txtFecha=(EditText)view.findViewById(R.id.txtFecha);
        txtFecha.setText(activo.getFechacompra());

        txtEmpresa=(EditText)view.findViewById(R.id.txtEmpresa);
        txtEmpresa.setText(activo.getCatalogo().getEmpresa().getEmpresa());

        txtCodCentro=(EditText)view.findViewById(R.id.txtCodCentro);
        txtCodCentro.setText(activo.getCentroCosto().getCodigo());

        txtSerie=(EditText)view.findViewById(R.id.txtSerie);
        txtSerie.setText(activo.getSerie());

        txtCatalogo=(AutoCompleteTextView)view.findViewById(R.id.txtCatalogo);
        txtCatalogo.setText(activo.getCatalogo().getCatalogo());

        txtSede=(AutoCompleteTextView)view.findViewById(R.id.txtSede);
        txtSede.setText(activo.getUbicacion().getSede().getSede());

        txtUbicacion=(AutoCompleteTextView)view.findViewById(R.id.txtUbicacion);
        txtUbicacion.setText(activo.getUbicacion().getUbicacion());

        txtResponsable =(AutoCompleteTextView)view.findViewById(R.id.txtResponsable);
        txtResponsable.setText(activo.getResponsable().getResponsable());

        txtPlaca=(AutoCompleteTextView)view.findViewById(R.id.txtPlaca);
        txtPlaca.setText(activo.getPlaca());

        txtMarca=(AutoCompleteTextView)view.findViewById(R.id.txtMarca);
        txtMarca.setText(activo.getMarca());

        txtModelo=(AutoCompleteTextView)view.findViewById(R.id.txtModelo);
        txtModelo.setText(activo.getModelo());

        txtCentro=(AutoCompleteTextView) view.findViewById(R.id.txtCentro);
        txtCentro.setText(activo.getCentroCosto().getCentro());

        txtDepartamento=(AutoCompleteTextView)view.findViewById(R.id.txtDepartamento);
        txtDepartamento.setText(activo.getUbicacion().getSede().getDepartamento().getDepartamento());


        List<Departamento> departamentoList= Controller.getDaoSession().getDepartamentoDao().loadAll();
        List<Catalogo>catalogoList=Controller.getDaoSession().getCatalogoDao().queryBuilder().where(CatalogoDao.Properties.Empresa_id.eq(activo.getCatalogo().getEmpresa_id())).list();
        List<Sede>sedeList=Controller.getDaoSession().getSedeDao().loadAll();
        List<Ubicacion>ubicacionList=Controller.getDaoSession().getUbicacionDao().loadAll();
        List<CentroCosto>centroCostoList=Controller.getDaoSession().getCentroCostoDao().loadAll();
        List<Responsable>responsableList=Controller.getDaoSession().getResponsableDao().loadAll();

        ArrayAdapter<Departamento> adapterDepartamento=new ArrayAdapter<Departamento>(getActivity(),android.R.layout.simple_list_item_1,departamentoList);
        txtDepartamento.setThreshold(1);
        txtDepartamento.setAdapter(adapterDepartamento);
        //txtDepartamento.setTextColor(Color.BLUE);

        ArrayAdapter<Catalogo> adapterCatalogo=new ArrayAdapter<Catalogo>(getActivity(),android.R.layout.simple_list_item_1,catalogoList);
        txtCatalogo.setThreshold(1);
        txtCatalogo.setAdapter(adapterCatalogo);

        ArrayAdapter<Sede> adapterSede=new ArrayAdapter<Sede>(getActivity(),android.R.layout.simple_list_item_1,sedeList);
        txtSede.setThreshold(1);
        txtSede.setAdapter(adapterSede);

        ArrayAdapter<Ubicacion> adapterUbicacion=new ArrayAdapter<Ubicacion>(getActivity(),android.R.layout.simple_list_item_1,ubicacionList);
        txtUbicacion.setThreshold(1);
        txtUbicacion.setAdapter(adapterUbicacion);

        ArrayAdapter<CentroCosto> adapterCentroCosto=new ArrayAdapter<CentroCosto>(getActivity(),android.R.layout.simple_list_item_1,centroCostoList);
        txtCentro.setThreshold(1);
        txtCentro.setAdapter(adapterCentroCosto);

        ArrayAdapter<Responsable> adapterResponsable=new ArrayAdapter<Responsable>(getActivity(),android.R.layout.simple_list_item_1,responsableList);
        txtResponsable.setThreshold(1);
        txtResponsable.setAdapter(adapterResponsable);

        //ArrayAdapter<Activo> adapterActivo=new ArrayAdapter<Activo>(getActivity(),android.R.layout.select_dialog_item,ac);
        //txtDepartamento.setThreshold(1);
        //txtDepartamento.setAdapter(adapter);

        return  view;
    }

    //La vista de layout ha sido creada y ya está disponible
    //Fijar todos los parámetros que queramos de nuestras vistas y restaurar estados en onViewCreated():
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //La vista ha sido creada y cualquier configuración guardada está cargada
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    //El Activity que contiene el Fragment ha terminado su creación
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);//Indicamos que este Fragment tiene su propio menu de opciones
    }

    //El Fragment ha sido quitado de su Activity y ya no está disponible
    //Eliminar la referencia al Callback durante onDetach():
    @Override
    public void onDetach() {
        mCallback = null;

        super.onDetach();
    }


    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.fabGuardar){
            String descripcion=txtDescripcion.getText().toString().trim().toUpperCase();
            String barra=txtBarras.getText().toString().trim().toUpperCase();
            String catalogo=txtCatalogo.getText().toString().trim().toUpperCase();
            String departamento=txtDepartamento.getText().toString().trim().toUpperCase();
            String sede=txtSede.getText().toString().trim().toUpperCase();
            String ubicacion=txtUbicacion.getText().toString().trim().toUpperCase();
            String centro=txtCentro.getText().toString().trim().toUpperCase();
            String responsable=txtResponsable.getText().toString().trim().toUpperCase();
            String placa=txtPlaca.getText().toString().trim().toUpperCase();
            String marca=txtMarca.getText().toString().trim().toUpperCase();
            String modelo=txtModelo.getText().toString().trim().toUpperCase();
            String serie=txtSerie.getText().toString().trim().toUpperCase();

            //guardar cambios
            if(editDescripcion(descripcion)||editBarras(barra)||editCatalogo(catalogo)||editUbicacion(ubicacion)||editCentro(centro)||editResponsable(responsable)||editPlaca(placa)||editMarca(marca)||editModelo(modelo)||editSerie(serie)){
               if(editDescripcion(descripcion)){
                   camposModificados.add("descripcion");
               }
                if(editBarras(barra)){
                    camposModificados.add("barra");
                }

                if(editCatalogo(catalogo)){
                    camposModificados.add("catalogo");
                }

                if(editSerie(serie)){
                    camposModificados.add("serie");
                }

                if(editModelo(modelo)){
                    camposModificados.add("modelo");
                }

                if(editCentro(centro)){
                    camposModificados.add("centro");
                }

                if(editMarca(marca)){
                    camposModificados.add("marca");
                }

                if(editPlaca(placa)){
                    camposModificados.add("placa");
                }

                if(editResponsable(responsable)){
                    camposModificados.add("responsable");
                }

                if(editUbicacion(ubicacion)){
                    camposModificados.add("ubicacion");
                }

                actualizarHistorialCambios();
                Bundle arguments = new Bundle();
                arguments.putString("activo", numActivo);

                ViewActivo fragment = ViewActivo.newInstance(arguments);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                //ft.replace(R.id.content_activo_view, fragment, EditActivo.TAG);
                //ft.addToBackStack(null);
                ft.commit();


                Toast.makeText(getActivity(),"Cambio Realizado, se actualizó la información", Toast.LENGTH_SHORT).show();
                //Log.e("sede", String.valueOf(activo.getUbicacion().getSede().getDepartamento().getDepartamento()));
            }else{
                Toast.makeText(getActivity(),"Ningún Cambio Realizado", Toast.LENGTH_SHORT).show();
            }


        }



    }

    private boolean editDescripcion(String texto){
        boolean estado=false;
        if(!activo.getDescripcion().equalsIgnoreCase(texto)){
            activo.setDescripcion(texto);
            Controller.getDaoSession().getActivoDao().update(activo);
            estado= true;
        }
        return estado;
    }

    private boolean editBarras(String texto){
        boolean estado=false;
        if(!activo.getCodigobarra().equalsIgnoreCase(texto)){
            activo.setCodigobarra(texto);
            Controller.getDaoSession().getActivoDao().update(activo);

            estado=true;

        }
        return estado;
    }

    private boolean editCatalogo(String texto){
        boolean estado=false;
        if(!activo.getCatalogo().getCatalogo().equalsIgnoreCase(texto)) {
            if (Buscar.buscarCatalogo(texto, txtEmpresa.getText().toString())== null) {
                crearNuevoCatalogo(texto);
            }
            activo.setCatalogo_id(Buscar.buscarCatalogo(texto, txtEmpresa.getText().toString()).getId());
            Controller.getDaoSession().getActivoDao().update(activo);

            estado=true;
        }
        return estado;
    }



    private boolean editUbicacion (String texto){
        boolean estado=false;
        if(!activo.getUbicacion().getUbicacion().equalsIgnoreCase(texto)){
            if(Buscar.buscarUbicacion(texto)==null){
                crearUbicacion(texto);
            }
            activo.setUbicacion_id(Buscar.buscarUbicacion(texto).getId());
            Controller.getDaoSession().getActivoDao().update(activo);

            estado= true;
        }
        return estado;
    }

    private boolean editCentro (String texto){
        boolean estado=false;
        if(!activo.getCentroCosto().getCentro().equalsIgnoreCase(texto)){
            if(Buscar.buscarCentro(texto)==null){
                crearCentro(texto);
            }
            activo.setCentrocosto_id(Buscar.buscarCentro(texto).getId());
            Controller.getDaoSession().getActivoDao().update(activo);

            estado= true;
        }
        return estado;
    }

    private boolean editResponsable (String texto){
        boolean estado=false;
        if(!activo.getResponsable().getResponsable().equalsIgnoreCase(texto)){
            if(Buscar.buscarResponsable(texto)==null){
                crearResponsable(texto);
            }
            activo.setResponsable_id(Buscar.buscarResponsable(texto).getId());
            Controller.getDaoSession().getActivoDao().update(activo);

            estado= true;
        }
        return estado;
    }

    private boolean editPlaca(String texto){
        boolean estado=false;
        if(!activo.getPlaca().equalsIgnoreCase(texto)){
            activo.setPlaca(texto);
            Controller.getDaoSession().getActivoDao().update(activo);
            estado= true;
        }
        return estado;
    }

    private boolean editMarca(String texto){
        boolean estado=false;
        if(!activo.getMarca().equalsIgnoreCase(texto)){
            activo.setMarca(texto);
            Controller.getDaoSession().getActivoDao().update(activo);

            estado= true;
        }
        return estado;
    }

    private boolean editModelo (String texto){
        boolean estado=false;
        if(!activo.getModelo().equalsIgnoreCase(texto)){
            activo.setModelo(texto);
            Controller.getDaoSession().getActivoDao().update(activo);

            estado= true;
        }
        return estado;
    }

    private boolean editSerie(String texto){
        boolean estado=false;
        if(!activo.getSerie().equalsIgnoreCase(texto)){
            activo.setSerie(texto);
            Controller.getDaoSession().getActivoDao().update(activo);

            estado= true;
        }
        return estado;
    }




    private void crearNuevoCatalogo(String texto){
        Catalogo catalogo=new Catalogo();
        catalogo.setCatalogo(texto);
        catalogo.setEmpresa_id(Buscar.buscarEmpresa(txtEmpresa.getText().toString()).getId());
        Controller.getDaoSession().getCatalogoDao().insert(catalogo);

    }


    private void crearUbicacion(String texto){
        Ubicacion ubicacion=new Ubicacion();
        ubicacion.setUbicacion(texto);
        ubicacion.setSede_id(Buscar.buscarSede(txtSede.getText().toString()).getId());
        Controller.getDaoSession().getUbicacionDao().insert(ubicacion);
    }

    private void crearCentro(String texto){
        CentroCosto centroCosto=new CentroCosto();
        centroCosto.setCentro(texto);
        Controller.getDaoSession().getCentroCostoDao().insert(centroCosto);
    }

    private void crearResponsable(String texto){
        Responsable responsable=new Responsable();
        responsable.setResponsable(texto);
        Controller.getDaoSession().getResponsableDao().insert(responsable);
    }

    private void actualizarHistorialCambios(){
        if(!camposModificados.isEmpty()){
            for (int i=0;i<camposModificados.size();i++){
                if(Buscar.buscarHistorial(camposModificados.get(i),activo.getId())==null){
                    Historial historial=new Historial();
                    historial.setActivo_id(activo.getId());
                    historial.setCampo_modificado(camposModificados.get(i));
                    historial.setFecha_modificacion("");
                    Controller.getDaoSession().getHistorialDao().insert(historial);
                }
        }

            }else{
            Toast.makeText(getActivity(),"Esta lista está vacía",Toast.LENGTH_SHORT).show();
        }
    }

}
