package com.example.rafaelaranda.pruebahogazarustica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuPedidoActivity extends AppCompatActivity {
  private ValueEventListener valueEventListener;
  private DatabaseReference ingredientesref;
  private TextView pedido;
  //private String ingredientes[];
  private static String[] datos = {"Chile serrano","Ajo","Orégano","Avena con salvado","Zanahoria", "Chocolate", "Café"/*,
    "Chile serrano","Ajo","Orégano","Avena con salvado","Zanahoria", "Chocolate", "Café"*/};
  public static String ingredientesel;
  private ListView lista;
  ArrayAdapter<String> adaptador;
  private CheckBox ingredientes[] = new CheckBox[datos.length];
  private RelativeLayout layout;
  private DisplayMetrics displayMetrics = new DisplayMetrics();
  private int scwidth, scheight;
  public static int totaling;
  private String ingpedido[]/*,panescreados[][]*/;
  private boolean ingselected[], pancreado;
  public static ArrayList<String[]> panescreados = new ArrayList<String[]>();
  private Button agregarcarrito, resetear;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu_pedido);

    //Para conocer el tamaño de la pantalla
    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    scwidth = displayMetrics.widthPixels;
    scheight = displayMetrics.heightPixels;

    lista = (ListView)findViewById(R.id.lista);
    pedido = (TextView)findViewById(R.id.pedido);
    agregarcarrito = (Button)findViewById(R.id.agregarcarrito);
    resetear = (Button)findViewById(R.id.resetear);

    adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
    lista.setAdapter(adaptador);

    ingpedido = new String[datos.length];
    ingselected = new boolean[datos.length];
    //panescreados = new ArrayList<String[]>();
    pancreado = false;

    for(byte i=0; i<datos.length; i++){
      ingselected[i] = false;
    }
    totaling = 0;
    lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(!ingselected[i]){
          pedido.append("\n -"+datos[i]);
          ingpedido[totaling] = datos[i];
          ingselected[i] = true;
          pancreado = true;
          totaling++;
        } else{
          Toast.makeText(getApplicationContext(), "El ingrediente "+datos[i]+" ya fue agregado al pedido", Toast.LENGTH_LONG).show();
        }
      }
    });

    agregarcarrito.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(pancreado){
          panescreados.add(ingpedido);
          resetearValores();
          //Toast.makeText(getApplicationContext(), "Se tiene un total de: "+panescreados.size()+" pan(es) en el carrito", Toast.LENGTH_LONG).show();
          startActivity(new Intent(MenuPedidoActivity.this, VistaCarritoComprasActivity.class));
        } else{
          Toast.makeText(getApplicationContext(), "Favor de elegir al menos un ingrediente para crear su pan", Toast.LENGTH_LONG).show();
        }
      }
    });

    resetear.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(getApplicationContext(), "Favor de elegir al menos un ingrediente para crear su pan", Toast.LENGTH_LONG).show();
        resetearValores();
      }
    });
    //lista.setMinimumHeight(scheight);

    //Toast.makeText(this, "Largo de la pantalla: "+scwidth+"px, alto de la pantalla: "+scheight+"px", Toast.LENGTH_LONG).show();
  }

  public void resetearValores(){
    ingpedido = new String[datos.length];
    for(byte i=0; i< datos.length; i++){
      //ingpedido[i] = null;
      ingselected[i] = false;
    }
    pedido.setText("");
    pancreado = false;
    totaling = 0;
  }
}
