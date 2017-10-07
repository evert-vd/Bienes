package com.evertvd.bienes.vista.adapters.PruebaAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.Catalogo;
import com.evertvd.bienes.modelo.Empresa;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.modelo.dao.CatalogoDao;
import com.kenber.view.DoubleStickHeadersListAdapter;
import com.kenber.view.DoubleStickyHeaderListView;

import java.util.List;
import java.util.Locale;

/**
 * Created by evertvd on 02/10/2017.
 */

public class ListAdapter extends ArrayAdapter<ListItem> implements DoubleStickHeadersListAdapter {
    private static final int LEVEL0_HEADERS_NUMBER = 10;
    private static final int LEVEL1_HEADERS_NUMBER = 4;
    private static final int LEVEL2_HEADERS_NUMBER = 5;

    private static final int BG_COLOR_LEVEL_0 = android.R.color.holo_purple;
    private static final int BG_COLOR_LEVEL_1 = android.R.color.holo_green_light;
    private static final int BG_COLOR_LEVEL_2 = android.R.color.white;
    public ListAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        genListData();
    }

    public void genListData() {


        List<Empresa> empresaList=Controller.getDaoSession().getEmpresaDao().loadAll();


        for (int i = 0; i < empresaList.size(); i++) {
            ListItem level0Header = new ListItem(DoubleStickyHeaderListView.HEADER_LEVEL_0, empresaList.get(i).getEmpresa());
            add(level0Header);

        //List<Catalogo> catalogoList=Controller.getDaoSession().getCatalogoDao().loadAll();
            List<Catalogo>catalogoList=Controller.getDaoSession().getCatalogoDao().queryBuilder().where(CatalogoDao.Properties.EmpresaId.eq(empresaList.get(i).getId())).list();
            for (int j = 0; j < catalogoList.size(); j++) {
                ListItem level1Header = new ListItem(DoubleStickyHeaderListView.HEADER_LEVEL_1, catalogoList.get(j).getCatalogo());
                add(level1Header);

                List<Activo>activoList=Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Catalogo_id.eq(catalogoList.get(j).getId())).list();
                for (int k = 0; k < activoList.size(); k++) {
                    ListItem level2Header = new ListItem(DoubleStickyHeaderListView.HEADER_LEVEL_2, activoList.get(k).getDescripcion());
                    add(level2Header);
                }
            }

        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTextColor(Color.BLACK);
        view.setTag("" + position);
        int level = getHeaderLevel(position);
        if (level == DoubleStickyHeaderListView.HEADER_LEVEL_0) {
            view.setBackgroundColor(parent.getResources().getColor(BG_COLOR_LEVEL_0));
        } else
        if (level == DoubleStickyHeaderListView.HEADER_LEVEL_1) {
            view.setBackgroundColor(parent.getResources().getColor(BG_COLOR_LEVEL_1));
        } else {
            view.setBackgroundColor(parent.getResources().getColor(BG_COLOR_LEVEL_2));
        }
        return view;
    }

    @Override
    public int getHeaderLevel(int position) {
        return getItem(position).level;
    }
}
