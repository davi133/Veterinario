package view;

public class Criterio {
	private String nome;
	private String query;
	private boolean isNumeric;
	public Criterio(String nome, String query) {
		this.nome = nome;
		this.query = query;
		isNumeric=false;
	}
	public Criterio(String nome, String query,boolean isNumeric) {
		this.nome = nome;
		this.query = query;
		this.isNumeric=isNumeric;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getQuery(Object valor) {
		String value = valor.toString();
		if (isNumeric)
		{
			value.replaceAll("[^\\d]", "");
		}
		return query.replace("{value}", value);
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	public String toString()
	{
		return nome;
	}
	
	public static Criterio CriterioDeID()
	{
		return new Criterio("id","id = {value}",true);
	}
	
}