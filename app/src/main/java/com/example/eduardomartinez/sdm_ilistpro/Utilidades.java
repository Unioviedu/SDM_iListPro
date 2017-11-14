package com.example.eduardomartinez.sdm_ilistpro;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by eduardomartinez on 14/11/17.
 */

public class Utilidades {

    public static String precio(double precio) {
        return precio+" €";
    }

    public static void  crearDialogoSencillo(Context context, String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
    }
}
