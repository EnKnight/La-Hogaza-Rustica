package com.example.rafaelaranda.pruebahogazarustica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu_pedido);

    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    scwidth = displayMetrics.widthPixels;
    scheight = displayMetrics.heightPixels;

    lista = (ListView)findViewById(R.id.lista);
    pedido = (TextView)findViewById(R.id.pedido);

    adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
    lista.setAdapter(adaptador);

    lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pedido.append("\n"+datos[i]);
      }
    });
    //lista.setMinimumHeight(scheight);

    //Toast.makeText(this, "Largo de la pantalla: "+scwidth+"px, alto de la pantalla: "+scheight+"px", Toast.LENGTH_LONG).show();

  }
}
