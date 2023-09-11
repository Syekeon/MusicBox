package com.example.musicbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class GeneroActivity extends AppCompatActivity {
    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase bd;
    private ContentValues registro;

    Button btnGuardar, btnVolver;
    EditText txtGenero;

    SoundPool sp;
    int sonidoreproduccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genero);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        admin = new AdminSQLiteOpenHelper(this, null);
        bd = admin.getWritableDatabase();
        registro = new ContentValues();

        btnGuardar = (Button)findViewById(R.id.btn_guardargenero);
        btnVolver = (Button)findViewById(R.id.btn_volvergenero);
        txtGenero = (EditText)findViewById(R.id.editxt_genero);

        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        sonidoreproduccion = sp.load(this, R.raw.sonidoboton, 1);
    }

    public void reconocimientoVoz(View view) {
        Intent intentActionRecognitionSpeech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentActionRecognitionSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-ES");

        try {
            startActivityForResult(intentActionRecognitionSpeech, RECOGNIZE_SPEECH_ACTIVITY);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Inténtelo de nuevo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RECOGNIZE_SPEECH_ACTIVITY:
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String strSpeechText = speech.get(0);
                    txtGenero.setText(strSpeechText);
                }
                break;
            default:
                break;
        }
    }

    public void guardarCancion(View view) {
        sp.play(sonidoreproduccion, 1, 1, 1 , 0, 0);

        if (txtGenero != null) {
            registro.put("nombre_genero", txtGenero.getText().toString());
            bd.insert("genero", null, registro);
            bd.close();

            Toast.makeText(this, "Nuevo género almacenado con éxito", Toast.LENGTH_SHORT).show();
            txtGenero.setText("");
        } else {
            Toast.makeText(this, "Introduzca un nuevo género", Toast.LENGTH_SHORT).show();
        }
    }

    public void volverMenu(View view) {
        sp.play(sonidoreproduccion, 1, 1, 1 , 0, 0);
        Intent volverMenu = new Intent(this, MainActivity.class);
        startActivity(volverMenu);
    }
}