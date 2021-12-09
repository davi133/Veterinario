package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends DAO {
	private static ClienteDAO instance;
	
	private ClienteDAO() {
        getConnection();
        createTable();
    }

	public static ClienteDAO getInstance() {
        return (instance==null?(instance = new ClienteDAO()):instance);
    }
	
	//CRUD
	//Create
	public Cliente create(String nome, String endereco, String cep, String email, String telefone)
	{
		PreparedStatement stmt;
		try {
			stmt = getConnection().prepareStatement("INSERT INTO cliente (nome, end, cep, email, telefone)"
							+ "VALUES ('"+nome+"',"
							+ "'"+endereco+"',"
							+ "'"+cep+"',"
							+ "'"+email+"',"
							+ "'"+telefone+"')");
			executeUpdate(stmt);
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return retrieveByID(lastId("cliente","id"));
	}
	public Cliente buildObj(ResultSet rs)
	{
		Cliente cl = null;
		
		try {
			cl = new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("end"), rs.getString("telefone"), 
					rs.getString("cep"), rs.getString("email"));
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return cl;
	}
	
	//Retrieve
	public Cliente retrieveByID(int id)
	{
		
		List<Cliente> clientes =retrieve("SELECT * FROM cliente WHERE id ="+id);
		if (clientes.isEmpty())
			return null;
		else
			return clientes.get(0);
	}
	public List retrieveAll()
	{
		return retrieve("SELECT * FROM cliente");
	}
	public List retrieveAll(int top)
	{
		return retrieve("SELECT * FROM cliente LIMIT"+top);
	}
	public List<Cliente> retrieve(String query)
	{
		List<Cliente> clientes = new ArrayList();
        ResultSet rs = getResultSet(query);
        if(rs==null)return null;
        try 
        {
        	
        	while(rs.next())
        	{
        		clientes.add(buildObj(rs));
        	}
        
        }
        catch (SQLException e) 
        {
        	
            System.err.println("Exception: " + e.getMessage());
        
        }
		return clientes;
	}
	public Cliente retrieveLast()
	{
		ResultSet rs = getResultSet("SELECT * FROM cliente WHERE id = " + lastId("cliente","id"));
        return buildObj(rs);
    }

	public List retrieveBySimilarName(String nome) 
	{
        return this.retrieve("SELECT * FROM cliente WHERE nome LIKE '%" + nome + "%'");
    }   
	
	//Update
	public void update(Cliente cl)
	{
		try {
			
			executeUpdate("UPDATE cliente SET nome = '"+ cl.getNome()					
									   + "',\n end = '" + cl.getEndereco()
									   + "',\n cep = '"+ cl.getCep()
									   + "',\n email = '"+ cl.getEmail()
									   + "',\n telefone = '"+ cl.getTelefone()
									   + "' \nWHERE id = "+cl.getId());
		
		} 
		catch (SQLException e) 
		{
		
			e.printStackTrace();
		
		}
		
	}
	
	//DELETE
	public void delete(Cliente cl)
	{
		delete(cl.getId());
	}
	public void delete(int id)
	{
		
		try 
		{
			executeUpdate("DELETE FROM cliente WHERE id =" + id);
		} 
		catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
		
		
	}
}
