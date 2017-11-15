package com.example.eduardomartinez.sdm_ilistpro.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.eduardomartinez.sdm_ilistpro.Utilidades;
import com.example.eduardomartinez.sdm_ilistpro.activities.adapters.ListaCompraItemAdapter;
import com.example.eduardomartinez.sdm_ilistpro.ListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.R;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    ListView listListaCompra;
    SearchView search;

    List<ListaCompra> listaListaCompra = new LinkedList<>();
    ListaCompraItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Esto base de datos
        listaListaCompra.add(new ListaCompra("papa", 10.5));
        listaListaCompra.add(new ListaCompra("mama", 11));

        buscarComponentes();
        rellenarLista(listaListaCompra);
        añadirFunciones();
    }

    private void rellenarLista(List<ListaCompra> listaListaCompra) {
        adapter = new ListaCompraItemAdapter(this, listaListaCompra);
        this.listListaCompra.setAdapter(adapter);

        search.setOnQueryTextListener(this);
    }

    private void buscarComponentes() {
        listListaCompra = (ListView) findViewById(R.id.listViewListaCompra);
        search = (SearchView) findViewById(R.id.searchList);
    }

    private void añadirFunciones() {
        listListaCompra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {

                ListaCompra item = (ListaCompra) listListaCompra.getAdapter().getItem(position);

                moverListShoppingSavedActivity(item);
            }
        });
    }

    private void moverListShoppingSavedActivity(ListaCompra lista) {
        //AQUI HAY QUE PASAR el objeto lista A LA ACTIVITY de la lista seleccionada
        //SavedListActivity
        Intent intent = new Intent(this, SavedListActivity.class);
        intent.putExtra(SerializablesTag.LISTA_COMPRA, lista);
        startActivity(intent);
    }

    public void moverAddListActivity (View view) {
        //AQUI HAY QUE PASAR A LA pantalla para crear una nueva lista
        //NewListActivity
        Intent intent = new Intent(this, NewListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<ListaCompra> temp = new LinkedList<>();
        temp.addAll(listaListaCompra);

        List<ListaCompra> listaFiltrada = Utilidades.filterListaCompra(temp, newText);
        rellenarLista(listaFiltrada);
        return false;
    }
}
