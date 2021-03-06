package com.example.rafaelaranda.pruebahogazarustica;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuPrincipalActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener, OnMapReadyCallback {
  //private TextView lblLatitud, lblLongitud, lblCielo;
  private Button pedido, borrarMarcador;
  public static Location lastLocation;
  public static  LatLng posped;
  private GoogleApiClient apiClient;
  private static final int REQUEST_LOCATION = 2;
  //private DatabaseReference dbCielo = FirebaseDatabase.getInstance().getReference().child("prediccion-hoy").child("cielo");
  private FirebaseAuth auth;
  private GoogleMap map;
  private MapFragment mapFragment;
  private Marker marker;
  private static boolean mapReady = false, markerSet = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu_principal);

    pedido = (Button) findViewById(R.id.pedido);
    borrarMarcador = (Button)findViewById(R.id.borrarMarcador);

    mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

    if(IniciarSesionActivity.useremail != null){
      //Toast.makeText(this, "Bienvenido(a) de nuevo"+IniciarSesionActivity.useremail, Toast.LENGTH_LONG).show();
    }

    /*DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
    float dpHeight = displayMetrics.heightPixels / displayMetrics.density;*/
    //Get Firebase auth instance
    auth = FirebaseAuth.getInstance();

    if (auth.getCurrentUser() == null) {
      Toast.makeText(this, "Favor de identificarse antes de continuar", Toast.LENGTH_LONG).show();
      startActivity(new Intent(MenuPrincipalActivity.this, IniciarSesionActivity.class));
      finish();
    }

    // Create an instance of GoogleAPIClient.
    if (apiClient == null) {
      //Toast.makeText(this, "Creando instancia de conexión a servicios de google play", Toast.LENGTH_LONG).show();
      apiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();
    } else {
      auth.getCurrentUser();
    }
    /*dbCielo.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        String valor = dataSnapshot.getValue().toString();
        lblCielo.setText(valor);
      }
      @Override
      public void onCancelled(DatabaseError databaseError) {
        //Log.e(TAGLOG, "Error!", databaseError.toException());
      }
    });*/
    //lblLatitud = (TextView) findViewById(R.id.lblLatitud);
    //lblLongitud = (TextView) findViewById(R.id.lblLongitud);
    //lblCielo = (TextView)findViewById(R.id.cielo);
    if(markerSet){
      pedido.setEnabled(true);
      borrarMarcador.setEnabled(true);
    } else{
      pedido.setEnabled(false);
      borrarMarcador.setEnabled(false);
    }

    pedido.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        markerSet = false;
        pedido.setEnabled(false);
        marker.remove();
        startActivity(new Intent(MenuPrincipalActivity.this, ConcluirPedidoActivity.class));
      }
    });

    borrarMarcador.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(markerSet){
          marker.remove();
          posped = null;
          borrarMarcador.setEnabled(false);
          markerSet = false;
          pedido.setEnabled(false);
        }
      }
    });

    //Toast.makeText(this, "Densidad de pixeles de la pantalla: "+dpHeight, Toast.LENGTH_LONG).show();
  }

  /*private void updateUI(Location loc) {
    if (loc != null) {
      lblLatitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
      lblLongitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
    } else {
      lblLatitud.setText("Latitud: (desconocida)");
      lblLongitud.setText("Longitud: (desconocida)");
    }
  }*/

  @Override
  public void onConnectionFailed(ConnectionResult result) {
    //Se ha producido un error que no se puede resolver automáticamente
    //y la conexión con los Google Play Services no se ha establecido.

    //Log.e(LOGTAG, "Error grave al conectar con Google Play Services");
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {
    //Toast.makeText(this, "Conectado a Google Play Services :D", Toast.LENGTH_LONG).show();
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      // Check Permissions Now
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
    } else {
      // permission has been granted, continue as usual
      lastLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
      moverMapaUltimaPosicionUsuario(lastLocation);
      //updateUI(lastLocation);
    }

  }

  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    if (requestCode == REQUEST_LOCATION) {
      if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        // We can now safely use the API we requested access to
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          // TODO: Consider calling
          //    ActivityCompat#requestPermissions
          // here to request the missing permissions, and then overriding
          //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
          //                                          int[] grantResults)
          // to handle the case where the user grants the permission. See the documentation
          // for ActivityCompat#requestPermissions for more details.
          return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
        moverMapaUltimaPosicionUsuario(lastLocation);
        //updateUI(lastLocation);
      } else {
        // Permission was denied or request was cancelled
      }
    }
  }


  @Override
  public void onConnectionSuspended(int i) {
    Toast.makeText(this, "Se suspendió la conexión con Google Play Services", Toast.LENGTH_LONG).show();
  }

  protected void onStart() {
    apiClient.connect();
    super.onStart();
  }

  protected void onStop() {
    apiClient.disconnect();
    super.onStop();
  }

  @Override
  public void onLocationChanged(Location location) {
    Toast.makeText(this, "Latitud: "+location.getLatitude()+", Longitud: "+location.getLongitude(), Toast.LENGTH_LONG).show();
    //updateUI(location);
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      // Check Permissions Now
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
    } else {
      // permission has been granted, continue as usual
      lastLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
      moverMapaUltimaPosicionUsuario(lastLocation);
      //updateUI(lastLocation);
    }
  }

  @Override
  public void onStatusChanged(String s, int i, Bundle bundle) {

  }

  @Override
  public void onProviderEnabled(String s) {

  }

  @Override
  public void onProviderDisabled(String s) {

  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mapReady = true;
    map = googleMap;
    map.getUiSettings().setZoomControlsEnabled(true);
    map.getUiSettings().setMyLocationButtonEnabled(true);
    map.getUiSettings().setMapToolbarEnabled(true);

    map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
      @Override
      public void onMapLongClick(LatLng location) {
        if(!markerSet){
          marker = map.addMarker(new MarkerOptions().position(location).title("pedido"));
          posped = location;
          borrarMarcador.setEnabled(true);
          markerSet = true;
          pedido.setEnabled(true);

        } else {
          Toast.makeText(getApplicationContext(), "Punto de pedido ya fijado", Toast.LENGTH_LONG).show();
        }
      }
    });

    CameraUpdate camUpd = CameraUpdateFactory.newLatLngZoom(new LatLng(21.8564522, -102.3315615), 11);
    map.animateCamera(camUpd);

    /*if(lastLocation != null){
      Toast.makeText(getApplicationContext(), "Moviéndonos a la ubicación del usuario en "+lastLocation.getLatitude()+", "+lastLocation.getLongitude()+" :D", Toast.LENGTH_LONG).show();
      CameraUpdate camUpd1 = CameraUpdateFactory.newLatLngZoom(new LatLng(40.41, -3.69), 5);
      //map.moveCamera(camUpd1);
      map.animateCamera(camUpd1);
    } else {
      Toast.makeText(getApplicationContext(), "Ubicación del usuario no obtenida", Toast.LENGTH_LONG).show();
    }*/
  }

  public void moverMapaUltimaPosicionUsuario(Location location){
    if(mapReady){
      if (location != null){
        CameraUpdate camUpd = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17);
        //map.moveCamera(camUpd1);
        map.animateCamera(camUpd);
        if(!markerSet){
          /*map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("pedido"));
          markerSet = true;
          pedido.setEnabled(true);*/
        }

      } else {
        //Toast.makeText(getApplicationContext(), "Ubicación del usuario no obtenida", Toast.LENGTH_LONG).show();
      }
    }
  }
}
