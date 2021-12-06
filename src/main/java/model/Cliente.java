package model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

	/*
	"CREATE TABLE IF NOT EXISTS cliente( \n" 
	+ "id INTEGER PRIMARY KEY, \n"
	+ "nome VARCHAR, \n" 
	+ "end VARCHAR, \n" 
	+ "cep VARCHAR, \n"
	+ "email VARCHAR, \n" 
	+ "telefone VARCHAR); \n");*/
	
	private Integer id;
	private String nome;
	private String endereco;
	private String cep;
	private String email;
	private String telefone;

	public Cliente(int id, String nome, String endereco, String telefone, String cep, String email) {
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.cep = cep;
		this.email = email;
		
	}
	
	public Cliente copy()
	{
		return new Cliente(id,nome,endereco,telefone,cep,email);
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
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
	
	public List getAnimais()
	{
		List<Animal> animais = new ArrayList<Animal>(
				AnimalDAO.getInstance().retrieve("SELECT * FROM animal WHERE id_cliente ="+this.id)); 
		
		
		return null;
	}	
	public Animal getAnimalById(int id)
	{
		
		Animal animal =AnimalDAO.getInstance().retrieveByID(id);
		if (animal != null && animal.getDonoId()==this.id)
			return animal;
		else
			return null;
	}
	
	@Override
	public String toString() {
		String str = "Cliente [id=" + id + ", nome=" + nome + ", endereco=" + endereco + ", telefone=" + telefone + ", cep="
				+ cep + ", email=" + email + "]\nAnimais:\n";
		
		/*for (Animal a: animais)
		{
			str += "	 "+a+"\n";
		}*/
		return str;
	}
	
	
}
