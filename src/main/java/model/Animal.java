package model;

import java.util.ArrayList;
import java.util.List;

public class Animal {
	
	/*"CREATE TABLE IF NOT EXISTS animal( \n" 
	+ "id INTEGER PRIMARY KEY, \n"
	+ "nome VARCHAR, \n" 
	+ "anoNasc INTEGER, \n" 
	+ "sexo VARCHAR, \n"
	+ "id_especie INTEGER, \n" 
	+ "id_cliente INTEGER); \n"*/
	
	private int id;
	private String nome;
	private int anoNasc;
	private String sexo; //M-Macho, F-Fêmea
	private int especieId;
	private int donoId;
	
	public Animal(Integer id, String nome, Integer anoNasc, String sexo,Integer donoId, Integer especieId) {
		this.id = id;
		this.nome = nome;
		this.anoNasc = anoNasc;
		this.sexo = sexo;
		this.donoId = donoId;
		this.especieId = especieId;
		
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getAnoNasc() {
		return anoNasc;
	}
	public void setAnoNasc(Integer idade) {
		this.anoNasc= idade;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Integer getId() {
		return id;
	}
	public Integer getEspecieId() {
		return especieId;
	}
	public void setEspecieId(Integer especieId) {
		this.especieId = especieId;
	}	
	public Integer getDonoId() {
		return donoId;
	}
	public void setDonoId(Integer donoId) {
		this.donoId = donoId;
	}
	
	public Cliente getDono()
	{
		return ClienteDAO.getInstance().retrieveByID(donoId);
	}
	
	@Override
 	public String toString() {
		return "Animal [id=" + id + ", nome=" + nome + ", especieId=" + especieId + ", anoNasc=" + anoNasc + ", sexo=" + sexo
				+ ", donoId=" + donoId + "]\n";
	}
	
	

}
