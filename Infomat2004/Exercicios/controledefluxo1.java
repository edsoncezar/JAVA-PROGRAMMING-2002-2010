public class controledefluxo1{
	public static void main(String args[]){
		int nota = 3;

		if(nota >= 7){
			System.out.println("Aprovado");
		}else if(nota >= 5){
			System.out.println("Exame");
		}else if(nota >= 3){
			System.out.println("Segunda epoca");
		}else{
			System.out.println("Reprovado");
		}
	}
}