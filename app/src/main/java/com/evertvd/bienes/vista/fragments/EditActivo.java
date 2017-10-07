package com.evertvd.bienes.vista.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
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
import com.evertvd.bienes.modelo.Responsable;
import com.evertvd.bienes.modelo.Sede;
import com.evertvd.bienes.modelo.Ubicacion;
import com.evertvd.bienes.modelo.dao.ActivoDao;

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

    public EditActivo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_edit_activo, container, false);

        numActivo=getActivity().getIntent().getExtras().getString("activo");
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
        List<Catalogo>catalogoList=Controller.getDaoSession().getCatalogoDao().loadAll();
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
            if(editDescripcion(descripcion)||editBarras(barra)||editDepartamento(departamento)||editCatalogo(catalogo)||editSede(sede)||editUbicacion(ubicacion)||editCentro(centro)||editREsponsable(responsable)||editPlaca(placa)||editMarca(marca)||editModelo(modelo)||editSerie(serie)){
                Toast.makeText(getActivity(),"Cambio Realizado, se actualizó la información", Toast.LENGTH_SHORT).show();
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
        if(!activo.getCatalogo().getCatalogo().equalsIgnoreCase(texto)){
            activo.getCatalogo().setCatalogo(texto);
            Controller.getDaoSession().getActivoDao().update(activo);
            estado= true;
        }
        return estado;
    }

    private boolean editDepartamento (String texto){
        boolean estado=false;
        if(!activo.getUbicacion().getSede().getDepartamento().getDepartamento().equalsIgnoreCase(texto)){
            activo.getUbicacion().getSede().getDepartamento().setDepartamento(texto);
            Controller.getDaoSession().getActivoDao().update(activo);
            estado= true;
        }
        return estado;
    }

    private boolean editSede (String texto){
        boolean estado=false;
        if(!activo.getUbicacion().getSede().getSede().equalsIgnoreCase(texto)){
            activo.getUbicacion().getSede().setSede(texto);
            Controller.getDaoSession().getActivoDao().update(activo);
            estado= true;
        }
        return estado;
    }

    private boolean editUbicacion (String texto){
        boolean estado=false;
        if(!activo.getUbicacion().getUbicacion().equalsIgnoreCase(texto)){
            activo.getUbicacion().setUbicacion(texto);
            Controller.getDaoSession().getActivoDao().update(activo);
            estado= true;
        }
        return estado;
    }

    private boolean editCentro (String texto){
        boolean estado=false;
        if(!activo.getCentroCosto().getCentro().equalsIgnoreCase(texto)){
            activo.getCentroCosto().setCentro(texto);
            Controller.getDaoSession().getActivoDao().update(activo);
            estado= true;
        }
        return estado;
    }

    private boolean editREsponsable (String texto){
        boolean estado=false;
        if(!activo.getResponsable().getResponsable().equalsIgnoreCase(texto)){
            activo.getResponsable().setResponsable(texto);
            Controller.getDaoSession().getActivoDao().update(activo);
            estado= true;
        }
        return estado;
    }

    private boolean editPlaca(String texto){
        boolean estado=false;
        if(!activo.getDescripcion().equalsIgnoreCase(texto)){
            activo.setDescripcion(texto);
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

}
