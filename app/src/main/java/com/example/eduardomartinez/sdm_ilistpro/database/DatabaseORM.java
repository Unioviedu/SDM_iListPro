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
        helper = new DaoMaster.DevOpenHelper(app, "prueba21.db", null);
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
       productoDao.insert(new Producto(null, SupermercadoNombres.ALIMERKA, "Carne1", 10.0, null, null, TiposProducto.CARNE));
       productoDao.insert(new Producto(null, SupermercadoNombres.ALIMERKA, "Carne2", 15.0, null, null, TiposProducto.CARNE));
       productoDao.insert(new Producto(null, SupermercadoNombres.ALIMERKA, "Pescado1", 5.0, null, null, TiposProducto.PESCADO));
       productoDao.insert(new Producto(null, SupermercadoNombres.MERCADONA, "Pescado1", 5.0, null, null, TiposProducto.PESCADO));
       productoDao.insert(new Producto(null, SupermercadoNombres.MERCADONA, "Fruta", 1.5, null, null, TiposProducto.FRUTA));
       productoDao.insert(new Producto(null, SupermercadoNombres.CORTE_INGLES, "Fruta", 3.5, null, null, TiposProducto.FRUTA));
       productoDao.insert(new Producto(null, SupermercadoNombres.MAS_MAS, "Verdura", 2.5, null, null, TiposProducto.VERDURA));
       productoDao.insert(new Producto(null, SupermercadoNombres.MAS_MAS, "Verdura2", 1.75, null, null, TiposProducto.VERDURA));
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

    public List<Supermercado> getSuperMercadosCercanos(Double latitud, Double longitud){
        List<Supermercado> lista = new LinkedList<>();
        lista.add(new Supermercado(1L, SupermercadoNombres.ALIMERKA, 0.0, 0.0));
        lista.add(new Supermercado(2L, SupermercadoNombres.MERCADONA, 1.0, 2.0));
        return lista;
    }


}
