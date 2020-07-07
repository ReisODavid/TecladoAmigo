package com.lilithandbelial.tecladoamigo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TEMPORIZADOR_CONFIG = "TEMPORIZADOR_CONFIG";
    private final int TEMPORIZADOR_DEFAULT = 0;
    int segundos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        SharedPreferences preferences = getSharedPreferences(TEMPORIZADOR_CONFIG, Activity.MODE_PRIVATE);
        segundos = preferences.getInt(TEMPORIZADOR_CONFIG,TEMPORIZADOR_DEFAULT);
        Log.d("Log","pegando preferences"+segundos);


    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(TEMPORIZADOR_CONFIG, Activity.MODE_PRIVATE);
        segundos = preferences.getInt(TEMPORIZADOR_CONFIG,TEMPORIZADOR_DEFAULT);
    }


    @Override
    public void onClick(View v) {
        if (segundos ==0 ){

            Intent intent = new Intent(getApplicationContext(),ActivityConfiguracoes.class);
            startActivity(intent);

        }else{
            Intent intent = new Intent(getApplicationContext(),ActivityPrincipal.class);
            startActivity(intent);
        }
    }
}
