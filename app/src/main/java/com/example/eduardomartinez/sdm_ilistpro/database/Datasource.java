package com.example.eduardomartinez.sdm_ilistpro.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eduardomartinez.sdm_ilistpro.R;


/**
 * Created by karolmc on 14/11/2017.
 */

public class Datasource {
    private static Datasource datasource;

    private SQLiteDatabase db;
    private DatabaseConfig dbConfig;

    private Datasource(Context context) {
        dbConfig = new DatabaseConfig(
                context,
                context.getResources().getString(R.string.databaseName),
                null,
                context.getResources().getInteger(R.integer.databaseVersion)
                );
        open();
    }

    public static Datasource getInstance(Context context) {
        if(datasource == null)
            return datasource = new Datasource(context);
        return datasource;
    }

    public void open() {
        db = dbConfig.getWritableDatabase();
    }

    public void close() {
        dbConfig.close();
    }


}
