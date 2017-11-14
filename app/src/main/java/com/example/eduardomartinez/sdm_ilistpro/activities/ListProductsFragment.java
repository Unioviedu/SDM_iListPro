package com.example.eduardomartinez.sdm_ilistpro.activities;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.eduardomartinez.sdm_ilistpro.Producto;
import com.example.eduardomartinez.sdm_ilistpro.R;
import com.example.eduardomartinez.sdm_ilistpro.TiposProducto;
import com.example.eduardomartinez.sdm_ilistpro.activities.adapters.ListaCompraItemAdapter;
import com.example.eduardomartinez.sdm_ilistpro.activities.adapters.ProductoAddedItemAdapter;
import com.example.eduardomartinez.sdm_ilistpro.activities.adapters.ProductoForAddItemAdapter;

import java.util.LinkedList;
import java.util.List;

public class ListProductsFragment extends Fragment {
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

        rellenarListaProductos();
        //setupAddProductButton();

        return rootView;
    }

    private void rellenarListaProductos() {
        //Esto desde la base de datos
        List<Producto> productos = new LinkedList<>();

        if (tipo == TiposProducto.CARNE) {
            productos.add(new Producto("Carne", 10, "Mercadona", 1));
        } else {
            productos.add(new Producto("Otro", 10, "Mercadona", 2));
        }

        this.listViewProductsForAdded.setAdapter(new ProductoForAddItemAdapter(getActivity(), productos));

    }

    private void setupAddProductButton() {
        listViewProductsForAdded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LOGICA PARA AÑADIR EL PRODUCTO

            }
        });
    }
}
