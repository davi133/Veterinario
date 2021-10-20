package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO extends DAO {
	private static AnimalDAO instance;
	
	private AnimalDAO() {
        getConnection();
        createTable();
    }

	public static AnimalDAO getInstance() {
        return (instance==null?(instance = new AnimalDAO()):instance);
    }
	
	//CRUD
	//Create
	public Animal create(String nome, int anoNasc, char sexo, int id_especie, int id_cliente)
	{
		PreparedStatement stmt;
		try {
			stmt = getConnection().prepareStatement("INSERT INTO animal (nome, anoNasc, sexo, id_cliente, id_especie)"
							+ "VALUES ('"+nome+"',"
							+ "'"+anoNasc+"',"
							+ "'"+sexo+"',"
							+ "'"+id_cliente+"',"
							+ "'"+id_especie+"')");
			executeUpdate(stmt);
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return retrieveByID(lastId("animal","id"));
	}
	public Animal buildObj(ResultSet rs)
	{
		Animal an = null;
		try {
			an = new Animal(rs.getInt("id"), rs.getString("nome"), rs.getInt("anoNasc"), rs.getString("sexo").charAt(0), 
					rs.getInt("id_cliente"), rs.getInt("id_especie"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return an;
	}
	
	//Retrieve
	public Animal retrieveByID(int id)
	{
		
		List<Animal> Animais =retrieve("SELECT * FROM animal WHERE id ="+id);
		if (Animais.isEmpty())
			return null;
		else
			return Animais.get(0);
	}
	public List retrieveByDono(int id_dono)
	{
		return retrieve("SELECT * FROM animal WHERE id_cliente ="+id_dono);
	}
	public List retrieveAll()
	{
		return retrieve("SELECT * FROM animal");
	}
	public List retrieve(String query)
	{
		List<Animal> Animais = new ArrayList();
        ResultSet rs = getResultSet(query);
        
        try 
        {
        	
        	while(rs.next()) 
        	{
        		Animais.add(buildObj(rs));
        	}
        	
        
        }
        catch (SQLException e) 
        {
        	
            System.err.println("Exception: " + e.getMessage());
        
        }
		return Animais;
	}
	public Animal retrieveLast()
	{
		ResultSet rs = getResultSet("SELECT * FROM animal WHERE id = " + lastId("animal","id"));
        return buildObj(rs);
    }

	public List retrieveBySimilarName(String nome) 
	{
        return this.retrieve("SELECT * FROM animal WHERE nome LIKE '%" + nome + "%'");
    }   
	
	//Update
	public void update(Animal an)
	{
		try {
			
			executeUpdate("UPDATE Animal SET"
					+ "nome = "+ an.getNome()+","
					+ "anoNasc = "+ an.getAnoNasc()+","
					+ "sexo = "+ an.getSexo()+","
					+ "id_cliente = "+ an.getDonoId()+","
					+ "id_especie = "+ an.getEspecieId()+" "
					+ "WHERE id ="+ an.getId()
					);
		
		} 
		catch (SQLException e) 
		{
		
			e.printStackTrace();
		
		}
		
	}
	
	//DELETE
	public void delete(Animal an)
	{
		delete(an.getId());
	}
	public void delete(int id)
	{
		
		try 
		{
			executeUpdate("DELETE FROM animal WHERE id =" + id);
		} 
		catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
		
		
	}
	
	
}
