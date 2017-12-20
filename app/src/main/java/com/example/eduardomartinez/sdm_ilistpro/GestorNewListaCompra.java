package com.example.eduardomartinez.sdm_ilistpro;

import com.example.eduardomartinez.sdm_ilistpro.database.DatabaseORM;
import com.example.eduardomartinez.sdm_ilistpro.database.model.ListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Producto;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Supermercado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by eduardomartinez on 14/11/17.
 */

public class GestorNewListaCompra {

    private static GestorNewListaCompra instance;

    private List<Producto> productosAñadidos;

    private HashMap<Long, Integer> cantidadProductos;

    private HashMap<Long, Producto> todosProductos;

    private boolean soloUnSupermercado = false;
    private List<Supermercado> supermercadosCercanos = new ArrayList<Supermercado>();
    private Supermercado supermercadoSeleccionado;

    private GestorNewListaCompra() {
        productosAñadidos = new LinkedList<>();
        cantidadProductos = new LinkedHashMap<>();
        todosProductos = new HashMap<>();

        for (Producto p : DatabaseORM.getInstance().getAllProductos()) {
            todosProductos.put(p.getId(), p);
        }
    }

    public static GestorNewListaCompra getInstance() {
        if (instance == null)
            instance = new GestorNewListaCompra();
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

        if (!cantidadProductos.containsKey(id)) {
            productosAñadidos.add(p);
            cantidadProductos.put(p.getId(), 1);
        } else {
            cantidadProductos.put(p.getId(), cantidadProductos.get(id)+1);
        }

        return p;
    }

    public Producto deleteProducto(long id) {
        Producto p = todosProductos.get(id);
        int cantidad = cantidadProductos.get(id) - 1;

        if (cantidad > 0) {
            cantidadProductos.put(p.getId(), cantidad);
        } else
            productosAñadidos.remove(p);

        return p;
    }

    public void clear() {
        productosAñadidos = new LinkedList<>();
        cantidadProductos = new HashMap<>();
    }

    public double getPrecioTotal() {
        double total = 0.0;
        for (Producto p: productosAñadidos)
            total += p.getPrecio();

        return total;
    }

    public void editList(ListaCompra listaCompraEditar) {
        productosAñadidos.addAll(listaCompraEditar.getProductos());

        for (Producto p: productosAñadidos)
            cantidadProductos.put(p.getId(), DatabaseORM.getInstance().getCantidadProducto(listaCompraEditar.getId(), p.getId()));
    }

    public boolean isProductoAñadido(long id) {
        for (Producto p: productosAñadidos) {
            if (p.getId() == id)
                return true;
        }

        return false;
    }

    public int cantidadProducto(long id) {
        if (cantidadProductos.containsKey(id))
            return cantidadProductos.get(id);

        return 1;
    }

    public boolean isSoloUnSupermercado() {
        return soloUnSupermercado;
    }

    public void setSoloUnSupermercado(boolean soloUnSupermercado) {
        this.soloUnSupermercado = soloUnSupermercado;
    }

    public Supermercado getSupermercadoSeleccionado() {
        return supermercadoSeleccionado;
    }

    public void setSupermercadoSeleccionado(Supermercado supermercadoSeleccionado) {
        this.supermercadoSeleccionado = supermercadoSeleccionado;
    }

    public List<Supermercado> getSupermercadosCercanos() {
        return supermercadosCercanos;
    }

    public void setSupermercadosCercanos(List<Supermercado> supermercadosCercanos) {
        this.supermercadosCercanos = supermercadosCercanos;
    }
}
