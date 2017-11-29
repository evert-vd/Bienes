package com.evertvd.bienes.vista.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.evertvd.bienes.R;
import com.evertvd.bienes.threads.ThreadSaveCollage;
import com.evertvd.bienes.utils.MainDirectorios;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Fragmento con un diálogo personalizado
 */
public class DialogViewCollage extends DialogFragment implements View.OnClickListener {
    private static final String TAG = DialogViewCollage.class.getSimpleName();
    private Button btnAceptar, btnCancelar;
    private ImageView imgCollageView;
    private String activo;
    private ProgressBar progressLoad;


    //Definimos la interfaz
    //public interface OnClickListener {
    //void onInputDialog(int conteo, String observacion);
    //}

    public DialogViewCollage() {
        //this.idProducto=idProducto;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme);
    }

    @Override
    public void onStart() {
        super.onStart();
        //dialog pantalla completa
        Dialog d = getDialog();
        if (d!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        return crearDialogoViewCollage();
    }

    /**
     * Crea un diÃƒÂ¡logo con personalizado para comportarse
     * como formulario de entrada de cantidad
     *
     * @return DiÃƒÂ¡logo
     */
    public AlertDialog crearDialogoViewCollage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_collage_view, null);

        activo = getArguments().getString("activo");

        progressLoad=(ProgressBar)view.findViewById(R.id.progressLoad);

        imgCollageView = (ImageView) view.findViewById(R.id.imgCollageView);
        cargarImagen(view);

        btnCancelar = (Button) view.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);

        btnAceptar = (Button) view.findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(this);

        builder.setView(view);
        //builder.setTitle("Foto Collage");
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAceptar) {

            ThreadSaveCollage threadSaveCollage=new ThreadSaveCollage(getActivity(),progressLoad,btnCancelar,btnAceptar,activo);
            threadSaveCollage.execute();

            //copia fotocollage de directorio cache a directorio inventario

            /*copyFileOfCache();
            //borrar directorio ori
            boolean directoryOri = DirectorioCollage.deleteDirectory(DirectorioCollage.crearDirectorioFotosOri(getActivity()));
            if (!directoryOri) {
                Log.e("ori","Error al eliminar directorio ori");
            }
            //borra directorio cache
            boolean directoryCache = DirectorioCollage.deleteDirectory(DirectorioCollage.crearDirectorioFotosTemp(getActivity()));
            if (!directoryCache) {
                Log.e("ori","Error al eliminar directorio cache");
            }
            Intent intent=new Intent(getActivity(),ActivoActivity.class);
            intent.putExtra("activo",activo);
            getActivity().startActivity(intent);
            getActivity().finish();
            dismiss();*/
        } else if (v.getId() == R.id.btnCancelar) {
            this.dismiss();
        }
    }

    private void cargarImagen(View view) {

        /*Glide.with(this)
                .load(DirectorioCollage.obtenerDirectorioFotosTemp(getActivity(),activo))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter()
                .into(imgCollageView);

        */
        Picasso.with(view.getContext())
                .load(new File(MainDirectorios.obtenerDirectorioFotosTemp(getActivity(),activo)))
                .noFade()
                .noPlaceholder()
                .memoryPolicy(MemoryPolicy.NO_CACHE)//no guardar en cache
                .memoryPolicy(MemoryPolicy.NO_STORE)//no guardar en memoria
                .into(imgCollageView);
    }

    private void copyFileOfCache() {
        File origen= new File (MainDirectorios.obtenerDirectorioFotosTemp(getActivity(),activo));
        File destino= new File (MainDirectorios.obtenerDirectorioFotos(getActivity(),activo));
        try{
        InputStream in = new FileInputStream(origen);
        OutputStream out = new FileOutputStream(destino);
        // Copy the bits from instream to outstream
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
        }catch (Exception e){

        }

    }


}

