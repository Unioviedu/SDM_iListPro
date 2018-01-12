package com.example.eduardomartinez.sdm_ilistpro.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eduardomartinez.sdm_ilistpro.GestorNewListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.R;
import com.example.eduardomartinez.sdm_ilistpro.Utilidades;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Producto;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eduardomartinez on 7/11/17.
 */

public class ProductoAddedNewListItemAdapter extends BaseAdapter {
    private Context context;
    private List<Producto> items = new LinkedList<>();

    public ProductoAddedNewListItemAdapter(Context context, List<Producto> items) {
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
            rowView = inflater.inflate(R.layout.product_added_in_newlist_item, parent, false);
        }

        TextView nombreLista = (TextView) rowView.findViewById(R.id.textViewNombreProductoAddedItem);
        TextView precioLista = (TextView) rowView.findViewById(R.id.textViewPrecioProductoAddedItem);
        TextView nombreSupermercado = (TextView) rowView.findViewById(R.id.textViewSupermercadoProductoAddedItem);
        EditText numeroProducto = (EditText) rowView.findViewById(R.id.editNumeroProductos);
        ImageButton addButton = (ImageButton) rowView.findViewById(R.id.buttonDeleteProduct);
        ImageView imagen = (ImageView) rowView.findViewById(R.id.imageViewProductoAddedItem);


        Producto item = this.items.get(i);
        nombreLista.setText(item.getNombre());
        precioLista.setText(Utilidades.precio(item.getPrecio()));
        nombreSupermercado.setText(item.getSupermercado());
        imagen.setImageResource(item.getFoto());

        int cantidad = GestorNewListaCompra.getInstance().cantidadProducto(item.getId());
        numeroProducto.setText(String.valueOf(cantidad));

        addButton.setTag(item.getId());

        return rowView;
    }
}
