package com.example.eduardomartinez.sdm_ilistpro.database;

import android.app.Application;

import com.example.eduardomartinez.sdm_ilistpro.database.model.DaoMaster;
import com.example.eduardomartinez.sdm_ilistpro.database.model.DaoSession;
import com.example.eduardomartinez.sdm_ilistpro.database.model.ListaCompraDao;
import com.example.eduardomartinez.sdm_ilistpro.database.model.ProductoDao;
import com.example.eduardomartinez.sdm_ilistpro.database.model.SupermercadoDao;


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
        helper = new DaoMaster.DevOpenHelper(app, "prueba.db", null);
        daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        productoDao = daoSession.getProductoDao();
        listaCompraDao = daoSession.getListaCompraDao();
        supermercadoDao = daoSession.getSupermercadoDao();
    }

    public static DatabaseORM getInstance(Application app){
        if(db == null)
            return db = new DatabaseORM(app);
        return db;
    }

    public ProductoDao getProductoDao() {
        return productoDao;
    }

    public ListaCompraDao getListaCompraDao() {
        return listaCompraDao;
    }

    public SupermercadoDao getSupermercadoDao() {
        return supermercadoDao;
    }


}
