package com.example.eduardomartinez.sdm_ilistpro.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.eduardomartinez.sdm_ilistpro.activities.adapters.ListaCompraItemAdapter;
import com.example.eduardomartinez.sdm_ilistpro.ListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.R;
import com.example.eduardomartinez.sdm_ilistpro.database.Datasource;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listListaCompra;
    List<ListaCompra> listaListaCompra = new LinkedList<>();
    Datasource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = Datasource.getInstance(getApplicationContext());
        buscarComponentes();
        rellenarLista();
        añadirFunciones();
    }

    private void rellenarLista() {
        //Esto lo tendriamos que sacar de la base de datos
        listaListaCompra.add(new ListaCompra("prueba", 10.5));
        listaListaCompra.add(new ListaCompra("prueba 2", 11));
        //

        this.listListaCompra.setAdapter(new ListaCompraItemAdapter(this, listaListaCompra));
    }

    private void buscarComponentes() {
        listListaCompra = (ListView) findViewById(R.id.listViewListaCompra);
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
}
