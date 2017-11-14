package com.example.eduardomartinez.sdm_ilistpro;

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
        todosProductos.put((long)1, new Producto("Producto1", 10.0, "Mercadona",
                TiposProducto.CARNE, 1));
        todosProductos.put((long)2, new Producto("Producto2", 10.0, "Mercadona",
                TiposProducto.PESCADO, 2));
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

    public void clear() {
        productosAñadidos = new LinkedList<>();
    }

    public double getPrecioTotal() {
        double total = 0.0;
        for (Producto p: productosAñadidos)
            total += p.getPrecio();

        return total;
    }
}
