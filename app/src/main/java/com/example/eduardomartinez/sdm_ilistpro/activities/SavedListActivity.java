package com.example.eduardomartinez.sdm_ilistpro.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;


import com.example.eduardomartinez.sdm_ilistpro.GestorListaCompraActual;
import com.example.eduardomartinez.sdm_ilistpro.R;
import com.example.eduardomartinez.sdm_ilistpro.Utilidades;
import com.example.eduardomartinez.sdm_ilistpro.activities.adapters.ProductoAddedItemAdapter;
import com.example.eduardomartinez.sdm_ilistpro.activities.barcode.BarcodeCaptureActivity;
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
        buscarComponentes();

        listaCompraActual = (ListaCompra) getIntent().getExtras().getSerializable(SerializablesTag.LISTA_COMPRA);

        if (listaCompraActual != null)
            GestorListaCompraActual.getInstance().setListaActual(listaCompraActual);
        else
            listaCompraActual = GestorListaCompraActual.getInstance().getListaActual();

        setTitle(listaCompraActual.getNombre());

        Object obj = getIntent().getExtras() == null ?
                null
                : getIntent().getExtras().get(BarcodeCaptureActivity.BARCODE);

        if (obj != null)
            comprarProducto((String) obj);
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

    public void comprarProducto(String codigo) {
        Producto producto = listaCompraActual.buscarProducto(codigo);

        if (producto != null) {
            DatabaseORM.getInstance().marcarComprado(listaCompraActual.getId(), producto.getId(), true);
            Utilidades.crearToast(this, listaCompraActual.buscarProducto(codigo).getNombre(),
                    "Has comprado este producto correctamente, puedes verlo activando la opcion Productos comprados de arriba");
            mostrarComprados(false);
        } else {
            comprarProductoNuevo(codigo);
        }

        comprobarLista();
    }

    private void comprarProductoNuevo(String codigo) {
        boolean encontrado = false;
        for (Producto p: DatabaseORM.getInstance().getAllProductos()) {
            final Producto producto = p;
            if (p.getCodigoBarra().equals(codigo)) {
                encontrado = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Quieres añadir y comprar este producto: "+p.getNombre())
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                listaCompraActual.addProducto(producto);
                                DatabaseORM.getInstance().updateListaCompra(listaCompraActual);
                                comprarProducto(producto.getCodigoBarra());
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create().show();
            }
        }

        if (!encontrado)
            new AlertDialog.Builder(this)
                    .setMessage("No se ha encontrado el producto con codigo "+ codigo)
                    .create()
                    .show();
    }

    public void comprarProducto(View view) {
        Producto producto = listaCompraActual.buscarProducto((long) view.getTag());

        if (GestorListaCompraActual.getInstance().isComprado(producto)) {
            DatabaseORM.getInstance().marcarComprado(listaCompraActual.getId(), producto.getId(), false);
            Utilidades.crearToast(this, producto.getNombre(),
                    "Has devuelto este producto correctamente, puedes verlo desactivando la opcion Productos comprados de arriba");
            mostrarComprados(true);
        } else {
            DatabaseORM.getInstance().marcarComprado(listaCompraActual.getId(), producto.getId(), true);
            Utilidades.crearToast(this, producto.getNombre(),
                    "Has comprado este producto correctamente, puedes verlo activando la opcion Productos comprados de arriba");
            mostrarComprados(false);
        }

        comprobarLista();
    }

    private void mostrarComprados(boolean comprado) {
        List<Producto> listaTemp = new LinkedList<>();
        listaTemp.addAll(listaCompraActual.getProductos());

        rellenarLista(Utilidades.filterProductoComprado(listaCompraActual.getId(), listaTemp, comprado));
    }

    private void comprobarLista() {
        if (DatabaseORM.getInstance().isListaAcabada(listaCompraActual.getId())) {
            new AlertDialog.Builder(this)
                    .setMessage("Ya has completado todos los productos de la lista")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveMainActivity();
                        }
                    })
                    .create()
                    .show();
        }
    }

    public void moveMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
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
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        startActivity(intent);
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
