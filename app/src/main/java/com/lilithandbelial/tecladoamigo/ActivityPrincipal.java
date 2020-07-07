package com.lilithandbelial.raya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityPrincipal extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {

    /*variaveis para o temporizador*/
    private final String TEMPORIZADOR_CONFIG = "TEMPORIZADOR_CONFIG";
    private  int TEMPORIZADOR;
    private final int TEMPORIZADOR_DEFAULT = 4;

    /*variavel para checar se o som esta ativado
    * opção de efeito sonoro ao trocar de botão selecionavel removida*/
    private final String CHECKEDBOX_CONFIG = "CHECKEDBOX_CONFIG";

    /*BTS PRIMÁRIOS*/
    Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9,bt0,btTemp1,btTemp2,btApaga;

    /*INTS para controle dos botoes selecionados/clicados*/
    int btSelecionadoNumMomento = 0;
    int btLetraSelecionadaNoMomento = 0;
    int valorPrimeiroBT=0;
    int valorSegundoBT = 0;


    /*booleans para controle*/
    /*boolean somAtivo = true;*/
    boolean selectedNum = false;

    /*Lista com os ids dos botões*/
    List<Integer> idsTemps = new ArrayList<>();

    /*string que preenche o textview da tela*/
    String frase;

    /*lista de listas de string, com as strings para serem usadas na formação das palavras*/
    private List<List<String>> opcoes;

    TextView txtFrase;

    /*Handler e Runnable para trocar de botão numerico de X segundos em X segundos
    * serve para controlar a troca de botões conforme o tempo configurado*/
    Handler timerHandlerToNumPad = new Handler();
    Runnable timerRunnableToNumPad /*= new Runnable() {

        @Override
        public void run() {

            selecionaBT();

            if (btSelecionadoNumMomento !=0){
                Log.d("Log","btSelecionado no momento direto do runabble"+btSelecionadoNumMomento);
                valorPrimeiroBT = btSelecionadoNumMomento-1;

            }else{
                valorPrimeiroBT = 10;
            }
            Log.d("Log","botao ativo"+btSelecionadoNumMomento);

            timerHandlerToNumPad.postDelayed(this,TEMPORIZADOR*1000);

        }
    }*/;

    /*Handler e Runnable para trocar de botão de letras de X segundos em X segundos*/
    Handler timerHandlerToLetterPad = new Handler();
    Runnable timerRunnableToLetterPad/* = new Runnable() {
        @Override
        public void run() {


            selecionaBTLetra();
            if (btLetraSelecionadaNoMomento!=0){
                valorSegundoBT = btLetraSelecionadaNoMomento-1;

            }
            else{
                valorSegundoBT = opcoes.get(valorPrimeiroBT).size()-1;

            }

            timerHandlerToLetterPad.postDelayed(this,TEMPORIZADOR*1000);

        }
    }*/;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        /*SETA OS RUNNABLES RESPONSAVEIS POR TROCAR OS BTS SELECIONADOS*/
        timerRunnableToNumPad = getRunnableToNumPad();
        timerRunnableToLetterPad = getRunnableToLetterPad();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*SETA TELA FULLSCREEN*/
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        /*SETA O ONCLICK DA ACTIVITY
        * O click precisa ser capturado em qualquer lugar da tela*/
        LinearLayout conteinerActvtPrin = findViewById(R.id.ConteinerActivityPrincipal);
        conteinerActvtPrin.setOnClickListener(this);

        /*RECUPERA SHARED PREFERENCES
        * no momento serve apenas para pegar a configuração de tempo do app*/
        SharedPreferences preferences = getSharedPreferences(TEMPORIZADOR_CONFIG, Activity.MODE_PRIVATE);
        TEMPORIZADOR = preferences.getInt(TEMPORIZADOR_CONFIG,TEMPORIZADOR_DEFAULT*1000);
        Log.d("Log"," valor do temporizador na activityprincipal "+TEMPORIZADOR*1000);
        txtFrase = findViewById(R.id.textViewFrase);

        /*LINKA BTS*/
        bt1 = findViewById(R.id.button1);
        bt2 = findViewById(R.id.button2);
        bt3 = findViewById(R.id.button3);
        bt4 = findViewById(R.id.button4);
        bt5 = findViewById(R.id.button5);
        bt6 = findViewById(R.id.button6);
        bt7 = findViewById(R.id.button7);
        bt8 = findViewById(R.id.button8);
        bt9 = findViewById(R.id.button9);
        bt0 = findViewById(R.id.button0);
        btApaga = findViewById(R.id.btApaga);

        opcoes =  GerenciadorDeConstantes.retornaOpcoesStringBts();

        timerHandlerToNumPad.postDelayed(timerRunnableToNumPad,50);


    }

    /*metodo onclick, controla os cliques, e executa a ação correta*/
    @Override
    public void onClick(View v) {

        Log.d("Log"," dentro do onclick da activity");
        /*if else para verificar a escolha do botão numérico, ou botão secundário com as letras*/
        if (!selectedNum){
            Log.d("Log","vindo de dentro do setOnclickListener e nao do metodo onClick");
            selectedNum = true;
            timerHandlerToNumPad.removeCallbacks(timerRunnableToNumPad);
            idsTemps = acrescentaBotoes(opcoes.get(valorPrimeiroBT));

            timerHandlerToLetterPad.postDelayed(timerRunnableToLetterPad,50);


        }else {
            selectedNum = false;
            timerHandlerToLetterPad.removeCallbacks(timerRunnableToLetterPad);
            LinearLayout layoutEscolheOpcoes = findViewById(R.id.ConteinerSelecao);
            if (valorPrimeiroBT !=10){
                Log.d("Log","valor do segundo bt testando"+valorSegundoBT);
                acrescentaString(opcoes.get(valorPrimeiroBT).get(valorSegundoBT));
            }else if (valorPrimeiroBT == 10){
                switch (valorSegundoBT){
                    case 0: apagaLetra();
                        break;
                    case 1: apagaFrase();
                        break;
                }
            }
            /*remove os botões secundários de letras, e reseta botões de escolha seleciondos*/
            layoutEscolheOpcoes.removeAllViews();
            btSelecionadoNumMomento =0;
            btLetraSelecionadaNoMomento = 0;
            timerHandlerToNumPad.postDelayed(timerRunnableToNumPad,50);

        }


  }
    @Override
    public boolean onLongClick(View v) {
        resetaContadorBtPrimario();
        return true;
    }

    @Override
    protected void onRestart() {
        SharedPreferences preferences = getSharedPreferences(TEMPORIZADOR_CONFIG,Activity.MODE_PRIVATE);
        TEMPORIZADOR = preferences.getInt(TEMPORIZADOR_CONFIG,TEMPORIZADOR_DEFAULT);
        timerHandlerToNumPad.postDelayed(timerRunnableToNumPad,50);
        super.onRestart();
    }

    @Override
    protected void onStop() {
        timerHandlerToNumPad.removeCallbacks(timerRunnableToNumPad);
        timerHandlerToLetterPad.removeCallbacks(timerRunnableToLetterPad);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("Log","onBackPressed chamado");
        timerHandlerToNumPad.removeCallbacks(timerRunnableToNumPad);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_principal,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int indexMenu = item.getItemId();
        if (indexMenu == R.id.itemMenuConfig){
            Intent intent = new Intent(getApplicationContext(),ActivityConfiguracoes.class);
            timerHandlerToNumPad.removeCallbacks(timerRunnableToNumPad);
            timerHandlerToLetterPad.removeCallbacks(timerRunnableToLetterPad);
            resetaContadores();
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    /*ACRESCENTA SEGUNDA FILEIRA DE BOTÕES
    * Pega as letras do botão primário escolhido, seta em novos botões, e acrescenta ao layout
    * Também configura o layout e o tema dos botões*/
    public List<Integer> acrescentaBotoes(final List<String> opcoes){
        /*PEGA O CONTEINER ONDE SERÃO ACRESCENTADOS OS BOTÕES*/
        LinearLayout layoutEscolheOpcoes = findViewById(R.id.ConteinerSelecao);

        /*ARRAY LIST AUXILIAR/TEMPORARIO*/
        List<Integer> listaDeIds = new ArrayList<>();



        for(int i = 0;i<opcoes.size();i++){
            ContextThemeWrapper themeWrapper = new ContextThemeWrapper(getApplicationContext(),R.style.ButtonProjectStyle);
            Button bt = new Button(themeWrapper);

            /*CONFIGURA LAYOUT DOS BTS
            * Peso, texto,background, e onclickListener*/
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            layoutParams.setMargins(6,6,6,6);
            bt.setLayoutParams(layoutParams);
            bt.setText(opcoes.get(i));
            bt.setBackgroundResource(R.drawable.button_background_theme);
            bt.setOnClickListener(this);


            /*CONFIGURA ID DO BT*/
            int idBt = (int)(i+(1*1.5));
            bt.setId(idBt);

            /*SETA O NOVO BT SECUNDÁRIO COM AS LETRAS DO BT PRIMÁRIO*/
            listaDeIds.add(idBt);

            /*ADICIONA O NOVO BT AO LAYOUT*/
            layoutEscolheOpcoes.addView(bt);
        }

        Log.d("Log","lista de idsSize dentro do acrescentaBts"+listaDeIds.size());
        return  listaDeIds;
    }

    /*metodo responsavel por montar a frase*/
    public void acrescentaString(String opcaoEscolhida){

        if (frase == null){
            frase = opcaoEscolhida;
            txtFrase.setText(frase);
        }else {
            frase = frase.concat(opcaoEscolhida);
            txtFrase.setText(frase);
        }
    }


    /*=============================METODOS DE ATUALIZAÇÃO DO BT SELECIONADO=======================*/
    /*VERIFICA QUAL BOTÃO PRIMARIO ESTA SELECIONADO
    * Serve para controlar a troca de bts, obedecendo o tempo confiurado pelo usuário
    * De forma que muda o botão selecionado, e altera o estado de pressed ou não*/
    public void selecionaBT(){

        Log.d("Log","valores no momento(antes SWITCH), primero= "+valorPrimeiroBT+"  segundo= "+valorSegundoBT);


        switch (btSelecionadoNumMomento){
            case 0:
                btSelecionadoNumMomento=1;
                btApaga.setPressed(false);
                bt1.setPressed(true);
                break;
            case 1:
                btSelecionadoNumMomento=2;
                bt1.setPressed(false);
                bt2.setPressed(true);
                break;
            case 2:
                btSelecionadoNumMomento=3;
                bt2.setPressed(false);
                bt3.setPressed(true);
                break;
            case 3:
                btSelecionadoNumMomento=4;
                bt3.setPressed(false);
                bt4.setPressed(true);
                break;
            case 4:
                btSelecionadoNumMomento=5;
                bt4.setPressed(false);
                bt5.setPressed(true);
                break;
            case 5:
                btSelecionadoNumMomento=6;
                bt5.setPressed(false);
                bt6.setPressed(true);
                break;
            case 6:
                btSelecionadoNumMomento=7;
                bt6.setPressed(false);
                bt7.setPressed(true);
                break;
            case 7:
                btSelecionadoNumMomento=8;
                bt7.setPressed(false);
                bt8.setPressed(true);
                break;
            case 8:
                btSelecionadoNumMomento=9;
                bt8.setPressed(false);
                bt9.setPressed(true);
                break;
            case 9:
                btSelecionadoNumMomento=10;
                bt9.setPressed(false);
                bt0.setPressed(true);
                break;
            case 10:
                btSelecionadoNumMomento=0;
                bt0.setPressed(false);
                btApaga.setPressed(true);
                break;
        }

        Log.d("Log","valores no momento(POS SWTICH), primero= "+valorPrimeiroBT+"  segundo= "+valorSegundoBT);


    }
    /*VERIFICA QUAL BOTÃO SECUNDÁRIO ESTA SELECIONADO
     * Serve para controlar a troca de bts, obedecendo o tempo confiurado pelo usuário
     * De forma que muda o botão selecionado, e altera o estado de pressed ou não*/
    public void selecionaBTLetra(){
        Log.d("Log","lista de idsSize dentro do SelecionaLetra "+idsTemps.size());

        switch (btLetraSelecionadaNoMomento){
            case 0:
                btLetraSelecionadaNoMomento = 1;
                btTemp1 = findViewById(idsTemps.get(0));
                btTemp2 = findViewById(idsTemps.get(idsTemps.size()-1));
                btTemp1.setPressed(true);
                btTemp2.setPressed(false);
                break;
            case 1:
                if (idsTemps.size()<3) {
                    btLetraSelecionadaNoMomento = 0;
                    btTemp1 = findViewById(idsTemps.get(1));
                    btTemp2 = findViewById(idsTemps.get(0));
                    btTemp1.setPressed(true);
                    btTemp2.setPressed(false);
                break;
                }else{
                    btLetraSelecionadaNoMomento = 2;
                    btTemp1 = findViewById(idsTemps.get(1));
                    btTemp2 = findViewById(idsTemps.get(0));
                    btTemp1.setPressed(true);
                    btTemp2.setPressed(false);
                break;}
            case 2:

                if (idsTemps.size()<4){

                    btTemp1 = findViewById(idsTemps.get(2));
                    btTemp2 = findViewById(idsTemps.get(1));
                    btLetraSelecionadaNoMomento = 0;
                    btTemp1.setPressed(true);
                    btTemp2.setPressed(false);
                    break;
                }else{
                    btLetraSelecionadaNoMomento = 3;
                    btTemp1 = findViewById(idsTemps.get(2));
                    btTemp2 = findViewById(idsTemps.get(1));
                    btTemp1.setPressed(true);
                     btTemp2.setPressed(false);
                break;}
            case 3:
                btLetraSelecionadaNoMomento = 0;
                btTemp1 = findViewById(idsTemps.get(3));
                btTemp2 = findViewById(idsTemps.get(2));
                btTemp1.setPressed(true);
                btTemp2.setPressed(false);
                break;
}
        }
    /*============================================================================================*/

    /*METODOS PARA APAGAR TODA A FRASE, OU A ULTIMA LETRA SELECIONADA*/
    private void apagaFrase() {

        if (frase!=null){
            resetaContadores();

        }else {
            Toast.makeText(getApplicationContext(),"Não há frase para ser apagada",Toast.LENGTH_SHORT).show();

        }

        }
    private void apagaLetra(){
        if (frase != null) {
            int sizeString = frase.length();
            frase = frase.substring(0, sizeString - 1);
            txtFrase.setText(frase);
        }else{
            Toast.makeText(getApplicationContext(),"Não há letra para ser apagada",Toast.LENGTH_SHORT).show();
        }

        }

    /*RESETA OS CONTADORES/CONTROLADORES
    * usado ao sair da activity ou ao apagar toda a frase*/
    private void resetaContadores(){
            btSelecionadoNumMomento = 0;
            btLetraSelecionadaNoMomento = 0;
            valorSegundoBT = 0;
            valorPrimeiroBT= 0;
            frase = "";
            txtFrase.setText(frase);

        }
    private void resetaContadorBtPrimario(){
        if (!selectedNum){
            valorPrimeiroBT=0;
        }
    }


    /*RETORNA RUNNABLES DE CONTROLE DE TROCA DE BT SELECIONA (BTS PRIMÁRIOS/NUMERICOS*/
    private Runnable getRunnableToNumPad(){
        Runnable timerRunnableToNumPad = new Runnable() {

            @Override
            public void run() {

                selecionaBT();

                if (btSelecionadoNumMomento !=0){
                    Log.d("Log","btSelecionado no momento direto do runabble"+btSelecionadoNumMomento);
                    valorPrimeiroBT = btSelecionadoNumMomento-1;

                }else{
                    valorPrimeiroBT = 10;
                }
                Log.d("Log","botao ativo"+btSelecionadoNumMomento);

                timerHandlerToNumPad.postDelayed(this,TEMPORIZADOR*1000);

            }
        };
        return timerRunnableToNumPad;
    }
    private Runnable getRunnableToLetterPad(){
        Runnable timerRunnableToLetterPad = new Runnable() {
            @Override
            public void run() {


                selecionaBTLetra();
                if (btLetraSelecionadaNoMomento!=0){
                    valorSegundoBT = btLetraSelecionadaNoMomento-1;

                }
                else{
                    valorSegundoBT = opcoes.get(valorPrimeiroBT).size()-1;

                }

                timerHandlerToLetterPad.postDelayed(this,TEMPORIZADOR*1000);

            }
        };


        return timerRunnableToLetterPad;
        }



}

