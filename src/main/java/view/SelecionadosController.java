package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Animal;
import model.Cliente;
import model.Consulta;
import model.Especie;
import model.Exame;
import model.Veterinario;

public class SelecionadosController {

	
	private Animal animal;
	private Veterinario veterinario;
	private Consulta consulta;
	private Exame exame;

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
    		cl_id.setText(""+cl.getId());
    		
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
    		es_id.setText(""+es.getId());
    		
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


}

