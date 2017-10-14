package com.evertvd.bienes.vista.fragments;


import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.evertvd.bienes.R;
import com.evertvd.bienes.tareas.Hilo1;
import com.evertvd.bienes.utils.TareaInicial;
import com.evertvd.bienes.vista.activitys.FileInterno;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    private Button btnBuscarData;
    private EditText txtNombreArchivo;
    private String path, nombreArchivo;
    int REQUEST_CODE = 1;
    int position;


    public ProgressBar progressBar;

    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_login, container, false);

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);


        btnBuscarData=(Button)view.findViewById(R.id.btnBuscarData);
        btnBuscarData.setOnClickListener(this);

        txtNombreArchivo=(EditText) view.findViewById(R.id.txtNombreArchivo);
        txtNombreArchivo.setOnClickListener(this);

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);



        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            }
        }

        return view;

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnBuscarData){
            //String nomArchivo = txtNombreArchivo.getText().toString().trim();
            if(TextUtils.isEmpty(txtNombreArchivo.getText().toString().trim())){
                Toast.makeText(getActivity(),"Ningún Archivo Seleccionado",Toast.LENGTH_SHORT).show();
                return;
            }
                ProgressDialog progress = new ProgressDialog(getActivity());
                progress.setMessage("Cargando data de "+path+"...");
               // new TareaInicial(progress,getActivity(),path).execute();
            new Hilo1(getActivity(),path).execute();

        }else if(txtNombreArchivo.getId()==R.id.txtNombreArchivo){
            Intent intent = new Intent(getActivity(), FileInterno.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_CODE) {
                // cogemos el valor devuelto por la otra actividad
                path = data.getStringExtra("path");
                nombreArchivo = data.getStringExtra("nombreArchivo");

                // enseñamos al usuario el resultado
                txtNombreArchivo.setText(path);
                //Toast.makeText(this, "ParametrosActivity devolviÃ³: " + nombreArchivo, Toast.LENGTH_LONG).show();
            }
        } else {
            if (txtNombreArchivo.getText().toString().trim().length() == 0) {
                Toast.makeText(getActivity(), "Ningún archivo seleccionado", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
            /*
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 2 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
            */
        }
    }


}
