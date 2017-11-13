package com.example.eduardomartinez.sdm_ilistpro;

import java.io.Serializable;

/**
 * Created by eduardomartinez on 6/11/17.
 */

public class ListaCompra implements Serializable{

    private long id;
    private String nombre;
    private double precio;
    private int imagen;

    public ListaCompra(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
