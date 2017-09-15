package com.evertvd.bienes.vista.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.app.Dialog;
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
import com.evertvd.bienes.modelo.Catalogo;
import com.evertvd.bienes.modelo.CentroCosto;
import com.evertvd.bienes.modelo.CuentaContable;
import com.evertvd.bienes.modelo.Departamento;
import com.evertvd.bienes.modelo.Empresa;
import com.evertvd.bienes.modelo.Responsable;
import com.evertvd.bienes.modelo.Sede;
import com.evertvd.bienes.modelo.Ubicacion;
import com.evertvd.bienes.modelo.dao.DepartamentoDao;
import com.evertvd.bienes.modelo.dao.EmpresaDao;
import com.evertvd.bienes.modelo.dao.UbicacionDao;
import com.evertvd.bienes.utils.Buscar;
import com.evertvd.bienes.vista.activitys.FileInterno;
import com.evertvd.bienes.vista.activitys.MainActivity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by evertvd on 14/09/2017.
 */

public class DBuscarArchivo extends DialogFragment implements View.OnClickListener {
    private static final String TAG = DBuscarArchivo.class.getSimpleName();
    private Button btnAceptar, btnCancelar, btnBuscar;
    private EditText txtArchivo;

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

        btnBuscar=(Button)view.findViewById(R.id.btnBuscar);
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
                loadDataBD();
               startActivity(new Intent(getContext(), MainActivity.class));
            }
        } else if (v.getId() == R.id.btnCancelar) {
            this.dismiss();
        }else if(v.getId()==R.id.btnBuscar){
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


    private void loadDataBD() {
        //String[] activosString = leerArchivoSD(path);

        try {
            CsvReader activos = new CsvReader(path);
            activos.readHeaders();
            while (activos.readRecord()) {

                String empresaNom = activos.get("Empresa");
                Log.e("Emp",empresaNom);
                if(Buscar.buscarBarras(empresaNom)==null){
                    Empresa empresa = new Empresa();
                    empresa.setEmpresa(empresaNom);
                    Controller.getDaoSession().getEmpresaDao().insert(empresa);
                }
            }
            activos.close();
        } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }


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


    private void obtenerEmpresas(String[] datos){
        List<String> lista = Arrays.asList(datos);
        //lista = new ArrayList<Element>(Arrays.asList(array));

        for(int i=1;i<lista.size();i++){
            Log.e("longitud",String.valueOf(i)+" dato:"+lista.get(i));

            /*
            String linea[]=datos[i].split(",");

                if (Buscar.buscarEmpresa(linea[2])==null){
                    Empresa empresa = new Empresa();
                    empresa.setEmpresa(linea[2]);
                    Controller.getDaoSession().getEmpresaDao().insert(empresa);
                }
                */
            }


    }


    private boolean validarCodigo(String etCodigo) {
        if (etCodigo.trim().length() == 0) {
            tilArchivo.setError("Ningun archivo seleccionado");
            return false;

        } else {
            tilArchivo.setError(null);
        }

        return true;
    }

}
