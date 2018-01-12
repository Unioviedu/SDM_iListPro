package com.example.eduardomartinez.sdm_ilistpro.database;

import android.app.Application;

import com.example.eduardomartinez.sdm_ilistpro.GestorNewListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.R;
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
        helper = new DaoMaster.DevOpenHelper(app, "pruebaA002.db", null);
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
            productoDao.insert(new Producto(null, SupermercadoNombres.ALIMERKA, "Pollo", 10.0, R.drawable.pollo, "AMK_pll_0001", TiposProducto.CARNE));
            productoDao.insert(new Producto(null, SupermercadoNombres.ALIMERKA, "Ternera", 15.0, R.drawable.ternera, "AMK_ter_0002", TiposProducto.CARNE));
            productoDao.insert(new Producto(null, SupermercadoNombres.ALIMERKA, "Lubina", 5.0, R.drawable.lubina, "AMK_lub_0003", TiposProducto.PESCADO));
            productoDao.insert(new Producto(null, SupermercadoNombres.ALIMERKA, "Merluza", 5.0, R.drawable.merluza, "AMK_mer_0004", TiposProducto.PESCADO));
            productoDao.insert(new Producto(null, SupermercadoNombres.ALIMERKA, "Naranjas", 1.5, R.drawable.naranja, "AMK_nar_0005", TiposProducto.FRUTA));
            productoDao.insert(new Producto(null, SupermercadoNombres.ALIMERKA, "Manzanas", 3.5, R.drawable.manzana, "AMK_man_0006", TiposProducto.FRUTA));
            productoDao.insert(new Producto(null, SupermercadoNombres.ALIMERKA, "Patatas", 2.5, R.drawable.patata, "AMK_pat_0007", TiposProducto.VERDURA));
            productoDao.insert(new Producto(null, SupermercadoNombres.ALIMERKA, "Cebollas", 1.75, R.drawable.cebolla, "AMK_ceb_0008", TiposProducto.VERDURA));
            /*-------------------------------------*/
            productoDao.insert(new Producto(null, SupermercadoNombres.CORTE, "Pollo", 12.0, R.drawable.pollo, "CRT_pll_1001", TiposProducto.CARNE));
            productoDao.insert(new Producto(null, SupermercadoNombres.CORTE, "Ternera", 15.75, R.drawable.ternera, "CRT_ter_1002", TiposProducto.CARNE));
            productoDao.insert(new Producto(null, SupermercadoNombres.CORTE, "Lubina", 8.0, R.drawable.lubina, "CRT_lub_1003", TiposProducto.PESCADO));
            productoDao.insert(new Producto(null, SupermercadoNombres.CORTE, "Merluza", 2.0, R.drawable.merluza, "CRT_mer_1004", TiposProducto.PESCADO));
            productoDao.insert(new Producto(null, SupermercadoNombres.CORTE, "Naranjas", 6.5, R.drawable.naranja, "CRT_nar_1005", TiposProducto.FRUTA));
            productoDao.insert(new Producto(null, SupermercadoNombres.CORTE, "Manzanas", 5.9, R.drawable.manzana, "CRT_man_1006", TiposProducto.FRUTA));
            productoDao.insert(new Producto(null, SupermercadoNombres.CORTE, "Patatas", 2.5, R.drawable.patata, "CRT_pat_1007", TiposProducto.VERDURA));
            productoDao.insert(new Producto(null, SupermercadoNombres.CORTE, "Cebollas", 1.75, R.drawable.cebolla, "CRT_ceb_1008", TiposProducto.VERDURA));
            /*-------------------------------------*/
            productoDao.insert(new Producto(null, SupermercadoNombres.MAS, "Pollo", 50.0, R.drawable.pollo, "MAS_pll_2001", TiposProducto.CARNE));
            productoDao.insert(new Producto(null, SupermercadoNombres.MAS, "Ternera", 5.0, R.drawable.ternera, "MAS_ter_2002", TiposProducto.CARNE));
            productoDao.insert(new Producto(null, SupermercadoNombres.MAS, "Lubina", 25.0, R.drawable.lubina, "MAS_lub_2003", TiposProducto.PESCADO));
            productoDao.insert(new Producto(null, SupermercadoNombres.MAS, "Merluza", 5.0, R.drawable.merluza, "MAS_mer_2004", TiposProducto.PESCADO));
            productoDao.insert(new Producto(null, SupermercadoNombres.MAS, "Naranjas", 1.5, R.drawable.naranja, "MAS_nar_2005", TiposProducto.FRUTA));
            productoDao.insert(new Producto(null, SupermercadoNombres.MAS, "Manzanas", 1.5, R.drawable.manzana, "MAS_man_2006", TiposProducto.FRUTA));
            productoDao.insert(new Producto(null, SupermercadoNombres.MAS, "Patatas", 4.5, R.drawable.patata, "MAS_pat_2007", TiposProducto.VERDURA));
            productoDao.insert(new Producto(null, SupermercadoNombres.MAS, "Cebollas", 1.75, R.drawable.cebolla, "MAS_ceb_2008", TiposProducto.VERDURA));
            /*-------------------------------------*/
            productoDao.insert(new Producto(null, SupermercadoNombres.MERCADONA, "Pollo", 20.5, R.drawable.pollo, "MRC_pll_3001", TiposProducto.CARNE));
            productoDao.insert(new Producto(null, SupermercadoNombres.MERCADONA, "Ternera", 17.0, R.drawable.ternera, "MRC_ter_3002", TiposProducto.CARNE));
            productoDao.insert(new Producto(null, SupermercadoNombres.MERCADONA, "Lubina", 7.0, R.drawable.lubina, "MRC_lub_3003", TiposProducto.PESCADO));
            productoDao.insert(new Producto(null, SupermercadoNombres.MERCADONA, "Merluza", 5.8, R.drawable.merluza, "MRC_mer_3004", TiposProducto.PESCADO));
            productoDao.insert(new Producto(null, SupermercadoNombres.MERCADONA, "Naranjas", 9.5, R.drawable.naranja, "MRC_nar_3005", TiposProducto.FRUTA));
            productoDao.insert(new Producto(null, SupermercadoNombres.MERCADONA, "Manzanas", 2.5, R.drawable.manzana, "MRC_man_3006", TiposProducto.FRUTA));
            productoDao.insert(new Producto(null, SupermercadoNombres.MERCADONA, "Patatas", 2.45, R.drawable.patata, "MRC_pat_3007", TiposProducto.VERDURA));
            productoDao.insert(new Producto(null, SupermercadoNombres.MERCADONA, "Cebollas", 3.75, R.drawable.cebolla, "MRC_ceb_3008", TiposProducto.VERDURA));
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
