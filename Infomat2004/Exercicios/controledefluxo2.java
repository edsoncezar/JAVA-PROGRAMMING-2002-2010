public class controledefluxo2{
	public static void main(String args[]){
		int nota = 5;

		switch(nota){
			case 10:
			case 9:
			case 8:
			case 7:
				System.out.println("Aprovado");
				break;			
			case 6:
			case 5:
				System.out.println("Exame");
				break;			
			case 4:
			case 3:
				System.out.println("Segunda epoca");
				break;			
			default:
				System.out.println("Reprovado");
				break;			
		}
	}
}