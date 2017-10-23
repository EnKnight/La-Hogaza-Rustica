package com.example.rafaelaranda.pruebahogazarustica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  private Button registrarse, invitado, iniciarSesion;
  private Intent intent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    registrarse = (Button) findViewById(R.id.registrarse);
    invitado = (Button) findViewById(R.id.invitado);
    iniciarSesion = (Button)findViewById(R.id.iniciarSesion);

    registrarse.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        intent = new Intent(MainActivity.this, RegistrarseActivity.class);
        startActivity(intent);
      }
    });

    iniciarSesion.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        intent = new Intent(MainActivity.this, IniciarSesionActivity.class);
        startActivity(intent);
      }
    });
  }
}
