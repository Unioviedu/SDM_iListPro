package com.example.eduardomartinez.sdm_ilistpro.activities;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.eduardomartinez.sdm_ilistpro.GestorNewListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.R;
import com.example.eduardomartinez.sdm_ilistpro.TiposProducto;
import com.example.eduardomartinez.sdm_ilistpro.Utilidades;
import com.example.eduardomartinez.sdm_ilistpro.activities.adapters.ProductoForAddItemAdapter;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Producto;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ListProductsFragment extends Fragment implements SearchView.OnQueryTextListener{
    private ListView listViewProductsForAdded;
    private SearchView searchProductsForAdded;
    private View rootView;
    private int tipo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tipo = getArguments().getInt(SerializablesTag.TIPO_LISTA_PRODUCTOS);
        rootView = inflater.inflate(R.layout.fragment_list_products, container, false);
        listViewProductsForAdded = (ListView) rootView.findViewById(R.id.listViewProductForAdd);
        searchProductsForAdded = (SearchView) rootView.findViewById(R.id.searchProductForAdd);
        searchProductsForAdded.setOnQueryTextListener(this);

        rellenarListaProductos(false, null);

        return rootView;
    }

    private void rellenarListaProductos(boolean filtro, String text) {
        //Esto desde la base de datos
        List<Producto> productos = new LinkedList<>();

        for (Map.Entry<Long, Producto> entry: GestorNewListaCompra.getInstance().getTodosProductos().entrySet()) {
                if (tipo == entry.getValue().getCategoria() || tipo == TiposProducto.TODO)
                    productos.add(entry.getValue());
        }
        Log.i("listaproductos", productos.size()+"_1");

        Utilidades.orderProductos(productos);
        productos = Utilidades.filterProductoBySupermercado(productos, GestorNewListaCompra.getInstance().getSupermercadosCercanos());
        Log.i("listaproductos", productos.size()+"_2");

        if (filtro) {
            productos = Utilidades.filterProducto(productos, text);
        }
        Log.i("listaproductos", productos.size()+"_3");

        GestorNewListaCompra gestor = GestorNewListaCompra.getInstance();
        productos = Utilidades.filtrarProductosPrecio(productos, gestor.activadoPrecioMin, gestor.precioMin, gestor.activadoPrecioMax, gestor.precioMax);
        Log.i("listaproductos", productos.size()+"_4");

        this.listViewProductsForAdded.setAdapter(new ProductoForAddItemAdapter(getActivity(), productos));

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        rellenarListaProductos(true, s);
        return false;
    }
}
