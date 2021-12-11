package view.Veterinario;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Veterinario;
import model.VeterinarioDAO;
import model.Veterinario;
import view.Criterio;
import view.SelecionadosController;
import view.Veterinario.FichaVeterinario;

public class VeterinarioController {

	@FXML
	private SelecionadosController selecionadosController;
	public void InjectSelecionadosController(SelecionadosController sc)
    {
    	selecionadosController=sc;
    }
    public SelecionadosController getSelectCtrl()
    {
    	return selecionadosController;
    }
    public TableView<Veterinario> getTable()
    {
    	return Table;
    }
	
	
	
    @FXML private TableView<Veterinario> Table;
   
    @FXML private TableColumn<Veterinario, Integer> table_id;
    @FXML private TableColumn<Veterinario, String> table_nome;
    @FXML private TableColumn<Veterinario, String> table_email;
    @FXML private TableColumn<Veterinario, String> table_telefone;
   
   
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	table_id.setCellValueFactory( new PropertyValueFactory<Veterinario, Integer>("id"));
		table_nome.setCellValueFactory(new PropertyValueFactory<Veterinario, String>("nome"));
		table_email.setCellValueFactory(new PropertyValueFactory<Veterinario, String>("email"));
		table_telefone.setCellValueFactory(new PropertyValueFactory<Veterinario, String>("telefone"));
		
		
		ObservableList<Veterinario> lista = FXCollections.observableArrayList(VeterinarioDAO.getInstance().retrieveAll(limitePadrao));
		Table.setItems(lista);
		
		
		Table.getSelectionModel().selectedItemProperty().addListener(
			(observable, oldValue, newValue)->
			{
				selecionadosController.setVeterinario(newValue);
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
  
    public boolean Excluir(Veterinario veterinario)
    {
    	if(veterinario !=null)
    	{
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Confirmação");
    		alert.setHeaderText("Deseja mesmo deletar esse registro?");
    		alert.setContentText("Deletar "+veterinario.getNome()+"(id: "+veterinario.getId()+")?");
    		Optional<ButtonType> result = alert.showAndWait();
    		if (result.get() == ButtonType.OK){
    			
    			if(selecionadosController.getVeterinario()!=null &&
    			   selecionadosController.getVeterinario().getId()==veterinario.getId())
    				selecionadosController.setVeterinario(null);
    			
    			VeterinarioDAO.getInstance().delete(veterinario);
    			Table.getItems().remove(veterinario);
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
    public boolean Adicionar(Veterinario veterinario)
    {
    	if (veterinario !=null)
    	{
    		Table.getItems().add(veterinario);
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
    	FichaVeterinario fc = new FichaVeterinario(null, this);
    	fc.Show("novo veterinario");
    }  
    @FXML private void excluirSelecionado()
    {
    	Excluir(selecionadosController.getVeterinario());
    }  
    @FXML private void editarSelecionado()
    {
    	if (selecionadosController.getVeterinario()==null)
    		return;
    	FichaVeterinario fc = new FichaVeterinario(selecionadosController.getVeterinario(), this);
    	fc.Show("Editar Veterinario");
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
    	VeterinarioDAO DaoInstance = VeterinarioDAO.getInstance();
    	
    	String query;
    	String oTexto = txtFSearch.getText();
    	String limite= txtLimite.getText().isBlank()?""+limitePadrao:txtLimite.getText();
    	Criterio crit = cbCriterio.getValue()==null?Criterio.CriterioVazio():cbCriterio.getValue();
    	
    	
    	try {
    		if (!oTexto.isBlank())
    		{
    			query = "SELECT * FROM veterinario WHERE "+ crit.getQuery(oTexto) + " LIMIT "+limite+";";
    		}
    		else
    		{
    			query = "SELECT * FROM veterinario LIMIT "+limite+";";
    		}
    		
    		//System.out.println(query);
    		List<Veterinario> lista = DaoInstance.retrieve(query);
    		ObservableList<Veterinario> listaOBS = FXCollections.observableArrayList(lista);
    		
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
