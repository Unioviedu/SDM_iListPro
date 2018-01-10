package com.example.eduardomartinez.sdm_ilistpro.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.eduardomartinez.sdm_ilistpro.R;

/**
 * Created by karolmc on 14/11/2017.
 */

public class DatabaseConfig extends SQLiteOpenHelper {
    private Context context;

    public DatabaseConfig(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        dropDatabase(db);
        createDatabase(db);
    }

    private void dropDatabase(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(false);
        db.execSQL(context.getResources().getString(R.string.dropTable_Supermarkets));
        db.execSQL(context.getResources().getString(R.string.dropTable_Products));
        db.execSQL(context.getResources().getString(R.string.dropTable_ShoppingList));
        db.execSQL(context.getResources().getString(R.string.dropTable_ShoppingList_Products));
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void createDatabase(SQLiteDatabase db) {
        db.execSQL(context.getResources().getString(R.string.createTable_Supermarkets));
        db.execSQL(context.getResources().getString(R.string.createTable_Products));
        db.execSQL(context.getResources().getString(R.string.createTable_ShoppingList));
        db.execSQL(context.getResources().getString(R.string.createTable_ShoppingList_Products));
    }
}
