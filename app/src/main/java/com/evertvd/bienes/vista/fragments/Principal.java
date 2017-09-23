package com.evertvd.bienes.vista.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.Historial;
import com.evertvd.bienes.scannercode.MaterialBarcodeScanner;
import com.evertvd.bienes.scannercode.MaterialBarcodeScannerBuilder;
import com.evertvd.bienes.utils.Buscar;
import com.evertvd.bienes.vista.activitys.BarScanner;
import com.evertvd.bienes.vista.activitys.Main3Activity;
import com.evertvd.bienes.vista.activitys.MainActivity;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Principal extends Fragment implements View.OnClickListener {
    private View view;
    private Button btnBorrarData;

    public static final String BARCODE_KEY = "BARCODE";
    private ScaleGestureDetector mScaleDetector;
    private Barcode barcodeResult;

    public Principal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_principal, container, false);

        btnBorrarData=(Button)view.findViewById(R.id.btnBorrarData);
        btnBorrarData.setOnClickListener(this);


        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                */
                startScan();
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnBorrarData){
            Controller.getDaoSession().deleteAll(Activo.class);
            Controller.getDaoSession().deleteAll(Historial.class);

            getActivity().finish();
           startActivity(new Intent(getActivity(),MainActivity.class));

        }
    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BARCODE_KEY, barcodeResult);
        super.onSaveInstanceState(outState);
    }


    private void startScan() {

        mScaleDetector = new ScaleGestureDetector(getActivity(), new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
            }
            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }
            @Override
            public boolean onScale(ScaleGestureDetector detector) {




                return true;
            }
        });



        /**
         * Build a new MaterialBarcodeScanner
         */
        final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                .withActivity(getActivity())
                .withEnableAutoFocus(true)
                .withBleepEnabled(true)
                .withBackfacingCamera()
                .withCenterTracker()
                //.withCenterTracker(R.drawable.common_full_open_on_phone, R.drawable.common_google_signin_btn_icon_dark)
                .withText("Buscando Código de Barras")
                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                    @Override
                    public void onResult(Barcode barcode) {
                        barcodeResult = barcode;
                        //String texto=barcode.rawValue;
                        //result.setText(barcode.rawValue);
                        List<Activo> activoList= Buscar.buscarBarras(barcode.rawValue);
                        Log.e("CB encontrado", barcode.rawValue);


                        if(!activoList.isEmpty()){
                            for (int i=0;i<activoList.size();i++){
                                activoList.get(i).setSeleccionado(1);
                                Controller.getDaoSession().getActivoDao().update(activoList.get(i));
                            }
                            Intent intent=new Intent(getActivity(),BarScanner.class);
                            intent.putExtra("activo", (Serializable) activoList);
                            startActivity(intent);
                            //startActivity(new Intent(getActivity(),BarScanner.class));

                        }else{
                            Toast.makeText(getActivity(),"C.B. "+barcode.rawValue+" no encontrado",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .build();
        materialBarcodeScanner.startScan();
    }
}

