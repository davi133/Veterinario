package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Animal;
import model.AnimalDAO;
import model.Cliente;
import model.ClienteDAO;
import model.Consulta;
import model.Especie;
import model.EspecieDAO;
import model.Exame;
import model.Tratamento;
import model.TratamentoDAO;
import model.Veterinario;
import model.VeterinarioDAO;

public class SelecionadosController {

	
	
	
	
	private Exame exame;
	private Tratamento tratamento;
	
	
	//CLIENTE
	private Cliente cliente;
    @FXML private Label cl_id;
    @FXML private Label cl_nome;
    @FXML private Label cl_email;
    @FXML private Label cl_telefone;
    
    public void setCliente(Cliente cl)
    {
    	cliente = cl;
    	if (cl!=null) 
    	{   		
    		cl_id.setText("id:"+cl.getId());
    		
    		if (cl.getNome().length()<=18 || cl.getNome().isEmpty())
    		{
    			cl_nome.setText(cl.getNome());
    		}
    		else
    		{
    			cl_nome.setText(cl.getNome().substring(0,18)+"...");
    		}
    		
    		if (cl.getEmail().length()<=16 || cl.getEmail().isEmpty())
    		{
    			cl_email.setText(cl.getEmail());
    		}
    		else
    		{
    			cl_email.setText(cl.getEmail().substring(0,16)+"...");
    		} 	
    		cl_telefone.setText(cl.getTelefone());
    	}
    	else
    	{
    		cl_id.setText("id");
        	cl_nome.setText("nome");
        	cl_email.setText("email");
        	cl_telefone.setText("telefone");
    	}
    }
    public Cliente getCliente()
    {
    	return cliente;
    }

    
    //ESPECIE
    private Especie especie;
    @FXML private Label es_id;
    @FXML private Label es_nome;
    
    public void setEspecie(Especie es)
    {
    	especie = es;
    	if (es!=null) 
    	{   		
    		es_id.setText("id:"+es.getId());
    		
    		if (es.getNome().length()<=18 || es.getNome().isEmpty())
    		{
    			es_nome.setText(es.getNome());
    		}
    		else
    		{
    			es_nome.setText(es.getNome().substring(0,18)+"...");
    		}
    	}
    	else
    	{
    		es_id.setText("id");
        	es_nome.setText("nome");
    	}
    }
    public Especie getEspecie()
    {
    	return especie;
    }
    
    
    //Animal
    private Animal animal; 
    @FXML private Label an_id;
    @FXML private Label an_nome;
    @FXML private Label an_idade;
    @FXML private Label an_sexo;
    @FXML private Label an_especie;
    @FXML private Label an_dono;
   
    public void setAnimal(Animal an)
    {
    	animal = an;
    	if (an!=null) 
    	{   		
    		an_id.setText("id:"+an.getId());
    		an_nome.setText(clamp(an.getNome()));	
    		an_idade.setText("nasc: "+an.getAnoNasc());		
    		an_sexo.setText(an.getSexo().charAt(0)=='M'?"Macho":"Fêmea");		
  		
    		Especie esp = EspecieDAO.getInstance().retrieveByID(an.getEspecieId());
    		an_especie.setText(esp==null?"indefinido":esp.getNome());
    		
    		
    		Cliente dono = ClienteDAO.getInstance().retrieveByID(an.getDonoId());
    		if (dono!=null)
    		an_dono.setText(clamp(dono.getNome()+"(id: "+dono.getId()+")"));
    		else
    		an_dono.setText("sem dono");
    	
    	}
    	else
    	{
    		an_id.setText("id");
        	an_nome.setText("nome");
        	an_idade.setText("nascimento");
        	an_sexo.setText("sexo");
        	an_especie.setText("especie");
        	an_dono.setText("dono");
        	
    	}
    }
    public Animal getAnimal() 
    {
    	return animal;
    }

    
    //Veterinario
    @FXML  private Label vt_id;
    @FXML private Label vt_nome;
    @FXML private Label vt_email;
    @FXML private Label vt_telefone;
    private Veterinario veterinario;
    
    public void setVeterinario(Veterinario vt)
    {
    	veterinario =vt;
    	if (vt!=null) 
    	{   		
    		vt_id.setText("id:"+vt.getId());
    		vt_nome.setText(clamp(vt.getNome()));	
    		vt_email.setText(clamp(vt.getEmail()));
    		vt_telefone.setText(clamp(vt.getTelefone()));
    	
    	}
    	else
    	{
    		vt_id.setText("id");
    		vt_nome.setText("nome");	
    		vt_email.setText("email");
    		vt_telefone.setText("telefone");
        	
    	}
    }
    public Veterinario getVeterinario()
    {
    	return veterinario;
    }

    
    //Consulta
    @FXML private Label co_id;
    @FXML private Label co_data;
    @FXML private Label co_animal;
    @FXML private Label co_veterinario;
    @FXML private Label co_tratamento;
    @FXML private Label co_terminou;
    private Consulta consulta;
   
    public void setConsulta(Consulta co)
    {
    	consulta = co;
    	if (co!=null) 
    	{   		
    		co_id.setText("id:"+co.getId());
    		co_data.setText(clamp(co.getDtConsultaString()));
    		Animal ani = AnimalDAO.getInstance().retrieveByID(co.getAnimalId());
    		co_animal.setText(ani==null?"sem animal":clamp(ani.getNome()+"(id: "+ani.getId()+")"));
    		Veterinario vet = VeterinarioDAO.getInstance().retrieveByID(co.getVeterinarioId());
    		co_veterinario.setText(vet==null?"sem veterinario":clamp(vet.getNome()+"(id: "+vet.getId()+")"));
    		Tratamento trat = TratamentoDAO.getInstance().retrieveByID(co.getTratamentoId());
    		co_tratamento.setText(trat==null?"sem veterinario":clamp(trat.getNome()+"(id: "+trat.getId()+")"));
    		co_terminou.setText(co.isTerminou()?"terminado":"não terminado");  	
    	}
    	else
    	{
    		co_id.setText("id");
        	co_data.setText("data");
        	co_animal.setText("animal");
        	co_veterinario.setText("veterinario");
        	co_tratamento.setText("tratamento");
        	co_terminou.setText("terminou?");
    	}
    }
    public Consulta getConsulta()
    {
    	return consulta;
    }
    
    
    
    
    
    
    private String clamp(String str)
    {
    	if (str.length()<=18 || str.isEmpty())
		{
			return str;
		}
		else
		{
			return str.substring(0,18)+"...";
		}
    }
    private String clamp(String str, int size)
    {
    	if (str.length()<=size || str.isEmpty())
		{
			return str;
		}
		else
		{
			return str.substring(0,size)+"...";
		}
    }
}

