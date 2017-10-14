package com.evertvd.bienes.vista.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.vista.activitys.CollageActivity;
import com.evertvd.bienes.vista.activitys.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewActivo extends Fragment implements View.OnClickListener{
    private TextView txtTipoActivo1;
    private TextView txtCodigo1, txtBarras1, txtDepartamento1, txtSede1, txtUbicacion1, txtCodCatalogo1, txtCatalogo1,
            txtDecripcion1, txtPlaca1, txtMarca1, txtModelo1, txtSerie1, txtCodCentro1, txtCentro1, txtResponsable1,
            txtEmpresa1, txtOC1,txtFactura1,txtFecCompra1;
    private ImageButton imgEstado1;
    private ImageButton  imgCamera;
    private String numActivo;
    private Activo activo;
    private View view;
    private FloatingActionButton fabEditar;

    public ViewActivo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_view_activo, container, false);
        setHasOptionsMenu(true);

        numActivo=getActivity().getIntent().getExtras().getString("activo");
        activo= Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Codigo.eq(getActivity().getIntent().getExtras().getString("activo"))).unique();

        Toast.makeText(getActivity(),activo.getDescripcion(),Toast.LENGTH_SHORT).show();

        fabEditar=(FloatingActionButton)view.findViewById(R.id.fabEditar);
        fabEditar.setOnClickListener(this);


        this.imgCamera = (ImageButton)view.findViewById(R.id.imgCamera);
        this.imgEstado1=(ImageButton)view.findViewById(R.id.imgEstado1);
        this.txtCodigo1 = (TextView) view. findViewById(R.id.edtCodigo1);
        this.txtBarras1 = (TextView)view. findViewById(R.id.edtBarras1);
        this.txtDepartamento1 = (TextView)view. findViewById(R.id.edtDepartamento1);
        this.txtSede1 = (TextView) view.findViewById(R.id.txtSede1);
        this.txtUbicacion1 = (TextView) view.findViewById(R.id.txtUbicacion1);
        //this.txtCodCatalogo1 = (TextView)view. findViewById(R.id.txtCodCatalogo1);
        this.txtCatalogo1 = (TextView)view. findViewById(R.id.txtCatalogo1);
        this.txtTipoActivo1 = (TextView)view. findViewById(R.id.txtTipoActivo1);
        this.txtDecripcion1 = (TextView) view.findViewById(R.id.txtDescipcion1);
        this.txtPlaca1 = (TextView)view. findViewById(R.id.txtPlaca1);
        this.txtMarca1 = (TextView) view.findViewById(R.id.txtMarca1);
        this.txtModelo1 = (TextView)view. findViewById(R.id.txtModelo1);
        this.txtSerie1 = (TextView)view. findViewById(R.id.txtSerie1);
        this.txtCodCentro1 = (TextView)view. findViewById(R.id.txtCodCentro1);
        this.txtCentro1 = (TextView) view.findViewById(R.id.txtCentro1);
        this.txtResponsable1 = (TextView) view.findViewById(R.id.txtResponsable1);
        this.txtEmpresa1=(TextView)view.findViewById(R.id.txtEmpresa1);
        this.txtOC1=(TextView)view.findViewById(R.id.txtOrdenCompra1);
        this.txtFactura1=(TextView)view.findViewById(R.id.txtFactura1);
        this.txtFecCompra1=(TextView)view.findViewById(R.id.txtFecha1);

        // Inflate the layout for this fragment
        imgCamera.setOnClickListener(this);

        cargarDatos();
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.fabEditar) {
            FragmentManager fragmentManager = getActivity().getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            EditActivo editActivo = new EditActivo();
            transaction.replace(R.id.content_activo,editActivo);
            transaction.addToBackStack(null);
            transaction.commit();

        }else if(v.getId()==R.id.imgCamera){
            Intent intent=new Intent(getActivity(),CollageActivity.class);
            intent.putExtra("activo",numActivo);
            startActivity(intent);
        }
    }

    private void cargarDatos() {
        txtDecripcion1.setText(activo.getDescripcion());
        txtCodigo1.setText(activo.getCodigo());
        txtBarras1.setText(activo.getCodigobarra());
        txtOC1.setText(activo.getOrdencompra());
        txtFactura1.setText(activo.getFactura());
        txtFecCompra1.setText(activo.getFechacompra());
        txtCatalogo1.setText(activo.getCatalogo().getCatalogo());
        txtEmpresa1.setText(activo.getEmpresa().getEmpresa());
        txtDepartamento1.setText(activo.getUbicacion().getSede().getDepartamento().getDepartamento());
        txtSede1.setText(activo.getUbicacion().getSede().getSede());
        txtUbicacion1.setText(activo.getUbicacion().getUbicacion());
        txtCodCentro1.setText(activo.getCentroCosto().getCodigo());
        txtCentro1.setText(activo.getCentroCosto().getCentro());
        txtResponsable1.setText(activo.getResponsable().getResponsable());
        txtPlaca1.setText(activo.getPlaca());
        txtMarca1.setText(activo.getMarca());
        txtModelo1.setText(activo.getModelo());
        txtSerie1.setText(activo.getSerie());


    }
}
