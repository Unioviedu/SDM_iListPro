package com.example.eduardomartinez.sdm_ilistpro.activities;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.eduardomartinez.sdm_ilistpro.R;

public class ListProductsFragment extends Fragment {
    ListView listProductsForAdded;
    SearchView searchProductsForAdded;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_products, container, false);
    }
}
