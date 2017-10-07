package com.evertvd.bienes.vista.activitys;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.evertvd.bienes.R;
import com.evertvd.bienes.vista.fragments.Collage1;
import com.evertvd.bienes.vista.fragments.Collage2;
import com.evertvd.bienes.vista.fragments.Collage3;



public class CollageActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 2;

    private Menu menu;
    private Toolbar toolbar;
    String numActivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);

        toolbar = (Toolbar) findViewById(R.id.toolbarCollage);
        setSupportActionBar(toolbar);

        numActivo=getIntent().getStringExtra("activo");

        abrirFragmentCollage(1);

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

    private void abrirFragmentCollage(int i) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(i==1){
            Collage1 collage1 = new Collage1();
            transaction.replace(R.id.content_collage,collage1);
        }else if(i==2){
            Collage2 collage2 = new Collage2();
            transaction.replace(R.id.content_collage,collage2);
        }else if(i==3){
            Collage3 collage3 = new Collage3();
            transaction.replace(R.id.content_collage,collage3);
        }
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_collage, menu);
        this.menu=menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //MenuItem item1=item;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id==R.id.action_collage1){
            menu.getItem(1).setIcon(R.drawable.ic_collage1b);
            menu.getItem(2).setIcon(R.drawable.ic_collage2);
            menu.getItem(3).setIcon(R.drawable.ic_collage3);
            abrirFragmentCollage(1);

        }else if(id==R.id.action_collage2){
            menu.getItem(1).setIcon(R.drawable.ic_collage1);
            menu.getItem(2).setIcon(R.drawable.ic_collage2b);
            menu.getItem(3).setIcon(R.drawable.ic_collage3);
            abrirFragmentCollage(2);
        }else if(id==R.id.action_collage3){
            menu.getItem(1).setIcon(R.drawable.ic_collage1);
            menu.getItem(2).setIcon(R.drawable.ic_collage2);
            menu.getItem(3).setIcon(R.drawable.ic_collage3b);
            abrirFragmentCollage(3);
        }

        return super.onOptionsItemSelected(item);
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



}
