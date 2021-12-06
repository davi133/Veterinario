package model;

public class Exame {

	/*"CREATE TABLE IF NOT EXISTS exame( \n"
	+ "id INTEGER PRIMARY KEY, \n" 
	+ "nome VARCHAR, \n" 
	+ "id_consulta INTEGER); \n"*/
	
	private Integer id;
	private String descricao;
	private int idConsulta;
	
	public Exame(Integer id, String descricao, int idConsulta) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.idConsulta = idConsulta;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(int idConsulta) {
		this.idConsulta = idConsulta;
	}

	public Integer getId() {
		return id;
	}
	
	
}
