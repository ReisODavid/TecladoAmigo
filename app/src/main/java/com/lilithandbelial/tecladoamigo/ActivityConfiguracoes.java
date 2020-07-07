package com.lilithandbelial.tecladoamigo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.prefs.Preferences;

public class ActivityConfiguracoes extends AppCompatActivity {


    private final String TEMPORIZADOR_CONFIG = "TEMPORIZADOR_CONFIG";
    private final int TEMPORIZADOR_DEFAULT = 1;

    EditText confTempo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);


        /*LINKA OS LAYOUTS*/
        confTempo = findViewById(R.id.editTextConfiguracaoTempo);
        Button btSalvaConfigTempo = findViewById(R.id.btSalvaConfigTempo);

        /*RECUPERA SHARED PREFERENCES*/
        SharedPreferences preferences = getSharedPreferences(TEMPORIZADOR_CONFIG,Activity.MODE_PRIVATE);
        int valorTemp = preferences.getInt(TEMPORIZADOR_CONFIG,TEMPORIZADOR_DEFAULT);

        /*SharedPreferences preferences1 = getSharedPreferences(CHECKEDBOX_CONFIG,Activity.MODE_PRIVATE);
        checkedBox = preferences1.getBoolean(CHECKEDBOX_CONFIG,true);
        Log.d("Log","valor do checkbox::::???"+checkedBox);
        if (checkedBox){
            checkBoxSom.setChecked(true);
        }else{
            checkBoxSom.setChecked(false);
        }*/

        /*SETA TEXTO NA CAIXA DE INPUT DE CONFIGURAÇÃO*/
        confTempo.setText(String.valueOf(valorTemp));

        /*SETA ONCLICKLISTENER*/
        btSalvaConfigTempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          /*      if (checkBoxSom.isChecked()){
                    checkedBox = true;
                }else {
                    checkedBox = false;
                }
            Log.d("Log","checked box??????"+checkedBox);*/

          /*PEGA O VALOR INFORMADO PELO USUÁRIO*/
            int tempo = Integer.parseInt(String.valueOf(confTempo.getText()));
          /*  SharedPreferences preferences1 = getSharedPreferences(CHECKEDBOX_CONFIG,Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor1 = preferences1.edit();
            editor1.putBoolean(CHECKEDBOX_CONFIG,checkedBox);
            editor1.commit();
*/
            /*if (tempo>=2){*/
          /*VERIFICA SE É UM VALOR VALIDO
          * Se sim, guarda no SharedPreferences e chama a activityPrincipal
          * Se não, avisa o usuário sobre o valor não ser valido*/
            if (tempo >= 1) {
                Log.d("Log", "setando valor nas preferencias " + tempo);
                SharedPreferences preferences = getSharedPreferences(TEMPORIZADOR_CONFIG, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(TEMPORIZADOR_CONFIG, tempo);
                editor.commit();
                finish();
                chamaActivityPrincipal();

            }
            else{
                Toast.makeText(getApplicationContext(),"Valor inválido, valor minímo é 1 segundo",Toast.LENGTH_SHORT).show();
            }

            }
        });
    }

    /*CHAMA A ACTIVITY PRINCIPAL APÓS SETAR O VALOR DO TEMPORIZADOR*/
    private void chamaActivityPrincipal(){
        Intent intent = new Intent(getApplicationContext(),ActivityPrincipal.class);
        startActivity(intent);

    }
}
