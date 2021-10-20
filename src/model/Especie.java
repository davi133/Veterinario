package model;

public class Especie 
{
	/*"CREATE TABLE IF NOT EXISTS especie( \n" 
	+ "id INTEGER PRIMARY KEY, \n" 
	+ "nome VARCHAR); \n");
	
	"INSERT OR IGNORE INTO especie (id, nome) VALUES (1, 'Cachorro')"
	*/
	
	private int ID;
	private String nome;
	
	public Especie(int ID, String nome)
	{
		this.ID=ID;
		this.nome=nome;
	}

	public String getNome() 
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public int getId()
	{
		return ID;
	}
}
