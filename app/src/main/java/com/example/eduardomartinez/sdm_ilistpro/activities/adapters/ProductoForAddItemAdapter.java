package com.example.eduardomartinez.sdm_ilistpro.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.eduardomartinez.sdm_ilistpro.Producto;
import com.example.eduardomartinez.sdm_ilistpro.R;

import org.w3c.dom.Text;

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
    public View getView(int i, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.product_added_item, parent, false);
        }

        ProductHolder p = new ProductHolder();
        p.producto = items.get(i);
        p.nombreLista = (TextView) rowView.findViewById(R.id.textViewNombreProductoForAddItem);
        p.precioLista = (TextView) rowView.findViewById(R.id.textViewPrecioProductoForAddItem);
        p.nombreSupermercado = (TextView) rowView.findViewById(R.id.textViewSupermercadoProductoForAddItem);
        p.addButton = rowView.findViewById(R.id.buttonAddProduct);

        setupItem(p);

        return rowView;
    }

    private void setupItem(ProductHolder p) {
        Producto item = p.producto;

        p.nombreLista.setText(item.getNombre());
        p.precioLista.setText(String.valueOf(item.getPrecio()));
        p.nombreLista.setText(item.getSupermercado());
    }

    public static class ProductHolder {
        Producto producto;
        TextView nombreLista;
        TextView precioLista;
        TextView nombreSupermercado;
        ImageButton addButton;
    }
}
