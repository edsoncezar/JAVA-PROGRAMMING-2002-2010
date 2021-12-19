package controle;

public class Avaliacao {
  private double nota;

  public void setNota(double novaNota){
    nota=novaNota;
  }

  public void setNota(String novaNota){
    nota=Double.parseDouble(novaNota.replaceAll( "," , "." ));
  }

  public double getNota(){
    return nota;
  }

  public String getSituacao(){
    if (nota>=7){
      return "Aprovado";
    } else {
      return "Reprovado";
    }
  }




}


