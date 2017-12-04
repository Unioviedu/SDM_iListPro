package com.example.eduardomartinez.sdm_ilistpro;

import com.example.eduardomartinez.sdm_ilistpro.database.DatabaseORM;
import com.example.eduardomartinez.sdm_ilistpro.database.model.ListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Producto;

/**
 * Created by eduardomartinez on 4/12/17.
 */

public class GestorListaCompraActual {
    private static GestorListaCompraActual instance = new GestorListaCompraActual();

    private ListaCompra listaActual;

    public static GestorListaCompraActual getInstance() {
        return instance;
    }

    public void setListaActual(ListaCompra listaActual) {
        this.listaActual = listaActual;
    }

    public ListaCompra getListaActual() {
        return listaActual;
    }

    public boolean isComprado(Producto p) {
        if (DatabaseORM.getInstance().isComprado(getListaActual().getId(), p.getId()))
            return true;

        return false;
    }
}
