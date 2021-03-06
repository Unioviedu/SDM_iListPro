package com.example.eduardomartinez.sdm_ilistpro.database.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by karolmc on 15/11/2017.
 */

@Entity
public class JoinProductoConListaCompra implements Serializable{

    static final long serialVersionUID = 1;

    @Id
    private Long id;
    private Long producto_id;
    private Long listaCompra_id;
    private Boolean comprado;
    private Integer cantidad;
    @Generated(hash = 2071196357)
    public JoinProductoConListaCompra(Long id, Long producto_id,
            Long listaCompra_id, Boolean comprado, Integer cantidad) {
        this.id = id;
        this.producto_id = producto_id;
        this.listaCompra_id = listaCompra_id;
        this.comprado = comprado;
        this.cantidad = cantidad;
    }
    @Generated(hash = 579603966)
    public JoinProductoConListaCompra() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getProducto_id() {
        return this.producto_id;
    }
    public void setProducto_id(Long producto_id) {
        this.producto_id = producto_id;
    }
    public Long getListaCompra_id() {
        return this.listaCompra_id;
    }
    public void setListaCompra_id(Long listaCompra_id) {
        this.listaCompra_id = listaCompra_id;
    }
    public Boolean getComprado() {
        return this.comprado;
    }
    public void setComprado(Boolean comprado) {
        this.comprado = comprado;
    }

    @Override
    public String toString() {
        return "JoinProductoConListaCompra{" +
                "producto_id=" + producto_id +
                ", listaCompra_id=" + listaCompra_id +
                ", comprado=" + comprado +
                '}';
    }
    public Integer getCantidad() {
        return this.cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
