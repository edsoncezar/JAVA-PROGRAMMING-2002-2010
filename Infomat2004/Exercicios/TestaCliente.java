public class TestaCliente{
	public static void main(String args[]){
		cliente c = new cliente();

		c.setNome("Roberta");
		c.setIdade(34);
		c.setTelefone("4444-4444");

		System.out.println("Nome = "+c.getNome());
		System.out.println("Telefone = "+c.getTelefone());
		System.out.println("Idade = "+c.getIdade());
	}
}