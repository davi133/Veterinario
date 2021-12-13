package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsultaDAO extends DAO{
private static ConsultaDAO instcoce;
	
	private ConsultaDAO() {
        getConnection();
        createTable();
    }

	public static ConsultaDAO getInstance() {
        return (instcoce==null?(instcoce = new ConsultaDAO()):instcoce);
    }

	//CRUD
	//Create
	public Consulta create( Date dtConsulta,String descricao, int animalId, int veterinarioId,int tratamentoId)
	{
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.SSS");
		PreparedStatement stmt;
		try {
			stmt = getConnection().prepareStatement("INSERT INTO consulta (data, comentario, id_animal, id_vet, "
							+ "id_tratamento, terminado)"
							+ "VALUES ('"+formater.format(dtConsulta)+"',"
										+ descricao+ ","
										+ animalId+ ","
										+ veterinarioId+ ","
										+ tratamentoId+ ","
										+ 0 +")");
			executeUpdate(stmt);
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return retrieveByID(lastId("consulta","id"));
	}
	public Consulta buildObj(ResultSet rs)
	{
		Consulta co = null;
		try {
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.SSS");
			
			co = new Consulta(rs.getInt("id"),formater.parse(rs.getString("data")),
					rs.getString("comentario") ,rs.getInt("id_animal"), rs.getInt("id_vet"), rs.getInt("id_tratamento"));
			
			boolean terminou = rs.getInt("terminado")==0?false:true;;
			co.setTerminou(terminou);
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(ParseException ex)
		{
			ex.printStackTrace();
		}
		return co;
	}
	
	//Retrieve
	public Consulta retrieveByID(int id)
	{
		
		List<Consulta> Consultas =retrieve("SELECT * FROM consulta WHERE id ="+id);
		if (Consultas.isEmpty())
			return null;
		else
			return Consultas.get(0);
	}
	public List retrieveAll()
	{
		return retrieve("SELECT * FROM consulta");
	}
	public List retrieveAll(int top)
	{
		return retrieve("SELECT * FROM consulta LIMIT "+top);
	}
	public List retrieve(String query)
	{
		List<Consulta> Consultas = new ArrayList();
        ResultSet rs = getResultSet(query);
        if (rs==null)return null;
        try 
        {
        	
        	while(rs.next()) 
        	{
        		Consultas.add(buildObj(rs));
        	}
        	
        
        }
        catch (SQLException e) 
        {
        	
            System.err.println("Exception: " + e.getMessage());
        
        }
		return Consultas;
	}
	public Consulta retrieveLast()
	{
		ResultSet rs = getResultSet("SELECT * FROM consulta WHERE id = " + lastId("consulta","id"));
        return buildObj(rs);
    }

	
	//Update
	public void update(Consulta co)
	{
		try {
			
			int terminou = co.isTerminou()?1:0;
			executeUpdate("UPDATE Consulta SET "
					+ "data = '"+ co.getDtConsultaString()+"',"
					+ "horario = '"+ co.getHorario()+"',"
					+ "comentario = '"+ co.getDescricao()+"',"
					+ "id_animal = "+ co.getAnimalId()+","
					+ "id_vet = "+ co.getVeterinarioId()+","
					+ "id_tratamento = "+ co.getTratamentoId()+","
					+ "terminado = "+ terminou+" "
					+ "WHERE id ="+ co.getId());
		
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	//DELETE
	public void delete(Consulta co)
	{
		delete(co.getId());
	}
	public void delete(int id)
	{
		
		try 
		{
			executeUpdate("DELETE FROM consulta WHERE id =" + id);
		} 
		catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
		
		
	}
	


}
