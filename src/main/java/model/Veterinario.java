package model;

public class Veterinario {

	/*"CREATE TABLE IF NOT EXISTS vet( \n" 
	+ "id INTEGER PRIMARY KEY, \n"
	+ "nome VARCHAR, \n" 
	+ "email VARCHAR, \n" 
	+ "telefone VARCHAR); \n"*/
	
	private Integer id;
	private String nome;
	private String email;
	private String telefone;
	
	
	public Veterinario(Integer id, String nome, String telefone, String email) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getId() {
		return id;
	}

	@Override
	public String toString()
	{
		return "Veterinario [id=" + id + ", nome=" + nome + ", email=" + email + ", telefone=" + telefone + "]";
	}
	
	
}
