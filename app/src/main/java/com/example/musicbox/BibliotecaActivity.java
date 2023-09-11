package com.example.musicbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Objects;

public class BibliotecaActivity extends AppCompatActivity {
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase bd;
    private Cursor fila;

    Button btnVolver;
    ListView bibliotecaCanciones;

    SoundPool sp;
    int sonidoreproduccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        admin = new AdminSQLiteOpenHelper(this, null);
        bd = admin.getWritableDatabase();

        btnVolver = (Button)findViewById(R.id.btn_volverbiblioteca);
        bibliotecaCanciones = (ListView)findViewById(R.id.biblioteca_canciones);

        fila = bd.rawQuery("select cancion.cod_cancion, cancion.nombre_cancion, genero.nombre_genero from cancion, genero where cancion.cod_genero = genero.cod_genero", null);
        int cantidad = fila.getCount();
        int i = 0;
        String[] lista = new String[cantidad];

        if (fila.moveToFirst()) {
            do {
                String linea = fila.getInt(0) + "   Canción: " + fila.getString(1) + "\n     Género: " + fila.getString(2);
                lista[i] = linea;
                i++;
            } while (fila.moveToNext());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        bibliotecaCanciones.setAdapter(adapter);

        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        sonidoreproduccion = sp.load(this, R.raw.sonidoboton, 1);
    }

    public void volverMenu(View view) {
        sp.play(sonidoreproduccion, 1, 1, 1 , 0, 0);
        Intent volverMenu = new Intent(this, MainActivity.class);
        startActivity(volverMenu);
    }
}