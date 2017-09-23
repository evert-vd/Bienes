package com.evertvd.bienes.vista.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ZoomButtonsController;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.evertvd.bienes.R;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


public class CollageActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 2;

    private SeekBar sbFoto1,sbFoto2,sbFoto3;

    //private ImageView collageImage;
    private Bitmap mergedImages;
    private FloatingActionButton btnGuardar;
    private ImageButton btnFoto1,btnClose1,btnRotar1, btnClose2,btnFoto2,btnFoto3,btnClose3;
    //private PhotoView photoView2, photoView3;
    private PhotoView photoView1,photoView2,photoView3;
    int widthPixels,heightPixels;
    //LinearLayout layout;
    //private com.evertvd.bienes.photoview.PhotoView   photoView2;

    //ImageView[] collage = {photoView1, photoView2, photoView3};
    String codigoBarras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);

        codigoBarras=getIntent().getStringExtra("barras");
        Toast.makeText(this,"C.Barras: "+codigoBarras,Toast.LENGTH_SHORT).show();


        btnGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);
        btnGuardar.setOnClickListener(this);

        //collageImage = (ImageView) findViewById(R.id.imgCombinar);


        //photoView1 = (PhotoView) findViewById(R.id.photo_view1);
        photoView1 = (PhotoView) findViewById(R.id.photo_view1);
        photoView1.setMaximumScale(10);
        //photoView1.getLayoutParams().width=widthPixels/2;
        //photoView1.getLayoutParams().height=heightPixels;

        sbFoto1=(SeekBar)findViewById(R.id.zbFoto1);
        sbFoto1.setOnSeekBarChangeListener(this);

        photoView2 = (PhotoView) findViewById(R.id.photo_view2);
        photoView2.setMaximumScale(10);
        //photoView2.getLayoutParams().width=widthF2;
        //photoView2.getLayoutParams().height=heightF2;
        //photoView2.setOnPhotoTapListener(this);

        sbFoto2=(SeekBar)findViewById(R.id.zbFoto2);
        sbFoto2.setOnSeekBarChangeListener(this);

        photoView3 = (PhotoView) findViewById(R.id.photo_view3);
        photoView3.setMaximumScale(10);
        //photoView3.getLayoutParams().width=widthF2;
        //photoView3.getLayoutParams().height=heightF2;
        //photoView3.setOnPhotoTapListener(this);

        sbFoto3=(SeekBar)findViewById(R.id.zbFoto3);
        sbFoto3.setOnSeekBarChangeListener(this);

        //Relacionamos con el XML


        btnFoto1 = (ImageButton) this.findViewById(R.id.btnFoto1);
        btnFoto1.setOnClickListener(this);
        btnClose1=(ImageButton) this.findViewById(R.id.btnClose1);
        btnClose1.setOnClickListener(this);
        //btnRotar1=(ImageButton)findViewById(R.id.btnRotar1);
        //btnRotar1.setOnClickListener(this);


        btnFoto2 = (ImageButton) this.findViewById(R.id.btnFoto2);
        btnFoto2.setOnClickListener(this);
        btnClose2=(ImageButton) this.findViewById(R.id.btnClose2);
        btnClose2.setOnClickListener(this);

        btnFoto3 = (ImageButton) this.findViewById(R.id.btnFoto3);
        btnFoto3.setOnClickListener(this);
        btnClose3=(ImageButton) this.findViewById(R.id.btnClose3);
        btnClose3.setOnClickListener(this);

        /*
        imgJoin=(ImageView)findViewById(R.id.imgJoin);

        Bitmap bm1= BitmapFactory.decodeResource(getResources(),R.drawable.g1);
        Bitmap bm2= BitmapFactory.decodeResource(getResources(),R.drawable.g2);
        ArrayList<Bitmap> a=new ArrayList<Bitmap>();
        a.add(bm1);
        a.add(bm2);

        */
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            }
        }


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

            }

        }
    }




    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabGuardar) {

            //Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.g1);
            //Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.g2);
            //ArrayList<Bitmap> a = new ArrayList<Bitmap>();
            //a.add(bm1);
            //a.add(bm2);

            //combineImageIntoOne(a);

            //collageImage.setImageBitmap(combineImageIntoOne(a));


            //Bitmap bigImage = BitmapFactory.decodeResource(getResources(), R.drawable.g1);
            //Bitmap smallImage = BitmapFactory.decodeResource(getResources(), R.drawable.g2);
            //Bitmap img3 = BitmapFactory.decodeResource(getResources(), R.drawable.g3);

            photoView1.buildDrawingCache();
            Bitmap map1 = photoView1.getDrawingCache();

            photoView2.buildDrawingCache();
            Bitmap map2 = photoView2.getDrawingCache();

            photoView3.buildDrawingCache();
            Bitmap map3 = photoView3.getDrawingCache();


            mergedImages = createSingleImageFromMultipleImages(map1, map2, map3);
            //collageImage.setImageBitmap(mergedImages);

            guardarImagen(mergedImages);


        }else if(v.getId()==R.id.btnFoto1){
            tomarFoto(1);
            Toast.makeText(this,"C.Barra"+codigoBarras,Toast.LENGTH_SHORT).show();
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


    private void guardarImagen(Bitmap imagen) {
        OutputStream fileOutStream = null;
        Uri uri;
        try {
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "imagenesguardadas" + File.separator);
            file.mkdirs();
            File directorioImagenes = new File(file, codigoBarras+".jpg");
            uri = Uri.fromFile(directorioImagenes);
            fileOutStream = new FileOutputStream(directorioImagenes);
        } catch (Exception e) {
            Log.e("ERROR!", e.getMessage());
        }

        try {
            imagen.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream);
            fileOutStream.flush();
            fileOutStream.close();
        } catch (Exception e) {
            Log.e("ERROR!", e.getMessage());
        }
    }


    //metodo 2

    private Bitmap createSingleImageFromMultipleImages(Bitmap firstImage, Bitmap secondImage, Bitmap img3) {
        int w = firstImage.getWidth() + secondImage.getWidth();
        int h = firstImage.getHeight();

        Bitmap result = Bitmap.createBitmap(w, h, firstImage.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(firstImage, 0f, 0f, null);
        canvas.drawBitmap(secondImage, firstImage.getWidth(), 0, null);
        canvas.drawBitmap(img3, firstImage.getWidth(), firstImage.getHeight() / 2, null);

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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprovamos que la foto se a realizado
        if (requestCode == 1 && resultCode == RESULT_OK) {


            //Creamos un bitmap con la imagen recientemente..se concluye que con esto,... hace lento la aplicacion, se opta por glide

            /*
            Bitmap bMap = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/FotosPruebasAF/" + codigoBarras +"("+ requestCode +")"+ ".jpg");

            */
            //Añadimos el bitmap al imageView para
            //mostrarlo por pantalla
            //img.setImageBitmap(bMap);
            String path="";
            path = Environment.getExternalStorageDirectory() +
                    "/FotosPruebasAF/" + codigoBarras +"("+ requestCode +")"+ ".jpg";



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
        } else if (requestCode == 2 && resultCode == RESULT_OK) {

            String path = Environment.getExternalStorageDirectory() +
                    "/FotosPruebasAF/" + codigoBarras +"("+ requestCode +")"+ ".jpg";

            photoView2.setVisibility(View.VISIBLE);

            //collage[1] = photoView2;
            Glide.with(this).load(path)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(photoView2);
            btnFoto2.setVisibility(View.GONE);
            btnClose2.setVisibility(View.VISIBLE);
            sbFoto2.setVisibility(View.VISIBLE);
        } else if (requestCode == 3 && resultCode == RESULT_OK) {
            String path = Environment.getExternalStorageDirectory() +
                    "/FotosPruebasAF/" + codigoBarras +"("+ requestCode +")"+ ".jpg";

            photoView3.setVisibility(View.VISIBLE);

            //collage[2] = photoView3;
            Glide.with(this).load(path)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(photoView3);
            btnFoto3.setVisibility(View.GONE);
            btnClose3.setVisibility(View.VISIBLE);
            sbFoto3.setVisibility(View.VISIBLE);
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
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 2 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }

    @Override
    protected void onDestroy(){

        super.onDestroy();
    }



    private void tomarFoto(int requestCode){
        //Creamos el Intent para llamar a la Camara
        //Intent cameraIntent = new Intent(
        //      android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Creamos una carpeta en la memeria del terminal
        File imagesFolder = new File(
                Environment.getExternalStorageDirectory(), "FotosPruebasAF");
        imagesFolder.mkdirs();
        //añadimos el nombre de la imagen
        File image = new File(imagesFolder, codigoBarras +"("+ requestCode +")"+".jpg");
        Uri uriSavedImage = Uri.fromFile(image);
        //Le decimos al Intent que queremos grabar la imagen
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        //Lanzamos la aplicacion de la camara con retorno (forResult)
        startActivityForResult(cameraIntent, requestCode);
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
