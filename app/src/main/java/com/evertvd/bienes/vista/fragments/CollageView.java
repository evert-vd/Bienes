package com.evertvd.bienes.vista.fragments;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.evertvd.bienes.R;
import com.evertvd.bienes.hilos.ThreadCreateCollage;
import com.evertvd.bienes.utils.DirectorioCollage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import uk.co.senab.photoview.PhotoView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollageView extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private SeekBar sbFoto1,sbFoto2,sbFoto3;
    private Bitmap fotoCollage;
    private FloatingActionButton btnGuardar;
    private ImageButton btnFoto1,btnClose1,btnRotar1, btnClose2,btnFoto2,btnFoto3,btnClose3;
    private PhotoView photoView1,photoView2,photoView3;
    private String activo;
    private View view;
    static int F1=0,F2=0, F3=0;

    public CollageView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_collage1, container, false);
        activo=getActivity().getIntent().getStringExtra("activo");//dato recuperado en el activity
        //codigoBarras=getIntent().getStringExtra("barras");
        Toast.makeText(getActivity(),"C.activo1: "+activo,Toast.LENGTH_SHORT).show();

        btnGuardar = (FloatingActionButton)view.findViewById(R.id.fabGuardar);
        btnGuardar.setOnClickListener(this);

        photoView1 = (PhotoView)view.findViewById(R.id.photo_view1);
        photoView1.setMaximumScale(10);

        sbFoto1=(SeekBar)view.findViewById(R.id.zbFoto1);
        sbFoto1.setOnSeekBarChangeListener(this);

        photoView2 = (PhotoView)view.findViewById(R.id.photo_view2);
        photoView2.setMaximumScale(10);

        sbFoto2=(SeekBar)view.findViewById(R.id.zbFoto2);
        sbFoto2.setOnSeekBarChangeListener(this);

        photoView3 = (PhotoView)view.findViewById(R.id.photo_view3);
        photoView3.setMaximumScale(10);

        sbFoto3=(SeekBar)view.findViewById(R.id.zbFoto3);
        sbFoto3.setOnSeekBarChangeListener(this);

        btnFoto1 = (ImageButton)view.findViewById(R.id.btnFoto1);
        btnFoto1.setOnClickListener(this);
        btnClose1=(ImageButton)view.findViewById(R.id.btnClose1);
        btnClose1.setOnClickListener(this);

        btnFoto2 = (ImageButton)view.findViewById(R.id.btnFoto2);
        btnFoto2.setOnClickListener(this);
        btnClose2=(ImageButton)view.findViewById(R.id.btnClose2);
        btnClose2.setOnClickListener(this);

        btnFoto3 = (ImageButton)view.findViewById(R.id.btnFoto3);
        btnFoto3.setOnClickListener(this);
        btnClose3=(ImageButton)view.findViewById(R.id.btnClose3);
        btnClose3.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabGuardar) {

        if(F1==0){
            Snackbar.make(view, "La Foto 01 está vacía", Snackbar.LENGTH_SHORT)
                    .show();
        }else if(F2==0){
            Snackbar.make(view, "La Foto 02 está vacía", Snackbar.LENGTH_SHORT)
                    .show();
        }else if(F3==0){
            Snackbar.make(view, "La Foto 03 está vacía", Snackbar.LENGTH_SHORT)
                    .show();
        }else {

            photoView1.buildDrawingCache();
            Bitmap foto1 = photoView1.getDrawingCache();
            photoView2.buildDrawingCache();
            Bitmap foto2 = photoView2.getDrawingCache();
            photoView3.buildDrawingCache();
            Bitmap foto3 = photoView3.getDrawingCache();
            fotoCollage = crearCollageFotos(foto1, foto2, foto3);

            ProgressDialog progressDialog=new ProgressDialog(getActivity());
            //progressDialog.setTitle("Foto");
            progressDialog.setTitle("Guardado Fotografía");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ThreadCreateCollage threadsSaveFoto=new ThreadCreateCollage(progressDialog,getActivity(),activo,fotoCollage, getFragmentManager(), photoView1.getMeasuredHeight());
            threadsSaveFoto.execute();

        }




    }else if(v.getId()==R.id.btnFoto1){
        tomarFoto(1);
        //Toast.makeText(this,"C.Barra"+codigoBarras,Toast.LENGTH_SHORT).show();
    }else if(v.getId()==R.id.btnFoto2){
        tomarFoto(2);

    }else if(v.getId()==R.id.btnFoto3){
        tomarFoto(3);

    }else if(v.getId()==R.id.btnClose1){
        btnFoto1.setVisibility(View.VISIBLE);
        btnClose1.setVisibility(View.GONE);
        //Glide.get(this).clearDiskCache();
        //Glide.clear(photoView1);
    }else if(v.getId()==R.id.btnClose2){
        btnFoto2.setVisibility(View.VISIBLE);
        btnClose2.setVisibility(View.GONE);
    }else if(v.getId()==R.id.btnClose3){
        btnFoto3.setVisibility(View.VISIBLE);
        btnClose3.setVisibility(View.GONE);
    }
    }

    private void guardarImagen(Bitmap fotoCollage) {
        OutputStream fileOutStream = null;
        Uri uri;
        try {
            //File file = new File(Environment.getExternalStorageDirectory()
              //      + File.separator + "imagenesguardadas" + File.separator);
            //file.mkdirs();

            DirectorioCollage directorioCollage=new DirectorioCollage();
            if(directorioCollage.crearDirectorioInventario(getActivity())!=null){
                File directorioImagenes = new File(directorioCollage.crearDirectorioInventario(getActivity()), activo+".jpg");
                uri = Uri.fromFile(directorioImagenes);
                fileOutStream = new FileOutputStream(directorioImagenes);
            }

        } catch (Exception e) {
            Log.e("ERROR!", e.getMessage());
        }

        try {
            fotoCollage.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream);
            fileOutStream.flush();
            fileOutStream.close();
        } catch (Exception e) {
            Log.e("ERROR!", e.getMessage());
        }
    }


    //metodo 2

    private Bitmap crearCollageFotos(Bitmap foto1, Bitmap foto2, Bitmap foto3) {
        int w = foto1.getWidth() + foto2.getWidth();
        int h = foto1.getHeight();

        Bitmap result = Bitmap.createBitmap(w, h, foto1.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(foto1, 0f, 0f, null);
        canvas.drawBitmap(foto2, foto1.getWidth(), 0, null);
        canvas.drawBitmap(foto3, foto1.getWidth(), foto1.getHeight() / 2, null);

        //Marcos del collage
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);//no pinta al rectangulo
        paint.setStrokeWidth(5);//grosor de la linea

        crearMarcoFoto(canvas, paint);

        return result;
    }

    private void crearMarcoFoto(Canvas canvas, Paint paint) {
        canvas.drawRect(0,0,(photoView1.getMeasuredWidth()+photoView2.getMeasuredWidth())-1,photoView1.getMeasuredHeight()-1,paint);//dibuja rectangulo
        canvas.drawLine(photoView1.getMeasuredWidth(), 0, photoView1.getMeasuredWidth(), photoView1.getMeasuredHeight(), paint);//dibuja linea vertical
        canvas.drawLine(photoView1.getMeasuredWidth(),photoView2.getMeasuredHeight(),photoView1.getMeasuredWidth()+photoView2.getMeasuredWidth(),photoView2.getMeasuredHeight(),paint);//dubuja linea horizontal
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprovamos que la foto se a realizado

        if (requestCode == 1 && resultCode ==getActivity().RESULT_OK) {

            //Creamos un bitmap con la imagen recientemente..se concluye que con esto,... hace lento la aplicacion, se opta por glide

            /*
            Bitmap bMap = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/FotosPruebasAF/" + codigoBarras +"("+ requestCode +")"+ ".jpg");

            */
            //Añadimos el bitmap al imageView para
            //mostrarlo por pantalla
            //img.setImageBitmap(bMap);
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +
                    "/"+getString(R.string.directorio)+"/" + activo +"("+ requestCode +")"+ ".jpg";

            photoView1.setVisibility(View.VISIBLE);
            //photoView1.setImageBitmap(bMap);


            Glide.with(this)
                    .load(path)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(photoView1);
            //collage[0] = photoView1;

            btnFoto1.setVisibility(View.GONE);
            btnClose1.setVisibility(View.VISIBLE);
            sbFoto1.setVisibility(View.VISIBLE);
            //validar que la foto se tomó
            F1=1;

        } else if (requestCode == 2 && resultCode == getActivity().RESULT_OK) {

            //String path = Environment.getExternalStorageDirectory() +
                    //"/FotosPruebasAF/" + codigoBarras +"("+ requestCode +")"+ ".jpg";

            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +
                    "/"+getString(R.string.directorio)+"/" + activo +"("+ requestCode +")"+ ".jpg";

            photoView2.setVisibility(View.VISIBLE);

            //collage[1] = photoView2;
            Glide.with(this).load(path)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(photoView2);

            btnFoto2.setVisibility(View.GONE);
            btnClose2.setVisibility(View.VISIBLE);
            sbFoto2.setVisibility(View.VISIBLE);
            F2=1;

        } else if (requestCode == 3 && resultCode ==getActivity().RESULT_OK) {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +
                    "/"+getString(R.string.directorio)+"/" + activo +"("+ requestCode +")"+ ".jpg";

            photoView3.setVisibility(View.VISIBLE);

            //collage[2] = photoView3;
            Glide.with(this).load(path)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(photoView3);
            btnFoto3.setVisibility(View.GONE);
            btnClose3.setVisibility(View.VISIBLE);
            sbFoto3.setVisibility(View.VISIBLE);
            F3=1;
        }

    }



    private void tomarFoto(int requestCode){
        //Creamos el Intent para llamar a la Camara
        //Intent cameraIntent = new Intent(
        //      android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Creamos una carpeta en la memeria del terminal
        //File imagesFolder = new File(
          //      Environment.getExternalStorageDirectory(), "FotosPruebasAF");
        //imagesFolder.mkdirs();


        DirectorioCollage directorioCollage=new DirectorioCollage();

        //añadimos el nombre de la imagen
        File image = new File(directorioCollage.crearDirectorioInventario(getActivity()), activo +"("+ requestCode +")"+".jpg");
        Uri uriSavedImage = Uri.fromFile(image);
        //Le decimos al Intent que queremos grabar la imagen
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        //Lanzamos la aplicacion de la camara con retorno (forResult)
        startActivityForResult(cameraIntent, requestCode);
    }

    @Override
    public void onDestroy(){

        super.onDestroy();
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar.getId()==R.id.zbFoto1){

            if(progress!=0){
                photoView1.setScale((float)progress/10);
            }else{
                photoView1.setScale(0);
            }

        } else if(seekBar.getId()==R.id.zbFoto2){

            if(progress!=0){
                photoView2.setScale((float)progress/10);
            }else{
                photoView2.setScale(0);
            }

        }else if(seekBar.getId()==R.id.zbFoto3){

            if(progress!=0){
                photoView3.setScale((float)progress/10);
            }else{
                photoView3.setScale(0);
            }

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
