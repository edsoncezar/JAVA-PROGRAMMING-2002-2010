class Escrita implements Runnable {
	private int i;
	public void run() {
		while(i<116)
			System.out.println("N�mero Escrita: "+ i++);
	}
}

class EscritaOutra implements Runnable {
	private int i;
	public void run() {
		while(i<100)
			System.out.println("N�mero EscritaOutra: "+ i++);
	}
}

public class SimplesThread {
	public static void main(String[] args) {
		Escrita e = new Escrita();         //Cria o contexto de execu��o
                EscritaOutra e1 = new EscritaOutra(); 
		Thread t = new Thread(e);        //Cria a linha de execu��o
                Thread t1 = new Thread(e1);
		t.start();                                     //Ativa a thread
                t1.start();
	}
}

