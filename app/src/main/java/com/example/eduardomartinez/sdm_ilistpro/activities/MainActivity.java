package com.example.eduardomartinez.sdm_ilistpro.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.eduardomartinez.sdm_ilistpro.GestorNewListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.Localizacion.Localizacion;
import com.example.eduardomartinez.sdm_ilistpro.Utilidades;
import com.example.eduardomartinez.sdm_ilistpro.activities.adapters.ListaCompraItemAdapter;
import com.example.eduardomartinez.sdm_ilistpro.R;
import com.example.eduardomartinez.sdm_ilistpro.database.DatabaseORM;
import com.example.eduardomartinez.sdm_ilistpro.database.model.ListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Supermercado;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, GoogleApiClient.ConnectionCallbacks{
    ListView listListaCompra;
    SearchView search;

    List<ListaCompra> listaListaCompra = new LinkedList<>();
    ListaCompraItemAdapter adapter;
    DatabaseORM db;

    private Localizacion loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loc = Localizacion.getInstance(this);

        db = DatabaseORM.getInstance(getApplication());
        listaListaCompra = db.getAllListaCompra();
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
        final GestorNewListaCompra gestor = GestorNewListaCompra.getInstance();
        final Intent intent = new Intent(this, NewListActivity.class);

        Location ubicacion = loc.getUbicacionActual();
        gestor.setLocation(ubicacion);
        Log.i("cr7", ubicacion+"");
        Localizacion.getInstance().getSupermercadosCercanos();

        if (ubicacion == null) {
            crearDialogoOffline();
            return;
        }

        final ProgressDialog progress=new ProgressDialog(this);
        progress.setMessage("Buscando supermercados cercanos...");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();

        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while(jumpTime < totalProgressTime) {
                    try {
                        jumpTime += 5;
                        progress.setProgress(jumpTime);
                        List<Supermercado> lista = gestor.getSupermercadosCercanos();
                        Thread.sleep(50);
                        while (lista.isEmpty()) {
                            lista = gestor.getSupermercadosCercanos();
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                startActivity(intent);
                progress.cancel();
            }
        };

        t.start();
    }

    private void crearDialogoOffline() {
        final Intent intent = new Intent(this, NewListActivity.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sin conexión")
                .setMessage("Actualmente no tiene conexión a Internet, ¿quiere entrar en el modo offline?.")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        builder.create().show();
    }

    public void deleteListItem(View view) {
        final long idLista = (long) view.getTag();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Quieres borrar esta lista?, la acción es permanente")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        borrarLista(idLista);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    public void verListItem(View view) {
        ListaCompra lista = db.getListaCompra((long) view.getTag());
        moverListShoppingSavedActivity(lista);
    }

    public void borrarLista (long idLista) {
        ListaCompra lista = new ListaCompra();

        for (ListaCompra l: listaListaCompra)
            if (l.getId() == idLista)
                lista = l;

        DatabaseORM.getInstance().deleteListaCompra(lista);
        listaListaCompra = DatabaseORM.getInstance().getAllListaCompra();
        rellenarLista(listaListaCompra);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listaListaCompra = db.getAllListaCompra();
        rellenarLista(listaListaCompra);
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Toast.makeText(this, loc.getUbicacionActual()+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
