public class tipodedados{
	public static void main(String args[]){
		int a = 14;
		int b = 3;
		int soma = 0;
		int sub = 0;
		double div = 0;
		double mult = 0;
		double resto = 0;

		soma = a + b;
		sub = a - b;
		div = a / b;
		mult = a * b;
		resto = a % b;

		System.out.println("Soma de a e b= "+soma);
		System.out.println("Subtracao de a e b= "+sub);
		System.out.println("Divisao de a por b= "+div);
		System.out.println("Multiplicacao de a e b= "+mult);
		System.out.println("Resto de a e b= "+resto);
	}
}