package com.evertvd.bienes.vista.adapters;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.evertvd.bienes.R;
import com.evertvd.bienes.modelo.Activo;

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
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private TextInputLayout tilCodigo,tilBarras,tilDepartamento,tilSede,tilUbicacion,tilCodCatalogo,tilCatalogo,
        tilDecripcion,tilPlaca,tilMarca,tilModelo,tilSerie,tilCodCentro,tilCentro,tilResponsable;
        EditText txtCodigo, txtBarras,txtDepartamento,txtSede,txtUbicacion,txtCodCatalogo,txtCatalogo,
                txtDecripcion,txtPlaca,txtMarca,txtModelo,txtSerie,txtCodCentro,txtCentro,txtResponsable;
        TextView txtTipoActivo;

        // TextView codigo, stock, estado;
       // TextView descripcion;
        //TextView zona;
       // TextView cantidad;//se estÃ¡ poniendo la cantidad
        //ImageView img;
       // TextView idProducto;


        public ViewHolder(View v) {
            super(v);

            this.tilCodigo = (TextInputLayout) v.findViewById(R.id.tilCodigo);
            this.tilBarras = (TextInputLayout) v.findViewById(R.id.tilBarras);
            this.tilDepartamento = (TextInputLayout) v.findViewById(R.id.tilDepartamento);
            this.tilSede = (TextInputLayout) v.findViewById(R.id.tilSede);
            this.tilUbicacion = (TextInputLayout) v.findViewById(R.id.tilUbicacion);
            this.tilCodCatalogo = (TextInputLayout) v.findViewById(R.id.tilCodCatalogo);
            this.tilCatalogo = (TextInputLayout) v.findViewById(R.id.tilCatalogo);
            this.tilDecripcion = (TextInputLayout) v.findViewById(R.id.tilDescripcion);
            this.tilPlaca = (TextInputLayout) v.findViewById(R.id.tilPlaca);
            this.tilMarca = (TextInputLayout) v.findViewById(R.id.tilMarca);
            this.tilModelo = (TextInputLayout) v.findViewById(R.id.tilModelo);
            this.tilSerie = (TextInputLayout) v.findViewById(R.id.tilSerie);
            this.tilCodCentro = (TextInputLayout) v.findViewById(R.id.tilCodCentro);
            this.tilCentro = (TextInputLayout) v.findViewById(R.id.tilCentro);
            this.tilResponsable = (TextInputLayout) v.findViewById(R.id.tilResponsable);

            this.txtCodigo = (EditText) v.findViewById(R.id.edtCodigo);
            this.txtBarras = (EditText) v.findViewById(R.id.edtBarras);
            this.txtDepartamento = (EditText) v.findViewById(R.id.edtDepartamento);
            this.txtSede = (EditText) v.findViewById(R.id.txtSede);
            this.txtUbicacion = (EditText) v.findViewById(R.id.txtUbicacion);
            this.txtCodCatalogo = (EditText) v.findViewById(R.id.txtCodCatalogo);
            this.txtCatalogo = (EditText) v.findViewById(R.id.txtCatalogo);
            this.txtTipoActivo=(TextView) v.findViewById(R.id.txtTipoActivo);
            this.txtDecripcion = (EditText) v.findViewById(R.id.txtDescipcion);
            this.txtPlaca = (EditText) v.findViewById(R.id.txtPlaca);
            this.txtMarca = (EditText) v.findViewById(R.id.txtMarca);
            this.txtModelo = (EditText) v.findViewById(R.id.txtModelo);
            this.txtSerie = (EditText) v.findViewById(R.id.txtSerie);
            this.txtCodCentro = (EditText) v.findViewById(R.id.txtCodCentro);
            this.txtCentro = (EditText) v.findViewById(R.id.txtCentro);
            this.txtResponsable = (EditText) v.findViewById(R.id.txtResponsable);
            //this.cantidad = (TextView) v.findViewById(R.id.cantidad);
            //this.zona = (TextView) v.findViewById(R.id.nombreZona);
            //METODOS DE PRUEBA
            //this.stock=(TextView)v.findViewById(R.id.stock);
            //this.estado=(TextView)v.findViewById(R.id.estadoVista);
            //this.img=(ImageView)v.findViewById(R.id.imgLlanta);
           // this.listener = listener;
           // v.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            //listener.onItemClick(v, getAdapterPosition());

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //Tener cuidado con los tipos de datos que se almaccena, debemos parsear todo a Sring

        try {
            holder.txtCodigo.setText(String.valueOf(activoList.get(position).getCodigo()));
            holder.txtBarras.setText(String.valueOf(activoList.get(position).getCodigobarra()));
            holder.txtDepartamento.setText(String.valueOf(activoList.get(position).getDepartamento()));
            holder.txtSede.setText(String.valueOf(activoList.get(position).getSede()));
            holder.txtUbicacion.setText(String.valueOf(activoList.get(position).getUbicacion()));
            holder.txtCodCatalogo.setText(String.valueOf(activoList.get(position).getCodcatalogo()));
            holder.txtCatalogo.setText(String.valueOf(activoList.get(position).getCatalogo()));
            if(!activoList.get(position).getTipoActivo().equalsIgnoreCase("bien de control")){
                holder.txtTipoActivo.setText(String.valueOf("AF"));
            }else{
                holder.txtTipoActivo.setText(String.valueOf("BC"));
            }

            holder.txtDecripcion.setText(String.valueOf(activoList.get(position).getObservacion()));
            holder.txtPlaca.setText(String.valueOf(activoList.get(position).getPlaca()));
            holder.txtMarca.setText(String.valueOf(activoList.get(position).getMarca()));
            holder.txtModelo.setText(String.valueOf(activoList.get(position).getModelo()));
            holder.txtSerie.setText(String.valueOf(activoList.get(position).getSerie()));
            holder.txtCodCentro.setText(String.valueOf(activoList.get(position).getCodCentro()));
            holder.txtCentro.setText(String.valueOf(activoList.get(position).getCentro()));
            holder.txtResponsable.setText(String.valueOf(activoList.get(position).getResponsable()));
            //holder.txtSerie.setText(String.valueOf(activoList.get(position).getDepartamento()));


           // holder.descripcion.setText(productoList.get(position).getDescripcion());
           // List<Zona> zonaList = AppController.getDaoSession().getZonaDao().queryBuilder().where(ZonaDao.Properties.Id.eq(productoList.get(position).getZona_id())).list();
           // holder.zona.setText(zonaList.get(0).getNombre());
           // holder.cantidad.setText(String.valueOf(productoList.get(position).getStock()));

            //METODOS DE PRUEBA
            //holder.stock.setText(String.valueOf(items.get(position).getStock()));
            //holder.estado.setText(String.valueOf(items.get(position).getEstado()));
            //TextDrawable drawable = TextDrawable.builder().buildRect("A", Color.RED);

        } catch (Exception e) {
            //Log.e("Error", e.getMessage().toString());
            //Toast.makeText(contexto, "Error al cargar los datos al adaptador", Toast.LENGTH_SHORT).show();
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
