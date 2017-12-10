package com.example.rafaelaranda.pruebahogazarustica;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

  private Button registrarse, invitado, iniciarSesion;
  private Intent intent;
  public static FirebaseAuth auth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    registrarse = (Button) findViewById(R.id.registrarse);
    invitado = (Button) findViewById(R.id.invitado);
    iniciarSesion = (Button)findViewById(R.id.iniciarSesion);

    auth = FirebaseAuth.getInstance();

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

    invitado.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        auth.signInAnonymously().addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              //Log.d(TAG, "OnComplete : " +task.isSuccessful());

              if (!task.isSuccessful()) {
                //Log.w(TAG, "Failed : ", task.getException());
                Toast.makeText(MainActivity.this, "Error de autenticación, favor de intentar más tarde", Toast.LENGTH_SHORT).show();
              } else{
                startActivity(new Intent(MainActivity.this, MenuPedidoActivity.class));
                finish();
              }
            }
          });
      }
    });
  }
}
