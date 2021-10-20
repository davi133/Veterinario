package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExameDAO extends DAO{
	private static ExameDAO instexace;
	
	private ExameDAO() {
        getConnection();
        createTable();
    }

	public static ExameDAO getInstexace() {
        return (instexace==null?(instexace = new ExameDAO()):instexace);
    }
	
	//CRUD
	//Create
	public Exame create(String descricao, int idConsulta)
	{
		PreparedStatement stmt;
		try {
			stmt = getConnection().prepareStatement("INSERT INTO exame (nome, id_consulta)"
							+ "VALUES ('"+descricao+","+idConsulta+"')");
			executeUpdate(stmt);
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return retrieveByID(lastId("exame","id"));
	}
	public Exame buildObj(ResultSet rs)
	{
		Exame exa = null;
		try {
			exa = new Exame(rs.getInt("id"), rs.getString("nome"), rs.getInt("id_consulta"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exa;
	}
	
	//Retrieve
	public Exame retrieveByID(int id)
	{
		
		List<Exame> Exames =retrieve("SELECT * FROM exame WHERE id ="+id);
		if (Exames.isEmpty())
			return null;
		else
			return Exames.get(0);
	}
	public List retrieveAll()
	{
		return retrieve("SELECT * FROM exame");
	}
	public List retrieve(String query)
	{
		List<Exame> Exames = new ArrayList();
        ResultSet rs = getResultSet(query);
        
        try 
        {
        	
        	while(rs.next()) 
        	{
        		Exames.add(buildObj(rs));
        	}
        	
        
        }
        catch (SQLException e) 
        {
        	
            System.err.println("Exception: " + e.getMessage());
        
        }
		return Exames;
	}
	public Exame retrieveLast()
	{
		ResultSet rs = getResultSet("SELECT * FROM exame WHERE id = " + lastId("exame","id"));
        return buildObj(rs);
    }

	public List retrieveBySimilarName(String nome) 
	{
        return this.retrieve("SELECT * FROM exame WHERE nome LIKE '%" + nome + "%'");
    }   
	
	//Update
	public void update(Exame exa)
	{
		try {
			
			executeUpdate("UPDATE Exame SET"
					+ "nome = "+ exa.getDescricao()+","
					+ "id_consulta = "+ exa.getIdConsulta()+" "
					+ "WHERE id ="+ exa.getId()
					);
		
		} 
		catch (SQLException e) 
		{
		
			e.printStackTrace();
		
		}
		
	}
	
	//DELETE
	public void delete(Exame exa)
	{
		delete(exa.getId());
	}
	public void delete(int id)
	{
		
		try 
		{
			executeUpdate("DELETE FROM exame WHERE id =" + id);
		} 
		catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
		
		
	}
	

}
