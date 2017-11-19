package com.example.eduardomartinez.sdm_ilistpro;

import android.util.Log;

import com.example.eduardomartinez.sdm_ilistpro.database.model.Producto;

import java.util.List;

/**
 * Created by eduardomartinez on 15/11/17.
 */

public class QuickSort {

    public static List<Producto> quicksort(List<Producto> productos, int izq, int der) {
        Log.i("ordenar", ""+izq);
        Producto pivote=productos.get(izq); // tomamos primer elemento como pivote
        int i=izq; // i realiza la búsqueda de izquierda a derecha
        int j=der; // j realiza la búsqueda de derecha a izquierda
        Producto aux;

        while(i<j){            // mientras no se crucen las búsquedas
            while(productos.get(i).getPrecio()<=pivote.getPrecio() && i<j) i++; // busca elemento mayor que pivote
            while(productos.get(j).getPrecio()>pivote.getPrecio()) j--;         // busca elemento menor que pivote
            if (i<j) {                      // si no se han cruzado
                aux= productos.get(i);                  // los intercambia
                productos.set(i, productos.get(j));
                productos.set(j, aux);
            }
        }
        productos.set(izq, productos.get(j)); // se coloca el pivote en su lugar de forma que tendremos
        productos.set(j, pivote); // los menores a su izquierda y los mayores a su derecha
        if(izq<j-1)
            quicksort(productos,izq,j-1); // ordenamos subarray izquierdo
        if(j+1 <der)
            quicksort(productos,j+1,der); // ordenamos subarray derecho

        return productos;
    }
}
