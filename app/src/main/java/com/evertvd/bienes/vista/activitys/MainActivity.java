package com.evertvd.bienes.vista.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import android.view.ScaleGestureDetector;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.evertvd.bienes.R;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.scannercode.MaterialBarcodeScanner;
import com.evertvd.bienes.scannercode.MaterialBarcodeScannerBuilder;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;

import com.evertvd.bienes.modelo.Historial;

import com.evertvd.bienes.utils.Buscar;
import com.evertvd.bienes.utils.TareaCarga;
import com.evertvd.bienes.utils.TareaRead;
import com.evertvd.bienes.vista.dialogs.DBuscarArchivo;
import com.google.android.gms.vision.barcode.Barcode;


import java.util.ArrayList;
import java.util.List;

import static com.evertvd.bienes.modelo.dao.ActivoDao.Properties.Catalogo;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String BARCODE_KEY = "BARCODE";
    private ScaleGestureDetector mScaleDetector;
    private Barcode barcodeResult;
    private ListView lisActivo;
    int zoomFactor=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //cargarEmpresas();
        lisActivo=(ListView)findViewById(R.id.listActivo);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                startScan();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if(savedInstanceState != null){
            Barcode restoredBarcode = savedInstanceState.getParcelable(BARCODE_KEY);
            if(restoredBarcode != null){
                Log.e("Resultado", restoredBarcode.rawValue);
                //result.setText(restoredBarcode.rawValue);
                barcodeResult = restoredBarcode;
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BARCODE_KEY, barcodeResult);
        super.onSaveInstanceState(outState);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            FragmentManager fragmentManager=getSupportFragmentManager();
            DBuscarArchivo dialogCierreInventario = new DBuscarArchivo();
            dialogCierreInventario.setCancelable(false);
            dialogCierreInventario.show(fragmentManager, "dialogo buscar Archivo");
        } else if (id == R.id.nav_gallery) {
           /* List<Empresa>empresaList= Controller.getDaoSession().getEmpresaDao().loadAll();
            for (int i=0;i<empresaList.size();i++){
                Log.e("Empresa",empresaList.get(i).getEmpresa());
            }

            List<Catalogo>catalogoList= Controller.getDaoSession().getCatalogoDao().loadAll();
            for (int i=0;i<catalogoList.size();i++){
                Log.e("nomCat"+String.valueOf(i+1),catalogoList.get(i).getCatalogo()+" cod:"+catalogoList.get(i).getEmpresa_id2());
            }
            */

            ProgressDialog progress = new ProgressDialog(this);
            //progreesDialog.setMax(20);
            //progreesDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setMessage("Cargando data, por favor espere...");
            new TareaRead(progress, this).execute();

        } else if (id == R.id.nav_slideshow) {
            List<Activo> activoDaoList=Controller.getDaoSession().getActivoDao().loadAll();
            ArrayList<String> lista=new ArrayList<String>();
            Log.e("tamaño",String.valueOf(activoDaoList.size()));
            for (int i=0;i<activoDaoList.size();i++){
                lista.add(String.valueOf(i)+" activo"+activoDaoList.get(i).getCodigo());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, lista);


            // Assign adapter to ListView
            lisActivo.setAdapter(adapter);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            //Controller.getDaoSession();
            //Controller.getDaoSession().deleteAll(Empresa.class);
            //Controller.getDaoSession().deleteAll(Departamento.class);
            //Controller.getDaoSession().deleteAll(Sede.class);
            //Controller.getDaoSession().deleteAll(Ubicacion.class);
            //Controller.getDaoSession().deleteAll(CuentaContable.class);
            //Controller.getDaoSession().deleteAll(CentroCosto.class);
            //Controller.getDaoSession().deleteAll(Catalogo.class);
            //Controller.getDaoSession().deleteAll(Responsable.class);
            Controller.getDaoSession().deleteAll(Activo.class);
            Controller.getDaoSession().deleteAll(Historial.class);
            //Controller.getDaoSession().getAllDaos().clear();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startScan() {

        mScaleDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
            }
            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }
            @Override
            public boolean onScale(ScaleGestureDetector detector) {




                return true;
            }
        });



        /**
         * Build a new MaterialBarcodeScanner
         */
        final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                .withActivity(MainActivity.this)
                .withEnableAutoFocus(true)
                .withBleepEnabled(true)
                .withBackfacingCamera()
                .withCenterTracker()

                //.withCenterTracker(R.drawable.common_full_open_on_phone, R.drawable.common_google_signin_btn_icon_dark)
                .withText("Buscando Código de Barras")
                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                    @Override
                    public void onResult(Barcode barcode) {
                        barcodeResult = barcode;
                        //String texto=barcode.rawValue;
                        //result.setText(barcode.rawValue);
                        List<Activo>activoList=Buscar.buscarBarras(barcode.rawValue);
                        Log.e("CB encontrado", barcode.rawValue);


                        if(!activoList.isEmpty()){
                         for (int i=0;i<activoList.size();i++){
                             activoList.get(i).setSeleccionado(1);
                             Controller.getDaoSession().getActivoDao().update(activoList.get(i));
                         }
                            startActivity(new Intent(getApplicationContext(),BarScanner.class));

                        }else{
                            Toast.makeText(getApplicationContext(),"C.B. "+barcode.rawValue+" no encontrado",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .build();
        materialBarcodeScanner.startScan();
    }



}
