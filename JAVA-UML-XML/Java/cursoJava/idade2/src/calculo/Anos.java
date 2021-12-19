package calculo;

public class Anos {
  private int idade;

  public void setIdade(int novaIdade) {
    idade = novaIdade;
  }
  public void setIdade(String novaIdade) {
    idade = Integer.parseInt(novaIdade);
  }


  public int getIdade() {
    return idade;
  }

  public String getPosicionamento() {
    if (idade >= 18) {
      return "Maior de idade";
    }
    else {
      return "Menor de idade";
    }
  }

  public int getDias(){
    return idade*365;
  }
}