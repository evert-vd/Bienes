package com.evertvd.bienes.vista.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.allenxuan.xuanyihuang.xuanimageview.XuanImageView;
import com.evertvd.bienes.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class CollageActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 2;

    private Button btnGuardar;
    private ImageView collageImage;
    private Bitmap mergedImages;
    private ImageButton btnFoto1,btnClose1,btnRotar1, btnClose2,btnFoto2,btnFoto3,btnClose3;
    //private PhotoView photoView2, photoView3;
    private XuanImageView photoView1,photoView2,photoView3;

    ImageView[] collage = {photoView1, photoView2, photoView3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(this);

        collageImage = (ImageView) findViewById(R.id.imgCombinar);


        //photoView1 = (PhotoView) findViewById(R.id.photo_view1);
        photoView1 = (XuanImageView) findViewById(R.id.photo_view1);
        photoView2 = (XuanImageView) findViewById(R.id.photo_view2);
        photoView3 = (XuanImageView) findViewById(R.id.photo_view3);

        //Relacionamos con el XML


        btnFoto1 = (ImageButton) this.findViewById(R.id.btnFoto1);
        btnFoto1.setOnClickListener(this);
        btnClose1=(ImageButton) this.findViewById(R.id.btnClose1);
        btnClose1.setOnClickListener(this);
        btnRotar1=(ImageButton)findViewById(R.id.btnRotar1);
        btnRotar1.setOnClickListener(this);


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
        if (v.getId() == R.id.btnGuardar) {

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
            collageImage.setImageBitmap(mergedImages);



            guardarImagen(mergedImages);


        }else if(v.getId()==R.id.btnFoto1){
            tomarFoto(1);
        }else if(v.getId()==R.id.btnFoto2){
            tomarFoto(2);
        }else if(v.getId()==R.id.btnFoto3){
            tomarFoto(3);
        }else if(v.getId()==R.id.btnClose1){
            btnFoto1.setVisibility(View.VISIBLE);
            btnClose1.setVisibility(View.GONE);
        }else if(v.getId()==R.id.btnClose2){
            btnFoto2.setVisibility(View.VISIBLE);
            btnClose2.setVisibility(View.GONE);
        }else if(v.getId()==R.id.btnClose3){
            btnFoto3.setVisibility(View.VISIBLE);
            btnClose3.setVisibility(View.GONE);
        }else if(v.getId()==R.id.btnRotar1){
            Bitmap bMap = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/FotosPruebasAF/" + "foto" + 1 + ".jpg");

            //photoView1.buildDrawingCache();
            //Bitmap map1 = photoView1.getDrawingCache();
            rotarImagen(bMap);
        }
    }

    private void rotarImagen(Bitmap original) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
        photoView1.setImageBitmap(rotatedBitmap);
    }

    private void guardarImagen(Bitmap imagen) {
        OutputStream fileOutStream = null;
        Uri uri;
        try {
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "imagenesguardadas" + File.separator);
            file.mkdirs();
            File directorioImagenes = new File(file, "mi_imagen.jpg");
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

        canvas.drawRect(0,0,(photoView1.getMeasuredWidth()+photoView2.getMeasuredWidth())-1,photoView1.getMeasuredHeight()-1,paint);//dibuja rectangulo
        canvas.drawLine(photoView1.getMeasuredWidth(), 0, photoView1.getMeasuredWidth(), photoView1.getMeasuredHeight(), paint);//dibuja linea vertical
        canvas.drawLine(photoView1.getMeasuredWidth(),photoView2.getMeasuredHeight(),photoView1.getMeasuredWidth()+photoView2.getMeasuredWidth(),photoView2.getMeasuredHeight(),paint);//dubuja linea horizontal

        return result;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprovamos que la foto se a realizado
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Creamos un bitmap con la imagen recientemente
            //almacenada en la memoria
            Bitmap bMap = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/FotosPruebasAF/" + "foto" + requestCode + ".jpg");
            //Añadimos el bitmap al imageView para
            //mostrarlo por pantalla
            //img.setImageBitmap(bMap);
            photoView1.setVisibility(View.VISIBLE);
            photoView1.setImageBitmap(bMap);
            collage[0] = photoView1;

            btnFoto1.setVisibility(View.GONE);
            btnClose1.setVisibility(View.VISIBLE);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            Bitmap bMap = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/FotosPruebasAF/" + "foto" + requestCode + ".jpg");
            //Añadimos el bitmap al imageView para
            //mostrarlo por pantalla
            //img.setImageBitmap(bMap);
            photoView2.setVisibility(View.VISIBLE);
            photoView2.setImageBitmap(bMap);
            collage[1] = photoView2;
            btnFoto2.setVisibility(View.GONE);
            btnClose2.setVisibility(View.VISIBLE);
        } else if (requestCode == 3 && resultCode == RESULT_OK) {
            Bitmap bMap = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/FotosPruebasAF/" + "foto" + requestCode + ".jpg");
            //Añadimos el bitmap al imageView para
            //mostrarlo por pantalla
            //img.setImageBitmap(bMap);
            photoView3.setVisibility(View.VISIBLE);
            photoView3.setImageBitmap(bMap);
            collage[2] = photoView3;
            btnFoto3.setVisibility(View.GONE);
            btnClose3.setVisibility(View.VISIBLE);
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
        File image = new File(imagesFolder, "foto"+requestCode+".jpg");
        Uri uriSavedImage = Uri.fromFile(image);
        //Le decimos al Intent que queremos grabar la imagen
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        //Lanzamos la aplicacion de la camara con retorno (forResult)
        startActivityForResult(cameraIntent, requestCode);
    }
}
