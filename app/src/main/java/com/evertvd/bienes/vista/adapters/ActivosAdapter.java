package com.evertvd.bienes.vista.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.evertvd.bienes.R;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.vista.activitys.CollageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evertvd on 19/09/2017.
 */

public class ActivosAdapter extends RecyclerView.Adapter<ActivosAdapter.ViewHolder> {

    private Context contexto;
    private List<Activo> activoList;



    public ActivosAdapter(List<Activo> activoList, Context contexto) {
        this.contexto = contexto;
        this.activoList = activoList;

    }




    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        EditText txtCodigo, txtBarras, txtDepartamento, txtSede, txtUbicacion, txtCodCatalogo, txtCatalogo,
                txtDecripcion, txtPlaca, txtMarca, txtModelo, txtSerie, txtCodCentro, txtCentro, txtResponsable,
                txtEmpresa, txtOC,txtFactura,txtFecCompra;

        TextView txtTipoActivo1;
        TextView txtCodigo1, txtBarras1, txtDepartamento1, txtSede1, txtUbicacion1, txtCodCatalogo1, txtCatalogo1,
                txtDecripcion1, txtPlaca1, txtMarca1, txtModelo1, txtSerie1, txtCodCentro1, txtCentro1, txtResponsable1,
                txtEmpresa1, txtOC1,txtFactura1,txtFecCompra1;
        ImageButton imgEstado1;
        private String codigoBarras;

        private ImageButton imgCamera;



        public ViewHolder(View v) {
            super(v);

            this.imgCamera = (ImageButton) v.findViewById(R.id.imgCamera);
            this.imgEstado1=(ImageButton)v.findViewById(R.id.imgEstado1);

            this.txtCodigo1 = (EditText) v.findViewById(R.id.edtCodigo1);
            this.txtBarras1 = (EditText) v.findViewById(R.id.edtBarras1);
            this.txtDepartamento1 = (EditText) v.findViewById(R.id.edtDepartamento);
            this.txtSede1 = (EditText) v.findViewById(R.id.txtSede);
            this.txtUbicacion1 = (EditText) v.findViewById(R.id.txtUbicacion);
            this.txtCodCatalogo1 = (EditText) v.findViewById(R.id.txtCodCatalogo);
            this.txtCatalogo1 = (EditText) v.findViewById(R.id.txtCatalogo);
            this.txtTipoActivo1 = (TextView) v.findViewById(R.id.txtTipoActivo);
            this.txtDecripcion1 = (EditText) v.findViewById(R.id.txtDescipcion);
            this.txtPlaca1 = (EditText) v.findViewById(R.id.txtPlaca);
            this.txtMarca1 = (EditText) v.findViewById(R.id.txtMarca);
            this.txtModelo1 = (EditText) v.findViewById(R.id.txtModelo);
            this.txtSerie1 = (EditText) v.findViewById(R.id.txtSerie);
            this.txtCodCentro1 = (EditText) v.findViewById(R.id.txtCodCentro);
            this.txtCentro1 = (EditText) v.findViewById(R.id.txtCentro);
            this.txtResponsable1 = (EditText) v.findViewById(R.id.txtResponsable);

            this.txtEmpresa1=(EditText)v.findViewById(R.id.txtEmpresa);
            this.txtOC1=(EditText)v.findViewById(R.id.txtOrdenCompra);
            this.txtFactura1=(EditText)v.findViewById(R.id.txtFactura);
            this.txtFecCompra1=(EditText)v.findViewById(R.id.txtFecha);

            /*
             this.imgEstado=(ImageButton)v.findViewById(R.id.imgEstado);

            this.txtCodigo = (EditText) v.findViewById(R.id.edtCodigo);
            this.txtBarras = (EditText) v.findViewById(R.id.edtBarras);
            this.txtDepartamento = (EditText) v.findViewById(R.id.edtDepartamento);
            this.txtSede = (EditText) v.findViewById(R.id.txtSede);
            this.txtUbicacion = (EditText) v.findViewById(R.id.txtUbicacion);
            this.txtCodCatalogo = (EditText) v.findViewById(R.id.txtCodCatalogo);
            this.txtCatalogo = (EditText) v.findViewById(R.id.txtCatalogo);
            this.txtTipoActivo = (TextView) v.findViewById(R.id.txtTipoActivo);
            this.txtDecripcion = (EditText) v.findViewById(R.id.txtDescipcion);
            this.txtPlaca = (EditText) v.findViewById(R.id.txtPlaca);
            this.txtMarca = (EditText) v.findViewById(R.id.txtMarca);
            this.txtModelo = (EditText) v.findViewById(R.id.txtModelo);
            this.txtSerie = (EditText) v.findViewById(R.id.txtSerie);
            this.txtCodCentro = (EditText) v.findViewById(R.id.txtCodCentro);
            this.txtCentro = (EditText) v.findViewById(R.id.txtCentro);
            this.txtResponsable = (EditText) v.findViewById(R.id.txtResponsable);

            this.txtEmpresa=(EditText)v.findViewById(R.id.txtEmpresa);
            this.txtOC=(EditText)v.findViewById(R.id.txtOrdenCompra);
            this.txtFactura=(EditText)v.findViewById(R.id.txtFactura);
            this.txtFecCompra=(EditText)v.findViewById(R.id.txtFecha);
             */

        }

        }


        // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activo_search_adapter, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        //ViewHolder vh = new ViewHolder(v);
        //return vh;
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //Tener cuidado con los tipos de datos que se almaccena, debemos parsear todo a Sring

        try {
            holder.txtTipoActivo1.setText(String.valueOf(tipoActivo(activoList.get(position).getTipo())));
            holder.txtDecripcion.setText(String.valueOf(activoList.get(position).getDescripcion()));
            if(activoList.get(position).getEstado()=="\u00fb"){
                holder.imgEstado1.setImageResource(R.drawable.ic_close_white);
                holder.imgEstado1.setBackgroundColor(Color.RED);
            }else{
                holder.imgEstado1.setImageResource(R.drawable.ic_check);
                holder.imgEstado1.setBackgroundColor(Color.GREEN);
            }

            holder.txtCodigo.setText(String.valueOf(activoList.get(position).getCodigo()));
            holder.txtBarras.setText(String.valueOf(activoList.get(position).getCodigobarra()));
            holder.txtDepartamento.setText(String.valueOf(activoList.get(position).getUbicacion().getSede().getDepartamento().getDepartamento()));
            holder.txtSede.setText(String.valueOf(activoList.get(position).getUbicacion().getSede().getSede()));
            holder.txtUbicacion.setText(String.valueOf(activoList.get(position).getUbicacion().getUbicacion()));
            holder.txtCodCatalogo.setText(String.valueOf(activoList.get(position).getCatalogo().getCodigo()));
            holder.txtCatalogo.setText(String.valueOf(activoList.get(position).getCatalogo().getCatalogo()));
            holder.txtEmpresa.setText(String.valueOf(activoList.get(position).getCatalogo().getEmpresa().getEmpresa()));
            holder.txtPlaca.setText(String.valueOf(activoList.get(position).getMarca()));
            holder.txtMarca.setText(String.valueOf(activoList.get(position).getMarca()));
            holder.txtModelo.setText(String.valueOf(activoList.get(position).getModelo()));
            holder.txtSerie.setText(String.valueOf(activoList.get(position).getSerie()));
            holder.txtCodCentro.setText(String.valueOf(activoList.get(position).getCentroCosto().getCodigo()));
            holder.txtCentro.setText(String.valueOf(activoList.get(position).getCentroCosto().getCentro()));
            holder.txtResponsable.setText(String.valueOf(activoList.get(position).getResponsable().getResponsable()));

            //holder.txtEmpresa.setText(String.valueOf(activoList.get(position).getEmpresa()));
            holder.txtOC.setText(String.valueOf(activoList.get(position).getOrdencompra()));
            holder.txtFactura.setText(String.valueOf(activoList.get(position).getFactura()));
            holder.txtFecCompra.setText(String.valueOf(activoList.get(position).getFechacompra()));

            holder.imgCamera.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(contexto, CollageActivity.class);
                    intent.putExtra("barras", activoList.get(position).getCodigobarra());
                    intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    contexto.startActivity(intent);
                }
            });


        } catch (Exception e) {
            //Log.e("Error", e.getMessage().toString());
            //Toast.makeText(contexto, "Error al cargar los datos al adaptador", Toast.LENGTH_SHORT).show();
        }
    }

    private int estadoActivo(String estado) {
        String activo="\u00fc";
        String baja="\u00fb";
        int estadoActivo=1;
        if(estado.equalsIgnoreCase(activo)){
            estadoActivo=1;
            Toast.makeText(contexto,"activo",Toast.LENGTH_SHORT).show();
        }else if(estado.equalsIgnoreCase(baja)){
            estadoActivo=-1;
            Toast.makeText(contexto,"baja",Toast.LENGTH_SHORT).show();
        }
        return estadoActivo;
    }

    private String tipoActivo(String tipoActivo) {
        if(tipoActivo.equalsIgnoreCase("bien de control")){
            return "BC";
        }else if(tipoActivo.equalsIgnoreCase("componente")){
            return "CO";
        }else if(tipoActivo.equalsIgnoreCase("costos vinculados")){
            return "CV";
        }else if(tipoActivo.equalsIgnoreCase("mejora")){
            return "ME";
        }else if(tipoActivo.equalsIgnoreCase("revaluacion")) {
            return "RV";
        }else{
            return "AF";
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //Log.e(String.valueOf(items.size()),"errpr");
        return activoList.size();

    }

    //metodo que asigna la nueva lista filtrada al recycler
    public void setFilter(List<Activo> activoList) {
        this.activoList = new ArrayList<>();
        this.activoList.addAll(activoList);
        notifyDataSetChanged();

    }



}
