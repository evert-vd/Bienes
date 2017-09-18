package com.evertvd.bienes.vista.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;


import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.dao.ActivoDao;

import java.util.List;


public class BarScanner extends AppCompatActivity {
private TextView txtInformacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_scanner);

        txtInformacion=(TextView)findViewById(R.id.txtDetalle);
        String informacion="";
        List<Activo> activoList= Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Seleccionado.eq(1)).list();
        for(int i=0;i<activoList.size();i++){
            informacion+="Codigo:"+activoList.get(i).getCodigo()+"\n"+
                    " Descripcion:"+activoList.get(i).getObservacion()+"\n"+
                    " Responsable:"+activoList.get(i).getResponsable()+"\n";

        }
        txtInformacion.setText(informacion);

    }
//compile 'com.edwardvanraak:MaterialBarcodeScanner:0.0.6-ALPHA'


}
