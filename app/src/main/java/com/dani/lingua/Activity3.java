package com.dani.lingua;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Activity3 extends AppCompatActivity {

    ArrayList<Pregunta> preguntas;
    TextView pregunta;
    List<Integer> desordename = Arrays.asList(0,1,2,3,4,5);
    List<Integer> desordenada;
    ImageButton b1,b2,b3,b4,b5,b6;
    ImageButton[] iButtons;
    Drawable[] iDrawable;
    String[] sImagenes;
    int iEscoba,iSofa,iMovil,iDog,iGato,iLampara;
    Integer posPregActual =0;
    MediaPlayer mpCorrecto,mpIncorrecto;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        //Crea un ArrayList a partir del Set conteniendo los valores originales.
        desordenada = new ArrayList<>(desordename);
        //Desordena aleatoriamente la lista.
        Collections.shuffle(desordenada);
        mpCorrecto= MediaPlayer.create(this, R.raw.correct);
        mpIncorrecto= MediaPlayer.create(this, R.raw.falso);

        pregunta =findViewById(R.id.pregunta2);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        b3=findViewById(R.id.b3);
        b4=findViewById(R.id.b4);
        b5=findViewById(R.id.b5);
        b6=findViewById(R.id.b6);
        iButtons = new ImageButton[]{b1,b2,b3,b4,b5,b6};
        iEscoba =R.drawable.escoba;
        iSofa = R.drawable.sofa;
        iMovil = R.drawable.movil;
        iLampara = R.drawable.lampara;
        iDog = R.drawable.perro;
        iGato = R.drawable.gato;

        sImagenes = new String[]{  getResources().getResourceEntryName(iGato),
                getResources().getResourceEntryName(iSofa),
                getResources().getResourceEntryName(iMovil),
                getResources().getResourceEntryName(iLampara),
                getResources().getResourceEntryName(iDog),
                getResources().getResourceEntryName(iEscoba)
        };

        iDrawable =new Drawable[]{getResources().getDrawable(iGato),
                getResources().getDrawable(iSofa),
                getResources().getDrawable(iMovil),
                getResources().getDrawable(iLampara),
                getResources().getDrawable(iDog),
                getResources().getDrawable(iEscoba)};

        //asignamos los eventos a todos los ImageButtons
        for (ImageButton iBoton: iButtons) {
            iBoton.setOnClickListener(view -> {
                comprobarRespuestaImagen(iBoton);
            });
        }
        rellenarLista();

        //seteamos las imagenes y sus tooltips
        int x=0;
        for (ImageButton iBoton: iButtons) {
            iBoton.setImageDrawable(iDrawable[desordenada.get(x)]);
            iBoton.setTooltipText(sImagenes[desordenada.get(x)]);
            x++;
        }
        siguientePregunta();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void comprobarRespuestaImagen(ImageButton boton){

        System.out.println(boton.getTooltipText());
        System.out.println(preguntas.get(posPregActual).respuestaCorrecta.toLowerCase());
        if (boton.getTooltipText().equals(preguntas.get(posPregActual).respuestaCorrecta.toLowerCase())){
            boton.setBackgroundColor(Color.GREEN);
            preguntas.remove(preguntas.get(posPregActual));

            if(posPregActual==preguntas.size()){
                posPregActual=0;
            }
            mpCorrecto.start();
            new CountDownTimer(1000, 1000){
                @Override
                public void onTick(long l) {
                }
                public  void onFinish(){
                    posPregActual++;
                    siguientePregunta();
                    quitarVisibilidadImageButton(boton);
                    boton.setBackgroundColor(Color.LTGRAY);;
                }
            }.start();

        }else {

            boton.setBackgroundColor(Color.RED);
            mpIncorrecto.start();
            new CountDownTimer(1000, 1000){
                @Override
                public void onTick(long l) { }
                public  void onFinish(){
                    posPregActual++;
                    siguientePregunta();
                    boton.setBackgroundColor(Color.LTGRAY);;
                }
            }.start();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void siguientePregunta(){

        if(preguntas.size()==0){
            Intent myIntent = new Intent(Activity3.this, Activity2.class);
            //myIntent.putExtra("key", value); //Optional parameters
            Activity3.this.startActivity(myIntent);
        }

        if(posPregActual==preguntas.size()){posPregActual=0;}
        pregunta.setText(preguntas.get(posPregActual).pregunta);

    }
    public void rellenarLista(){

        //preguntar por el indexof de la respuesta en el array original
        preguntas = new ArrayList<>();
        preguntas.add(new Pregunta(getString(R.string.escobaP),getString(R.string.escobaR),sImagenes));
        preguntas.add(new Pregunta(getString(R.string.lamparaP),getString(R.string.lamparaR),sImagenes));
        preguntas.add(new Pregunta(getString(R.string.gato2P),getString(R.string.gato2R),sImagenes));
        preguntas.add(new Pregunta(getString(R.string.perro2P),getString(R.string.perro2R),sImagenes));
        preguntas.add(new Pregunta(getString(R.string.sofaP),getString(R.string.sofaR),sImagenes));
        preguntas.add(new Pregunta(getString(R.string.movilP),getString(R.string.movilR),sImagenes));

    }
    public void quitarVisibilidadImageButton(ImageButton boton){

        boton.setVisibility(View.GONE);
    }
}