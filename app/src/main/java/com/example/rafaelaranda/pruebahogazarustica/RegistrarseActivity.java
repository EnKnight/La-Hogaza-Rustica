package com.example.rafaelaranda.pruebahogazarustica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrarseActivity extends AppCompatActivity {
  private Button registrarse;
  Intent intent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registrarse);

    registrarse = (Button)findViewById(R.id.registrarse);

    registrarse.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        intent = new Intent(RegistrarseActivity.this, MenuPrincipalActivity.class);
        startActivity(intent);
      }
    });
  }
}
