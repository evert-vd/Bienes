package com.evertvd.bienes.vista.dialogs;





import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.DialogFragment;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.evertvd.bienes.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by evertvd on 13/03/2017.
 */

public class DialogSendEmail extends DialogFragment implements View.OnClickListener {
    private Button btnAceptar,btnCancelar;
    private TextView txtMensaje;
    private List<String> archivosAdjuntos;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_send_email, null);

        //txtMensaje=(TextView) view.findViewById(R.id.txtMensaje);
        btnCancelar = (Button) view.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);
        btnAceptar = (Button) view.findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(this);

       //AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Seleccionar Reportes");
        int totalArchivos = obtenerDirectorioDownload().length;
        boolean[] cantSeleccionado = new boolean[totalArchivos];

        builder.setMultiChoiceItems(obtenerDirectorioDownload(), cantSeleccionado, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                ListView list = ((AlertDialog) dialog).getListView();
                // make selected item in the comma seprated string
                //StringBuilder stringBuilder = new StringBuilder();
                 archivosAdjuntos= new ArrayList<String>();
                for (int i = 0; i < list.getCount(); i++) {
                    boolean checked = list.isItemChecked(i);
                    if (checked) {
                        //File carpeta = EetActivity().getExternalFilesDir(getResources().getString(R.string.fileExport));//Ruta relativa a la app
                        //File ubicacionReporte = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                        File ruta = Environment.getExternalStorageDirectory();//files sd
                        String archivo = getResources().getString(R.string.directorioFileExport) + "/" + list.getItemAtPosition(i);
                        //String archivo = list.getItemAtPosition(i).toString();
                        //File reporte = new File(ruta, archivo);
                        File reporte = new File(ruta, archivo);
                        archivosAdjuntos.add(reporte.getAbsolutePath());
                    }}
            }
        });

        /*
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        //StringBuilder stringBuilder = new StringBuilder();
                        List<String> archivosAdjuntos = new ArrayList<String>();
                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);
                            if (checked) {
                                //File carpeta = EetActivity().getExternalFilesDir(getResources().getString(R.string.fileExport));//Ruta relativa a la app
                                //File ubicacionReporte = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                                File ruta = Environment.getExternalStorageDirectory();//files sd
                                String archivo = getResources().getString(R.string.fileExport)+"/"+list.getItemAtPosition(i);
                                //String archivo = list.getItemAtPosition(i).toString();
                                //File reporte = new File(ruta, archivo);
                                File reporte = new File(ruta, archivo);
                                archivosAdjuntos.add(reporte.getAbsolutePath());
                            }
                        }
                        //Envio del List con las rutas
                        compartirPorEmail(archivosAdjuntos);
                    }
                });

        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        */
        //alert.show();
        builder.setView(view);
        return builder.create();
    }

    private CharSequence[] obtenerDirectorioDownload(){



        List<String> list = new ArrayList<String>();
        //File ubicacionReporte = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //File carpeta = getActivity().getExternalFilesDir(getResources().getString(R.string.fileExport));//Ruta relativa a la app
        File sdCardRoot = Environment.getExternalStorageDirectory();
        File dirInventario = new File(sdCardRoot, getResources().getString(R.string.directorioFileExport));
        for (File f : dirInventario.listFiles()) {
            if (f.isFile()) {

                Date lastModified = new Date(f.lastModified());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String formattedDateString = formatter.format(lastModified);
                //String name = f.getName()+"\n"+formattedDateString;
                String name = f.getName();
                Log.i("file names", name);
                list.add(name);
            }
        }
        // Intialize  readable sequence of char values
        CharSequence[] archivosDownload = list.toArray(new CharSequence[list.size()]);

        return archivosDownload;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnAceptar){
            try{
                if(!archivosAdjuntos.isEmpty()){
                    compartirPorEmail(archivosAdjuntos);
                    this.dismiss();
                }else{
                    Toast.makeText(getActivity(),"Ningun archivo Seleccionado",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Toast.makeText(getActivity(),"Ningun archivo Seleccionado",Toast.LENGTH_SHORT).show();
            }

        }else if(view.getId()==R.id.btnCancelar){
            this.dismiss();
        }
    }


    private void compartirPorEmail(List<String> archivosAdjuntos){

        String cuerpoMje="";
        //String titulo=getResources().getString(R.string.mensaje);
        //String archivoOrigen="INV-"+inventario.getEmpresa().getCodempresa()+"-"+inventario.getNuminventario()+"-"+inventario.getNumequipo();
        //String fechaInicio=inventario.getFechaCreacion();
        //String fechaCierre=inventario.getFechaCierre();
        //cuerpoMje=titulo+"\n"+"Archivo Origen: "+archivoOrigen+"\n"+"Fecha Inicio: "+fechaInicio+"\n"+"Fecha Cierre: "+fechaCierre;




        final Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        //String[] para= getResources().getStringArray(R.array.para);
        //String[] cc= getResources().getStringArray(R.array.cc);
        String para="";
        String cc="";
        emailIntent.setType("text/plain");
        //emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,para);
        //emailIntent.putExtra(Intent.EXTRA_EMAIL,posiciones,new String[]{"correo"}));
        emailIntent.putExtra(Intent.EXTRA_CC,cc);
        //emailIntent.putExtra(Intent.EXTRA_CC, new String[]{"dsada@correo"});
        //emailIntent.setType("message/rfc822");
        //emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.asunto)+" Equipo "+numeroEquipo);
        //emailIntent.putExtra(Intent.EXTRA_TEXT,cuerpoMje);
        //emailIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.mensaje));
        //has to be an ArrayList
        ArrayList<Uri> uris = new ArrayList<Uri>();
        //convert from paths to Android friendly Parcelable Uri's
        for (String file : archivosAdjuntos) {
            File fileIn = new File(file);
            Uri uri = Uri.fromFile(fileIn);
            uris.add(uri);
        }

        try {
            emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
            startActivity(Intent.createChooser(emailIntent,  "Enviar "+getResources().getString(R.string.nombreFileExport)+getResources().getString(R.string.extensionFileExport)+" con:"));
            getActivity().finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(),
                    "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
        }
    }


}
