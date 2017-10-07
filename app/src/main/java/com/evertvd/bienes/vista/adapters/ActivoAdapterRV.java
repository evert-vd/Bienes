package com.evertvd.bienes.vista.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evertvd.bienes.R;
import com.evertvd.bienes.modelo.Activo;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.List;

/**
 * Created by evertvd on 27/09/2017.
 */

public class ActivoAdapterRV extends RecyclerView.Adapter<ActivoAdapterRV.MyViewHolder> implements SectionTitleProvider{

    private List<Activo> activoList;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView catalogo, descripcion;

        public MyViewHolder(View view) {
            super(view);
            descripcion= (TextView) view.findViewById(R.id.txtDescripcion);
            catalogo = (TextView) view.findViewById(R.id.txtCatalogo);

        }

    }

    public ActivoAdapterRV(List<Activo> activoList) {
        this.activoList = activoList;
    }

    @Override
    public ActivoAdapterRV.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_activo_item, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivoAdapterRV.MyViewHolder holder, int position) {
        Activo activo=activoList.get(position);
        holder.catalogo.setText(activo.getCodigo());
        holder.descripcion.setText(activo.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return activoList.size();
        //return  0;
    }

    @Override
    public String getSectionTitle(int position) {
        Activo activo=activoList.get(position);
        return activoList.get(position).getDescripcion().substring(0,1);
    }

}
