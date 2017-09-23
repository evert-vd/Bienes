package com.evertvd.bienes.vista.fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.evertvd.bienes.R;
import com.evertvd.bienes.utils.TareaCarga;
import com.evertvd.bienes.utils.TareaRead;
import com.evertvd.bienes.vista.activitys.FileInterno;
import com.evertvd.bienes.vista.dialogs.DialogTask;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment implements HiddenFrmTask.TaskCallbacks, View.OnClickListener {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    public static final String HIDDEN_FRAGMENT_TAG = "ABTestFragment";
    public static final int LENGHT = 20000;
    public static int numbers[] = new int[LENGHT];


    TextView progressLabel;
    private Button btnBuscarData;
    private EditText txtNombreArchivo;
    private String path, nombreArchivo;
    int REQUEST_CODE = 1;
    int position;


    public ProgressBar progressBar;
    //SimpleTask simpleTask;
    HiddenFrmTask fragment;
    Button cancelButton;


    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_login, container, false);

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);


        // Generar nÃºmeros aleatorios
        if(fragment==null)
            generateNumbers();

        /*
        ObtenciÃ³n de la posiciÃ³n del item en la lista
         */
        Intent i = getActivity().getIntent();
        //position = i.getIntExtra(getActivity().Main.SEND_KEY_VALUE, -1);


        btnBuscarData=(Button)view.findViewById(R.id.btnBuscarData);
        btnBuscarData.setOnClickListener(this);

        txtNombreArchivo=(EditText) view.findViewById(R.id.txtNombreArchivo);
        txtNombreArchivo.setOnClickListener(this);

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        cancelButton = (Button)view.findViewById(R.id.btnCancelar);
        progressLabel = (TextView)view.findViewById(R.id.progressLabel);


        // Obtener referencia del fragmento
        fragment = (HiddenFrmTask) getFragmentManager().
                findFragmentByTag(HIDDEN_FRAGMENT_TAG);

        /*
        En caso de una rotaciÃ³n de pantalla, es necesario hacer visible
        la barra de progreso, hacer visible el botÃ³n de cancelar de nuevo y
        desactivar el botÃ³n "Ordenar"
         */
        if(position==3 && fragment!=null) {
            if(fragment.progressBarTask.getStatus()== AsyncTask.Status.RUNNING){
                progressBar.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                btnBuscarData.setEnabled(false);
            }
        }




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
                //progreesDialog.setMax(20);
                //progreesDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setMessage("Cargando data de "+path+"...");
                new TareaCarga(progress, getActivity(),path).execute();
            //}


            /*
            DialogFragment dialogFragment = new DialogTask();
            dialogFragment.setCancelable(false);
            dialogFragment.show(getFragmentManager(), "dialogoRegistrar");
            new TareaCarga(dialogFragment, getActivity(),path).execute();
            */

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
                txtNombreArchivo.setText(nombreArchivo);
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


    private void generateNumbers() {
        Random rnd = new Random();
        for(int i=0; i<LENGHT; i++){
            numbers[i]= (int) (rnd.nextDouble() * LENGHT + 1);
        }
    }


    private void execWithProgresBar() {
        FragmentManager fg = getFragmentManager();
        fragment = new HiddenFrmTask();
        FragmentTransaction transaction = fg.beginTransaction();
        transaction.add(fragment, HIDDEN_FRAGMENT_TAG);
        transaction.commit();
    }


    /*
    MÃ©todo que aplica el ordenamiento tipo burbuja simple
     */
    private void bubbleSort(int[] numbers) {

        int aux;

        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = 0; j < numbers.length -1; j++) {
                if (numbers[j] > numbers[j+1])
                {
                    aux          = numbers[j];
                    numbers[j]   = numbers[j+1];
                    numbers[j+1] = aux;
                }
            }
        }

    }


    @Override
    public void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
        btnBuscarData.setEnabled(false);
    }

    @Override
    public void onProgressUpdate(int progress) {
        progressBar.setProgress(progress);
        progressLabel.setText(progress+"%");
    }

    @Override
    public void onCancelled() {
        progressBar.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);
        progressLabel.setText("En la Espera");
        btnBuscarData.setEnabled(true);
    }

    @Override
    public void onPostExecute() {
        progressBar.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);
        progressLabel.setText("Completado");
        btnBuscarData.setEnabled(true);
    }
}
