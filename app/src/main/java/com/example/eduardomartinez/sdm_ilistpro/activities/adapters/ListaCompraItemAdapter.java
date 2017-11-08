package com.example.eduardomartinez.sdm_ilistpro.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.eduardomartinez.sdm_ilistpro.ListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.R;

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

        ListaCompra item = this.items.get(i);
        nombreLista.setText(item.getNombre());
        precioLista.setText(String.valueOf(item.getPrecio()));

        return rowView;
    }
}
