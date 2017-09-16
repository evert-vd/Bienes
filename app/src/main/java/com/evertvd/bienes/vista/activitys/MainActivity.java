package com.evertvd.bienes.vista.activitys;

import android.content.Intent;
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
import android.widget.Toast;

import com.evertvd.bienes.R;
import com.evertvd.bienes.scannercode.MaterialBarcodeScanner;
import com.evertvd.bienes.scannercode.MaterialBarcodeScannerBuilder;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.Catalogo;
import com.evertvd.bienes.modelo.CentroCosto;
import com.evertvd.bienes.modelo.CuentaContable;
import com.evertvd.bienes.modelo.Departamento;
import com.evertvd.bienes.modelo.Empresa;
import com.evertvd.bienes.modelo.Historial;
import com.evertvd.bienes.modelo.Responsable;
import com.evertvd.bienes.modelo.Sede;
import com.evertvd.bienes.modelo.Ubicacion;
import com.evertvd.bienes.utils.Buscar;
import com.evertvd.bienes.vista.dialogs.DBuscarArchivo;
import com.google.android.gms.vision.barcode.Barcode;


import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String BARCODE_KEY = "BARCODE";
    private ScaleGestureDetector mScaleDetector;
    private Barcode barcodeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //cargarEmpresas();

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
            List<Empresa>empresaList= Controller.getDaoSession().getEmpresaDao().loadAll();
            for (int i=0;i<empresaList.size();i++){
                Log.e("Empresa",empresaList.get(i).getEmpresa());
            }

            List<Catalogo>catalogoList= Controller.getDaoSession().getCatalogoDao().loadAll();
            for (int i=0;i<catalogoList.size();i++){
                Log.e("nomCat"+String.valueOf(i+1),catalogoList.get(i).getCatalogo()+" cod:"+catalogoList.get(i).getEmpresa_id2());
            }

        } else if (id == R.id.nav_slideshow) {
            List<Activo>activoList= Controller.getDaoSession().getActivoDao().loadAll();
            for (int i=0;i<activoList.size();i++){
                Log.e("Empresa",activoList.get(i).getCodigo()+" Ubicacion:"+activoList.get(i).getUbicacion().getSede().getDepartamento().getEmpresa().getEmpresa()+ " Catalogo:"+activoList.get(i).getCatalogo().getCatalogo());
            }

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Controller.getDaoSession();
            Controller.getDaoSession().deleteAll(Empresa.class);
            Controller.getDaoSession().deleteAll(Departamento.class);
            Controller.getDaoSession().deleteAll(Sede.class);
            Controller.getDaoSession().deleteAll(Ubicacion.class);
            Controller.getDaoSession().deleteAll(CuentaContable.class);
            Controller.getDaoSession().deleteAll(CentroCosto.class);
            Controller.getDaoSession().deleteAll(Catalogo.class);
            Controller.getDaoSession().deleteAll(Responsable.class);
            Controller.getDaoSession().deleteAll(Activo.class);
            Controller.getDaoSession().deleteAll(Historial.class);
            //Controller.getDaoSession().getAllDaos().clear();



            List<Empresa>empresa=Controller.getDaoSession().getEmpresaDao().loadAll();
            Controller.getDaoSession().getEmpresaDao().deleteAll();
            for(int i=0;i<empresa.size();i++){
            }

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
                Log.d("dato", "zoom ongoing, scale: " + detector.getScaleFactor());
                return false;
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
                        String texto=barcode.rawValue;
                        //result.setText(barcode.rawValue);
                        if(Buscar.buscarBarras(barcode.rawValue)!=null){
                            startActivity(new Intent(getApplicationContext(),BarScanner.class));
                        }else{
                            Toast.makeText(getApplicationContext(),"C.B. "+barcode.rawValue+" no encontrado",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .build();
        materialBarcodeScanner.startScan();
    }

        private void cargarEmpresas(){
            Empresa molinos=new Empresa();
            molinos.setEmpresa("MOLINOS & CIA SA");
            Controller.getDaoSession().getEmpresaDao().insert(molinos);

            Empresa comercio=new Empresa();
            comercio.setEmpresa("COMERCIO & CIA SA");
            Controller.getDaoSession().getEmpresaDao().insert(comercio);

            Empresa miromina=new Empresa();
            miromina.setEmpresa("MIROMINA SA");
            Controller.getDaoSession().getEmpresaDao().insert(miromina);

            Empresa fertimax=new Empresa();
            fertimax.setEmpresa("FERTIMAX SAC");
            Controller.getDaoSession().getEmpresaDao().insert(fertimax);

        }

}
