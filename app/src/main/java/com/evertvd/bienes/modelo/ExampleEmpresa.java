package com.evertvd.bienes.modelo;

/**
 * Created by evertvd on 16/09/2017.
 */

public class ExampleEmpresa {
    private String nombre;


    public ExampleEmpresa() {
    }

    public String toString() {
        return String.format("%s - %s - %f", nombre);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
