package com.evertvd.bienes.vista.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import android.app.ListActivity;
import com.evertvd.bienes.R;

import com.evertvd.bienes.vista.adapters.PruebaAdapters.ListAdapter;
import com.evertvd.bienes.vista.adapters.PruebaAdapters.ListItem;

public class GrupoActivity extends ListActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);

        setListAdapter(new ListAdapter(this, R.layout.view_list_item, R.id.text1));


    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ListItem item  = (ListItem) getListView().getAdapter().getItem(position);
        if (item != null) {
            Toast.makeText(getApplicationContext(), "Item " + position + ": level " + item.level + ", text: " + item.text, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Item " + position + ": not exist", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Item: " + view.getTag(), Toast.LENGTH_SHORT).show();
    }
}
