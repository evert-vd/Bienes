package com.evertvd.bienes.vista.dialogs;

import android.app.Dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Catalogo;
import com.evertvd.bienes.modelo.CentroCosto;
import com.evertvd.bienes.modelo.Departamento;
import com.evertvd.bienes.modelo.Empresa;
import com.evertvd.bienes.modelo.Sede;
import com.evertvd.bienes.modelo.Ubicacion;
import com.evertvd.bienes.modelo.dao.CatalogoDao;
import com.evertvd.bienes.modelo.dao.CentroCostoDao;
import com.evertvd.bienes.modelo.dao.DepartamentoDao;
import com.evertvd.bienes.modelo.dao.EmpresaDao;
import com.evertvd.bienes.modelo.dao.SedeDao;
import com.evertvd.bienes.modelo.dao.UbicacionDao;
import com.evertvd.bienes.searchablespinner.SearchableSpinner;
import com.evertvd.bienes.searchablespinner.interfaces.IStatusListener;
import com.evertvd.bienes.searchablespinner.interfaces.OnItemSelectedListener;
import com.evertvd.bienes.vista.adapters.ListSpinnerSearchAdapter;

import java.util.ArrayList;
import java.util.List;




/**
 * Fragmento con un diálogo personalizado
 */
public class DialgoFilterData extends DialogFragment implements View.OnClickListener {
    private static final String TAG = DialgoFilterData.class.getSimpleName();
    private Button btnDepSedeUbic, btnEmpCatalogo, btnCentro, btnCancelar;

    private ListSpinnerSearchAdapter listAdapterDepartamento, listAdapterSede, listAdapterUbicacion, listAdapterEmpresa, listAdapterCatalogo, listAdapterCentroCosto;
    private SearchableSpinner spinnerDepartamento, spinnerSede, spinnerUbicacion, spinnerEmpresa, spinnerCatalogo, spinnerCentroCosto;

    private ArrayList<String> stringArrayListDepartamento = new ArrayList<String>();
    private ArrayList<String> stringArrayListSede = new ArrayList<String>();
    private ArrayList<String> stringArrayListUbicacion = new ArrayList<String>();
    private ArrayList<String> stringArrayListEmpresa = new ArrayList<String>();
    private ArrayList<String> stringArrayListCatalogo = new ArrayList<String>();
    private ArrayList<String> stringArrayListCentroCosto = new ArrayList<String>();

    private List<Departamento> departamentoList;
    private List<Sede>sedeList;
    private List<Ubicacion>ubicacionList;
    private List<Empresa>empresaList;
    private List<Catalogo>catalogoList;
    private List<CentroCosto>centroCostoList;
    private Departamento departamento=null;
    private Sede sede=null;
    private Ubicacion ubicacion=null;
    private Empresa empresa=null;
    private Catalogo catalogo=null;
    private CentroCosto centroCosto=null;
    private int value;
    private LinearLayout layoutMargen;

    private OnAddFilter filtro;

    //Definimos la interfaz
    public interface OnAddFilter {
        public void onAddFilterDepSedeUbic(Departamento departamento, Sede sede, Ubicacion ubicacion);
        public void onAddFilterEmpCatalogo(Empresa empresa, Catalogo catalogo);
        public void onAddFilterCentroCosto(CentroCosto centroCosto);
        public void onButtonCancel();
    }


    public DialgoFilterData() {
        //this.idProducto=idProducto;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            filtro = (OnAddFilter) getTargetFragment();
        } catch (Exception e) {
            throw new ClassCastException("Calling Fragment must implement OnAddFriendListener");
        }
    }
    /*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Amigos")
                .setMessage("dasdads")
                //.setView(editText)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Grab the text from the input
                        final String friendEmail = "dasd";
                        callback.onAddFriendSubmit(friendEmail);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //AddFriendDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
*/

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        return crearAgregarfiltro();

    }

    /**
     * Crea un diÃ¡logo con personalizado para comportarse
     * como formulario de entrada de cantidad
     *
     * @return DiÃ¡logo
     */


    public AlertDialog crearAgregarfiltro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        value=getArguments().getInt("value");//bundle de fragment principal
        //Toast.makeText(getActivity(),"valor:"+String.valueOf(value),Toast.LENGTH_SHORT).show();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_filter_data, null);
        //View v = inflater.inflate(R.layout.dialogo_registrar_conteo, null);
        btnDepSedeUbic=(Button)view.findViewById(R.id.btnDepSedeUbic);
        btnDepSedeUbic.setOnClickListener(this);
        btnEmpCatalogo=(Button)view.findViewById(R.id.btnEmpCatalogo);
        btnEmpCatalogo.setOnClickListener(this);
        btnCentro=(Button)view.findViewById(R.id.btnCentroCosto);
        btnCentro.setOnClickListener(this);
        btnCancelar=(Button)view.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);
        layoutMargen=(LinearLayout)view.findViewById(R.id.layoutMargen);

        spinnerDepartamentoSedeUbicacion(view);
        spinnerEmpresaCatalogo(view);
        spinnerCentroCosto(view);
        habilitarOCultarControles(value);

        builder.setView(view);
        builder.setTitle("Filtrar Registros");
        builder.setCancelable(false);
        return builder.create();
    }

    private void habilitarOCultarControles(int value) {
        if(value==1){//departamento/sede/ubicacion
            //ocultar
            btnEmpCatalogo.setVisibility(View.GONE);
            btnCentro.setVisibility(View.GONE);
            spinnerEmpresa.setVisibility(View.GONE);
            spinnerCatalogo.setVisibility(View.GONE);
            spinnerCentroCosto.setVisibility(View.GONE);



        }else if(value==2){//empresa/catalogo
            //ocultar
            btnDepSedeUbic.setVisibility(View.GONE);
            btnCentro.setVisibility(View.GONE);
            spinnerDepartamento.setVisibility(View.GONE);
            spinnerSede.setVisibility(View.GONE);
            spinnerUbicacion.setVisibility(View.GONE);
            spinnerCentroCosto.setVisibility(View.GONE);
            //btnEmpCatalogo.setOnClickListener(this);
        }else if(value==3){
            //ocultar
            btnEmpCatalogo.setVisibility(View.GONE);
            btnDepSedeUbic.setVisibility(View.GONE);
            spinnerDepartamento.setVisibility(View.GONE);
            spinnerSede.setVisibility(View.GONE);
            spinnerUbicacion.setVisibility(View.GONE);
            spinnerEmpresa.setVisibility(View.GONE);
            spinnerCatalogo.setVisibility(View.GONE);
            //btnCentro.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnDepSedeUbic) {
            //callback.onSearchDepSedeUbicDialog();
            if(spinnerDepartamento.getSelectedPosition()==0){
                departamento=null;
            }
            if(spinnerSede.getSelectedPosition()==0){
                sede=null;
            }
            if(spinnerUbicacion.getSelectedPosition()==0){
                ubicacion=null;
            }

            OnAddFilter filter = (OnAddFilter) getTargetFragment();
            filter.onAddFilterDepSedeUbic(departamento, sede, ubicacion);
            this.dismiss();

            /*
             RegistrarConteo.OnClickListener activity = (RegistrarConteo.OnClickListener) getActivity();
                activity.onInputDialog(Integer.parseInt(tilCantidad.getEditText().getText().toString()), tilObservacion.getEditText().getText().toString());
                this.dismiss();
             */
            } else if(view.getId()==R.id.btnEmpCatalogo){
            if(spinnerEmpresa.getSelectedPosition()==0){
                empresa=null;
            }
            if(spinnerCatalogo.getSelectedPosition()==0){
                catalogo=null;
            }
            OnAddFilter filter = (OnAddFilter) getTargetFragment();
            filter.onAddFilterEmpCatalogo(empresa, catalogo);
            this.dismiss();

              }else if(view.getId()==R.id.btnCentroCosto){
            if(spinnerCentroCosto.getSelectedPosition()==0){
                centroCosto=null;
            }
            OnAddFilter filter = (OnAddFilter) getTargetFragment();
            filter.onAddFilterCentroCosto(centroCosto);
            this.dismiss();
        }else if(view.getId()==R.id.btnCancelar){
            OnAddFilter filter = (OnAddFilter) getTargetFragment();
            filter.onButtonCancel();
            this.dismiss();
        }
            }


            private OnItemSelectedListener mOnDepartamentoSelectedListener = new OnItemSelectedListener() {
                @Override
                public void onItemSelected(View view, int position, long id) {
                    //Toast.makeText(getActivity(), "Item on position " + position + " : " + listAdapterDepartamento.getItem(position) + " Selected", Toast.LENGTH_SHORT).show();
                    stringArrayListSede.clear();
                    if (position > 0) {
                        departamento = Controller.getDaoSession().getDepartamentoDao().queryBuilder().where(DepartamentoDao.Properties.Departamento.eq(listAdapterDepartamento.getItem(position))).unique();
                        sedeList = Controller.getDaoSession().getSedeDao().queryBuilder().where(SedeDao.Properties.Departamento_id.eq(departamento.getId())).orderAsc(SedeDao.Properties.Sede).list();
                    } else {
                        sedeList = Controller.getDaoSession().getSedeDao().queryBuilder().orderAsc(SedeDao.Properties.Sede).list();
                    }
                    initListSede(sedeList);
                    listAdapterSede = new ListSpinnerSearchAdapter(getActivity(), stringArrayListSede);
                    spinnerSede.setAdapter(listAdapterSede);
                    spinnerSede.setSelectedItem(0);
                    spinnerUbicacion.setSelectedItem(0);

                }

                @Override
                public void onNothingSelected() {
                    //Toast.makeText(getActivity(), "Nothing Selected", Toast.LENGTH_SHORT).show();
                }
            };

            private OnItemSelectedListener mOnSedeSelectedListener = new OnItemSelectedListener() {
                @Override
                public void onItemSelected(View view, int position, long id) {
                    //Toast.makeText(getActivity(), "Item on position " + position + " : " + mSimpleListAdapter2.getItem(position) + " Selected", Toast.LENGTH_SHORT).show();
                    stringArrayListUbicacion.clear();
                    if (position > 0) {
                        sede = Controller.getDaoSession().getSedeDao().queryBuilder().where(SedeDao.Properties.Sede.eq(listAdapterSede.getItem(position))).unique();
                        ubicacionList = Controller.getDaoSession().getUbicacionDao().queryBuilder().where(UbicacionDao.Properties.Sede_id.eq(sede.getId())).orderAsc(UbicacionDao.Properties.Ubicacion).list();
                    } else {
                        ubicacionList = Controller.getDaoSession().getUbicacionDao().queryBuilder().orderAsc(UbicacionDao.Properties.Ubicacion).list();
                    }
                    initListUbicacion(ubicacionList);
                    listAdapterUbicacion = new ListSpinnerSearchAdapter(getActivity(), stringArrayListUbicacion);
                    spinnerUbicacion.setAdapter(listAdapterUbicacion);
                    //spinnerSede.setSelectedItem(0);
                    spinnerUbicacion.setSelectedItem(0);
                }

                @Override
                public void onNothingSelected() {
                    //Toast.makeText(getActivity(), "Nothing Selected", Toast.LENGTH_SHORT).show();
                }
            };

            private OnItemSelectedListener mOnUbicacionSelectedListener = new OnItemSelectedListener() {
                @Override
                public void onItemSelected(View view, int position, long id) {
                    //Toast.makeText(getActivity(), "Item on position " + position + " : " + mSimpleListAdapter3.getItem(position) + " Selected", Toast.LENGTH_SHORT).show();
                    if(position>0){
                        ubicacion = Controller.getDaoSession().getUbicacionDao().queryBuilder().where(UbicacionDao.Properties.Ubicacion.eq(listAdapterUbicacion.getItem(position))).unique();
                    }
                    //
                }

                @Override
                public void onNothingSelected() {
                    //Toast.makeText(getActivity(), "Nothing Selected", Toast.LENGTH_SHORT).show();
                }
            };


    private OnItemSelectedListener mOnEmpresaSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(View view, int position, long id) {
            //Toast.makeText(getActivity(), "Item on position " + position + " : " + mSimpleListAdapter3.getItem(position) + " Selected", Toast.LENGTH_SHORT).show();
            stringArrayListCatalogo.clear();
            if (position > 0) {
               empresa = Controller.getDaoSession().getEmpresaDao().queryBuilder().where(EmpresaDao.Properties.Empresa.eq(listAdapterEmpresa.getItem(position))).unique();
                catalogoList = Controller.getDaoSession().getCatalogoDao().queryBuilder().where(CatalogoDao.Properties.Empresa_id.eq(empresa.getId())).orderAsc(CatalogoDao.Properties.Catalogo).list();
           } else {
                catalogoList = Controller.getDaoSession().getCatalogoDao().queryBuilder().orderAsc(CatalogoDao.Properties.Catalogo).list();
            }
            initListCatalogo(catalogoList);
            listAdapterCatalogo = new ListSpinnerSearchAdapter(getActivity(), stringArrayListCatalogo);
            spinnerCatalogo.setAdapter(listAdapterCatalogo);
            //spinnerSede.setSelectedItem(0);
            spinnerCatalogo.setSelectedItem(0);

            //
        }

        @Override
        public void onNothingSelected() {
            //Toast.makeText(getActivity(), "Nothing Selected", Toast.LENGTH_SHORT).show();
        }
    };

    private OnItemSelectedListener mOnCatalogoSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(View view, int position, long id) {
            //Toast.makeText(getActivity(), "Item on position " + position + " : " + mSimpleListAdapter3.getItem(position) + " Selected", Toast.LENGTH_SHORT).show();
            if(position>0){
                //empresa = Controller.getDaoSession().getEmpresaDao().queryBuilder().where(EmpresaDao.Properties.Empresa.eq(listAdapterEmpresa.getItem(position))).unique();
                List<Catalogo>catalogoList1=Controller.getDaoSession().getCatalogoDao().queryBuilder().where(CatalogoDao.Properties.Catalogo.eq(listAdapterCatalogo.getItem(position))).list();
                Log.e("TAG", String.valueOf(position));

                catalogo=catalogoList1.get(0);

                //inicializa objeto a enviar
                //catalogo = Controller.getDaoSession().getCatalogoDao().queryBuilder().where(CatalogoDao.Properties.Catalogo.eq(listAdapterCatalogo.getItem(position))).unique();
            }
        }

        @Override
        public void onNothingSelected() {
            //Toast.makeText(getActivity(), "Nothing Selected", Toast.LENGTH_SHORT).show();
        }
    };

    private OnItemSelectedListener mOnCentroCostoSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(View view, int position, long id) {
            //Toast.makeText(getActivity(), "Item on position " + position + " : " + mSimpleListAdapter3.getItem(position) + " Selected", Toast.LENGTH_SHORT).show();
            if(position>0){
                centroCosto = Controller.getDaoSession().getCentroCostoDao().queryBuilder().where(CentroCostoDao.Properties.Centro.eq(listAdapterCentroCosto.getItem(position))).unique();
            }
            //
        }

        @Override
        public void onNothingSelected() {
            //Toast.makeText(getActivity(), "Nothing Selected", Toast.LENGTH_SHORT).show();
        }
    };

        private void spinnerDepartamentoSedeUbicacion(View view){
            departamentoList= Controller.getDaoSession().getDepartamentoDao().queryBuilder().orderAsc(DepartamentoDao.Properties.Departamento).list();
            sedeList= Controller.getDaoSession().getSedeDao().queryBuilder().orderAsc(SedeDao.Properties.Sede).list();
            ubicacionList= Controller.getDaoSession().getUbicacionDao().queryBuilder().orderAsc(UbicacionDao.Properties.Ubicacion).list();
            initListDepartamento(departamentoList);
            initListSede(sedeList);
            initListUbicacion(ubicacionList);
            //mStrings=Controller.getDaoSession().getEmpresaDao().loadAll();
            listAdapterDepartamento = new ListSpinnerSearchAdapter(getActivity(), stringArrayListDepartamento);
            listAdapterSede = new ListSpinnerSearchAdapter(getActivity(), stringArrayListSede);
            listAdapterUbicacion = new ListSpinnerSearchAdapter(getActivity(), stringArrayListUbicacion);

            spinnerDepartamento = (SearchableSpinner) view.findViewById(R.id.searchDepartamento);
            spinnerDepartamento.setAdapter(listAdapterDepartamento);
            spinnerDepartamento.setOnItemSelectedListener(mOnDepartamentoSelectedListener);
            spinnerDepartamento.setStatusListener(new IStatusListener() {
                @Override
                public void spinnerIsOpening() {
                    spinnerSede.hideEdit();
                    spinnerUbicacion.hideEdit();
                }

                @Override
                public void spinnerIsClosing() {
                }
            });

            spinnerSede = (SearchableSpinner) view.findViewById(R.id.searchSede);
            spinnerSede.setAdapter(listAdapterSede);
            spinnerSede.setOnItemSelectedListener(mOnSedeSelectedListener);
            spinnerSede.setStatusListener(new IStatusListener() {
                @Override
                public void spinnerIsOpening() {
                    spinnerDepartamento.hideEdit();
                    spinnerUbicacion.hideEdit();

                }

                @Override
                public void spinnerIsClosing() {

                }
            });

            spinnerUbicacion = (SearchableSpinner)view.findViewById(R.id.searchUbicacion);
            spinnerUbicacion.setAdapter(listAdapterUbicacion);
            spinnerUbicacion.setOnItemSelectedListener(mOnUbicacionSelectedListener);
            spinnerUbicacion.setStatusListener(new IStatusListener() {
                @Override
                public void spinnerIsOpening() {
                    spinnerDepartamento.hideEdit();
                    spinnerSede.hideEdit();
                    layoutMargen.setVisibility(View.VISIBLE);
                }

                @Override
                public void spinnerIsClosing() {
                    layoutMargen.setVisibility(View.GONE);
                }
            });
        }

        private void spinnerEmpresaCatalogo(View view){
            empresaList= Controller.getDaoSession().getEmpresaDao().queryBuilder().orderAsc(EmpresaDao.Properties.Empresa).list();
            catalogoList= Controller.getDaoSession().getCatalogoDao().queryBuilder().orderAsc(CatalogoDao.Properties.Catalogo).list();

            initListEmpresa(empresaList);
            initListCatalogo(catalogoList);

            //mStrings=Controller.getDaoSession().getEmpresaDao().loadAll();
            listAdapterEmpresa = new ListSpinnerSearchAdapter(getActivity(), stringArrayListEmpresa);
            listAdapterCatalogo = new ListSpinnerSearchAdapter(getActivity(), stringArrayListCatalogo);

            spinnerEmpresa = (SearchableSpinner) view.findViewById(R.id.searchEmpresa);
            spinnerEmpresa.setAdapter(listAdapterEmpresa);
            spinnerEmpresa.setOnItemSelectedListener(mOnEmpresaSelectedListener);
            spinnerEmpresa.setStatusListener(new IStatusListener() {
                @Override
                public void spinnerIsOpening() {
                    spinnerCatalogo.hideEdit();
                    //spinnerUbicacion.hideEdit();
                }

                @Override
                public void spinnerIsClosing() {
                }
            });

            spinnerCatalogo = (SearchableSpinner) view.findViewById(R.id.searchCatalogo);
            spinnerCatalogo.setAdapter(listAdapterCatalogo);
            spinnerCatalogo.setOnItemSelectedListener(mOnCatalogoSelectedListener);
            spinnerCatalogo.setStatusListener(new IStatusListener() {
                @Override
                public void spinnerIsOpening() {
                    spinnerEmpresa.hideEdit();
                    //spinnerUbicacion.hideEdit();
                    layoutMargen.setVisibility(View.VISIBLE);

                }

                @Override
                public void spinnerIsClosing() {
                    layoutMargen.setVisibility(View.GONE);
                }
            });
        }

        private void spinnerCentroCosto(View view){
            centroCostoList= Controller.getDaoSession().getCentroCostoDao().queryBuilder().orderAsc(CentroCostoDao.Properties.Centro).list();

            initListCentroCosto(centroCostoList);

            //mStrings=Controller.getDaoSession().getEmpresaDao().loadAll();
            listAdapterCentroCosto = new ListSpinnerSearchAdapter(getActivity(), stringArrayListCentroCosto);

            spinnerCentroCosto = (SearchableSpinner) view.findViewById(R.id.searchCentroCosto);
            spinnerCentroCosto.setAdapter(listAdapterCentroCosto);
            spinnerCentroCosto.setOnItemSelectedListener(mOnCentroCostoSelectedListener);
            spinnerCentroCosto.setStatusListener(new IStatusListener() {
                @Override
                public void spinnerIsOpening() {
                    //spinnerCatalogo.hideEdit();
                    //spinnerUbicacion.hideEdit();
                    layoutMargen.setVisibility(View.VISIBLE);
                }

                @Override
                public void spinnerIsClosing() {
                    layoutMargen.setVisibility(View.GONE);
                }
            });
        }


        private void initListDepartamento (List < Departamento > departamentoList) {
            for (int i = 0; i < departamentoList.size(); i++) {
                stringArrayListDepartamento.add(departamentoList.get(i).getDepartamento());
            }
        }

        private void initListSede (List < Sede > sedeList) {
            for (int i = 0; i < sedeList.size(); i++) {
                stringArrayListSede.add(sedeList.get(i).getSede());
            }
        }

        private void initListUbicacion (List < Ubicacion > ubicacionList) {
            for (int i = 0; i < ubicacionList.size(); i++) {
                stringArrayListUbicacion.add(ubicacionList.get(i).getUbicacion());
            }
        }

    private void initListEmpresa (List < Empresa > empresaList) {
        for (int i = 0; i < empresaList.size(); i++) {
            stringArrayListEmpresa.add(empresaList.get(i).getEmpresa());
        }
    }

    private void initListCatalogo (List < Catalogo > catalogoList) {
        for (int i = 0; i < catalogoList.size(); i++) {
            if(!stringArrayListCatalogo.contains(catalogoList.get(i).getCatalogo())){
                stringArrayListCatalogo.add(catalogoList.get(i).getCatalogo());
            }

        }
    }

    private void initListCentroCosto (List < CentroCosto > centroCostoList) {
        for (int i = 0; i < centroCostoList.size(); i++) {
            stringArrayListCentroCosto.add(centroCostoList.get(i).getCentro());
        }
    }
        }

