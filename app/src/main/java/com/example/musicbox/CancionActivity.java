package com.example.musicbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class CancionActivity extends AppCompatActivity {
    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase bd;
    private ContentValues registro;
    private Cursor fila;

    Button btnGuardar, btnVolver;
    EditText txtCancion;
    Spinner spnGenero;

    SoundPool sp;
    int sonidoreproduccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancion);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        admin = new AdminSQLiteOpenHelper(this, null);
        bd = admin.getWritableDatabase();
        registro = new ContentValues();

        btnGuardar = (Button)findViewById(R.id.btn_guardarcancion);
        btnVolver = (Button)findViewById(R.id.btn_volvercancion);
        txtCancion = (EditText)findViewById(R.id.editxt_cancion);
        spnGenero = (Spinner)findViewById(R.id.spinner_generocancion);

        fila = bd.rawQuery("select cod_genero as _id, nombre_genero from genero order by nombre_genero", null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_dropdown_item, fila,
                new String[] {"nombre_genero"}, new int [] {android.R.id.text1}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGenero.setAdapter(adapter);

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
                    txtCancion.setText(strSpeechText);
                }
                break;
            default:
                break;
        }
    }

    public void guardarCancion(View view) {
        sp.play(sonidoreproduccion, 1, 1, 1 , 0, 0);

        if (txtCancion != null) {
            registro.put("nombre_cancion", txtCancion.getText().toString());
            registro.put("cod_genero", spnGenero.getSelectedItemId());
            bd.insert("cancion", null, registro);
            bd.close();

            Toast.makeText(this, "Nueva canción almacenada con éxito", Toast.LENGTH_SHORT).show();
            txtCancion.setText("");
        } else {
            Toast.makeText(this, "Introduzca una nueva canción", Toast.LENGTH_SHORT).show();
        }
    }

    public void volverMenu(View view) {
        sp.play(sonidoreproduccion, 1, 1, 1 , 0, 0);
        Intent volverMenu = new Intent(this, MainActivity.class);
        startActivity(volverMenu);
    }
}