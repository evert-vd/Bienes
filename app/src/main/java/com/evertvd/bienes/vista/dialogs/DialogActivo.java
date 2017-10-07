package com.evertvd.bienes.vista.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.evertvd.bienes.R;

/**
 * Created by evertvd on 22/09/2017.
 */

public class DialogActivo extends DialogFragment {

    View view;
    private ProgressBar progressBar;

    public DialogActivo(){

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        // this.idProducto=idProducto;
        //recuperacion del parametro que viene del punto de invocacion del dialog--viene como string
        // idProducto = getArguments().getInt("idProducto");

        return crearDialogo();
    }

    /**
     * Crea un diÃƒÂ¡logo con personalizado para comportarse
     * como formulario de entrada de cantidad
     *
     * @return DiÃƒÂ¡logo
     */
    public AlertDialog crearDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_task, null);
        //View v = inflater.inflate(R.layout.dialogo_registrar_conteo, null);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);

        builder.setView(view);
        builder.setTitle("Mensaje");
        builder.setMessage("Se encontró más de un activo con el codigo de barras");
        return builder.create();
    }


}
