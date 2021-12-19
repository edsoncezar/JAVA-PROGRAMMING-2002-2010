public class cliente{
	private String nome;
	private String telefone;
	private int idade;

	public void setNome(String n){
		nome = n;
	}
	public void setTelefone(String tel){
		telefone = tel;
	}
	public void setIdade(int id){
		idade = id;
	}

	public String getNome(){
		return nome;
	}
	public String getTelefone(){
		return telefone;
	}
	public int getIdade(){
		return idade;
	}
}
