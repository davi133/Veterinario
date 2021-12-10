package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EspecieDAO extends DAO{
private static EspecieDAO instesce;
	
	private EspecieDAO() {
        getConnection();
        createTable();
    }

	public static EspecieDAO getInstance() {
        return (instesce==null?(instesce = new EspecieDAO()):instesce);
    }
	
	//CRUD
	//Create
	public Especie create(String nome)
	{
		PreparedStatement stmt;
		try 
		{
			stmt = getConnection().prepareStatement("INSERT INTO especie (nome)"
							+ "VALUES ('"+nome+"')");
			executeUpdate(stmt);
		
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return retrieveByID(lastId("especie","id"));
	}
	public Especie buildObj(ResultSet rs)
	{
		Especie es = null;
		try 
		{
			es = new Especie(rs.getInt("id"), rs.getString("nome"));
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return es;
	}
	
	//Retrieve
	public Especie retrieveByID(int id)
	{
		
		List<Especie> Especies =retrieve("SELECT * FROM especie WHERE id ="+id);
		if (Especies.isEmpty())
			return null;
		else
			return Especies.get(0);
	}
	public List retrieveAll()
	{
		return retrieve("SELECT * FROM especie");
	}
	public List retrieveAll(int top)
	{
		return retrieve("SELECT * FROM especie LIMIT "+top);
	}
	public List retrieve(String query)
	{
		List<Especie> Especies = new ArrayList();
        ResultSet rs = getResultSet(query);
        if(rs==null)return null;
        try 
        {
        	
        	while(rs.next()) 
        	{
        		Especies.add(buildObj(rs));
        	}
        	
        
        }
        catch (SQLException e) 
        {
        	
            System.err.println("Exception: " + e.getMessage());
        
        }
		return Especies;
	}
	public Especie retrieveLast()
	{
		ResultSet rs = getResultSet("SELECT * FROM especie WHERE id = " + lastId("especie","id"));
        return buildObj(rs);
    }

	public List retrieveBySimilarName(String nome) 
	{
        return this.retrieve("SELECT * FROM especie WHERE nome LIKE '%" + nome + "%'");
    }   
	public List retrieveBySimilarName(String nome, int top) 
	{
        return this.retrieve("SELECT * FROM especie WHERE nome LIKE '%" + nome + "%' LIMIT "+top);
    }  
	
	//Update
	public void update(Especie es)
	{
		try {
			
			executeUpdate("UPDATE Especie SET nome = '"+ es.getNome()
					+ "' WHERE id ="+ es.getId()
					);
		
		} 
		catch (SQLException e) 
		{
		
			e.printStackTrace();
		
		}
		
	}
	
	//DELETE
	public void delete(Especie es)
	{
		delete(es.getId());
	}
	public void delete(int id)
	{
		
		try 
		{
			executeUpdate("DELETE FROM especie WHERE id =" + id);
		} 
		catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
		
		
	}
}
