package com.evertvd.bienes.vista.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evertvd.bienes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewActivoCL extends Fragment {


    public ViewActivoCL() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_activo_cl, container, false);
    }

}
