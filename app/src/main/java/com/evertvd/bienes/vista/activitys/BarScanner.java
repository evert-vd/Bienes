package com.evertvd.bienes.vista.activitys;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.vista.adapters.ActivosAdapter;

import java.util.ArrayList;
import java.util.List;


public class BarScanner extends AppCompatActivity {


    ActivosAdapter adapter;
    List<Activo> activoList;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;
    ImageButton imgCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_scanner);

        //activoList= Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Seleccionado.eq(1)).list();

        //ArrayList<String>listBarras=(ArrayList<String>)getIntent().getStringArrayListExtra("barras");

        //getIntent().getSerializableExtra("activo");
        activoList = (ArrayList<Activo> ) getIntent().getSerializableExtra("activo");

        for (int i=0;i<activoList.size();i++){
            Log.e("dato recuperado",activoList.get(i).getCodigobarra());
        }

        /*
        txtInformacion=(TextView)findViewById(R.id.txtDetalle);
        String informacion="";
        List<Activo> activoList= Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Seleccionado.eq(1)).list();
        for(int i=0;i<activoList.size();i++){
            informacion+="Codigo:"+activoList.get(i).getCodigo()+"\n"+
                    " Descripcion:"+activoList.get(i).getObservacion()+"\n"+
                    " Responsable:"+activoList.get(i).getResponsable()+"\n";

        }
        txtInformacion.setText(informacion);
            */

        recycler = (RecyclerView) findViewById(R.id.recActivos);
        recycler.setHasFixedSize(true);



        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new ActivosAdapter(activoList, this);
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new DefaultItemAnimator());

        if (savedInstanceState != null) {
           // Log.e("saved instance", savedInstanceState.toString());
        }




    }
//compile 'com.edwardvanraak:MaterialBarcodeScanner:0.0.6-ALPHA'


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void showPopupMenu(View view, int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(view.getContext(),view );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        //popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }

}
