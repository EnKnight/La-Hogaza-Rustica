package com.example.rafaelaranda.pruebahogazarustica;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IniciarSesionActivity extends AppCompatActivity {
  private EditText correo, passwd;
  private Button ingresar;
  private Intent intent;
  private FirebaseAuth auth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_iniciar_sesion);

    //Get Firebase auth instance
    auth = FirebaseAuth.getInstance();
    if (auth.getCurrentUser() != null) {
      startActivity(new Intent(IniciarSesionActivity.this, MenuPrincipalActivity.class));
      finish();
    }

    correo = (EditText)findViewById(R.id.correo);
    passwd = (EditText)findViewById(R.id.passwd);
    ingresar = (Button)findViewById(R.id.ingresar);

    ingresar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        if(TextUtils.isEmpty(correo.getText().toString())){
          Toast.makeText(getApplicationContext(), "Favor de ingresar su correo electrónico para continuar", Toast.LENGTH_LONG).show();
          return;
        }

        if(TextUtils.isEmpty(passwd.getText().toString())){
          Toast.makeText(getApplicationContext(), "Favor de ingresar su contraseña para continuar", Toast.LENGTH_LONG).show();
          return;
        }

        auth.signInWithEmailAndPassword(correo.getText().toString(), passwd.getText().toString())
          .addOnCompleteListener(IniciarSesionActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              // If sign in fails, display a message to the user. If sign in succeeds
              // the auth state listener will be notified and logic to handle the
              // signed in user can be handled in the listener.
              //progressBar.setVisibility(View.GONE);
              if (!task.isSuccessful()) {
                Toast.makeText(getApplicationContext(), "Nombre de usuario o contraseña incorrectos. Favor de verificar", Toast.LENGTH_LONG).show();
                // there was an error
                /*if (password.length() < 6) {
                  inputPassword.setError(getString(R.string.minimum_password));
                } else {
                  Toast.makeText(IniciarSesionActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                }*/
              } else {
                Intent intent = new Intent(IniciarSesionActivity.this, MenuPrincipalActivity.class);
                startActivity(intent);
                finish();
              }
            }
          });
      }
    });

  }
}
