package view.Cliente;



import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Cliente;
import model.ClienteDAO;
import view.Criterio;
import view.SelecionadosController;

public class ClienteController implements Initializable{

	
	
	private SelecionadosController selecionadosController;
	public void InjectSelecionadosController(SelecionadosController sc)
    {
    	selecionadosController=sc;
    }
    public SelecionadosController getSelectCtrl()
    {
    	return selecionadosController;
    }
    public TableView<Cliente> getTable()
    {
    	return Table;
    }
	
	
	
    @FXML private TableView<Cliente> Table;
   
    @FXML private TableColumn<Cliente, Integer> table_id;
    @FXML private TableColumn<Cliente, String> table_nome;
    @FXML private TableColumn<Cliente, String> table_email;
    @FXML private TableColumn<Cliente, String> table_telefone;
    @FXML private TableColumn<Cliente, String> table_endereco;
    @FXML private TableColumn<Cliente, String> table_CEP;
   
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	table_id.setCellValueFactory( new PropertyValueFactory<Cliente, Integer>("id"));
		table_nome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
		table_email.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));
		table_telefone.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefone"));
		table_endereco.setCellValueFactory(new PropertyValueFactory<Cliente, String>("endereco"));
		table_CEP.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cep"));
		
		ObservableList<Cliente> lista = FXCollections.observableArrayList(ClienteDAO.getInstance().retrieveAll(limitePadrao));
		Table.setItems(lista);
		
		
		Table.getSelectionModel().selectedItemProperty().addListener(
			(observable, oldValue, newValue)->
			{
				selecionadosController.setCliente(newValue);
				btnEdit.setDisable(newValue==null);
				btnDelete.setDisable(newValue==null);
			}
			);
		txtLimite.textProperty().addListener(
				(observable, oldValue, newValue)->
				{							
					if (newValue!=null && !newValue.matches("\\d*"))
					{
						txtLimite.setText(oldValue);
					}
					else if (newValue!=null && !newValue.isBlank()&& Integer.parseInt(newValue)>1000)
					{
						txtLimite.setText(""+1000);
					}
					
					
					
				}
				
				
				);
		
		cbCriterio.getItems().addAll(criterios);
		
		
	}
  
    public boolean Excluir(Cliente cliente)
    {
    	if(cliente !=null)
    	{
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Confirmação");
    		alert.setHeaderText("Deseja mesmo deletar esse registro?");
    		alert.setContentText("Deletar "+cliente.getNome()+"(id: "+cliente.getId()+")?");
    		Optional<ButtonType> result = alert.showAndWait();
    		if (result.get() == ButtonType.OK){
    			
    			if(selecionadosController.getCliente()!=null &&
    			   selecionadosController.getCliente().getId()==cliente.getId())
    				selecionadosController.setCliente(null);
    			
    			ClienteDAO.getInstance().delete(cliente);
    			Table.getItems().remove(cliente);
    			Table.refresh();
    			
    			
    			return true;
    		}
    		
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
    
    //provavelmente inútil
    public boolean Adicionar(Cliente cliente)
    {
    	if (cliente !=null)
    	{
    		Table.getItems().add(cliente);
    		Table.refresh();
    		
    		return true;
    	}
    	return false;
    	
    }
    
    
    //BOTOES ->
    
    @FXML private Button btnNew;
    @FXML private Button btnDelete;
    @FXML private Button btnEdit;
    
    
 
    @FXML private void novoRegistro()
    {
    	FichaCliente fc = new FichaCliente(null, this);
    	fc.Show("novo cliente");
    }  
    @FXML private void excluirSelecionado()
    {
    	Excluir(selecionadosController.getCliente());
    }  
    @FXML private void editarSelecionado()
    {
    	if (selecionadosController.getCliente()==null)
    		return;
    	FichaCliente fc = new FichaCliente(selecionadosController. getCliente(), this);
    	fc.Show("Editar Cliente");
    }
    
    
    
    
    //PESQUISA ->
    @FXML private TextField txtFSearch;
    @FXML private Button btnSearch;
    @FXML private ComboBox<Criterio> cbCriterio;
    @FXML private TextField txtLimite;
    private int limitePadrao=10;
    
    private ObservableList<Criterio> criterios = FXCollections.observableArrayList(
    				Criterio.CriterioDeID(),
    				new Criterio("Nome","nome LIKE '%{value}%'"),
    				new Criterio("email","email LIKE '%{value}%'"),
    				new Criterio("Telefone","telefone LIKE '%{value}%'"),
    				new Criterio("Endereço","end LIKE '%{value}%'"),
    				new Criterio("CEP","cep LIKE '%{value}%'"),
    				Criterio.CriterioVazio()
    				);
    
    @FXML private void procurar()
    {
    	ClienteDAO DaoInstance = ClienteDAO.getInstance();
    	
    	String query;
    	String oTexto = txtFSearch.getText();
    	String limite= txtLimite.getText().isBlank()?""+limitePadrao:txtLimite.getText();
    	Criterio crit = cbCriterio.getValue()==null?Criterio.CriterioVazio():cbCriterio.getValue();
    	
    	
    	try {
    		if (!oTexto.isBlank())
    		{
    			query = "SELECT * FROM cliente WHERE "+ crit.getQuery(oTexto) + " LIMIT "+limite+";";
    		}
    		else
    		{
    			query = "SELECT * FROM cliente LIMIT "+limite+";";
    		}
    		
    		//System.out.println(query);
    		List<Cliente> lista = DaoInstance.retrieve(query);
    		ObservableList<Cliente> listaOBS = FXCollections.observableArrayList(lista);
    		
    		Table.setItems(listaOBS);
    	}
    	catch (Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	Table.refresh();
    	
    }
    
    @FXML void procurarPorEnter(KeyEvent event) {
    	if( event.getCode() == KeyCode.ENTER ) 
    	{
    		procurar();
		}
    }
	
   
}
