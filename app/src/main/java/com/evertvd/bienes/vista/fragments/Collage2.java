package com.evertvd.bienes.vista.fragments;


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
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
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
public class Collage2 extends Fragment implements View.OnClickListener{

    private SeekBar sbFoto1,sbFoto2,sbFoto3, sbFoto4;
    private Bitmap fotoCollage;
    private FloatingActionButton btnGuardar;
    private ImageButton btnClose1,btnClose2,btnClose3, btnClose4,btnRotar1,btnRotar2,btnRotar3, btnRotar4;
    private ImageButton btnNumero1,btnNumero2,btnNumero3,btnNumero4;
    private ImageButton btnFoto1,btnFoto2,btnFoto3,btnFoto4;
    private PhotoView photoView1,photoView2,photoView3,photoView4;
    private String activo;
    private int requesCodeFotos;
    private View view;
    private View viewInclude1,viewInclude2, viewInclude3,viewInclude4;
    static int F1=0,F2=0, F3=0, F4=0;
    private int numClicks = 1;

    public Collage2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View Principal
        view=inflater.inflate(R.layout.fragment_collage2, container, false);
        activo=getActivity().getIntent().getStringExtra("activo");

        btnGuardar = (FloatingActionButton)view.findViewById(R.id.fabGuardar);
        btnGuardar.setOnClickListener(this);

        photoView1 = (PhotoView)view.findViewById(R.id.photo_view1);
        photoView1.setMaximumScale(10);

        photoView2 = (PhotoView)view.findViewById(R.id.photo_view2);
        photoView2.setMaximumScale(10);

        photoView3 = (PhotoView)view.findViewById(R.id.photo_view3);
        photoView3.setMaximumScale(10);

        photoView4 = (PhotoView)view.findViewById(R.id.photo_view4);
        photoView4.setMaximumScale(10);

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

        //Include2
        viewInclude2 = (View)view.findViewById(R.id.include2);
        btnFoto2 = (ImageButton)viewInclude2.findViewById(R.id.btnFoto);
        btnFoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFoto(1);
            }
        });

        btnNumero2 = (ImageButton)viewInclude2.findViewById(R.id.btnNumero);
        btnNumero2.setImageResource(R.drawable.ic_number_two);
        btnNumero2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFoto(2);
            }
        });
        sbFoto2=(SeekBar)viewInclude2.findViewById(R.id.zbFoto);
        sbFoto2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i!=0){
                    photoView2.setScale((float)i/10);
                    photoView2.setDrawingCacheEnabled(false);
                }else{
                    photoView2.setScale(0);
                    photoView2.setDrawingCacheEnabled(false);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btnRotar2=(ImageButton)viewInclude2.findViewById(R.id.btnRotar);
        btnRotar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageGlideRotate(45*numClicks, photoView2);
                numClicks ++;
                if (numClicks*45==360){
                    numClicks=0;
                }
            }
        });
        btnClose2=(ImageButton)viewInclude2.findViewById(R.id.btnClose);
        btnClose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoView2.setDrawingCacheEnabled(false);
                photoView2.setImageDrawable(null);
                sbFoto2.setVisibility(View.GONE);
                sbFoto2.setProgress(0);
                btnFoto2.setVisibility(View.VISIBLE);
                btnClose2.setVisibility(View.GONE);
                btnNumero2.setVisibility(View.VISIBLE);
                btnRotar2.setVisibility(View.GONE);
            }
        });

        //Include3
        viewInclude3 = (View)view.findViewById(R.id.include3);
        btnFoto3 = (ImageButton)viewInclude3.findViewById(R.id.btnFoto);
        btnFoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFoto(3);
            }
        });

        btnNumero3 = (ImageButton)viewInclude3.findViewById(R.id.btnNumero);
        btnNumero3.setImageResource(R.drawable.ic_number_three);
        btnNumero3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFoto(3);
            }
        });
        sbFoto3=(SeekBar)viewInclude3.findViewById(R.id.zbFoto);
        sbFoto3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i!=0){
                    photoView3.setScale((float)i/10);
                    photoView3.setDrawingCacheEnabled(false);
                }else{
                    photoView3.setScale(0);
                    photoView3.setDrawingCacheEnabled(false);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btnRotar3=(ImageButton)viewInclude3.findViewById(R.id.btnRotar);
        btnRotar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageGlideRotate(45*numClicks, photoView3);
                numClicks ++;
                if (numClicks*45==360){
                    numClicks=0;
                }
            }
        });
        btnClose3=(ImageButton)viewInclude3.findViewById(R.id.btnClose);
        btnClose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoView3.setDrawingCacheEnabled(false);
                photoView3.setImageDrawable(null);
                sbFoto3.setVisibility(View.GONE);
                sbFoto3.setProgress(0);
                btnFoto3.setVisibility(View.VISIBLE);
                btnNumero3.setVisibility(View.VISIBLE);
                btnClose3.setVisibility(View.GONE);
                btnRotar3.setVisibility(View.GONE);
                F3=0;
            }
        });


        //Include4
        viewInclude4 = (View)view.findViewById(R.id.include4);
        btnFoto4 = (ImageButton)viewInclude4.findViewById(R.id.btnFoto);
        btnFoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFoto(4);
            }
        });

        btnNumero4 = (ImageButton)viewInclude4.findViewById(R.id.btnNumero);
        btnNumero4.setImageResource(R.drawable.ic_number_four);
        btnNumero4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFoto(4);
            }
        });
        sbFoto4=(SeekBar)viewInclude4.findViewById(R.id.zbFoto);
        sbFoto4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i!=0){
                    photoView4.setScale((float)i/10);
                    photoView4.setDrawingCacheEnabled(false);
                }else{
                    photoView4.setScale(0);
                    photoView4.setDrawingCacheEnabled(false);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btnRotar4=(ImageButton)viewInclude4.findViewById(R.id.btnRotar);
        btnRotar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageGlideRotate(45*numClicks, photoView4);
                numClicks ++;
                if (numClicks*45==360){
                    numClicks=0;
                }
            }
        });
        btnClose4=(ImageButton)viewInclude4.findViewById(R.id.btnClose);
        btnClose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoView4.setDrawingCacheEnabled(false);
                photoView4.setImageDrawable(null);
                sbFoto4.setVisibility(View.GONE);
                sbFoto4.setProgress(0);
                btnFoto4.setVisibility(View.VISIBLE);
                btnNumero4.setVisibility(View.VISIBLE);
                btnClose4.setVisibility(View.GONE);
                btnRotar4.setVisibility(View.GONE);
                F3=0;
            }
        });
        return view;
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabGuardar) {
            if (validarFotoVacia()) {
                //photoView1.setDrawingCacheEnabled(true);
                // Bitmap foto1 = photoView1.getDrawingCache();
                //Bitmap foto1 = Bitmap.createBitmap(photoView1.getDrawingCache());
                //photoView2.buildDrawingCache();
                photoView1.destroyDrawingCache();
                photoView1.setDrawingCacheEnabled(true);
                Bitmap foto1 = Bitmap.createBitmap(photoView1.getDrawingCache());


                photoView2.destroyDrawingCache();
                photoView2.setDrawingCacheEnabled(true);
                Bitmap foto2 = photoView2.getDrawingCache();

                photoView3.destroyDrawingCache();
                photoView3.setDrawingCacheEnabled(true);
                Bitmap foto3 = photoView3.getDrawingCache();

                photoView4.destroyDrawingCache();
                photoView4.setDrawingCacheEnabled(true);
                Bitmap foto4 = photoView4.getDrawingCache();

                fotoCollage = crearCollageFotos(foto1, foto2, foto3, foto4);

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

    private boolean validarFotoVacia(){
        boolean estado=true;
        if(F1==0){
            Snackbar.make(view, "La Foto 1 está vacía", Snackbar.LENGTH_SHORT)
                    .show();
            estado=false;
        }else if (F2 == 0) {
            Snackbar.make(view, "La Foto 2 está vacía", Snackbar.LENGTH_SHORT)
                    .show();
            estado=false;
        } else if (F3 == 0) {
            Snackbar.make(view, "La Foto 3 está vacía", Snackbar.LENGTH_SHORT)
                    .show();
            estado=false;
        }else if (F4 == 0) {
            Snackbar.make(view, "La Foto 4 está vacía", Snackbar.LENGTH_SHORT)
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

    private Bitmap crearCollageFotos(Bitmap bitImage1, Bitmap bitImage2, Bitmap bitImage3, Bitmap bitImage4) {
        int w = bitImage1.getWidth() + bitImage2.getWidth();
        int h = bitImage1.getHeight();

        Bitmap result = Bitmap.createBitmap(w, h, bitImage1.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(bitImage1, 0f, 0f, null);
        canvas.drawBitmap(bitImage2, bitImage1.getWidth(), 0, null);
        canvas.drawBitmap(bitImage3, bitImage1.getWidth(), bitImage2.getHeight(), null);
        canvas.drawBitmap(bitImage4, bitImage1.getWidth(), bitImage2.getHeight()+bitImage3.getHeight() , null);

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
        canvas.drawLine(photoView1.getMeasuredWidth(),photoView2.getMeasuredHeight()+photoView3.getHeight(),photoView1.getMeasuredWidth()+photoView4.getMeasuredWidth(),photoView2.getMeasuredHeight()+photoView3.getHeight(),paint);//dubuja linea horizontal
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprovamos que la foto se a realizado
        if (requestCode == 1 && resultCode ==getActivity().RESULT_OK) {
            loadImageGlide(photoView1,requestCode);
            /*Picasso.with(getActivity())
                    .load(new File(DirectorioCollage.obtenerDirectorioFotosOri(getActivity(),activo, requestCode)))
                    .memoryPolicy(MemoryPolicy.NO_STORE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(photoView1);*/

            btnFoto1.setVisibility(View.GONE);
            btnNumero1.setVisibility(View.GONE);
            btnClose1.setVisibility(View.VISIBLE);
            btnRotar1.setVisibility(View.VISIBLE);
            sbFoto1.setVisibility(View.VISIBLE);
            F1=1;
        } else if (requestCode == 2 && resultCode == getActivity().RESULT_OK) {
            loadImageGlide(photoView2,requestCode);

            /*Picasso.with(getActivity())
                    .load(new File(DirectorioCollage.obtenerDirectorioFotosOri(getActivity(),activo, requestCode)))
                    .memoryPolicy(MemoryPolicy.NO_STORE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(photoView1);*/

            btnFoto2.setVisibility(View.GONE);
            btnNumero2.setVisibility(View.GONE);
            btnClose2.setVisibility(View.VISIBLE);
            btnRotar2.setVisibility(View.VISIBLE);
            sbFoto2.setVisibility(View.VISIBLE);
            F2=1;
        } else if (requestCode == 3 && resultCode ==getActivity().RESULT_OK) {
            loadImageGlide(photoView3,requestCode);
            /*Picasso.with(getActivity())
                    .load(new File(DirectorioCollage.obtenerDirectorioFotosOri(getActivity(),activo, requestCode)))
                    .memoryPolicy(MemoryPolicy.NO_STORE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(photoView1);*/

            btnFoto3.setVisibility(View.GONE);
            btnNumero3.setVisibility(View.GONE);
            btnClose3.setVisibility(View.VISIBLE);
            btnRotar3.setVisibility(View.VISIBLE);
            sbFoto3.setVisibility(View.VISIBLE);
            F3=1;
        }else if (requestCode == 4 && resultCode ==getActivity().RESULT_OK) {
            loadImageGlide(photoView4,requestCode);
            /*Picasso.with(getActivity())
                    .load(new File(MainDirectorios.obtenerDirectorioFotosOri(getActivity(),activo, requestCode)))
                    .memoryPolicy(MemoryPolicy.NO_STORE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(photoView4);*/

            btnFoto4.setVisibility(View.GONE);
            btnNumero4.setVisibility(View.GONE);
            btnClose4.setVisibility(View.VISIBLE);
            btnRotar4.setVisibility(View.VISIBLE);
            sbFoto4.setVisibility(View.VISIBLE);
            F4=1;
        }

    }


    private void tomarFoto(int requestCode){
        //Creamos el Intent para llamar a la Camara
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
