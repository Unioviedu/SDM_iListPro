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
import com.example.eduardomartinez.sdm_ilistpro.ListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.TiposProducto;
import com.example.eduardomartinez.sdm_ilistpro.Utilidades;
import com.example.eduardomartinez.sdm_ilistpro.activities.adapters.ProductoAddedItemAdapter;
import com.example.eduardomartinez.sdm_ilistpro.Producto;
import com.example.eduardomartinez.sdm_ilistpro.R;
import com.example.eduardomartinez.sdm_ilistpro.activities.adapters.ProductoAddedNewListItemAdapter;
import com.example.eduardomartinez.sdm_ilistpro.activities.adapters.ProductoForAddItemAdapter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

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
            ListaCompra listaCompraEditar = (ListaCompra) obj;
            modoEdicion = true;
            GestorNewListaCompra.getInstance().editList(listaCompraEditar);
            nombreLista.setText(listaCompraEditar.getNombre());
        } else
            modoEdicion = false;

        rellenarLista();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        rellenarLista();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GestorNewListaCompra.getInstance().clear();
    }

    public void deleteProductItem (View view) {
        Producto producto = GestorNewListaCompra.getInstance().deleteProducto((long)view.getTag());
        rellenarLista();

        Utilidades.crearDialogoSencillo(this, producto.getNombre(),
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
        //Pasamos a la activity donde seleccionar los productos para la lista
        //AddProductTabbedActivity
        //HAY QUE PASAR este objeto (this) a esta activity
        Intent intent = new Intent(this, AddProductTabbedActivity.class);
        startActivity(intent);
    }

    public void saveList (View v) {
        newListaCompra.setNombre(nombreLista.getText().toString());
        newListaCompra.setPrecio(GestorNewListaCompra.getInstance().getPrecioTotal());
        newListaCompra.setProductos(GestorNewListaCompra.getInstance().getProductosAñadidos());
        //Logica para guardar la lista en la BDD

        moverListShoppingSavedActivity();
    }

    private void moverSettingsActivity () {

    }

    private void moverListShoppingSavedActivity() {
        //AQUI HAY QUE PASAR A LA ACTIVITY de la lista que acabamos de guardar
        //SavedListActivity
        Intent intent = new Intent(this, SavedListActivity.class);
        intent.putExtra(SerializablesTag.NEW_LIST_COMPRA, newListaCompra);
        startActivity(intent);
    }
}
