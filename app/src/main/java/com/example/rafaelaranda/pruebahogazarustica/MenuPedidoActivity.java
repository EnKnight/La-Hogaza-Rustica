package com.example.rafaelaranda.pruebahogazarustica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuPedidoActivity extends AppCompatActivity {
  private ValueEventListener valueEventListener;
  private DatabaseReference ingredientesref;
  private String ingredientes[];
  private static String[] datos = {"Chile serrano","Ajo","Orégano","Avena con salvado","Zanahoria", "Chocolate", "Café"};
  public static String ingredientesel;
  private ListView lista;

  ArrayAdapter<String> adaptador;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu_pedido);

    /*ingredientesref = FirebaseDatabase.getInstance().getReference().child("pan");
    valueEventListener = new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        Toast.makeText(getApplicationContext(), "nodos anidados: "+dataSnapshot.getChildrenCount(), Toast.LENGTH_LONG).show();
        for(byte i=0; i<dataSnapshot.getChildrenCount(); i++){

        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    };

    ingredientesref.addListenerForSingleValueEvent(valueEventListener);*/

    adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
    lista = (ListView)findViewById(R.id.lista);
    lista.setAdapter(adaptador);
    lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ingredientesel = datos[i];
        startActivity(new Intent(MenuPedidoActivity.this, DetallePanActivity.class));
        /*try{
          ingredientesel = ingredientes[i];
          Toast.makeText(getApplicationContext(), "Ingrediente seleccionado: "+ingredientesel, Toast.LENGTH_LONG).show();
        }catch (Exception e){
          Toast.makeText(MenuPedidoActivity.this, "Error: "+e.toString(), Toast.LENGTH_LONG).show();
        }*/
      }
    });
  }
}
