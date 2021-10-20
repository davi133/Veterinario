package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tratamento {

	/*"CREATE TABLE IF NOT EXISTS tratamento( \n" 
	+ "id INTEGER PRIMARY KEY, \n"
	+ "id_animal INTEGER, \n" 
	+ "nome VARCHAR, \n" 
	+ "dataIni TEXT, \n" 
	+ "dataFim TEXT, \n"
	+ "terminado INTEGER); \n"*/
	
	private Integer id;
	private String nome;
	private Date dtInicio;
	private Date dtFim;
	private int animalId;
	private boolean terminou;
	
	public Tratamento(Integer id, Date dtInicio, Date dtFim, int animalId, String nome) {
		this.id = id;
		this.dtInicio = dtInicio;
		this.dtFim = dtFim;
		this.animalId = animalId;
		this.nome = nome;
		terminou = false;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Date getDtInicio() {
		return dtInicio;
	}
	public String getDtInicioString()
	{
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.SSS"); 
		return dateFormater.format(dtInicio);
	}
	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}
	
	public Date getDtFim() {
		return dtFim;
	}
	public String getDtFimString()
	{
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.SSS"); 
		return dateFormater.format(dtFim);
	}
	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}
	
	public int getAnimalId() {
		//TODO ligar com o sqlite
		return animalId;
	}
	public int getId()
	{
		return id;
	}
	
	public boolean isTerminou() {
		return terminou;
	}
	public void setTerminou(boolean terminou) {
		this.terminou = terminou;
	}


}
