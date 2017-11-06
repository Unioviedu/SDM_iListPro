package com.example.eduardomartinez.sdm_ilistpro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listListaCompra;
    List<ListaCompraPrueba> listaListaCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buscarComponentes();
        rellenarLista();
    }

    private void rellenarLista() {
        listaListaCompra = new ArrayList<>();
        listaListaCompra.add(new ListaCompraPrueba("prueba", 10.5));
        listaListaCompra.add(new ListaCompraPrueba("prueba 2", 11));

        this.listListaCompra.setAdapter(new ListaCompraItemAdapter(this, listaListaCompra));
    }

    private void buscarComponentes() {
        listListaCompra = (ListView) findViewById(R.id.listViewListaCompra);
    }
}
