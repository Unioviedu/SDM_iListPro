package com.example.eduardomartinez.sdm_ilistpro.database;

import android.app.Application;

import com.example.eduardomartinez.sdm_ilistpro.GestorNewListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.SupermercadoNombres;
import com.example.eduardomartinez.sdm_ilistpro.TiposProducto;
import com.example.eduardomartinez.sdm_ilistpro.database.model.DaoMaster;
import com.example.eduardomartinez.sdm_ilistpro.database.model.DaoSession;
import com.example.eduardomartinez.sdm_ilistpro.database.model.JoinProductoConListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.database.model.JoinProductoConListaCompraDao;
import com.example.eduardomartinez.sdm_ilistpro.database.model.ListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.database.model.ListaCompraDao;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Producto;
import com.example.eduardomartinez.sdm_ilistpro.database.model.ProductoDao;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Supermercado;
import com.example.eduardomartinez.sdm_ilistpro.database.model.SupermercadoDao;

import java.util.LinkedList;
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
    private JoinProductoConListaCompraDao productoConListaCompraDao;

    private DatabaseORM(Application app) {
        this.app = app;
        helper = new DaoMaster.DevOpenHelper(app, "prueba0001.db", null);
        daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        productoDao = daoSession.getProductoDao();
        listaCompraDao = daoSession.getListaCompraDao();
        supermercadoDao = daoSession.getSupermercadoDao();
        productoConListaCompraDao = daoSession.getJoinProductoConListaCompraDao();

        inicializarProductos();
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
        List<Producto> productos = getAllProductos();

        if (productos.size() == 0) {
            productoDao.insert(new Producto(null, SupermercadoNombres.ALIMERKA, "Carne1", 10.0, null, "0001", TiposProducto.CARNE));
            productoDao.insert(new Producto(null, SupermercadoNombres.ALIMERKA, "Carne2", 15.0, null, "0002", TiposProducto.CARNE));
            productoDao.insert(new Producto(null, SupermercadoNombres.ALIMERKA, "Pescado1", 5.0, null, "0003", TiposProducto.PESCADO));
            productoDao.insert(new Producto(null, SupermercadoNombres.MERCADONA, "Pescado1", 5.0, null, "0004", TiposProducto.PESCADO));
            productoDao.insert(new Producto(null, SupermercadoNombres.MERCADONA, "Fruta", 1.5, null, "0005", TiposProducto.FRUTA));
            productoDao.insert(new Producto(null, SupermercadoNombres.CORTE, "Fruta", 3.5, null, "0006", TiposProducto.FRUTA));
            productoDao.insert(new Producto(null, SupermercadoNombres.MAS, "Verdura", 2.5, null, "0007", TiposProducto.VERDURA));
            productoDao.insert(new Producto(null, SupermercadoNombres.MAS, "Verdura2", 1.75, null, "0008", TiposProducto.VERDURA));
        }
    }

    public ListaCompra getListaCompra(long id) {
        return listaCompraDao.load(id);
    }

    public List<ListaCompra> getAllListaCompra(){
        List<ListaCompra> listas = listaCompraDao.loadAll();
        for(ListaCompra l: listas) {
            l.resetProductos();
            l.getProductos();
        }
        return listas;
    }

    public List<Producto> getAllProductos() {
        return productoDao.loadAll();
    }

    public void saveListaCompra(ListaCompra lista) {
        listaCompraDao.insert(lista);

        for(Producto p: lista.getProductos()){
            productoConListaCompraDao.insert(new JoinProductoConListaCompra(null, p.getId(), lista.getId(), false, GestorNewListaCompra.getInstance().cantidadProducto(p.getId())));
        }
        lista.resetProductos();
        lista.getProductos();

    }

    public void deleteListaCompra(ListaCompra lista) {

        for (Producto p: lista.getProductos()) {
            productoConListaCompraDao.queryBuilder()
                    .where(JoinProductoConListaCompraDao.Properties.ListaCompra_id.eq(lista.getId()))
                    .buildDelete();
        }

        listaCompraDao.delete(lista);
    }

    public void updateListaCompra(ListaCompra lista) {
        productoConListaCompraDao.queryBuilder().where(JoinProductoConListaCompraDao.Properties.ListaCompra_id.eq(lista.getId())).buildDelete().executeDeleteWithoutDetachingEntities();
        for(Producto p: lista.getProductos()){
            productoConListaCompraDao.insert(new JoinProductoConListaCompra(null, p.getId(), lista.getId(), false, GestorNewListaCompra.getInstance().cantidadProducto(p.getId())));
        }
    }

    public void marcarComprado(Long listaId, Long productoId, boolean comprado){
        JoinProductoConListaCompra p = productoConListaCompraDao.queryBuilder()
                .where(JoinProductoConListaCompraDao.Properties.Producto_id.eq(productoId))
                .where(JoinProductoConListaCompraDao.Properties.ListaCompra_id.eq(listaId))
                .list().get(0);
        p.setComprado(comprado);
        productoConListaCompraDao.update(p);
    }

    public Integer getCantidadProducto(Long listaId, Long productoId){
        return productoConListaCompraDao.queryBuilder()
                .where(JoinProductoConListaCompraDao.Properties.Producto_id.eq(productoId))
                .where(JoinProductoConListaCompraDao.Properties.ListaCompra_id.eq(listaId))
                .list().get(0).getCantidad();
    }

    public boolean isComprado(Long listaId, Long productoId) {
        return productoConListaCompraDao.queryBuilder()
                .where(JoinProductoConListaCompraDao.Properties.Producto_id.eq(productoId))
                .where(JoinProductoConListaCompraDao.Properties.ListaCompra_id.eq(listaId))
                .list().get(0).getComprado();
    }

    public List<Producto> getProductosSupermercado(String supermercado){
        return productoDao.queryBuilder()
                .where(ProductoDao.Properties.Supermercado.eq(supermercado))
                .list();
    }

    public boolean isListaAcabada(Long idLista) {
        int tamañoProductosComprados = productoConListaCompraDao.queryBuilder()
                .where(JoinProductoConListaCompraDao.Properties.ListaCompra_id.eq(idLista))
                .where(JoinProductoConListaCompraDao.Properties.Comprado.eq(true))
                .list().size();

        int tamañoLista = listaCompraDao.load(idLista).getProductos().size();

        return tamañoProductosComprados == tamañoLista;
    }


}
