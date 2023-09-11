package com.example.musicbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Button btnCancion, btnGenero, btnLista;

    SoundPool sp;
    int sonidoreproduccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        btnLista = (Button)findViewById(R.id.btn_biblioteca);
        btnGenero = (Button)findViewById(R.id.btn_genero);
        btnCancion = (Button)findViewById(R.id.btn_cancion);

        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        sonidoreproduccion = sp.load(this, R.raw.sonidoboton, 1);
    }

    public void verBiblioteca(View view) {
        sp.play(sonidoreproduccion, 1, 1, 1 , 0, 0);
        Intent ingresarBiblioteca = new Intent(this, BibliotecaActivity.class);
        startActivity(ingresarBiblioteca);
    }

    public void nuevaCancion(View view) {
        sp.play(sonidoreproduccion, 1, 1, 1 , 0, 0);
        Intent ingresarCancion = new Intent(this, CancionActivity.class);
        startActivity(ingresarCancion);
    }

    public void nuevoGenero(View view) {
        sp.play(sonidoreproduccion, 1, 1, 1 , 0, 0);
        Intent ingresarGenero = new Intent(this, GeneroActivity.class);
        startActivity(ingresarGenero);
    }

    public void verManual(View view) {
        sp.play(sonidoreproduccion, 1, 1, 1 , 0, 0);
        Intent ingresarManual = new Intent(this, ManualActivity.class);
        startActivity(ingresarManual);
    }
}