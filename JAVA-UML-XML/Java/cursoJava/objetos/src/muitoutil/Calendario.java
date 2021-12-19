package muitoutil;

import java.util.*;

public class Calendario {
  private int dia,mes,ano;

  // construtores => qdo criar o objeto, dependendo da qtd de parametros
  //               ele aponta p/ um construtor

  //construtor default(sem parametros)
  public Calendario(){
    setData();
  }

  //construtor com parametros
  public Calendario (int d, int m, int a){
    setData();
  }

  //funcao sem retorno de valores(void)
  public void setData(int d, int m, int a){
    dia=d;
    mes=m;
    ano=a;
  }

  public void setData(){
    Date d=new Date();
    dia=d.getDate();
    mes=d.getMonth()+1;
    ano=d.getYear()+1900;
  }


  //funcao com retorno de valores(return)
  public String getData(){
    return "("+dia+"/"+mes+"/"+ano+")";
  }
}