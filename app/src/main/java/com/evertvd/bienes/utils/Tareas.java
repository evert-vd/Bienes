package com.evertvd.bienes.utils;

import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.ActivoAll;
import com.evertvd.bienes.modelo.Departamento;
import com.evertvd.bienes.modelo.Empresa;
import com.evertvd.bienes.modelo.Sede;
import com.evertvd.bienes.modelo.Ubicacion;

/**
 * Created by evertvd on 28/09/2017.
 */

public class Tareas implements Runnable {
/*
    String empresa;
    String codigo ;
    String barras;
    String departamento ;
    String sede ;
    String ubicacion ;
    String codCatalogo ;
    String nomCatalogo;
    String placa ;
    String marca ;
    String modelo;
    String serie ;
    String foto ;
    String responsable ;
    String tipoActivo ;
    String activoPadre ;
    String codCentro ;
    String centroCosto ;
    String cuenta ;
    String estado ;
    String expediente;
    String ordenCompra;
    String factura ;
    String fecCompra;
    String observacion;
    */
    Empresa empresa;
    Departamento departamento;
    Sede sede;
    Ubicacion ubicacion;
    Activo activo;
    JavaCsvData data;


    public void Tareas(ActivoAll activoAll,String path){
        this.empresa=empresa;
        this.departamento=departamento;
        this.sede=sede;
        this.ubicacion=ubicacion;
        this.activo=activo;
        //this.data=data;
    }

    @Override
    public void run() {
        this.data.leerArchivo();
    }
}
