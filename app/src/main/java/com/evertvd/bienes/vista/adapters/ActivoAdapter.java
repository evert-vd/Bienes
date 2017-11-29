package com.evertvd.bienes.vista.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.ItemClickListener;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.vista.activitys.ActivoView;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;




import java.util.ArrayList;
import java.util.List;


/**
 * Created by evertvd on 30/01/2017.
 */

public class ActivoAdapter extends RecyclerView.Adapter<ActivoAdapter.ViewHolder> implements ItemClickListener, SectionTitleProvider {
    //implements SectionTitleProvider
    private Context contexto;
    private List<Activo> activoList;

    public ActivoAdapter(List<Activo> activoList, Context contexto) {
        this.contexto = contexto;
        //activoList = new ArrayList<>(activoList);
        this.activoList = activoList;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent=new Intent(contexto,ActivoView.class);
        intent.putExtra("activo",activoList.get(position).getCodigo());
        contexto.startActivity(intent);
        //Toast.makeText(contexto,"ITem:"+ String.valueOf(activoList.get(position).getCodigo()),Toast.LENGTH_LONG).show();
    }


    @Override
    public String getSectionTitle(int position) {
        //Activo activo=activoList.get(position);
        try{
            return String.valueOf(activoList.get(position).getDescripcion().charAt(0));
        }catch (Exception e){
            //Log.e("TAG","El activo no tiene descripcion");
            return "null";
        }


    }
    /*

       @Override
       public String getSectionText(int position) {
          // return getItem(position).getIndex();
           return String.valueOf(activoList.get(position).getDescripcion().charAt(0));
       }
    */
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        TextView txtCodigo;
        TextView txtDescripcion;
        TextView txtEmpresa;

        public ItemClickListener listener;

        public ViewHolder(View v, ItemClickListener listener) {
            super(v);

            this.txtCodigo = (TextView) v.findViewById(R.id.txtCodigo);
            this.txtDescripcion = (TextView) v.findViewById(R.id.txtDescripcion);
            this.txtEmpresa=(TextView)v.findViewById(R.id.txtEmpresa);
            //METODOS DE PRUEBA
            //this.stock=(TextView)v.findViewById(R.id.stock);
            //this.estado=(TextView)v.findViewById(R.id.estadoVista);
            //this.img=(ImageView)v.findViewById(R.id.imgLlanta);
            this.listener = listener;
            v.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_activo_item, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        //ViewHolder vh = new ViewHolder(v);
        //return vh;
        return new ViewHolder(v, this);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //Tener cuidado con los tipos de datos que se almaccena, debemos parsear todo a Sring
        //final Activo model = activoList.get(position);
        //holder.bind(model);
        try {
            holder.txtCodigo.setText("("+String.valueOf(position+1)+") "+String.valueOf(activoList.get(position).getCodigo()));
            holder.txtDescripcion.setText(activoList.get(position).getDescripcion());
            holder.txtEmpresa.setText(activoList.get(position).getCatalogo().getEmpresa().getEmpresa());

            //METODOS DE PRUEBA
            //holder.stock.setText(String.valueOf(items.get(position).getStock()));
            //holder.estado.setText(String.valueOf(items.get(position).getEstado()));
            //TextDrawable drawable = TextDrawable.builder().buildRect("A", Color.RED);

        } catch (Exception e) {
            Log.e("Error", e.getMessage().toString());
            Toast.makeText(contexto, "Error al cargar los datos al adaptador", Toast.LENGTH_SHORT).show();
        }

    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //Log.e(String.valueOf(items.size()),"errpr");
        return activoList.size();

    }

    //metodo que asigna la nueva lista filtrada al recycler, sin animacion
    public void setFilter(List<Activo> activoList) {
        this.activoList = new ArrayList<>();
        this.activoList.addAll(activoList);
        notifyDataSetChanged();

    }


    //filtro con animacion
    public void setFilterAnimated(List<Activo> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);

    }
    /**
     * Logica del filtro con animacion
     **/

    private void applyAndAnimateRemovals(List<Activo> newModels) {

        for (int i = activoList.size() - 1; i >= 0; i--) {
            final Activo model = activoList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Activo> newModels) {

        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Activo model = newModels.get(i);
            if (!activoList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Activo> newModels) {

        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Activo model = newModels.get(toPosition);
            final int fromPosition = activoList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Activo removeItem(int position) {
        final Activo model = activoList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Activo model) {
        activoList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Activo model = activoList.remove(fromPosition);
        activoList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }


}
