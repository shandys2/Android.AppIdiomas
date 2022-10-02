package com.dani.lingua;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.*;

public class MainActivity extends AppCompatActivity{

    MediaPlayer mpCorrecto,mpIncorrecto;
    ArrayList<Pregunta>preguntas2;
    String[]respuestas;
    Drawable[] iDrawable;
    List<Integer> desordename = Arrays.asList(0,1,2,3,4,5);
    List<Integer> desordenada;
    TextView pregunta;
    Button res1,res2,res3,res4,res5 ,res6,siguienteFase,voz;
    ImageButton iButton1,iButton2,iButton3,iButton4,iButton5,iButton6;
    Integer posPregActual =0;
    int iEscoba,iSofa,iMovil,iDog,iGato,iLampara;
    String[] sImagenes;
    ImageButton[] iButtons ;
    Button[]buttons;
    TextToSpeech t1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
       //Crea un ArrayList a partir del Set conteniendo los valores originales.
         desordenada = new ArrayList<>(desordename);
       //Desordena aleatoriamente la lista.
         Collections.shuffle(desordenada);

         mpCorrecto= MediaPlayer.create(this, R.raw.correct);
         mpIncorrecto= MediaPlayer.create(this, R.raw.falso);

         //generamos el arraylist de preguntas y respuestas
         voz =findViewById(R.id.voz);

         pregunta =findViewById(R.id.pregunta);
         res1 =findViewById(R.id.res1);
         res2 =findViewById(R.id.res2);
         res3 =findViewById(R.id.res3);
         res4 =findViewById(R.id.res4);
         res5 =findViewById(R.id.res5);
         res6 =findViewById(R.id.res6);
         siguienteFase=findViewById(R.id.siguienteFase);
         siguienteFase.setVisibility(View.GONE);//este boton no aparecerá hasta el final de la primera fase
        //las respuestas seran siempre las mismas en diferente orden
         respuestas=new String[]{getString(R.string.puertaR),getString(R.string.perroR),getString(R.string.gallina),getString(R.string.gatoR),getString(R.string.silla),getString(R.string.puerto)};

         buttons = new Button[]{res1, res2, res3, res4, res5, res6};

         //cambiamos el color de fondo de todos los botones
        for (Button boton: buttons) {
            boton.setBackgroundColor(Color.BLUE);
        }

         iButton1 = findViewById(R.id.b1);
         iButton2 = findViewById(R.id.b2);
         iButton3 = findViewById(R.id.b3);
         iButton4 = findViewById(R.id.b4);
         iButton5 = findViewById(R.id.b5);
         iButton6 = findViewById(R.id.b6);

         iButtons = new ImageButton[]{iButton1, iButton2, iButton3, iButton4, iButton5, iButton6};

         for (ImageButton boton:iButtons) {
            quitarVisibilidadImageButton(boton);
         }

         //pasamos las imagenes a variables int
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
                                     getResources().getResourceEntryName(iEscoba)};


          iDrawable =new Drawable[]{getResources().getDrawable(iGato),
                                    getResources().getDrawable(iSofa),
                                    getResources().getDrawable(iMovil),
                                    getResources().getDrawable(iLampara),
                                    getResources().getDrawable(iDog),
                                    getResources().getDrawable(iEscoba)};

          t1= new TextToSpeech(this, new TextToSpeech.OnInitListener() {
              @Override
              public void onInit(int i) {
                  if(i!=TextToSpeech.ERROR){
                      t1.setLanguage(Locale.getDefault());
                  }
              }
          });
         rellenarLista();
         asignarValores();

         voz.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String v=pregunta.getText().toString();
                 int s =t1.speak(v,TextToSpeech.QUEUE_FLUSH,null);

             }
         });

        //asignamos los eventos a todos los Buttons
        for (Button boton: buttons) {
            boton.setOnClickListener(view -> {
                comprobarRespuesta(boton);
            });
        }

        //asignamos los eventos a todos los ImageButtons
        for (ImageButton iBoton: iButtons) {
            iBoton.setOnClickListener(view -> {
                comprobarRespuestaImagen(iBoton);
            });
        }

        siguienteFase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, Activity3.class);
                //myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());

            }
        });
    }

    public void comprobarRespuesta(Button boton){

        if(preguntas2.get(posPregActual).respuestaCorrecta.equals(boton.getText())){

            boton.setBackgroundColor(Color.GREEN);
            mpCorrecto.start();
            posPregActual++;
            new CountDownTimer(2500, 2500){
                @Override
                public void onTick(long l) {
                }
                @RequiresApi(api = Build.VERSION_CODES.O)
                public  void onFinish(){
                    asignarValores();
                    boton.setBackgroundColor(Color.BLUE);
                }
            }.start();

        }else {

            boton.setBackgroundColor(Color.RED);
            mpIncorrecto.start();
            new CountDownTimer(1000, 1000){
                @Override
                public void onTick(long l) {
                }
                public  void onFinish(){
                    boton.setBackgroundColor(Color.BLUE);;
                }
            }.start();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void comprobarRespuestaImagen(ImageButton boton){

        if (boton.getTooltipText().equals(preguntas2.get(posPregActual).respuestaCorrecta.toLowerCase())){

            boton.setBackgroundColor(Color.GREEN);
            posPregActual++;
            mpCorrecto.start();
            new CountDownTimer(1000, 1000){
                @Override
                public void onTick(long l) {
                }
                public  void onFinish(){
                    asignarValores();
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
                    boton.setBackgroundColor(Color.LTGRAY);;
                }
            }.start();
        }
    }
    public void rellenarLista(){

        //preguntar por el indexof de la respuesta en el array original
        preguntas2 = new ArrayList<>();
        preguntas2.add(new Pregunta(getString(R.string.puertaP),getString(R.string.puertaR),respuestas));
        preguntas2.add(new Pregunta(getString(R.string.perroP),getString(R.string.perroR),respuestas));
        preguntas2.add(new Pregunta(getString(R.string.gatoP),getString(R.string.gatoR),respuestas));
        preguntas2.add(new Pregunta(getString(R.string.escobaP),getString(R.string.escobaR),sImagenes));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void asignarValores(){

        if( posPregActual < preguntas2.size()-1) {

            Collections.shuffle(desordenada);
            String text =preguntas2.get(posPregActual).pregunta;
            pregunta.setText(text);

            for (int i = 0; i < buttons.length; i++) {
                 String respuesta=(preguntas2.get(posPregActual).respuestas[desordenada.get(i)]);
                 buttons[i].setText(respuesta);
            }

        }else if(posPregActual+1==preguntas2.size()){

             pregunta.setText(preguntas2.get(posPregActual).pregunta);

            //quitamos de la vista los botones normales
            for (Button boton: buttons) {
                quitarVisibilidadButton(boton);
            }

            //y damos visibilidad a los botones con imagen
            int x=0;
            for (ImageButton iBoton: iButtons) {
                iBoton.setImageDrawable(iDrawable[desordenada.get(x)]);
                iBoton.setTooltipText(sImagenes[desordenada.get(x)]);
                darVisibilidadImageButton(iBoton);
                x++;
            }
        } else{
            pregunta.setText("");
            siguienteFase.setVisibility(View.VISIBLE);
            voz.setVisibility(View.GONE);
        }
    }

    public void quitarVisibilidadImageButton(ImageButton boton){

        boton.setVisibility(View.GONE);
    }
    public void darVisibilidadImageButton(ImageButton boton){

        boton.setVisibility(View.VISIBLE);
    }
    public void quitarVisibilidadButton(Button boton){

        boton.setVisibility(View.GONE);
    }
    public void darVisibilidadButton(Button boton){

        boton.setVisibility(View.VISIBLE);
    }

}
