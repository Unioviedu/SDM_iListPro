package com.example.eduardomartinez.sdm_ilistpro.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;


import com.example.eduardomartinez.sdm_ilistpro.R;
import com.example.eduardomartinez.sdm_ilistpro.Utilidades;
import com.example.eduardomartinez.sdm_ilistpro.activities.adapters.ProductoAddedItemAdapter;
import com.example.eduardomartinez.sdm_ilistpro.database.DatabaseORM;
import com.example.eduardomartinez.sdm_ilistpro.database.model.ListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Producto;

import java.util.LinkedList;
import java.util.List;

public class SavedListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    ListView listViewProductos;
    SearchView search;
    Switch verCompletados;

    ListaCompra listaCompraActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_list);
        listaCompraActual = (ListaCompra) getIntent().getExtras().getSerializable(SerializablesTag.LISTA_COMPRA);
        setTitle(listaCompraActual.getNombre());

        buscarComponentes();
        mostrarComprados(false);
    }

    public boolean onCreateOptionsMenu (final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_saved_list, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            moverAHome();
        } else if (id == R.id.action_edit) {
            moverEditListActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void moverAHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void moverEditListActivity() {
        Intent intent = new Intent(this, NewListActivity.class);
        intent.putExtra(SerializablesTag.EDIT_LIST_COMPRA, listaCompraActual);
        startActivity(intent);
        finish();
    }

    public void comprarProducto(View view) {
        Producto producto = listaCompraActual.comprarProducto((long) view.getTag());
        DatabaseORM.getInstance().marcarComprado(listaCompraActual.getId(), producto.getId(), true);
        Utilidades.crearDialogoSencillo(this, producto.getNombre(),
                "Has comprado este producto correctamente, puedes verlo activando la opcion Productos comprados de arriba");
        mostrarComprados(false);
    }

    private void mostrarComprados(boolean comprado) {
        List<Producto> listaTemp = new LinkedList<>();
        listaTemp.addAll(listaCompraActual.getProductos());

        rellenarLista(Utilidades.filterProductoComprado(listaCompraActual.getId(), listaTemp, comprado));
    }

    private void buscarComponentes() {
        listViewProductos = (ListView) findViewById(R.id.listViewProductSaved);

        verCompletados = (Switch) findViewById(R.id.switchVerCompletados);
        verCompletados.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mostrarComprados(true);
                else
                    mostrarComprados(false);
            }
        });

        search = (SearchView) findViewById(R.id.searchProductSaved);
        search.setOnQueryTextListener(this);
    }

    private void rellenarLista(List<Producto> productos) {
        this.listViewProductos.setAdapter(new ProductoAddedItemAdapter(this, productos));
    }

    public void escanear(View view) {
        //AQUI LOGICA PARA ESCANEAR Y ELIMINAR PRODUCTO
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Producto> temp = new LinkedList<>();
        temp.addAll(listaCompraActual.getProductos());

        List<Producto> listaFiltrada = Utilidades.filterProducto(temp, newText);
        rellenarLista(listaFiltrada);
        return false;
    }
}
