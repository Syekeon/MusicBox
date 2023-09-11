package com.example.musicbox;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, "canciones", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase bbdd) {
        bbdd.execSQL("create table genero (cod_genero integer primary key autoincrement, nombre_genero text)");
        bbdd.execSQL("create table cancion (cod_cancion integer primary key autoincrement, nombre_cancion text, cod_genero integer not null constraint fk_cod_genero references genero(cod_genero) on delete cascade on update cascade)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bbdd, int i, int i1) {
        bbdd.execSQL("drop table if exists genero");
        bbdd.execSQL("create table genero (cod_genero integer primary key autoincrement, nombre_genero text)");
        bbdd.execSQL("drop table if exists cancion");
        bbdd.execSQL("create table cancion (cod_cancion integer primary key autoincrement, nombre_cancion text, cod_genero integer not null constraint fk_cod_genero references genero(cod_genero) on delete cascade on update cascade)");
    }
}