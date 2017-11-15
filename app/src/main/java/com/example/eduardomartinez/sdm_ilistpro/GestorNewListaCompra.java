package com.example.eduardomartinez.sdm_ilistpro;

import com.example.eduardomartinez.sdm_ilistpro.database.DatabaseORM;
import com.example.eduardomartinez.sdm_ilistpro.database.model.ListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Producto;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by eduardomartinez on 14/11/17.
 */

public class GestorNewListaCompra {

    private static final GestorNewListaCompra instance = new GestorNewListaCompra();

    private List<Producto> productosAñadidos = new LinkedList<>();

    private HashMap<Long, Producto> todosProductos = new HashMap<>();

    private GestorNewListaCompra() {
        for (Producto p : DatabaseORM.getInstance().getAllProductos()) {
            todosProductos.put(p.getId(), p);
        }
    }

    public static GestorNewListaCompra getInstance() {
        return instance;
    }

    public HashMap<Long, Producto> getTodosProductos() {
        return todosProductos;
    }

    public List<Producto> getProductosAñadidos () {
        return productosAñadidos;
    }

    public Producto addProducto(long id) {
        Producto p = todosProductos.get(id);
        productosAñadidos.add(p);

        return p;
    }

    public Producto deleteProducto(long id) {
        Producto p = todosProductos.get(id);
        productosAñadidos.remove(p);

        return p;
    }

    public void clear() {
        productosAñadidos = new LinkedList<>();
    }

    public double getPrecioTotal() {
        double total = 0.0;
        for (Producto p: productosAñadidos)
            total += p.getPrecio();

        return total;
    }

    public void editList(ListaCompra listaCompraEditar) {
        productosAñadidos.addAll(listaCompraEditar.getProductos());
    }

    public boolean isProductoAñadido(long id) {
        for (Producto p: productosAñadidos)
            if (p.getId() == id)
                return true;

        return false;
    }
}
