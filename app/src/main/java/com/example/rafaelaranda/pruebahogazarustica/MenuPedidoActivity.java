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
import android.widget.Spinner;
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
  private TextView pedido, preciopantxt;
  //private String ingredientes[];
  private static String[] datos = {"Chile serrano","Ajo","Orégano","Zanahoria", "Chocolate", "Café", "Amaranto", "Ajonjolí",
  "Avena", "Canela", "Chía", "Linaza", "Salvado", "Semillas de girasol", "Semillas de calabaza", "Pasas", "Nueces", "Almendras"}, tam = {"Chico", "Mediano", "Grande"};
  public static String ingredientesel;
  public static int tamanio;

  private ListView lista;
  ArrayAdapter<String> adaptadortam;
  ArrayAdapter<String> adaptador;
  ArrayAdapter<CharSequence> adapter;

  //private CheckBox ingredientes[] = new CheckBox[datos.length];
  //private RelativeLayout layout;
  private DisplayMetrics displayMetrics = new DisplayMetrics();
  private int scwidth, scheight;
  public static int totaling, preciopan, costoextra=0, costopan;
  private String ingpedido[]/*,panescreados[][]*/;
  private boolean ingselected[], pancreado;
  public static ArrayList<String[]> panescreados = new ArrayList<String[]>();
  private Button agregarcarrito, resetear, vercarrito;
  private Spinner cmbOpciones;

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
    vercarrito = (Button)findViewById(R.id.vercarrito);
    preciopantxt = (TextView)findViewById(R.id.preciopan);
    cmbOpciones = (Spinner)findViewById(R.id.CmbOpciones);

    adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
    adaptadortam = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tam);
    adapter = ArrayAdapter.createFromResource(this, R.array.valores_array, android.R.layout.simple_spinner_item);
    adaptadortam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    cmbOpciones.setAdapter(adapter);
    lista.setAdapter(adaptador);

    ingpedido = new String[datos.length];
    ingselected = new boolean[datos.length];
    //panescreados = new ArrayList<String[]>();
    pancreado = false;
    costopan = 0;

    for(byte i=0; i<datos.length; i++){
      ingselected[i] = false;
    }

    totaling = preciopan = 0;
    lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(!ingselected[i]){
          if(preciopan==0){
            if(tamanio == 0){
              preciopan = 30;
            } else if(tamanio == 1){
              preciopan = 40;
            } else if(tamanio == 2){
              preciopan = 50;
            }
          }
          if(datos[i].equals("Pasas")){
            costoextra += 5;
            preciopan += 5;
          } else if(datos[i].equals("Nueces") || datos[i].equals("Almendras")){
            costoextra += 10;
            preciopan += 10;
          }
          pedido.append("\n -"+datos[i]);
          ingpedido[totaling] = datos[i];
          ingselected[i] = true;
          pancreado = true;
          totaling++;
          preciopantxt.setText("Total del pan creado: $"+preciopan);
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
        costopan = preciopan;
        //Toast.makeText(getApplicationContext(), "Se tiene un total de: "+panescreados.size()+" pan(es) en el carrito", Toast.LENGTH_LONG).show();
        resetearValores();
        startActivity(new Intent(MenuPedidoActivity.this, VistaCarritoComprasActivity.class));
      } else{
        Toast.makeText(getApplicationContext(), "Favor de elegir al menos un ingrediente para crear su pan", Toast.LENGTH_LONG).show();
      }
      }
    });

    vercarrito.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(MenuPedidoActivity.this, VistaCarritoComprasActivity.class));
      }
    });

    resetear.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //Toast.makeText(getApplicationContext(), "Favor de elegir al menos un ingrediente para crear su pan", Toast.LENGTH_LONG).show();
        resetearValores();
      }
    });

    cmbOpciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
        tamanio = position;
        if(position == 0){
          preciopan = 30;
          preciopan += costoextra;
        } else if(position == 1){
          preciopan = 40;
          preciopan += costoextra;
        } else{
          preciopan = 50;
          preciopan += costoextra;
        }
        preciopantxt.setText("Total del pan creado: $"+preciopan);

        //Toast.makeText(getApplicationContext(), "Tamaño: "+tam[tamanio], Toast.LENGTH_LONG).show();
      }

      public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(), "Favor de seleccionar un tamaño para continuar", Toast.LENGTH_LONG).show();

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
    preciopan = 0;
    costoextra = 0;
    preciopantxt.setText("Total del pan creado: $0");
  }
}
