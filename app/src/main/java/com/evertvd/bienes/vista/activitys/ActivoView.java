package com.evertvd.bienes.vista.activitys;

import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.utils.MainDirectorios;

import java.io.File;

public class ActivoView extends AppCompatActivity implements View.OnClickListener {

    int request_code = 1;
    private TextView txtTipoActivo;
    private TextView txtCodigo, txtBarras, txtDepartamento, txtSede, txtUbicacion, txtCodCatalogo, txtCatalogo,
            txtDecripcion, txtPlaca, txtMarca, txtModelo, txtSerie, txtCodCentro, txtCentro, txtResponsable,
            txtEmpresa, txtOC, txtFactura, txtFecCompra, txtFoto;
    private ImageButton imgEstado;

    private FloatingActionButton fabCamera;
    Activo activo;

    public String numActivo;
    private Toolbar toolbar;
    private Menu menu;
    private ImageView imgActivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activo);
        numActivo = getIntent().getExtras().getString("activo");
        activo = Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Codigo.eq(this.getIntent().getExtras().getString("activo"))).unique();
        //activo= Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Codigo.eq(getIntent().getExtras().getString("activo"))).unique();

        toolbar = (Toolbar) findViewById(R.id.toolbarActivo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(activo.getCodigo());

        //Toast.makeText(getActivity(),activo.getDescripcion(),Toast.LENGTH_SHORT).show();

        //fabEditar = (FloatingActionButton) findViewById(R.id.fabEditar);
        //fabEditar.setOnClickListener(this);


        //this.imgCamera = (ImageButton) findViewById(R.id.imgCamera);
        this.imgEstado = (ImageButton) findViewById(R.id.imgEstado);
        this.txtCodigo = (TextView) findViewById(R.id.txtCodigo);
        this.txtBarras = (TextView) findViewById(R.id.txtBarras);
        this.txtDepartamento = (TextView) findViewById(R.id.edtDepartamento);
        this.txtSede = (TextView) findViewById(R.id.txtSede);
        this.txtUbicacion = (TextView) findViewById(R.id.txtUbicacion);
        //this.txtCodCatalogo = (TextView)view. findViewById(R.id.txtCodCatalogo1);
        this.txtCatalogo = (TextView) findViewById(R.id.txtCatalogo);
        this.txtTipoActivo = (TextView) findViewById(R.id.txtTipoActivo);
        this.txtDecripcion = (TextView) findViewById(R.id.txtDescipcion);
        this.txtPlaca = (TextView) findViewById(R.id.txtPlaca);
        this.txtMarca = (TextView) findViewById(R.id.txtMarca);
        this.txtModelo = (TextView) findViewById(R.id.txtModelo);
        this.txtSerie = (TextView) findViewById(R.id.txtSerie);
        this.txtCodCentro = (TextView) findViewById(R.id.txtCodCentro);
        this.txtCentro = (TextView) findViewById(R.id.txtCentro);
        this.txtResponsable = (TextView) findViewById(R.id.txtResponsable);
        this.txtEmpresa = (TextView) findViewById(R.id.txtEmpresa);
        this.txtOC = (TextView) findViewById(R.id.txtOrdenCompra);
        this.txtFactura = (TextView) findViewById(R.id.txtFactura);
        this.txtFecCompra = (TextView) findViewById(R.id.txtFecha);
        this.txtFoto = (TextView) findViewById(R.id.txtFoto);

        // Inflate the layout for this fragment
        //imgCamera.setOnClickListener(this);
        cargarDatos();

        imgActivo = (ImageView) findViewById(R.id.imgActivo);
        File file = new File(MainDirectorios.obtenerDirectorioFotos(this, numActivo));
        if (file != null) {
            Glide.with(this)
                    .load(MainDirectorios.obtenerDirectorioFotos(this, numActivo))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .placeholder(R.drawable.untitled)
                    .into(imgActivo);
        }


        /*if(numActivo!=null){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            ViewActivo viewActivo = new ViewActivo();
            transaction.replace(R.id.content_activo_view,viewActivo);
            transaction.commit();
        }*/

        //fabCamera = (FloatingActionButton) findViewById(R.id.fabCamera);
        //fabCamera.setOnClickListener(this);

        /*
        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    showOption(R.id.action_camera);
                } else if (isShow) {
                    isShow = false;
                    hideOption(R.id.action_camera);
                }
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_view, menu);
        //hideOption(R.id.action_camera);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_camera) {
            Intent intent=new Intent(this,CollageActivity.class);
            intent.putExtra("activo",numActivo);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_edit) {
            Intent intent=new Intent(this,ActivoEdit.class);
            intent.putExtra("activo",numActivo);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
/*
    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }
    */
    @Override
    public void onClick(View view) {
        /*if (view.getId()== R.id.fabCamera) {
            Intent intent = new Intent(this, CollageActivity.class);
            intent.putExtra("activo", numActivo);
            startActivity(intent);
        }else if(view.getId()==R.id.imgCamera){
            Intent intent=new Intent(this,CollageActivity.class);
            intent.putExtra("activo",numActivo);
            startActivity(intent);
        }
        }*/
    }


    private void cargarDatos() {
        txtDecripcion.setText(activo.getDescripcion());
        txtCodigo.setText(activo.getCodigo());
        txtBarras.setText(activo.getCodigobarra());
        txtOC.setText(activo.getOrdencompra());
        txtFactura.setText(activo.getFactura());
        txtFecCompra.setText(activo.getFechacompra());
        txtCatalogo.setText(activo.getCatalogo().getCatalogo());
        txtEmpresa.setText(activo.getCatalogo().getEmpresa().getEmpresa());
        txtDepartamento.setText(activo.getUbicacion().getSede().getDepartamento().getDepartamento());
        txtSede.setText(activo.getUbicacion().getSede().getSede());
        txtUbicacion.setText(activo.getUbicacion().getUbicacion());
        txtCodCentro.setText(activo.getCentroCosto().getCodigo());
        txtCentro.setText(activo.getCentroCosto().getCentro());
        txtResponsable.setText(activo.getResponsable().getResponsable());
        txtPlaca.setText(activo.getPlaca());
        txtMarca.setText(activo.getMarca());
        txtModelo.setText(activo.getModelo());
        txtSerie.setText(activo.getSerie());

        //comprobar si existe foto guardada
        File ubicacionReporte = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String archivo = activo.getCodigo() + getString(R.string.extensionFoto);
        File reporte = new File(ubicacionReporte, getResources().getString(R.string.directorioFotos)+"/"+archivo);

        if (activo.getFoto().equalsIgnoreCase("si")) {
            txtFoto.setText(activo.getFoto() + "|" + activo.getOrigenfoto());
        }else if(reporte.exists()){
            txtFoto.setText("Si|Aplicación");
        }else {
            txtFoto.setText(activo.getFoto());
        }

        if (activo.getEstado().equalsIgnoreCase("\u00FB")) {
            imgEstado.setBackgroundColor(getResources().getColor(R.color.colorRed));
            imgEstado.setImageResource(R.drawable.ic_close_white);
        }
    }
}

