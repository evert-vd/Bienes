package com.evertvd.bienes.vista.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.Catalogo;
import com.evertvd.bienes.modelo.CentroCosto;
import com.evertvd.bienes.modelo.Departamento;
import com.evertvd.bienes.modelo.Historial;
import com.evertvd.bienes.modelo.Responsable;
import com.evertvd.bienes.modelo.Sede;
import com.evertvd.bienes.modelo.Ubicacion;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.modelo.dao.CatalogoDao;
import com.evertvd.bienes.utils.Buscar;

import java.util.ArrayList;
import java.util.List;

public class ActivoEdit extends AppCompatActivity{

    private String numActivo;
    private Activo activo;
    private AutoCompleteTextView txtDepartamento, txtCatalogo, txtSede, txtUbicacion, txtCentro, txtResponsable, txtPlaca, txtMarca, txtModelo;
    private EditText txtDescripcion, txtCodigo, txtBarras, txtOC, txtFactura, txtFecha, txtEmpresa, txtCodCentro, txtSerie;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activo_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //camposModificados = new ArrayList<>();
        numActivo=getIntent().getExtras().getString("activo");
        activo = Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Codigo.eq(this.getIntent().getExtras().getString("activo"))).unique();
        setTitle("Editar activo "+activo.getCodigo());


        txtCodigo = (EditText) findViewById(R.id.txtCodigo);
        txtCodigo.setText(activo.getCodigo());
        txtCodigo.setFocusable(false);
        txtCodigo.clearFocus();

        txtOC = (EditText)findViewById(R.id.txtOrdenCompra);
        txtOC.setText(activo.getOrdencompra());
        txtOC.setFocusable(false);

        txtFactura = (EditText)findViewById(R.id.txtFactura);
        txtFactura.setText(activo.getFactura());
        txtFactura.setFocusable(false);

        txtFecha = (EditText) findViewById(R.id.txtFecha);
        txtFecha.setText(activo.getFechacompra());
        txtFecha.setFocusable(false);

        txtEmpresa = (EditText) findViewById(R.id.txtEmpresa);
        txtEmpresa.setText(activo.getCatalogo().getEmpresa().getEmpresa());
        txtEmpresa.setFocusable(false);

        txtSede = (AutoCompleteTextView) findViewById(R.id.txtSede);
        txtSede.setText(activo.getUbicacion().getSede().getSede());
        txtSede.setFocusable(false);

        txtDepartamento = (AutoCompleteTextView) findViewById(R.id.txtDepartamento);
        txtDepartamento.setText(activo.getUbicacion().getSede().getDepartamento().getDepartamento());


        //campos editables
        txtDescripcion = (EditText)findViewById(R.id.txtDescipcion);
        txtDescripcion.setText(activo.getDescripcion());

        txtBarras = (EditText)findViewById(R.id.txtBarras);
        txtBarras.setText(activo.getCodigobarra());

        txtCatalogo = (AutoCompleteTextView) findViewById(R.id.txtCatalogo);
        txtCatalogo.setText(activo.getCatalogo().getCatalogo());

        txtUbicacion = (AutoCompleteTextView) findViewById(R.id.txtUbicacion);
        txtUbicacion.setText(activo.getUbicacion().getUbicacion());

        txtCentro = (AutoCompleteTextView) findViewById(R.id.txtCentro);
        txtCentro.setText(activo.getCentroCosto().getCentro());

        txtCodCentro = (EditText) findViewById(R.id.txtCodCentro);
        txtCodCentro.setText(activo.getCentroCosto().getCodigo());
        txtCodCentro.setFocusable(false);

        txtSerie = (EditText) findViewById(R.id.txtSerie);
        txtSerie.setText(activo.getSerie());

        txtResponsable = (AutoCompleteTextView) findViewById(R.id.txtResponsable);
        txtResponsable.setText(activo.getResponsable().getResponsable());

        txtPlaca = (AutoCompleteTextView) findViewById(R.id.txtPlaca);
        txtPlaca.setText(activo.getPlaca());

        txtMarca = (AutoCompleteTextView) findViewById(R.id.txtMarca);
        txtMarca.setText(activo.getMarca());

        txtModelo = (AutoCompleteTextView) findViewById(R.id.txtModelo);
        txtModelo.setText(activo.getModelo());


        List<Departamento> departamentoList = Controller.getDaoSession().getDepartamentoDao().loadAll();
        List<Catalogo> catalogoList = Controller.getDaoSession().getCatalogoDao().queryBuilder().where(CatalogoDao.Properties.Empresa_id.eq(activo.getCatalogo().getEmpresa_id())).list();
        List<Sede> sedeList = Controller.getDaoSession().getSedeDao().loadAll();
        List<Ubicacion> ubicacionList = Controller.getDaoSession().getUbicacionDao().loadAll();
        List<CentroCosto> centroCostoList = Controller.getDaoSession().getCentroCostoDao().loadAll();
        List<Responsable> responsableList = Controller.getDaoSession().getResponsableDao().loadAll();

        ArrayAdapter<Departamento> adapterDepartamento = new ArrayAdapter<Departamento>(this, android.R.layout.simple_list_item_1, departamentoList);
        txtDepartamento.setThreshold(1);
        txtDepartamento.setAdapter(adapterDepartamento);
        //txtDepartamento.setTextColor(Color.BLUE);

        ArrayAdapter<Catalogo> adapterCatalogo = new ArrayAdapter<Catalogo>(this, android.R.layout.simple_list_item_1, catalogoList);
        txtCatalogo.setThreshold(1);
        txtCatalogo.setAdapter(adapterCatalogo);
        ocultarTeclado(txtCatalogo);

        ArrayAdapter<Sede> adapterSede = new ArrayAdapter<Sede>(this, android.R.layout.simple_list_item_1, sedeList);
        txtSede.setThreshold(1);
        txtSede.setAdapter(adapterSede);

        ArrayAdapter<Ubicacion> adapterUbicacion = new ArrayAdapter<Ubicacion>(this, android.R.layout.simple_list_item_1, ubicacionList);
        txtUbicacion.setThreshold(1);
        txtUbicacion.setAdapter(adapterUbicacion);
        ocultarTeclado(txtUbicacion);

        ArrayAdapter<CentroCosto> adapterCentroCosto = new ArrayAdapter<CentroCosto>(this, android.R.layout.simple_list_item_1, centroCostoList);
        txtCentro.setThreshold(1);
        txtCentro.setAdapter(adapterCentroCosto);
        ocultarTeclado(txtCentro);

        ArrayAdapter<Responsable> adapterResponsable = new ArrayAdapter<Responsable>(this, android.R.layout.simple_list_item_1, responsableList);
        txtResponsable.setThreshold(1);
        txtResponsable.setAdapter(adapterResponsable);
        ocultarTeclado(txtResponsable);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_edit, menu);
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
        if (id == R.id.action_save) {
            guardarCambios();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void guardarCambios(){
            String descripcion=txtDescripcion.getText().toString().trim().toUpperCase();
            String barra=txtBarras.getText().toString().trim().toUpperCase();
            String catalogo=txtCatalogo.getText().toString().trim().toUpperCase();
            String departamento=txtDepartamento.getText().toString().trim().toUpperCase();
            String sede=txtSede.getText().toString().trim().toUpperCase();
            String ubicacion=txtUbicacion.getText().toString().trim().toUpperCase();
            String centro=txtCentro.getText().toString().trim().toUpperCase();
            String responsable=txtResponsable.getText().toString().trim().toUpperCase();
            String placa=txtPlaca.getText().toString().trim().toUpperCase();
            String marca=txtMarca.getText().toString().trim().toUpperCase();
            String modelo=txtModelo.getText().toString().trim().toUpperCase();
            String serie=txtSerie.getText().toString().trim().toUpperCase();

            //guardar cambios
            boolean editDescripcion=editDescripcion(descripcion);
            boolean editBarras=editBarras(barra);
            boolean editCatalogo=editCatalogo(catalogo);
            boolean editUbicacion=editUbicacion(ubicacion);
            boolean editCentro=editCentro(centro);
            boolean editResponsable=editResponsable(responsable);
            boolean editPlaca=editPlaca(placa);
            boolean editMarca=editMarca(marca);
            boolean editModelo=editModelo(modelo);
            boolean editSerie=editSerie(serie);

               if(editDescripcion || editBarras || editCatalogo || editUbicacion || editCentro || editResponsable|| editPlaca ||editMarca||editModelo||editSerie){
               List<String> camposModificados=new ArrayList<>();
                if(editDescripcion){
                    camposModificados.add("descripcion");
                }

                if(editBarras){
                    camposModificados.add("barra");
                }

                if(editCatalogo){
                    camposModificados.add("catalogo");
                }

                if(editSerie){
                    camposModificados.add("serie");
                }

                if(editModelo){
                    camposModificados.add("modelo");
                }

                if(editCentro){
                    camposModificados.add("centro");
                }

                if(editMarca){
                    camposModificados.add("marca");
                }

                if(editPlaca){
                    camposModificados.add("placa");
                }

                if(editResponsable){
                    camposModificados.add("responsable");
                }

                if(editUbicacion){
                    camposModificados.add("ubicacion");
                }

                actualizarHistorialCambios(camposModificados);

                Intent intent=new Intent(this,ActivoView.class);
                intent.putExtra("activo",numActivo);
                startActivity(intent);
                finish();
                Toast.makeText(this,"Cambio Realizado, se actualizó la información", Toast.LENGTH_SHORT).show();
                //Log.e("sede", String.valueOf(activo.getUbicacion().getSede().getDepartamento().getDepartamento()));
            }else{
                Toast.makeText(this,"Ningún Cambio Realizado", Toast.LENGTH_SHORT).show();
            }
        }


    private boolean editDescripcion(String texto){
        boolean estado=false;
        if(!activo.getDescripcion().equalsIgnoreCase(texto)){
            activo.setDescripcion(texto);
            Controller.getDaoSession().getActivoDao().update(activo);
            estado= true;
        }
        return estado;
    }

    private boolean editBarras(String texto){
        boolean estado=false;
        if(!activo.getCodigobarra().equalsIgnoreCase(texto)){
            activo.setCodigobarra(texto);
            Controller.getDaoSession().getActivoDao().update(activo);
            estado=true;

        }
        return estado;
    }

    private boolean editCatalogo(String texto){
        boolean estado=false;
        if(!activo.getCatalogo().getCatalogo().equalsIgnoreCase(texto)) {
            if (Buscar.buscarCatalogo(texto, txtEmpresa.getText().toString())== null) {
                crearNuevoCatalogo(texto);
            }
            activo.setCatalogo_id(Buscar.buscarCatalogo(texto, txtEmpresa.getText().toString()).getId());
            Controller.getDaoSession().getActivoDao().update(activo);

            estado=true;
        }
        return estado;
    }



    private boolean editUbicacion (String texto){
        boolean estado=false;
        if(!activo.getUbicacion().getUbicacion().equalsIgnoreCase(texto)){
            if(Buscar.buscarUbicacion(texto)==null){
                crearUbicacion(texto);
            }
            activo.setUbicacion_id(Buscar.buscarUbicacion(texto).getId());
            Controller.getDaoSession().getActivoDao().update(activo);

            estado= true;
        }
        return estado;
    }

    private boolean editCentro (String texto){
        boolean estado=false;
        if(!activo.getCentroCosto().getCentro().equalsIgnoreCase(texto)){
            if(Buscar.buscarCentro(texto)==null){
                crearCentro(texto);
            }
            activo.setCentrocosto_id(Buscar.buscarCentro(texto).getId());
            Controller.getDaoSession().getActivoDao().update(activo);
            estado= true;
        }
        return estado;
    }

    private boolean editResponsable (String texto){
        boolean estado=false;
        if(!activo.getResponsable().getResponsable().equalsIgnoreCase(texto)){
            if(Buscar.buscarResponsable(texto)==null){
                crearResponsable(texto);
            }
            activo.setResponsable_id(Buscar.buscarResponsable(texto).getId());
            Controller.getDaoSession().getActivoDao().update(activo);

            estado= true;
        }
        return estado;
    }

    private boolean editPlaca(String texto){
        boolean estado=false;
        if(!activo.getPlaca().equalsIgnoreCase(texto)){
            activo.setPlaca(texto);
            Controller.getDaoSession().getActivoDao().update(activo);
            estado= true;
        }
        return estado;
    }

    private boolean editMarca(String texto){
        boolean estado=false;
        if(!activo.getMarca().equalsIgnoreCase(texto)){
            activo.setMarca(texto);
            Controller.getDaoSession().getActivoDao().update(activo);
            estado= true;
        }
        return estado;
    }

    private boolean editModelo (String texto){
        boolean estado=false;
        if(!activo.getModelo().equalsIgnoreCase(texto)){
            activo.setModelo(texto);
            Controller.getDaoSession().getActivoDao().update(activo);
            estado= true;
        }
        return estado;
    }

    private boolean editSerie(String texto){
        boolean estado=false;
        if(!activo.getSerie().equalsIgnoreCase(texto)){
            activo.setSerie(texto);
            Controller.getDaoSession().getActivoDao().update(activo);
            estado= true;
        }
        return estado;
    }




    private void crearNuevoCatalogo(String texto){
        Catalogo catalogo=new Catalogo();
        catalogo.setCatalogo(texto);
        catalogo.setEmpresa_id(Buscar.buscarEmpresa(txtEmpresa.getText().toString()).getId());
        Controller.getDaoSession().getCatalogoDao().insert(catalogo);

    }


    private void crearUbicacion(String texto){
        Ubicacion ubicacion=new Ubicacion();
        ubicacion.setUbicacion(texto);
        ubicacion.setSede_id(Buscar.buscarSede(txtSede.getText().toString()).getId());
        Controller.getDaoSession().getUbicacionDao().insert(ubicacion);
    }

    private void crearCentro(String texto){
        CentroCosto centroCosto=new CentroCosto();
        centroCosto.setCentro(texto);
        Controller.getDaoSession().getCentroCostoDao().insert(centroCosto);
    }

    private void crearResponsable(String texto){
        Responsable responsable=new Responsable();
        responsable.setResponsable(texto);
        Controller.getDaoSession().getResponsableDao().insert(responsable);
    }

    private void actualizarHistorialCambios(List<String>camposModificados){

        if(!camposModificados.isEmpty()){
            for (int i=0;i<camposModificados.size();i++){
                if(Buscar.buscarHistorial(camposModificados.get(i),activo.getId())==null){
                    Historial historial=new Historial();
                    historial.setActivo_id(activo.getId());
                    historial.setCampo_modificado(camposModificados.get(i));
                    historial.setFecha_modificacion("");
                    Controller.getDaoSession().getHistorialDao().insert(historial);
                }
            }

        }else{
            Log.e("TAG", "lista historial vacía");
        }
    }

    private void ocultarTeclado(final AutoCompleteTextView autoCompleteTextView){
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);
            }
        });
    }

}

