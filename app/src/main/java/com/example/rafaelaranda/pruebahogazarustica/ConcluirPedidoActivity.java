package com.example.rafaelaranda.pruebahogazarustica;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ConcluirPedidoActivity extends AppCompatActivity {
  private Button nuevo, regresar;
  private MediaPlayer player;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_concluir_pedido);

    nuevo = (Button)findViewById(R.id.nuevo);
    regresar = (Button)findViewById(R.id.regresar);

    player = MediaPlayer.create(getApplicationContext(), R.raw.agradecimiento);
    player.start();
    Toast.makeText(getApplicationContext(), "Reproduciendo audio :D", Toast.LENGTH_LONG).show();

    nuevo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(ConcluirPedidoActivity.this, MenuPedidoActivity.class));
      }
    });

    regresar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(ConcluirPedidoActivity.this, MainActivity.class));
      }
    });
  }
}
