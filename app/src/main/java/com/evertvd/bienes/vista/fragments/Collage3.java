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
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.evertvd.bienes.R;
import com.evertvd.bienes.threads.ThreadCreateCollage;
import com.evertvd.bienes.utils.MainDirectorios;
import com.evertvd.bienes.utils.RotateTransformation;

import java.io.File;

import uk.co.senab.photoview.PhotoView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Collage3 extends Fragment implements View.OnClickListener{
    private SeekBar sbFoto1;
    private Bitmap fotoCollage;
    private FloatingActionButton btnGuardar;
    private ImageButton btnFoto1,btnClose1, btnNumero1, btnRotar1;
    private PhotoView photoView1;
    private String activo;
    private View view, viewInclude1;
    private int numClicks=1;
    private static int F1=0;
    public Collage3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_collage3, container, false);
        activo=getActivity().getIntent().getStringExtra("activo");//dato recuperado en el activity

        btnGuardar = (FloatingActionButton)view.findViewById(R.id.fabGuardar);
        btnGuardar.setOnClickListener(this);

        photoView1 = (PhotoView)view.findViewById(R.id.photo_view1);
        photoView1.setMaximumScale(10);


        //Include1
        viewInclude1=(View)view.findViewById(R.id.include1);
        btnFoto1 = (ImageButton)viewInclude1.findViewById(R.id.btnFoto);
        btnFoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFoto(1);
            }
        });
        btnNumero1=(ImageButton)viewInclude1.findViewById(R.id.btnNumero);
        btnNumero1.setImageResource(R.drawable.ic_number_one);
        btnNumero1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFoto(1);
            }
        });
        sbFoto1=(SeekBar)viewInclude1.findViewById(R.id.zbFoto);
        sbFoto1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i!=0){
                    photoView1.setScale((float)i/10);
                    photoView1.setDrawingCacheEnabled(false);
                }else{
                    photoView1.setScale(0);
                    photoView1.setDrawingCacheEnabled(false);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btnRotar1=(ImageButton)viewInclude1.findViewById(R.id.btnRotar);
        btnRotar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageGlideRotate(45*numClicks, photoView1);
                numClicks ++;
                if (numClicks*45==360){
                    numClicks=0;
                }
            }
        });

        btnClose1=(ImageButton)viewInclude1.findViewById(R.id.btnClose);
        btnClose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoView1.setDrawingCacheEnabled(false);
                photoView1.setImageDrawable(null);
                sbFoto1.setVisibility(View.GONE);
                sbFoto1.setProgress(0);
                btnFoto1.setVisibility(View.VISIBLE);
                btnClose1.setVisibility(View.GONE);
                btnNumero1.setVisibility(View.VISIBLE);
                btnRotar1.setVisibility(View.GONE);
                F1=0;
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabGuardar) {
            if (validaFotoVacia()) {
                //photoView1.setDrawingCacheEnabled(true);
                // Bitmap foto1 = photoView1.getDrawingCache();
                //Bitmap foto1 = Bitmap.createBitmap(photoView1.getDrawingCache());
                //photoView2.buildDrawingCache();
                photoView1.destroyDrawingCache();
                photoView1.setDrawingCacheEnabled(true);
                Bitmap foto1 = Bitmap.createBitmap(photoView1.getDrawingCache());

                fotoCollage = crearCollageFotos(foto1);
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                //progressDialog.setTitle("Foto");
                progressDialog.setTitle("Creando collage de fotos...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                ThreadCreateCollage threadsSaveFoto = new ThreadCreateCollage(progressDialog, getActivity(), activo, fotoCollage, getFragmentManager(), photoView1.getMeasuredHeight());
                threadsSaveFoto.execute();
            }
        }
    }

    private boolean validaFotoVacia(){
        boolean estado=true;
        if(F1==0){
            Snackbar.make(view, "La Foto 1 está vacía", Snackbar.LENGTH_SHORT)
                    .show();
            estado=false;
        }
        return estado;
    }

    private void loadImageGlide(PhotoView photoView, int requestCode) {
        Glide.with(this)
                .load(MainDirectorios.obtenerDirectorioFotosOri(getActivity(),activo,requestCode))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(photoView);
    }

    private void loadImageGlideRotate(float angle, PhotoView photoView) {
        Glide.with(this)
                .load(MainDirectorios.obtenerDirectorioFotosOri(getActivity(), activo, 1))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transform( new RotateTransformation( getActivity(), angle ))
                .into( photoView );
    }

    private Bitmap crearCollageFotos(Bitmap foto1) {
        int w = foto1.getWidth();
        int h = foto1.getHeight();

        Bitmap result = Bitmap.createBitmap(w, h, foto1.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(foto1, 0f, 0f, null);

        //Marcos del collage
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);//no pinta al rectangulo
        paint.setStrokeWidth(5);//grosor de la linea
        crearMarcoFoto(canvas, paint);

        return result;
    }

    private void crearMarcoFoto(Canvas canvas, Paint paint) {
        canvas.drawRect(0,0,(photoView1.getMeasuredWidth())-1,photoView1.getMeasuredHeight()-1,paint);//dibuja rectangulo
        //canvas.drawLine(photoView1.getMeasuredWidth(), 0, photoView1.getMeasuredWidth(), photoView1.getMeasuredHeight(), paint);//dibuja linea vertical
        //canvas.drawLine(photoView1.getMeasuredWidth(),photoView2.getMeasuredHeight(),photoView1.getMeasuredWidth()+photoView2.getMeasuredWidth(),photoView2.getMeasuredHeight(),paint);//dubuja linea horizontal
        //canvas.drawLine(photoView1.getMeasuredWidth(),photoView2.getMeasuredHeight()+photoView3.getHeight(),photoView1.getMeasuredWidth()+photoView4.getMeasuredWidth(),photoView2.getMeasuredHeight()+photoView3.getHeight(),paint);//dubuja linea horizontal
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprovamos que la foto se a realizado
        if (requestCode == 1 && resultCode ==getActivity().RESULT_OK) {
            loadImageGlide(photoView1,requestCode);

            //Picasso
            /*Picasso.with(getActivity())
                    .load(new File(DirectorioCollage.obtenerDirectorioFotosOri(getActivity(),activo,requestCode)))
                    .noFade()
                    .noPlaceholder()
                    .memoryPolicy(MemoryPolicy.NO_STORE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(photoView1);*/

            btnFoto1.setVisibility(View.GONE);
            btnNumero1.setVisibility(View.GONE);
            btnClose1.setVisibility(View.VISIBLE);
            btnRotar1.setVisibility(View.VISIBLE);
            sbFoto1.setVisibility(View.VISIBLE);

            F1=1;
        }
    }

    private void tomarFoto(int requestCode){
        //Creamos el Intent para llamar a la Camara
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Creamos una carpeta en la memeria del terminal
        File image = new File(MainDirectorios.crearDirectorioFotosOri(getActivity()), activo +"("+ requestCode +")"+getString(R.string.extensionFoto));
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

}
