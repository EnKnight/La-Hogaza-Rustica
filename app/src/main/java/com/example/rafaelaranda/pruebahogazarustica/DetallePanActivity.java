package com.example.rafaelaranda.pruebahogazarustica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetallePanActivity extends AppCompatActivity {
  private TextView bienvenida;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detalle_pan);

    bienvenida = (TextView)findViewById(R.id.bienvenida);
    bienvenida.setText("Pan de "+MenuPedidoActivity.ingredientesel);
  }
}
