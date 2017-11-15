package com.example.eduardomartinez.sdm_ilistpro.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.eduardomartinez.sdm_ilistpro.Utilidades;
import com.example.eduardomartinez.sdm_ilistpro.activities.adapters.ListaCompraItemAdapter;
import com.example.eduardomartinez.sdm_ilistpro.R;
import com.example.eduardomartinez.sdm_ilistpro.database.DatabaseORM;
import com.example.eduardomartinez.sdm_ilistpro.database.model.ListaCompra;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    ListView listListaCompra;
    SearchView search;

    List<ListaCompra> listaListaCompra = new LinkedList<>();
    ListaCompraItemAdapter adapter;
    DatabaseORM db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = DatabaseORM.getInstance(getApplication());
        listaListaCompra = db.getAllListaCompra();
        Log.d("Prueba",listaListaCompra.toString());
        buscarComponentes();

        rellenarLista(listaListaCompra);
        añadirFunciones();
    }

    private void rellenarLista(List<ListaCompra> listaListaCompra) {
        adapter = new ListaCompraItemAdapter(this, listaListaCompra);
        this.listListaCompra.setAdapter(adapter);
    }

    private void buscarComponentes() {
        listListaCompra = (ListView) findViewById(R.id.listViewListaCompra);
        search = (SearchView) findViewById(R.id.searchList);
        search.setOnQueryTextListener(this);
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
    protected void onRestart() {
        super.onRestart();
        listaListaCompra = db.getAllListaCompra();
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
