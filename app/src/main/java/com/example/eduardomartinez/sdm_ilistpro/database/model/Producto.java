package com.example.eduardomartinez.sdm_ilistpro.database.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import java.util.List;

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
    @ToMany()
    @JoinEntity(entity = JoinProductoConListaCompra.class,
            sourceProperty = "producto_id",
            targetProperty = "listaCompra_id"
    )
    private List<ListaCompra> listasCompra;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2021018510)
    private transient ProductoDao myDao;


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


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 869940023)
    public List<ListaCompra> getListasCompra() {
        if (listasCompra == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ListaCompraDao targetDao = daoSession.getListaCompraDao();
            List<ListaCompra> listasCompraNew = targetDao
                    ._queryProducto_ListasCompra(id);
            synchronized (this) {
                if (listasCompra == null) {
                    listasCompra = listasCompraNew;
                }
            }
        }
        return listasCompra;
    }


    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 352377749)
    public synchronized void resetListasCompra() {
        listasCompra = null;
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1100354616)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProductoDao() : null;
    }
}
