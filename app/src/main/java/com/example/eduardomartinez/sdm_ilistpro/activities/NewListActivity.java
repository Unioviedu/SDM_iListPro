package com.example.eduardomartinez.sdm_ilistpro.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eduardomartinez.sdm_ilistpro.GestorNewListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.Utilidades;
import com.example.eduardomartinez.sdm_ilistpro.R;
import com.example.eduardomartinez.sdm_ilistpro.activities.adapters.ProductoAddedNewListItemAdapter;
import com.example.eduardomartinez.sdm_ilistpro.database.DatabaseORM;
import com.example.eduardomartinez.sdm_ilistpro.database.model.ListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Producto;

import java.io.Serializable;

public class NewListActivity extends AppCompatActivity implements Serializable{
    EditText nombreLista;
    TextView precioLista;
    ListView listViewProductos;

    boolean modoEdicion;

    ListaCompra newListaCompra = new ListaCompra();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        buscarComponentes();
        
        Object obj = getIntent().getExtras() == null ?
                null
                : getIntent().getExtras().get(SerializablesTag.EDIT_LIST_COMPRA);

        if (obj != null) {
            prepararEdicion(obj);
            modoEdicion = true;
        } else
            modoEdicion = false;

        rellenarLista();
    }

    private void prepararEdicion(Object obj) {
        GestorNewListaCompra.getInstance().clear();
        ListaCompra listaCompraEditar = (ListaCompra) obj;
        GestorNewListaCompra.getInstance().editList(listaCompraEditar);
        newListaCompra.setId(listaCompraEditar.getId());
        nombreLista.setText(listaCompraEditar.getNombre());
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        rellenarLista();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SettingsListActivity.preferenciasPorDefecto();
        GestorNewListaCompra.getInstance().clear();
    }

    public void deleteProductItem (View view) {
        Producto producto = GestorNewListaCompra.getInstance().deleteProducto((long)view.getTag());
        rellenarLista();

        Utilidades.crearToast(this, producto.getNombre(),
                "Se ha borrado correctamente de tu lista de la compra");
    }

    public boolean onCreateOptionsMenu (final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_list, menu);

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
        precioLista = (TextView) findViewById(R.id.textViewValorPrecioLista);
        listViewProductos = (ListView) findViewById(R.id.listViewProductos);
    }

    private void rellenarLista() {
        this.listViewProductos.setAdapter(new ProductoAddedNewListItemAdapter(this,
                GestorNewListaCompra.getInstance().getProductosAñadidos()));

        precioLista.setText(Utilidades.precio(GestorNewListaCompra.getInstance().getPrecioTotal()));
    }

    public void addProduct (View view) {
        Intent intent = new Intent(this, AddProductTabbedActivity.class);
        startActivity(intent);
    }

    public void saveList (View v) {
        if (nombreLista.getText().toString().isEmpty()) {
            Utilidades.crearToast(this, "Dale un nombre a tu lista",
                    "Es necesario que le asignes un nombre a la lista para poder guardarla");
        }

        else if (GestorNewListaCompra.getInstance().getProductosAñadidos().isEmpty()) {
            Utilidades.crearToast(this, "No has añadido ningun producto",
                    "Es necesario que añadas por lo menos un producto a tu lista para gaurdarla");
        }

        else {
            newListaCompra.setNombre(nombreLista.getText().toString());
            newListaCompra.addProductos(GestorNewListaCompra.getInstance().getProductosAñadidos());

            if (!modoEdicion)
                DatabaseORM.getInstance().saveListaCompra(newListaCompra);
            else {
                DatabaseORM.getInstance().updateListaCompra(newListaCompra);
            }

            SettingsListActivity.preferenciasPorDefecto();
            moverListShoppingSavedActivity();
        }
    }

    private void moverSettingsActivity () {
        Intent intent = new Intent(this, SettingsListActivity.class);
        startActivity(intent);
    }

    private void moverListShoppingSavedActivity() {
        //AQUI HAY QUE PASAR A LA ACTIVITY de la lista que acabamos de guardar
        //SavedListActivity
        Intent intent = new Intent(this, SavedListActivity.class);
        intent.putExtra(SerializablesTag.LISTA_COMPRA, newListaCompra);
        startActivity(intent);
        finish();
    }
}
