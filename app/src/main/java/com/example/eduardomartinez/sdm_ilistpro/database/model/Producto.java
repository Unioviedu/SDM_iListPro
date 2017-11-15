package com.example.eduardomartinez.sdm_ilistpro.database.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by karolmc on 15/11/2017.
 */

@Entity
public class Producto implements Serializable {
    static final long serialVersionUID = 1;

    @Id
    private Long id;
    private String supermercado;
    private String nombre;
    private Double precio;
    private Integer foto;
    private Long codigo_barra;
    private Integer categoria;


    public Producto() {
    }


    @Generated(hash = 1052805079)
    public Producto(Long id, String supermercado, String nombre, Double precio,
            Integer foto, Long codigo_barra, Integer categoria) {
        this.id = id;
        this.supermercado = supermercado;
        this.nombre = nombre;
        this.precio = precio;
        this.foto = foto;
        this.codigo_barra = codigo_barra;
        this.categoria = categoria;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getFoto() {
        return foto;
    }

    public void setFoto(Integer foto) {
        this.foto = foto;
    }

    public Long getCodigoBarra() {
        return codigo_barra;
    }

    public void setCodigoBarra(Long codigoBarra) {
        this.codigo_barra = codigoBarra;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Producto producto = (Producto) o;

        return id != null ? id.equals(producto.id) : producto.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }

    public Long getCodigo_barra() {
        return this.codigo_barra;
    }

    public void setCodigo_barra(Long codigo_barra) {
        this.codigo_barra = codigo_barra;
    }

    public void setSupermercado(String supermercado) {
        this.supermercado = supermercado;
    }


    public String getSupermercado() {
        return this.supermercado;
    }
}
