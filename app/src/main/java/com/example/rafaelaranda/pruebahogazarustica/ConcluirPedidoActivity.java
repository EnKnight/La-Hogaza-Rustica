package com.example.rafaelaranda.pruebahogazarustica;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ConcluirPedidoActivity extends AppCompatActivity {
  private Button nuevo, regresar;
  private MediaPlayer player;
  String fechaPed;
  //private FirebaseDatabase database;
  private DatabaseReference databaseReference;
  private boolean pedidoValido = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_concluir_pedido);

    nuevo = (Button)findViewById(R.id.nuevo);
    regresar = (Button)findViewById(R.id.regresar);

    databaseReference = FirebaseDatabase.getInstance().getReference();

    player = MediaPlayer.create(getApplicationContext(), R.raw.agradecimiento);
    player.start();
    //Toast.makeText(getApplicationContext(), "Reproduciendo audio :D", Toast.LENGTH_LONG).show();

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

  public void pedidoAceptado(FirebaseUser user){
    if(pedidoValido){
      registrarPedido(user.getUid(), MenuPedidoActivity.panescreados, MenuPedidoActivity.preciopan,
        MenuPrincipalActivity.posped.latitude, MenuPrincipalActivity.posped.longitude, fechaPed);
    } else{
      Toast.makeText(this, "Error registrando el pedido, favor de intentar más tarde", Toast.LENGTH_LONG).show();
    }
  }

  public void registrarPedido(String pedidoId, ArrayList<String[]> panes, int costo, double lat, double longitud, String fechaPedido){

  }
}
