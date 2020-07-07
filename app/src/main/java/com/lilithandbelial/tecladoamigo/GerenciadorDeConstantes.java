package com.lilithandbelial.tecladoamigo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GerenciadorDeConstantes {


    /*strings para serem adicionadas as Lista de opções*/
    private static final List<String> op1 = new ArrayList<String>( Arrays.asList(" ","1"));
    private static final List<String> op2 = new ArrayList<String>( Arrays.asList("A","B","C","2"));
    private static final List<String> op3 = new ArrayList<String>( Arrays.asList("D","E","F","3"));
    private static final List<String> op4 = new ArrayList<String>( Arrays.asList("G","H","I","4"));
    private static final List<String> op5 = new ArrayList<String>( Arrays.asList("J","K","L","5"));
    private static final List<String> op6 = new ArrayList<String>( Arrays.asList("M","N","O","6"));
    private static final List<String> op7 = new ArrayList<String>( Arrays.asList("P","Q","R","7"));
    private static final List<String> op8 = new ArrayList<String>( Arrays.asList("S","T","U","8"));
    private static final List<String> op9 = new ArrayList<String>( Arrays.asList("V","W","X","9"));
    private static final List<String> op0 = new ArrayList<String>( Arrays.asList("Y","Z","0"));
    private static final List<String> opApaga = new ArrayList<>(Arrays.asList("Apaga letra","Apaga tudo"));
    private static final List<List<String>> opcoes = new ArrayList<List<String>>(Arrays.asList(op1,op2,op3,op4,op5,op6,op7,op8,op9,op0,opApaga));

    public static List<List<String>> retornaOpcoesStringBts(){
        return  opcoes;
    }
}
