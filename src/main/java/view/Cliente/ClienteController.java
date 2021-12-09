package view.Cliente;



import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Cliente;
import model.ClienteDAO;
import view.SelecionadosController;

public class ClienteController implements Initializable{

	
	private static Cliente Selecionado=null;
	private SelecionadosController selecionadosController;
	private static boolean isFirstInstance = true;
	private static boolean isEditing = false;
	//false: creating new register
	//true: editing selected register
	
    @FXML private TableView<Cliente> Table;
    private static TableView<Cliente> tabelaStatic;
    @FXML private TableColumn<Cliente, Integer> table_id;
    @FXML private TableColumn<Cliente, String> table_nome;
    @FXML private TableColumn<Cliente, String> table_email;
    @FXML private TableColumn<Cliente, String> table_telefone;
    @FXML private TableColumn<Cliente, String> table_endereco;
    @FXML private TableColumn<Cliente, String> table_CEP;
    
    ObservableList<Cliente> lista = FXCollections.observableArrayList(ClienteDAO.getInstance().retrieveAll());
 
   
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	table_id.setCellValueFactory( new PropertyValueFactory<Cliente, Integer>("id"));
		table_nome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
		table_email.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));
		table_telefone.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefone"));
		table_endereco.setCellValueFactory(new PropertyValueFactory<Cliente, String>("endereco"));
		table_CEP.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cep"));
		Table.setItems(lista);
		tabelaStatic=Table;
		
		Table.getSelectionModel().selectedItemProperty().addListener(
			(observable, oldValue, newValue)->
			{
				Selecionado = newValue;
				selecionadosController.setCliente(newValue);
					
			}
			);
		
		
		
	}
    
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
    
    
 
    @FXML private void novoCliente()
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
    	
    }
    
    
    //PESQUISA ->
    
    @FXML private TextField txtFSearch;
    @FXML private Button btnSearch;
    
    @FXML private void procurar()
    {
    	String oTexto = txtFSearch.getText();
    	if(oTexto.isEmpty())
    	{
    		Table.setItems(FXCollections.observableArrayList(ClienteDAO.getInstance().retrieveAll()));
    	}
    	else if (onlyDigits(oTexto))
    	{
    		Table.setItems(FXCollections.observableArrayList(ClienteDAO.getInstance().retrieveByID(Integer.parseInt(oTexto))));
    	}
    	else
    	{
    		Table.setItems(FXCollections.observableArrayList(ClienteDAO.getInstance().retrieveBySimilarName(oTexto)));
    	}
    	Table.refresh();
    	
    }
    
  //Copiado de https://www.geeksforgeeks.org/how-to-check-if-string-contains-only-digits-in-java/
    private static boolean onlyDigits(String str)
    {
    	// Regex to check string
        // contains only digits
        String regex = "[0-9]+";
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        // If the string is empty
        // return false
        if (str == null) {
            return false;
        }  
        // Find match between given string
        // and regular expression
        // using Pattern.matcher()
        Matcher m = p.matcher(str);  
        // Return if the string
        // matched the ReGex
        return m.matches();
    }
    
    
    
   
	
	
}
