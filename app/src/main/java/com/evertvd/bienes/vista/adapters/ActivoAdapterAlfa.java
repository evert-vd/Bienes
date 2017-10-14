package com.evertvd.bienes.vista.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evertvd.bienes.R;
import com.evertvd.bienes.modelo.Activo;

import java.util.List;

/**
 * Created by evertvd on 27/09/2017.
 */

public class ActivoAdapterAlfa extends RecyclerView.Adapter<ActivoAdapterAlfa.MyViewHolder> {

    private List<Activo> activoList;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView catalogo, descripcion;

        public MyViewHolder(View view) {
            super(view);
            descripcion= (TextView) view.findViewById(R.id.txtDescripcion);
            catalogo = (TextView) view.findViewById(R.id.txtCatalogo);

        }

    }

    public ActivoAdapterAlfa(List<Activo> activoList) {
        this.activoList = activoList;
    }

    @Override
    public ActivoAdapterAlfa.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_activo_item, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivoAdapterAlfa.MyViewHolder holder, int position) {
        Activo activo=activoList.get(position);
        holder.catalogo.setText(activo.getCodigo()+" "+"("+String.valueOf(position+1)+")");
        holder.descripcion.setText(activo.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return activoList.size();
        //return  0;
    }

    /*
    @Override
    public String getSectionTitle(int position) {
        Activo activo=activoList.get(position);
        return activoList.get(position).getDescripcion().substring(0,1);
    }
    */
}
