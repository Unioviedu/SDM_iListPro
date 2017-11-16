package com.example.eduardomartinez.sdm_ilistpro;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.eduardomartinez.sdm_ilistpro.activities.ListProductsFragment;
import com.example.eduardomartinez.sdm_ilistpro.database.DatabaseORM;
import com.example.eduardomartinez.sdm_ilistpro.database.model.ListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Producto;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by eduardomartinez on 14/11/17.
 */

public class Utilidades {

    public static String precio(double precio) {
        return precio+" €";
    }

    public static void  crearDialogoSencillo(Context context, String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
    }

    public static List<ListaCompra> filterListaCompra(List<ListaCompra> items, String text) {

        if (text.length() > 0) {
            List<ListaCompra> listaTemp = new LinkedList<>();
            listaTemp.addAll(items);

            items.clear();

            for (ListaCompra lista: listaTemp) {
                if (lista.getNombre().toLowerCase(Locale.getDefault()).contains(text))
                    items.add(lista);
            }
        }

        return items;
    }

    public static List<Producto> filterProducto(List<Producto> items, String text) {

        if (text.length() > 0) {
            List<Producto> listaTemp = new LinkedList<>();
            listaTemp.addAll(items);

            items.clear();

            for (Producto producto: listaTemp) {
                if (producto.getNombre().toLowerCase(Locale.getDefault()).contains(text))
                    items.add(producto);
            }
        }

        return items;
    }

    public static List<Producto> filterProductoComprado(long idLista, List<Producto> items, boolean comprado) {
        List<Producto> listaTemp = new LinkedList<>();

        for (Producto producto: items) {
            boolean isComprado = DatabaseORM.getInstance().isComprado(idLista, producto.getId());
            if (isComprado == comprado)
                listaTemp.add(producto);
        }

        return listaTemp;
    }

    public static List<Producto> orderProductos(List<Producto> productos) {
        return QuickSort.quicksort(productos, 0, productos.size()-1);
    }
}
