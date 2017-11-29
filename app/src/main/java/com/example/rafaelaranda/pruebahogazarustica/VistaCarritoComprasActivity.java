package com.example.rafaelaranda.pruebahogazarustica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VistaCarritoComprasActivity extends AppCompatActivity {
  private TextView pedido, costopedidotxt;
  private String descpedido[];
  private Button agregar, pagar, resetar;
  private boolean carritoval = false;
  private static int costopedido=0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_vista_carrito_compras);

    pedido = (TextView)findViewById(R.id.pedido);
    agregar = (Button)findViewById(R.id.agregar);
    pagar = (Button)findViewById(R.id.pagar);
    resetar = (Button)findViewById(R.id.resetear);
    costopedidotxt = (TextView)findViewById(R.id.costopedido);

    //costopedido=0;

    if(MenuPedidoActivity.panescreados.size() > 0){
      carritoval = true;
      pedido.setText("");
      costopedido += MenuPedidoActivity.costopan;
      costopedidotxt.setText("Costo total del pedido: $"+costopedido);

      for(int i=0, aux; i<MenuPedidoActivity.panescreados.size(); i++) {
        aux = i + 1;
        descpedido = MenuPedidoActivity.panescreados.get(i);
        pedido.append("\n\nIngredientes del pan " + aux + ": ");
        for (byte j = 0; j < descpedido.length; j++) {
          if (descpedido[j] != null) {
            pedido.append(descpedido[j] + ", ");
          }
        }
        //pedido.append("Tamaño: ");
      }

    } else{
      pedido.setText("Aún no se han añadido panes al carrito de compras");
      //pedido.setText("Favor de añadir por lo menos un pan al pedido para conntinuar");
    }
    agregar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(VistaCarritoComprasActivity.this, MenuPedidoActivity.class));
      }
    });

    pagar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(carritoval){
          startActivity(new Intent(VistaCarritoComprasActivity.this, MenuPrincipalActivity.class));
          Toast.makeText(getApplicationContext(), "Pedido realizado exitosamente", Toast.LENGTH_LONG).show();
          resetear();
        } else{
          Toast.makeText(getApplicationContext(), "Favor de añadir por lo menos un pan al pedido para continuar", Toast.LENGTH_LONG).show();
        }
      }
    });

    resetar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        resetear();
      }
    });
  }

  public void resetear(){
    MenuPedidoActivity.panescreados = new ArrayList<String[]>();
    costopedido = 0;
    pedido.setText("Aún no se han añadido panes al carrito de compras");
    carritoval = false;
  }
}
