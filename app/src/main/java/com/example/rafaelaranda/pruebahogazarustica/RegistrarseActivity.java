package com.example.rafaelaranda.pruebahogazarustica;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrarseActivity extends AppCompatActivity {
  private EditText nombre, apellido, correo, dia, mes, anio, tel, passwd, passwd2;
  private Button registrarse;
  private FirebaseAuth auth;
  private Intent intent;
  private ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registrarse);

    //Get Firebase auth instance
    auth = FirebaseAuth.getInstance();

    registrarse = (Button)findViewById(R.id.registrarse);
    nombre = (EditText)findViewById(R.id.nombre);
    apellido = (EditText)findViewById(R.id.apellido);
    correo = (EditText)findViewById(R.id.correo);
    dia = (EditText)findViewById(R.id.dia);
    mes = (EditText)findViewById(R.id.mes);
    anio = (EditText)findViewById(R.id.anio);
    tel = (EditText)findViewById(R.id.tel);
    passwd = (EditText)findViewById(R.id.passwd);
    passwd2 = (EditText)findViewById(R.id.passwd2);
    progressBar = (ProgressBar)findViewById(R.id.progressBar);

    registrarse.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(TextUtils.isEmpty(nombre.getText().toString())){
          Toast.makeText(getApplicationContext(), "Favor de ingresar su nombre para continuar", Toast.LENGTH_LONG).show();
          return;
        }
        if(TextUtils.isEmpty(apellido.getText().toString())){
          Toast.makeText(getApplicationContext(), "Favor de ingresar su apellido para continuar", Toast.LENGTH_LONG).show();
          return;
        }
        if(TextUtils.isEmpty(correo.getText().toString())){
          Toast.makeText(getApplicationContext(), "Favor de ingresar su correo electrónico para continuar", Toast.LENGTH_LONG).show();
          return;
        }
        if(TextUtils.isEmpty(dia.getText().toString()) || TextUtils.isEmpty(mes.getText().toString()) || TextUtils.isEmpty(anio.getText().toString())){
          Toast.makeText(getApplicationContext(), "Favor de ingresar su fecha de nacimiento completa para continuar", Toast.LENGTH_LONG).show();
          return;
        }

        if(TextUtils.isEmpty(tel.getText().toString())){
          Toast.makeText(getApplicationContext(), "Favor de ingresar su número de teléfono para continuar", Toast.LENGTH_LONG).show();
          return;
        }

        if(TextUtils.isEmpty(passwd.getText().toString())){
          Toast.makeText(getApplicationContext(), "Favor de ingresar su nueva contraseña para continuar", Toast.LENGTH_LONG).show();
          return;
        }
        if(TextUtils.isEmpty(passwd2.getText().toString())){
          Toast.makeText(getApplicationContext(), "Favor de confirmar su nueva contraseña para continuar", Toast.LENGTH_LONG).show();
          return;
        }

        if(!passwd.getText().toString().equals(passwd2.getText().toString())){
          Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden, favor de verificar", Toast.LENGTH_LONG).show();
          return;
        }

        if(passwd.getText().toString().length() <= 6){
          Toast.makeText(getApplicationContext(), "La contraseña al menos debe de contener 7 caracteres", Toast.LENGTH_LONG).show();
          return;
        }

        //create user

        auth.createUserWithEmailAndPassword(correo.getText().toString().trim(), passwd.getText().toString().trim())
          .addOnCompleteListener(RegistrarseActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              Toast.makeText(RegistrarseActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
              progressBar.setVisibility(View.GONE);
              // If sign in fails, display a message to the user. If sign in succeeds
              // the auth state listener will be notified and logic to handle the
              // signed in user can be handled in the listener.
              if (!task.isSuccessful()) {
                Toast.makeText(RegistrarseActivity.this, "Authentication failed." + task.getException(),
                  Toast.LENGTH_SHORT).show();
              } else {
                startActivity(new Intent(RegistrarseActivity.this, MenuPrincipalActivity.class));
                finish();
              }
            }
          });
      }
    });
  }
}
