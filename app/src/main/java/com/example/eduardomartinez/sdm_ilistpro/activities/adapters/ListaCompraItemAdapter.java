package com.example.eduardomartinez.sdm_ilistpro.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.eduardomartinez.sdm_ilistpro.R;
import com.example.eduardomartinez.sdm_ilistpro.Utilidades;
import com.example.eduardomartinez.sdm_ilistpro.database.DatabaseORM;
import com.example.eduardomartinez.sdm_ilistpro.database.model.ListaCompra;

import java.util.List;

/**
 * Created by eduardomartinez on 6/11/17.
 */

public class ListaCompraItemAdapter extends BaseAdapter {
    private Context context;
    private List<ListaCompra> items;

    public ListaCompraItemAdapter(Context context, List<ListaCompra> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.lista_compra_item, parent, false);
        }

        TextView nombreLista = (TextView) rowView.findViewById(R.id.textViewNombreListaItem);
        TextView precioLista = (TextView) rowView.findViewById(R.id.textViewPrecioListaItem);
        ImageButton button = (ImageButton) rowView.findViewById(R.id.buttonDeleteList);
        ImageButton buttonVer = (ImageButton) rowView.findViewById(R.id.buttonVerList);

        ListaCompra item = this.items.get(i);

        boolean listaCompletada = DatabaseORM.getInstance().isListaAcabada(item.getId());
        nombreLista.setText(listaCompletada ? item.getNombre()+ " (COMPLETADA)" : item.getNombre());
        precioLista.setText(Utilidades.precio(item.getImporteTotal()));
        button.setTag(item.getId());
        buttonVer.setTag(item.getId());

        return rowView;
    }
}
