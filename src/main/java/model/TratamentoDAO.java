package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TratamentoDAO extends DAO{
private static TratamentoDAO insttrce;
	
	private TratamentoDAO() {
        getConnection();
        createTable();
    }

	public static TratamentoDAO getInstance() {
        return (insttrce==null?(insttrce = new TratamentoDAO()):insttrce);
    }
	
	//CRUD
	//Create
	public Tratamento create(Date dtInicio, Date dtFim, int animalId, String nome)
	{
		//"YYYY-MM-DD HH:MM:SS.SSS"
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.SSS");  

		PreparedStatement stmt;
		try {
			stmt = getConnection().prepareStatement("INSERT INTO tratamento (dataIni, dataFim, id_animal, nome, terminado )"
							+ "VALUES ('"+dateFormater.format(dtInicio)+"',"
							+ "'"+dateFormater.format(dtFim)+"',"
							+ ""+animalId+","
							+ "'"+nome+"',"
							+ ""+0+" )");
			executeUpdate(stmt);
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return retrieveByID(lastId("tratamento","id"));
	}
	public Tratamento buildObj(ResultSet rs)
	{
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.SSS");  
		Tratamento tr = null;
		try {
			tr = new Tratamento(rs.getInt("id"),dateFormater.parse(rs.getString("dataIni")), 
					dateFormater.parse(rs.getString("dataFim")), rs.getInt("id_animal"),rs.getString("nome"));
			
			boolean terminou = rs.getInt("terminado")==0?false:true;
			tr.setTerminou(terminou);
			//TODO can't build if the dates are null;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch(ParseException ex)
		{
			ex.printStackTrace();
		}
		return tr;
	}
	
	//Retrieve
	public Tratamento retrieveByID(int id)
	{
		
		List<Tratamento> Tratamentos =retrieve("SELECT * FROM tratamento WHERE id ="+id);
		if (Tratamentos.isEmpty())
			return null;
		else
			return Tratamentos.get(0);
	}
	public List retrieveAll()
	{
		return retrieve("SELECT * FROM tratamento");
	}
	public List retrieve(String query)
	{
		List<Tratamento> Tratamentos = new ArrayList();
        ResultSet rs = getResultSet(query);
        
        try 
        {
        	
        	while(rs.next()) 
        	{
        		Tratamentos.add(buildObj(rs));
        	}
        	
        
        }
        catch (SQLException e) 
        {
        	
            System.err.println("Exception: " + e.getMessage());
        
        }
		return Tratamentos;
	}
	public Tratamento retrieveLast()
	{
		ResultSet rs = getResultSet("SELECT * FROM tratamento WHERE id = " + lastId("tratamento","id"));
        return buildObj(rs);
    }

	public List retrieveBySimilarName(String nome) 
	{
        return this.retrieve("SELECT * FROM tratamento WHERE nome LIKE '%" + nome + "%'");
    }   
	
	//Update
	public void update(Tratamento tr)
	{
		try {
			int terminou = tr.isTerminou()?	1:	0;
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.SSS"); 
			
			executeUpdate("UPDATE Tratamento SET"
					+ "nome = "+ tr.getNome()+","
					+ "dataIni = "+ dateFormater.format(tr.getDtInicio())+","
					+ "dataFim = "+ dateFormater.format(tr.getDtFim())+","
					+ "id_animal = "+ tr.getAnimalId()+","
					+ "terminado = "+ terminou+" "
					+ "WHERE id ="+ tr.getId()
					);
		
		} 
		catch (SQLException e) 
		{
		
			e.printStackTrace();
		
		}
		
	}
	
	//DELETE
	public void delete(Tratamento tr)
	{
		delete(tr.getId());
	}
	public void delete(int id)
	{
		
		try 
		{
			executeUpdate("DELETE FROM tratamento WHERE id =" + id);
		} 
		catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
		
		
	}
	
}
