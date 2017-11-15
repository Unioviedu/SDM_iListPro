package com.example.eduardomartinez.sdm_ilistpro.database;

import android.app.Application;
import android.util.Log;

import com.example.eduardomartinez.sdm_ilistpro.Supermercado;
import com.example.eduardomartinez.sdm_ilistpro.TiposProducto;
import com.example.eduardomartinez.sdm_ilistpro.database.model.DaoMaster;
import com.example.eduardomartinez.sdm_ilistpro.database.model.DaoSession;
import com.example.eduardomartinez.sdm_ilistpro.database.model.JoinProductoConListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.database.model.ListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.database.model.ListaCompraDao;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Producto;
import com.example.eduardomartinez.sdm_ilistpro.database.model.ProductoDao;
import com.example.eduardomartinez.sdm_ilistpro.database.model.SupermercadoDao;

import java.util.List;


/**
 * Created by karolmc on 14/11/2017.
 */

public class DatabaseORM {
    private static DatabaseORM db;

    private Application app;
    private DaoMaster.DevOpenHelper helper;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ProductoDao productoDao;
    private ListaCompraDao listaCompraDao;
    private SupermercadoDao supermercadoDao;

    private DatabaseORM(Application app) {
        this.app = app;
        helper = new DaoMaster.DevOpenHelper(app, "prueba2.db", null);
        daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        productoDao = daoSession.getProductoDao();
        listaCompraDao = daoSession.getListaCompraDao();
        supermercadoDao = daoSession.getSupermercadoDao();
        //inicializarProductos();
        //inicializarListasCompra();
    }

    public static DatabaseORM getInstance(Application app){
        if(db == null)
            return db = new DatabaseORM(app);
        return db;
    }

    public static DatabaseORM getInstance(){
        return db;
    }




    private void inicializarProductos(){
       productoDao.insert(new Producto(null, Supermercado.ALIMERKA, "Carne1", 10.0, null, null, TiposProducto.CARNE));
       productoDao.insert(new Producto(null, Supermercado.ALIMERKA, "Carne2", 15.0, null, null, TiposProducto.CARNE));
       productoDao.insert(new Producto(null, Supermercado.ALIMERKA, "Pescado1", 5.0, null, null, TiposProducto.PESCADO));
       productoDao.insert(new Producto(null, Supermercado.MERCADONA, "Pescado1", 5.0, null, null, TiposProducto.PESCADO));
       productoDao.insert(new Producto(null, Supermercado.MERCADONA, "Fruta", 1.5, null, null, TiposProducto.FRUTA));
       productoDao.insert(new Producto(null, Supermercado.CORTE_INGLES, "Fruta", 3.5, null, null, TiposProducto.FRUTA));
       productoDao.insert(new Producto(null, Supermercado.MAS_MAS, "Verdura", 2.5, null, null, TiposProducto.VERDURA));
       productoDao.insert(new Producto(null, Supermercado.MAS_MAS, "Verdura2", 1.75, null, null, TiposProducto.VERDURA));
    }

    private void inicializarListasCompra(){
        List<Producto> productos = productoDao.loadAll();
        ListaCompra lista = new ListaCompra(null, "prueba2", null);
        lista.addProductos(productos);
        listaCompraDao.insert(lista);
    }

    public List<ListaCompra> getAllListaCompra(){
        List<ListaCompra> listas = listaCompraDao.loadAll();
        for(ListaCompra l: listas)
            l.getProductos();
        return listas;
    }

    public List<Producto> getAllProductos() {
        return productoDao.loadAll();
    }

    public void saveListaCompra(ListaCompra lista) {

        listaCompraDao.insert(lista);
    }

    public void updateListaCompra(ListaCompra lista) {
        listaCompraDao.update(lista);
    }

    public List<Producto> getProductosDeListaCompra(Long id){
        return null;
    }


}
