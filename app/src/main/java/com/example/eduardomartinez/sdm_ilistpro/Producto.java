package com.example.eduardomartinez.sdm_ilistpro;

import java.io.Serializable;

/**
 * Created by eduardomartinez on 7/11/17.
 */

public class Producto implements Serializable{
    private String nombre;
    private double precio;
    private String supermercado;
    private int tipo;
    private int imagen;

    private long id;

    public Producto(String nombre, double precio, String supermercado, int tipo, long id) {
        this.nombre = nombre;
        this.precio = precio;
        this.supermercado = supermercado;
        this.tipo = tipo;
        this.id = id;
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

    public String getSupermercado() {
        return supermercado;
    }

    public void setSupermercado(String supermercado) {
        this.supermercado = supermercado;
    }

    public long getId() {return id; }

    public int getTipo() {
        return tipo;
    }
}
