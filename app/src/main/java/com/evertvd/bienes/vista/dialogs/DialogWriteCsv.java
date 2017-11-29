package com.evertvd.bienes.vista.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.evertvd.bienes.R;

import static com.evertvd.bienes.R.id.textView;

/**
 * Fragmento con un diálogo personalizado
 */
public class DialogWriteCsv extends DialogFragment implements View.OnClickListener {
    private static final String TAG = DialogWriteCsv.class.getSimpleName();
    private Button btnNuevo, btnCancelar, btnReemplazar;
    private TextView txtMensaje1;

    //Definimos la interfaz
    public interface OnClickListener {
    void botonDialogOnClick(int evento);
    }

    public DialogWriteCsv() {
        //this.idProducto=idProducto;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme);
    }

    @Override
    public void onStart() {
        super.onStart();
        //dialog pantalla completa
        /*Dialog d = getDialog();
        if (d!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }*/
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        return crearDialogoViewCollage();
    }

    /**
     * Crea un diÃƒÂ¡logo con personalizado para comportarse
     * como formulario de entrada de cantidad
     *
     * @return DiÃƒÂ¡logo
     */
    public AlertDialog crearDialogoViewCollage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_write_data, null);

        txtMensaje1=(TextView)view.findViewById(R.id.txtMensaje1);

        String mensaje1="El archivo";
        String mensaje2=getActivity().getString(R.string.nombreFileExport)+getActivity().getString(R.string.extensionFileExport);
        String mensaje3="ya existe. Seleccionar una opción:";
        SpannableString mensaje = new SpannableString(mensaje1+" "+mensaje2+" "+mensaje3);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorGreyDarken_4));// Puedes usar tambien .. new ForegroundColorSpan(Color.RED);
        mensaje.setSpan(colorSpan, mensaje1.length()+1, (mensaje1.length()+mensaje2.length()+1), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        txtMensaje1.setText(mensaje);

        //txtMensaje1.setText("Ya existe un archivo ");
        //txtMensaje2.setText(nombreArchivo);
        //txtMensaje3.setText(". Seleccione una opción..!");
        //txtMensaje.setText("Existe un archivo con el nombre "+ Html.fromHtml("<b>"+nombreArchivo+"</b>")+ ".Seleccione una opción");

        btnNuevo = (Button) view.findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(this);

        btnReemplazar = (Button) view.findViewById(R.id.btnReemplazar);
        btnReemplazar.setOnClickListener(this);

        btnCancelar = (Button) view.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);

        builder.setView(view);
        builder.setTitle("Advertencia");
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnNuevo) {
            this.dismiss();
            DialogWriteCsv.OnClickListener filter = (DialogWriteCsv.OnClickListener) getTargetFragment();
            filter.botonDialogOnClick(1);


        } else if (v.getId() == R.id.btnReemplazar) {
            this.dismiss();
            DialogWriteCsv.OnClickListener filter = (DialogWriteCsv.OnClickListener) getTargetFragment();
            filter.botonDialogOnClick(2);

        } else {
            this.dismiss();
        }
    }

}

