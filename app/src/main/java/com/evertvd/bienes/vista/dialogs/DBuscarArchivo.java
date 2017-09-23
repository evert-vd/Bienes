package com.evertvd.bienes.vista.dialogs;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csvreader.CsvReader;

import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;

import com.evertvd.bienes.utils.TareaCarga;
import com.evertvd.bienes.vista.activitys.FileInterno;
import com.evertvd.bienes.vista.activitys.Main3Activity;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
;

/**
 * Created by evertvd on 14/09/2017.
 */

public class DBuscarArchivo extends DialogFragment implements View.OnClickListener {
    private static final String TAG = DBuscarArchivo.class.getSimpleName();
    private Button btnAceptar, btnCancelar, btnBuscar;
    private EditText txtArchivo;
    ProgressDialog progreesDialog;
    int time=0, tamaño=0;
    private TextInputLayout tilArchivo;
    private String path, nombreArchivo;
    int REQUEST_CODE = 1;
    View view;


    //MetodosAuxiliares metodosAuxiliares;

    public DBuscarArchivo() {

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle bundle) {

        //btnProcess.setProgress(0);
        // this.idProducto=idProducto;
        //recuperacion del parametro que viene del punto de invocacion del dialog--viene como string
        // idProducto = getArguments().getInt("idProducto");

        return buscarArchivo();
    }

    /**
     * Crea un diÃƒÂ¡logo con personalizado para comportarse
     * como formulario de entrada de cantidad
     *
     * @return DiÃƒÂ¡logo
     */
    public AlertDialog buscarArchivo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.d_buscar_archivo, null);


        txtArchivo = (EditText) view.findViewById(R.id.txtArchivo);

        btnCancelar = (Button) view.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);

        btnAceptar = (Button) view.findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(this);

        btnBuscar = (Button) view.findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(this);

        tilArchivo = (TextInputLayout) view.findViewById(R.id.tilArchivo);

        builder.setView(view);
        builder.setTitle("Buscar Archivo");
        //builder.setMessage("B");
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAceptar) {
            if (validarCodigo(tilArchivo.getEditText().getText().toString())) {
               // dialogoProgreso();
                //Long time=readFile();
                //long durationInMs = TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS);

                //Log.e("tu met se ejecuta en:",String.valueOf(durationInMs)+" ms");

                //startActivity(new Intent(getContext(),MainActivity.class));


                ProgressDialog progress = new ProgressDialog(getContext());
                //progreesDialog.setMax(20);
                //progreesDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
               progress.setCanceledOnTouchOutside(false);
                progress.setCancelable(false);
                progress.setMessage("Cargando data, por favor espere...");
                //new TareaCarga(progress, getContext(),path).execute();
            }
        } else if (v.getId() == R.id.btnCancelar) {
            this.dismiss();
        } else if (v.getId() == R.id.btnBuscar) {
            Intent intent = new Intent(getContext(), FileInterno.class);
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
                txtArchivo.setText(nombreArchivo);
                //Toast.makeText(this, "ParametrosActivity devolviÃ³: " + nombreArchivo, Toast.LENGTH_LONG).show();
            }
        } else {
            if (txtArchivo.getText().toString().trim().length() == 0) {
                Toast.makeText(getContext(), "Ningún archivo seleccionado", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private String[] leerArchivoSD(String nombre) {
        ByteArrayOutputStream byteArrayOutputStream = null;

        try {
            File file;
            FileReader lectorArchivo;

            file = new File(nombre);
            lectorArchivo = new FileReader(file);

            byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedReader bufferedReader = new BufferedReader(lectorArchivo);
            int i = bufferedReader.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);

                i = bufferedReader.read();
            }

            bufferedReader.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(getContext(), "Error al leer el archivo .txt", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getContext(), "Error al leer el archivo .txt", Toast.LENGTH_LONG).show();
            //btnCargarDatos.setVisibility(View.VISIBLE);
        }
        return byteArrayOutputStream.toString().split("\n");
    }


    private void readFile() {
        //String[] activosString = leerArchivoSD(path);


        // ...
        // El resto del código

        Long startTime = System.nanoTime();


        try {

            CsvReader activos = new CsvReader(path);
            activos.readHeaders();
            /*
            ArrayList<String> empresaList=new ArrayList<String>();
            ArrayList<String> departamentoList=new ArrayList<String>();
            ArrayList<String> sedeList=new ArrayList<String>();
            ArrayList<String> ubicacionList=new ArrayList<String>();
            ArrayList<String> codCatalogoList=new ArrayList<String>();
            ArrayList<String> responsableList=new ArrayList<String>();
            ArrayList<String> codCentroList=new ArrayList<String>();
            ArrayList<String> cuentaList=new ArrayList<String>();
            */

            while (activos.readRecord()) {
                String codigo = activos.get("Cod. Activo");
                String barras = activos.get("Cod. Barras");
                String empresa = activos.get("Empresa");
                String departamento = activos.get("Departamento");
                String sede = activos.get("Sede");
                String ubicacion = activos.get("Ubicación");
                String codCatalogo = activos.get("Cod. Catálogo");
                String nomCatalogo = activos.get("Catálogo");
                String placa = activos.get("Placa");
                String marca = activos.get("Marca");
                String modelo = activos.get("Modelo");
                String serie = activos.get("Serie");
                String foto = activos.get("Si/No");
                String responsable = activos.get("Responsable");
                String tipoActivo = activos.get("Tipo Activo");
                String activoPadre = activos.get("Cod. Activo Padre");
                String codCentro = activos.get("C. Resp");
                String CentroCosto = activos.get("Centro Responsabilidad");
                String cuenta = activos.get("Cta. Ctble.");
                String estado = activos.get("Estado");
                String expediente = activos.get("Expediente");
                String ordenCompra = activos.get("Ord. Compra");
                String factura = activos.get("Fac. Compra");
                String fecCompra = activos.get("Fec. Compra");
                String observacion = activos.get("Observacion");

                cargarDatos(codigo,barras,empresa,departamento,sede,ubicacion,codCatalogo,nomCatalogo,
                        placa,marca,modelo,serie,foto,responsable,tipoActivo,activoPadre,codCentro,CentroCosto,cuenta,estado,
                        expediente,ordenCompra,factura,fecCompra,observacion);



                /*
                if (!empresaList.contains(empresa)) {
                    empresaList.add(empresa);
                    cargarEmpresa(empresa);
                }
                if (!departamentoList.contains(departamento)) {
                    departamentoList.add(departamento);
                }
                if (!sedeList.contains(sede)) {
                    departamentoList.add(sede);
                }
                if (!ubicacionList.contains(ubicacion)) {
                    ubicacionList.add(ubicacion);
                }
                if (!codCatalogoList.contains(codCatalogo+" "+nomCatalogo+" "+empresa)) {
                    codCatalogoList.add(codCatalogo+" "+nomCatalogo+" "+empresa);
                    cargarCatalogo(codCatalogo,nomCatalogo, empresa);
                }
                */



            }



            //cargarCatalogo(catalogoList);

            activos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        int time2=(int)TimeUnit.MILLISECONDS.convert((endTime-startTime), TimeUnit.NANOSECONDS);
        time=time2;
        //resultado en milisecugundos

        //convertir arraystring en liststring
        //List<String> lista = new ArrayList<String>();
        //lista = Arrays.asList(empresaStrings);

        //obtenerEmpresas(activosString);


        /*
        for(int i=1;i<activosString.length;i++){
            String[] linea = activosString[i].split(",");
            //0,1,2,3,4,5,6

            if (Buscar.buscarEmpresa(linea[2])==null){
                Empresa empresa = new Empresa();
                empresa.setEmpresa(linea[2]);
                Controller.getDaoSession().insert(empresa);

            }

            if (Buscar.buscarDepartamento(linea[3])==null){
                Departamento departamento=new Departamento();
                departamento.setDepartamento(linea[3]);
                Empresa empresa=Buscar.buscarEmpresa(linea[2]);
                departamento.setEmpresa_id(empresa.getId());
                Controller.getDaoSession().insert(departamento);
            }

            if (Buscar.buscarSede(linea[4])==null){
                Sede sede=new Sede();
                sede.setSede(linea[4]);
                Departamento departamento=Buscar.buscarDepartamento(linea[3]);
                sede.setDepartamento_id(departamento.getId());
                Controller.getDaoSession().insert(sede);
            }

            if (Buscar.buscarUbicacion(linea[5])==null){
                Ubicacion ubicacion=new Ubicacion();
                ubicacion.setUbicacion(linea[5]);
                Sede sede=Buscar.buscarSede(linea[4]);
                ubicacion.setSede_id(sede.getId());
                Controller.getDaoSession().insert(ubicacion);
            }


            if (Buscar.buscarResponsable(linea[16])==null){
                Responsable responsable=new Responsable();
                responsable.setResponsable(linea[16]);
                Controller.getDaoSession().insert(responsable);
            }


            if (Buscar.buscarCuenta(linea[21])==null){
                CuentaContable cuentaContable=new CuentaContable();
                cuentaContable.setCodigo(linea[21]);
                Controller.getDaoSession().insert(cuentaContable);
            }

            if (Buscar.buscarCentro(linea[19])==null){
                CentroCosto centroCosto=new CentroCosto();
                centroCosto.setCodigo(linea[19]);
                centroCosto.setCentro(linea[20]);
                Controller.getDaoSession().insert(centroCosto);
            }


            if (Buscar.buscarCatalogo(linea[2],linea[8])==null){
                Catalogo catalogo=new Catalogo();
                catalogo.setCodigo(linea[8]);
                catalogo.setCatalogo(linea[9]);
                Empresa empresa=Buscar.buscarEmpresa(linea[2]);
                catalogo.setEmpresa_id2(empresa.getId());
                Controller.getDaoSession().insert(catalogo);
            }

            Activo activo=new Activo();
            activo.setCodigo(linea[0]);
            activo.setCodigobarra(linea[1]);
            activo.setActivopadre(linea[18]);
            activo.setDescripcion(linea[27]);
            activo.setPlaca(linea[10]);
            activo.setMarca(linea[11]);
            activo.setModelo(linea[12]);
            activo.setSerie(linea[13]);
            activo.setTipo(linea[17]);
            activo.setExpediente(linea[23]);
            activo.setOrdencompra(linea[24]);
            activo.setFactura(linea[25]);
            activo.setFechacompra(linea[26]);
            activo.setEstado(linea[22]);

            //ids
            Ubicacion ubicacion=Buscar.buscarUbicacion(linea[5]);
            Responsable responsable=Buscar.buscarResponsable(linea[16]);
            CuentaContable cuentaContable=Buscar.buscarCuenta(linea[21]);
            CentroCosto centroCosto=Buscar.buscarCentro(linea[19]);
            Catalogo catalogo=Buscar.buscarCatalogo(linea[2],linea[8]);

            activo.setUbicacion_id(ubicacion.getId());
            activo.setResponsable_id(responsable.getId());
            activo.setCuentacontable_id(cuentaContable.getId());
            activo.setCentrocosto_id(centroCosto.getId());
            activo.setCatalogo_id(catalogo.getId());

            Controller.getDaoSession().insert(activo);

        }
*/

    }

    private void dialogoProgreso(){

        Log.e("time",String.valueOf(time));
        progreesDialog = new ProgressDialog(getContext());
        progreesDialog.setMax(time);
        progreesDialog.setMessage("Cargando informacion de "+path);
        progreesDialog.setTitle("Leyendo archivo");
        progreesDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progreesDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progreesDialog.getProgress() <= progreesDialog.getMax()) {
                        readFile();
                        handle.sendMessage(handle.obtainMessage());
                        if (progreesDialog.getProgress() == progreesDialog.getMax()) {

                            progreesDialog.dismiss();
                            //break;
                        }
                    }
                    startActivity(new Intent(getContext(), Main3Activity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    private void tamañoData(){
        try {
            CsvReader activos = new CsvReader(path);
            activos.readHeaders();
            List<String>tamañoData=new ArrayList<String>();

            while (activos.readRecord()) {
                String codigo = activos.get("Cod. Activo");

                tamañoData.add(codigo);

            }

            tamaño=tamañoData.size();

            activos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    private void cargarDatos(String codigo, String barras, String empresa,
                             String departamento, String sede, String ubicacion,
                             String codCatalogo, String nomCatalogo, String placa,
                             String marca, String modelo, String serie, String foto,
                             String responsable, String tipoActivo, String activoPadre,
                             String codCentro, String centroCosto, String cuenta,
                             String estado, String expediente, String ordenCompra,
                             String factura, String fecCompra, String observacion) {

        Activo activo=new Activo();
        activo.setCodigo(codigo);
        activo.setCodigobarra(barras);
        activo.setEmpresa(empresa);
        activo.setDepartamento(departamento);
        activo.setSede(sede);
        activo.setUbicacion(ubicacion);
        activo.setCodcatalogo(codCatalogo);
        activo.setCatalogo(nomCatalogo);
        activo.setPlaca(placa);
        activo.setMarca(marca);
        activo.setModelo(modelo);
        activo.setSerie(serie);
        activo.setFoto(foto);
        activo.setResponsable(responsable);
        activo.setTipoActivo(tipoActivo);
        activo.setActivopadre(activoPadre);
        activo.setCodCentro(codCentro);
        activo.setCentro(centroCosto);
        activo.setCuenta(cuenta);
        activo.setEstado(estado);
        activo.setExpediente(expediente);
        activo.setOrdencompra(ordenCompra);
        activo.setFactura(factura);
        activo.setFechacompra(fecCompra);
        activo.setObservacion(observacion);

        Controller.getDaoSession().getActivoDao().insert(activo);


    }

    private void cargarDatos3(String[] cabecera) {
        for(int i=0;i<cabecera.length;i++){
            Activo activo=new Activo();
            activo.setCodigo(cabecera[0]);
            activo.setCodigobarra(cabecera[1]);
            activo.setEmpresa(cabecera[2]);
            activo.setDepartamento(cabecera[3]);
            activo.setSede(cabecera[4]);
            activo.setUbicacion(cabecera[5]);
            activo.setCodcatalogo(cabecera[6]);
            activo.setCatalogo(cabecera[7]);
            activo.setPlaca(cabecera[8]);
            activo.setMarca(cabecera[9]);
            activo.setModelo(cabecera[10]);
            activo.setSerie(cabecera[11]);
            activo.setFoto(cabecera[12]);
            activo.setResponsable(cabecera[13]);
            activo.setTipoActivo(cabecera[14]);
            activo.setActivopadre(cabecera[15]);
            activo.setCodCentro(cabecera[16]);
            activo.setCentro(cabecera[17]);
            activo.setCuenta(cabecera[18]);
            activo.setEstado(cabecera[19]);
            activo.setExpediente(cabecera[20]);
            activo.setOrdencompra(cabecera[21]);
            activo.setFactura(cabecera[22]);
            activo.setFechacompra(cabecera[23]);
            activo.setObservacion(cabecera[24]);

            Controller.getDaoSession().getActivoDao().insert(activo);

        }
    }

    /*
    private void cargarEmpresa(String texto){
        Empresa empresa=new Empresa();
        empresa.setEmpresa(texto);
        //Controller.getDaoSession().getEmpresaDao().insert(empresa);

    }

    private void cargarDepartamento(List<String> list){

    }

    private void cargarSede(List<String> list){

    }

    private void cargarCatalogo(String codCatalogo, String nomCatalogo, String nomEmpresa){
        Catalogo catalogo=new Catalogo();
        catalogo.setCodigo(codCatalogo);
        catalogo.setCatalogo(nomCatalogo);
        Empresa empresa=Buscar.buscarEmpresa(nomEmpresa);
        catalogo.setEmpresa_id2(empresa.getId());
        //Controller.getDaoSession().getCatalogoDao().insert(catalogo);
    }
*/

    private boolean validarCodigo(String etCodigo) {
        if (etCodigo.trim().length() == 0) {
            tilArchivo.setError("Ningun archivo seleccionado");
            return false;

        } else {
            tilArchivo.setError(null);
        }

        return true;
    }



    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progreesDialog.incrementProgressBy(1);
        }
    };

    }

