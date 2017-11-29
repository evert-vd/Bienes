package com.evertvd.bienes.threads;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.evertvd.bienes.threads.grupo1.HiloCentroCosto;
import com.evertvd.bienes.threads.grupo1.HiloCuentaContable;
import com.evertvd.bienes.threads.grupo1.HiloResponsable;
import com.evertvd.bienes.threads.grupo2.HiloDepartamento;
import com.evertvd.bienes.threads.grupo2.HiloSede;
import com.evertvd.bienes.threads.grupo2.HiloUbicacion;
import com.evertvd.bienes.threads.grupo3.HiloCatalogo;
import com.evertvd.bienes.threads.grupo3.HiloEmpresa;
import com.evertvd.bienes.threads.grupo4.HiloActivo;


/**
 * Created by evertvd on 18/09/2017.
 */

public class MainThreadsReadData extends AsyncTask<Void, Void, Void> {
    private Context context;
    private ProgressBar progressBar;
    private String path;
    private Button  btnLeerArchivo;
    private EditText ruta;

    //para progresDialog
    public MainThreadsReadData(Context context, ProgressBar progressBar, Button btnLeerArchivo, EditText ruta, String path) {
        this.context=context;
        this.progressBar=progressBar;
        this.path=path;
        this.btnLeerArchivo=btnLeerArchivo;
        this.ruta=ruta;
    }

    /*
    //para dialog fragment
    public TareaCarga(DialogFragment progress, Context context, String path) {
        this.dialogTask = progress;
        this.context = context;
        this.path=path;
    }
    */

    public void onPreExecute() {

        //aquí se puede colocar código a ejecutarse previo
        //a la operación

        //hiloDepartamento=new HiloDepartamento(path);
        //hiloCentroCosto=new HiloCentroCosto(path);
        //hiloResponsable=new HiloResponsable(path);
        //hiloCuentaContable=new HiloCuentaContable(path);
        progressBar.setVisibility(View.VISIBLE);
        btnLeerArchivo.setEnabled(false);
        ruta.setEnabled(false);

    }


    public void onPostExecute(Void unused) {
        //aquí se puede colocar código que
        //se ejecutará tras finalizar
       }


    protected Void doInBackground(Void... params) {
        try{
            //realizar la operación aquí
            ThreadGroup grupo1 = new ThreadGroup("TIndependientes");
            HiloCentroCosto hiloCentroCosto=new HiloCentroCosto(grupo1, "CentroCosto", path);
            HiloCuentaContable hiloCuentaContable=new HiloCuentaContable(grupo1, "Cuenta", path);
            HiloResponsable hiloResponsable=new HiloResponsable(grupo1, "Responsable", path);

            ThreadGroup grupo2 = new ThreadGroup("Ubicacion");
            HiloDepartamento hiloDepartamento=new HiloDepartamento(grupo2, "Departamento", path);
            HiloSede hiloSede=new HiloSede(grupo2, "Sede", path);
            HiloUbicacion hiloUbicacion=new HiloUbicacion(grupo2, "Ubicacion", path);

            ThreadGroup grupo3 = new ThreadGroup("Empresa");
            HiloEmpresa hiloEmpresa=new HiloEmpresa(grupo3, "Empresa1", path);
            HiloCatalogo hiloCatalogo=new HiloCatalogo(grupo3, "Catalogo", path);

            ThreadGroup grupo4 = new ThreadGroup("Activo");
            HiloActivo hiloActivo=new HiloActivo(grupo4, "Activo1",context, path);

            try {
                //hilos Grupo1
                hiloCentroCosto.start();
                hiloCuentaContable.start();
                hiloResponsable.start();

                //hilos Grupo2
                hiloDepartamento.start();
                hiloDepartamento.join();
                hiloSede.start();
                hiloSede.join();
                hiloUbicacion.start();

                //hilos Grupo3
                hiloEmpresa.start();
                hiloEmpresa.join();
                hiloCatalogo.start();

                //hilo Grupo4
                hiloUbicacion.join();
                hiloCatalogo.join();
                hiloCentroCosto.join();
                hiloCuentaContable.join();
                hiloResponsable.join();
                hiloActivo.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }catch (Exception e){

        }
        return null;
    }

}