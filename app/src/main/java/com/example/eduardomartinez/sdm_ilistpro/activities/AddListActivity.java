package com.example.eduardomartinez.sdm_ilistpro.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.eduardomartinez.sdm_ilistpro.activities.adapters.ProductoAddedItemAdapter;
import com.example.eduardomartinez.sdm_ilistpro.Producto;
import com.example.eduardomartinez.sdm_ilistpro.R;

import java.util.LinkedList;
import java.util.List;

public class AddListActivity extends AppCompatActivity {
    EditText nombreLista;
    EditText precioLista;
    ListView listViewProductos;

    List<Producto> productosAñadidos = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        buscarComponentes();
        rellenarLista();
    }

    public boolean onCreateOptionsMenu (final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_list, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            moverSettingsActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void buscarComponentes() {
        nombreLista = (EditText) findViewById(R.id.textNombreLista);
        precioLista = (EditText) findViewById(R.id.textPrecioLista);
        listViewProductos = (ListView) findViewById(R.id.listViewListaCompra);
    }

    private void rellenarLista() {
        //Esto lo tendriamos que sacar de la base de datos
        productosAñadidos.add(new Producto("ProductoPrueba1", 10, "Mercadona"));
        productosAñadidos.add(new Producto("ProductoPrueba2", 10.5, "Alimerka"));
        //

        this.listViewProductos.setAdapter(new ProductoAddedItemAdapter(this, productosAñadidos));
    }

    public void saveList (View v) {
        //Logica para guardar la lista en la BDD
        moverSavedShoppingListActivity();
    }

    private void moverSettingsActivity () {

    }

    private void moverSavedShoppingListActivity() {

    }
}
