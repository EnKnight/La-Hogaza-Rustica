package com.example.rafaelaranda.pruebahogazarustica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IniciarSesionActivity extends AppCompatActivity {
  private Button ingresar;
  private Intent intent;
  private FirebaseAuth firebaseAuth;
  private FirebaseUser firebaseUser;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_iniciar_sesion);

    ingresar = (Button)findViewById(R.id.ingresar);

    ingresar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        intent = new Intent(IniciarSesionActivity.this, MenuPrincipalActivity.class);
        startActivity(intent);
      }
    });

    firebaseAuth = FirebaseAuth.getInstance();
    firebaseUser = firebaseAuth.getCurrentUser();
    //Now check if this user is null
    if (firebaseUser == null){
      Toast.makeText(this, "usuario no identificado", Toast.LENGTH_LONG).show();
      //send user to the login page
    }


  }
}
