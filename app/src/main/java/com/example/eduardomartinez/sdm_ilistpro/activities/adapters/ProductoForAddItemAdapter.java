package com.example.eduardomartinez.sdm_ilistpro.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eduardomartinez.sdm_ilistpro.R;
import com.example.eduardomartinez.sdm_ilistpro.Utilidades;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Producto;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eduardomartinez on 7/11/17.
 */

public class ProductoForAddItemAdapter extends BaseAdapter {
    private Context context;
    private List<Producto> items = new LinkedList<>();

    public ProductoForAddItemAdapter(Context context, List<Producto> items) {
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
    public View getView(int i, final View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.product_for_add_item, parent, false);
        }

        TextView nombreLista = (TextView) rowView.findViewById(R.id.textViewNombreProductoForAddItem);
        TextView precioLista = (TextView) rowView.findViewById(R.id.textViewPrecioProductoForAddItem);
        TextView nombreSupermercado = (TextView) rowView.findViewById(R.id.textViewSupermercadoProductoForAddItem);
        ImageButton addButton = (ImageButton) rowView.findViewById(R.id.buttonAddProduct);
        ImageView imagen = (ImageView) rowView.findViewById(R.id.imageViewProductoForAddItem);

        Producto item = this.items.get(i);
        nombreLista.setText(item.getNombre());
        precioLista.setText(Utilidades.precio(item.getPrecio()));
        nombreSupermercado.setText(item.getSupermercado());
        imagen.setImageResource(item.getFoto());

        addButton.setTag(item.getId());

        return rowView;
    }
}
