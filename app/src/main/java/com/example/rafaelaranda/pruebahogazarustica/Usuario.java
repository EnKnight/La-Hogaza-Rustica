package com.example.rafaelaranda.pruebahogazarustica;

public class Usuario {
  public String nom, ap, correoe, tel;
  public int dia, mes, anio;

  public Usuario(){
    // Default constructor required for calls to DataSnapshot.getValue(User.class)
  }

  public Usuario(String nom, String ap, String correoe, String tel, int dia, int mes, int anio){
    this.nom = nom;
    this.ap = ap;
    this.correoe = correoe;
    this.tel = tel;
    this.dia = dia;
    this.mes = mes;
    this.anio = anio;
  }

}
