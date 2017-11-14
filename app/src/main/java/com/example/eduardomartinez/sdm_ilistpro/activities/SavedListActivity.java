package com.example.eduardomartinez.sdm_ilistpro.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.eduardomartinez.sdm_ilistpro.ListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.Producto;
import com.example.eduardomartinez.sdm_ilistpro.R;
import com.example.eduardomartinez.sdm_ilistpro.TiposProducto;
import com.example.eduardomartinez.sdm_ilistpro.activities.adapters.ProductoAddedItemAdapter;

import java.util.LinkedList;
import java.util.List;

public class SavedListActivity extends AppCompatActivity {
    ListView listViewProductos;

    ListaCompra listaCompraActual;

    List<Producto> productosAñadidos = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_list);
        listaCompraActual = (ListaCompra) getIntent().getExtras().getSerializable(SerializablesTag.NEW_LIST_COMPRA);
        setTitle(listaCompraActual.getNombre());

        buscarComponentes();
        rellenarLista();
    }

    public boolean onCreateOptionsMenu (final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_saved_list, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            //HAY QUE VOLVER A LA PANTALLA DE INICIO
            //MainActivity
        } else if (id == R.id.action_edit) {
            moverEditListActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void moverEditListActivity() {
        Intent intent = new Intent(this, NewListActivity.class);
        intent.putExtra(SerializablesTag.EDIT_LIST_COMPRA, listaCompraActual);
        startActivity(intent);
    }

    private void buscarComponentes() {
        listViewProductos = (ListView) findViewById(R.id.listViewProductSaved);
    }

    private void rellenarLista() {
        this.listViewProductos.setAdapter(new ProductoAddedItemAdapter(this, listaCompraActual.getProductos()));
    }

    public void escanear(View view) {
        //AQUI LOGICA PARA ESCANEAR Y ELIMINAR PRODUCTO
    }
}
