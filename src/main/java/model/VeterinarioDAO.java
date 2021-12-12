package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDAO extends DAO{
private static VeterinarioDAO instvece;
	
	private VeterinarioDAO() {
        getConnection();
        createTable();
    }

	public static VeterinarioDAO getInstance() {
        return (instvece==null?(instvece = new VeterinarioDAO()):instvece);
    }
	
	//CRUD
	//Create
	public Veterinario create(String nome, String email, String telefone)
	{
		PreparedStatement stmt;
		try {
			stmt = getConnection().prepareStatement("INSERT INTO veterinario (nome, email, telefone)"
							+ "VALUES ('"+nome+"', '"+email+"','"+telefone+"')");
			executeUpdate(stmt);
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return retrieveByID(lastId("veterinario","id"));
	}
	public Veterinario buildObj(ResultSet rs)
	{
		Veterinario ve = null;
		try {
			//String nome, String telefone, String email
			ve = new Veterinario(rs.getInt("id"), rs.getString("nome"), rs.getString("telefone"), rs.getString("email"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ve;
	}
	
	//Retrieve
	public Veterinario retrieveByID(int id)
	{
		
		List<Veterinario> Veterinarios =retrieve("SELECT * FROM veterinario WHERE id ="+id);
		if (Veterinarios.isEmpty())
			return null;
		else
			return Veterinarios.get(0);
	}
	public List retrieveAll()
	{
		return retrieve("SELECT * FROM veterinario");
	}
	public List retrieveAll(int top)
	{
		return retrieve("SELECT * FROM veterinario LIMIT "+top);
	}
	public List retrieve(String query)
	{
		List<Veterinario> Veterinarios = new ArrayList();
        ResultSet rs = getResultSet(query);
        if (rs==null) return null;
        try 
        {
        	
        	while(rs.next()) 
        	{
        		Veterinarios.add(buildObj(rs));
        	}
        	
        
        }
        catch (SQLException e) 
        {
        	
            System.err.println("Exception: " + e.getMessage());
        
        }
		return Veterinarios;
	}
	public Veterinario retrieveLast()
	{
		ResultSet rs = getResultSet("SELECT * FROM veterinario WHERE id = " + lastId("veterinario","id"));
        return buildObj(rs);
    }

	public List retrieveBySimilarName(String nome) 
	{
        return this.retrieve("SELECT * FROM veterinario WHERE nome LIKE '%" + nome + "%'");
    }   
	
	//Update
	public void update(Veterinario ve)
	{
		try {
			
			executeUpdate("UPDATE Veterinario SET "
					+ "nome = '"+ ve.getNome()+"',"
					+ "email = '"+ ve.getEmail()+"',"
					+ "telefone = '"+ ve.getTelefone()+"' "
					+ "WHERE id ="+ ve.getId()
					);
		
		} 
		catch (SQLException e) 
		{
		
			e.printStackTrace();
		
		}
		
	}
	
	//DELETE
	public void delete(Veterinario ve)
	{
		delete(ve.getId());
	}
	public void delete(int id)
	{
		
		try 
		{
			executeUpdate("DELETE FROM veterinario WHERE id =" + id);
		} 
		catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
		
		
	}
	

}
