package com.example.rafaelaranda.pruebahogazarustica;

import java.util.ArrayList;

public class Pedido {
  //public String descripcion;
  public int costo;
  public double lat, longitud;
  public ArrayList<String> panes = new ArrayList<String>();
  public String fechaPed, estado, userUid;

  public Pedido(){

  }

  public Pedido(ArrayList<String> panes, int costo, double lat, double longitud, String fechaPed, String userUid){
    this.panes = panes;
    this.costo = costo;
    this.lat = lat;
    this.longitud = longitud;
    this.fechaPed = fechaPed;
    this.estado = "Generado";
    this.userUid = userUid;
  }

}
