package com.evertvd.bienes.vista.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.Historial;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.modelo.dao.HistorialDao;
import com.evertvd.bienes.vista.activitys.CollageActivity;
import com.evertvd.bienes.vista.activitys.MainActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewActivo extends Fragment implements View.OnClickListener{
    private TextView txtTipoActivo;
    private TextView txtCodigo, txtBarras, txtDepartamento, txtSede, txtUbicacion, txtCodCatalogo, txtCatalogo,
            txtDecripcion, txtPlaca, txtMarca, txtModelo, txtSerie, txtCodCentro, txtCentro, txtResponsable,
            txtEmpresa, txtOC,txtFactura,txtFecCompra, txtFoto;
    private ImageButton imgEstado;
    private ImageButton  imgCamera;
    private String numActivo;
    private Activo activo;
    private View view;
    private FloatingActionButton fabEditar;


    public static final String TAG = "ViewActivo";

    private FragmentIterationListener mCallback = null;
    public interface FragmentIterationListener{
        public void onFragmentIteration(Bundle parameters);
    }

    public static ViewActivo newInstance(Bundle arguments){
        ViewActivo viewActivo = new ViewActivo();
        if(arguments != null){
            viewActivo.setArguments(arguments);
        }
        return viewActivo;
    }

    public ViewActivo() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mCallback = (FragmentIterationListener) activity;
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

        view= inflater.inflate(R.layout.fragment_view_activo, container, false);
        //setHasOptionsMenu(true);

        numActivo=getActivity().getIntent().getExtras().getString("activo");
        activo= Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Codigo.eq(getActivity().getIntent().getExtras().getString("activo"))).unique();

        List<Historial> historialList=Controller.getDaoSession().getHistorialDao().queryBuilder().where(HistorialDao.Properties.Activo_id.eq(activo.getId())).list();
        if(!historialList.isEmpty()){
          for (int i=0;i<historialList.size();i++){
              Log.e("hisotrial",historialList.get(i).getCampo_modificado());
          }
        }


        //Toast.makeText(getActivity(),activo.getDescripcion(),Toast.LENGTH_SHORT).show();

        fabEditar=(FloatingActionButton)view.findViewById(R.id.fabEditar);
        fabEditar.setOnClickListener(this);


        this.imgCamera = (ImageButton)view.findViewById(R.id.imgCamera);
        this.imgEstado=(ImageButton)view.findViewById(R.id.imgEstado);
        this.txtCodigo = (TextView) view. findViewById(R.id.txtCodigo);
        this.txtBarras = (TextView)view. findViewById(R.id.txtBarras);
        this.txtDepartamento = (TextView)view. findViewById(R.id.edtDepartamento);
        this.txtSede = (TextView) view.findViewById(R.id.txtSede);
        this.txtUbicacion = (TextView) view.findViewById(R.id.txtUbicacion);
        //this.txtCodCatalogo = (TextView)view. findViewById(R.id.txtCodCatalogo1);
        this.txtCatalogo = (TextView)view. findViewById(R.id.txtCatalogo);
        this.txtTipoActivo = (TextView)view. findViewById(R.id.txtTipoActivo);
        this.txtDecripcion = (TextView) view.findViewById(R.id.txtDescipcion);
        this.txtPlaca = (TextView)view. findViewById(R.id.txtPlaca);
        this.txtMarca = (TextView) view.findViewById(R.id.txtMarca);
        this.txtModelo = (TextView)view. findViewById(R.id.txtModelo);
        this.txtSerie = (TextView)view. findViewById(R.id.txtSerie);
        this.txtCodCentro = (TextView)view. findViewById(R.id.txtCodCentro);
        this.txtCentro = (TextView) view.findViewById(R.id.txtCentro);
        this.txtResponsable = (TextView) view.findViewById(R.id.txtResponsable);
        this.txtEmpresa=(TextView)view.findViewById(R.id.txtEmpresa);
        this.txtOC=(TextView)view.findViewById(R.id.txtOrdenCompra);
        this.txtFactura=(TextView)view.findViewById(R.id.txtFactura);
        this.txtFecCompra=(TextView)view.findViewById(R.id.txtFecha);
        this.txtFoto=(TextView)view.findViewById(R.id.txtFoto);

        // Inflate the layout for this fragment
        imgCamera.setOnClickListener(this);
        cargarDatos();
        return view;
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
        if(v.getId()==R.id.fabEditar) {
           /* FragmentManager fragmentManager = getActivity().getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            EditActivo editActivo = new EditActivo();
            transaction.replace(R.id.content_activo_view,editActivo);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(null);
            transaction.commit();*/


            Bundle arguments = new Bundle();
            arguments.putString("activo", numActivo);
            EditActivo fragment = EditActivo.newInstance(arguments);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            //ft.replace(R.id.content_activo_view, fragment, ViewActivo.TAG);
            ft.addToBackStack(null);
            ft.commit();



        }else if(v.getId()==R.id.imgCamera){
            Intent intent=new Intent(getActivity(),CollageActivity.class);
            intent.putExtra("activo",numActivo);
            startActivity(intent);

        }
    }

    private void cargarDatos() {
        txtDecripcion.setText(activo.getDescripcion());
        txtCodigo.setText(activo.getCodigo());
        txtBarras.setText(activo.getCodigobarra());
        txtOC.setText(activo.getOrdencompra());
        txtFactura.setText(activo.getFactura());
        txtFecCompra.setText(activo.getFechacompra());
        txtCatalogo.setText(activo.getCatalogo().getCatalogo());
        txtEmpresa.setText(activo.getCatalogo().getEmpresa().getEmpresa());
        txtDepartamento.setText(activo.getUbicacion().getSede().getDepartamento().getDepartamento());
        txtSede.setText(activo.getUbicacion().getSede().getSede());
        txtUbicacion.setText(activo.getUbicacion().getUbicacion());
        txtCodCentro.setText(activo.getCentroCosto().getCodigo());
        txtCentro.setText(activo.getCentroCosto().getCentro());
        txtResponsable.setText(activo.getResponsable().getResponsable());
        txtPlaca.setText(activo.getPlaca());
        txtMarca.setText(activo.getMarca());
        txtModelo.setText(activo.getModelo());
        txtSerie.setText(activo.getSerie());

        if(activo.getFoto().equalsIgnoreCase("si")){
            txtFoto.setText(activo.getFoto()+"|"+activo.getOrigenfoto());
        }else{
            txtFoto.setText(activo.getFoto());
        }

        if(activo.getEstado().equalsIgnoreCase("\u00FB")){
            imgEstado.setBackgroundColor(getResources().getColor(R.color.colorRed));
            imgEstado.setImageResource(R.drawable.ic_close_white);
        }


    }
}
