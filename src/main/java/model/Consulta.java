package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Consulta {

	/*"CREATE TABLE IF NOT EXISTS consulta( \n" 
	+ "id INTEGER PRIMARY KEY, \n" 
	+ "data TEXT, \n"
	+ "horario VARCHAR, \n" 
	+ "comentario VARCHAR, \n" 
	+ "id_animal INTEGER, \n"
	+ "id_vet INTEGER, \n" 
	+ "id_tratamento INTEGER, \n" 
	+ "terminado INTEGER); \n"*/
	
	private int id;
	private Date dtConsulta;
	private String horario;//inútil, só não tiro pq teria que mudar outras coisas
	private String descricao;//comentario
	private int animalId;
	private int veterinarioId;
	private int tratamentoId;
	private boolean terminou;
	
	public Consulta(int id, Date dtConsulta,String descricao, int animalId, int veterinarioId, int tratamentoId) {
		this.id = id;
		this.dtConsulta = dtConsulta;
		this.descricao = descricao;
		this.animalId = animalId;
		this.veterinarioId = veterinarioId;
		this.tratamentoId=tratamentoId;
		terminou = false;
	}
	
	public Date getDtConsulta() {
		return dtConsulta;
	}
	public String getDtConsultaString()
	{
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.SSS");
		return formater.format(dtConsulta);
	}
	public void setDtConsulta(Date dtConsulta) {
		this.dtConsulta = dtConsulta;
	}
	
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public int getAnimalId() {
		return animalId;
	}
	public void setAnimalId(int animalId) {
		this.animalId = animalId;
	}
	
	public int getVeterinarioId() {
		return veterinarioId;
	}
	public void setVeterinarioId(int veterinarioId) {
		this.veterinarioId = veterinarioId;
	}
	
	public boolean isTerminou() {
		return terminou;
	}
	public void setTerminou(boolean terminou) {
		this.terminou = terminou;
	}
	
	public int getId() {
		return id;
	}
	
	public int getTratamentoId() {
		return tratamentoId;
	}
	public void setTratamentoId(int tratamentoId) {
		this.tratamentoId = tratamentoId;
	}
	
	
	
	
	
	
	
	
}
