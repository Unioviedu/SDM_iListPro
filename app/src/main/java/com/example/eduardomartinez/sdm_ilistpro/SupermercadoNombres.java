package com.example.eduardomartinez.sdm_ilistpro;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardomartinez on 8/11/17.
 */

public class SupermercadoNombres {
    public static final String MERCADONA = "MERCADONA", ALIMERKA = "ALIMERKA", CORTE = "CORTE", MAS = "masymas";

    public static boolean estaSupermercado(String sup) {
        List<String> supermercados = new ArrayList<>();
        supermercados.add(MERCADONA);
        supermercados.add(ALIMERKA);
        supermercados.add(CORTE);
        supermercados.add(MAS);

        for (String s: supermercados)
            if (sup.toLowerCase().contains(s.toLowerCase()))
                return true;

        return false;
    }
}
