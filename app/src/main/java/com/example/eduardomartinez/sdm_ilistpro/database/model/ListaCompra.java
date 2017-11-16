package com.example.eduardomartinez.sdm_ilistpro.database.model;

import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToMany;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by karolmc on 15/11/2017.
 */
@Entity
public class ListaCompra implements Serializable {

    static final long serialVersionUID = 1;

    @Id
    private Long id;
    private String nombre;
    private Integer imagen;
    @ToMany()
    @JoinEntity(entity = JoinProductoConListaCompra.class,
    sourceProperty = "listaCompra_id",
    targetProperty = "producto_id"
    )
    private List<Producto> productos = new LinkedList<>();

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 682974165)
    private transient ListaCompraDao myDao;

    public ListaCompra() {
    }

    public ListaCompra(String nombre) {
        this.nombre = nombre;
    }

    @Generated(hash = 13077465)
    public ListaCompra(Long id, String nombre, Integer imagen) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
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

    public Integer getImagen() {
        return imagen;
    }

    public void setImagen(Integer imagen) {
        this.imagen = imagen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListaCompra that = (ListaCompra) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    public void addProducto(Producto producto){
        productos.add(producto);
    }

    public void addProductos(List<Producto> productos){
        this.productos.addAll(productos);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ListaCompra{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", productos=" + productos +
                '}';
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1863614475)
    public synchronized void resetProductos() {
        productos = null;
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

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1043042392)
    public List<Producto> getProductos() {
        if (productos == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProductoDao targetDao = daoSession.getProductoDao();
            List<Producto> productosNew = targetDao._queryListaCompra_Productos(id);
            synchronized (this) {
                if (productos == null) {
                    productos = productosNew;
                }
            }
        }
        return productos;
    }

    public Producto comprarProducto (long id) {
        //AQUI DEBERIA IR LA LOGICA DE COMPRAR PRODUCTO
        for (Producto p: productos)
            if (p.getId() == id)
                return p;

        return null;
    }

    public double getImporteTotal() {
        double importe = 0.0;
        for (Producto p: productos)
            importe += p.getPrecio();

        return importe;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1456840910)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getListaCompraDao() : null;
    }
}