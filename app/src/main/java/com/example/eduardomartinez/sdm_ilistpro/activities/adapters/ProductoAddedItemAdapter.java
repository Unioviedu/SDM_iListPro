package com.example.eduardomartinez.sdm_ilistpro.activities.adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eduardomartinez.sdm_ilistpro.GestorListaCompraActual;
import com.example.eduardomartinez.sdm_ilistpro.GestorNewListaCompra;
import com.example.eduardomartinez.sdm_ilistpro.R;
import com.example.eduardomartinez.sdm_ilistpro.Utilidades;
import com.example.eduardomartinez.sdm_ilistpro.database.DatabaseORM;
import com.example.eduardomartinez.sdm_ilistpro.database.model.Producto;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eduardomartinez on 7/11/17.
 */

public class ProductoAddedItemAdapter extends BaseAdapter {
    private Context context;
    private List<Producto> items = new LinkedList<>();

    public ProductoAddedItemAdapter(Context context, List<Producto> items) {
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

        TextView nombreLista = (TextView) rowView.findViewById(R.id.textViewNombreProductoItem);
        TextView precioLista = (TextView) rowView.findViewById(R.id.textViewPrecioProductoItem);
        EditText numeroUnidades = (EditText) rowView.findViewById(R.id.editNumeroProductosItemAdded);
        CheckBox check = (CheckBox) rowView.findViewById(R.id.checkBoxProducto);
        ImageView imagen = (ImageView) rowView.findViewById(R.id.imageViewProductoItem);

        Producto item = this.items.get(i);
        nombreLista.setText(item.getNombre());
        precioLista.setText(Utilidades.precio(item.getPrecio()));
        imagen.setImageResource(item.getFoto());

        long idLista = GestorListaCompraActual.getInstance().getListaActual().getId();
        int unidades = DatabaseORM.getInstance().getCantidadProducto(idLista, item.getId());
        numeroUnidades.setText(String.valueOf(unidades));

        check.setTag(item.getId());
        check.setChecked(GestorListaCompraActual.getInstance().isComprado(item));

        return rowView;
    }
}
