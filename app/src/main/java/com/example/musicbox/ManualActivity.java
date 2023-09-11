package com.example.musicbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class ManualActivity extends AppCompatActivity {
    Button btnVolver;

    SoundPool sp;
    int sonidoreproduccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        btnVolver = (Button)findViewById(R.id.btn_volvermanual);

        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        sonidoreproduccion = sp.load(this, R.raw.sonidoboton, 1);
    }

    public void volverMenu(View view) {
        sp.play(sonidoreproduccion, 1, 1, 1 , 0, 0);
        Intent volverMenu = new Intent(this, MainActivity.class);
        startActivity(volverMenu);
    }
}