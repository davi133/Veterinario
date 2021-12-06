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
	private static SelecionadosController selecionadosController;
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
    	if (isFirstInstance)
		{
    	
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
		
		isFirstInstance=false;
		}
    	else if (isEditing)
    	{
    		lbl_ID.setText(""+Selecionado.getId());
    		txt_Nome.setText(Selecionado.getNome());
    		txt_email.setText(Selecionado.getEmail());
    		txt_endereco.setText(Selecionado.getEndereco());
    		txt_CEP.setText(Selecionado.getCep());
    		txt_telefone.setText(Selecionado.getTelefone());
    		toEdit = Selecionado;
    	}
	}
    
    public void InjectSelecionadosController(SelecionadosController sc)
    {
    	selecionadosController=sc;
    }

    @FXML private Button btnNew;
    @FXML private Button btnDelete;
    @FXML private Button btnEdit;
    
    private void ClienteSheet(String nome)
    {		
			Stage stage = new Stage();
			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("ViewNewCliente.fxml"));
			} catch (IOException e) {
				System.out.println("sadsadasd");
				e.printStackTrace();
			}
			Scene scene = new Scene(root);
			stage.setTitle(nome);
			stage.setScene(scene);
			
			stage.showAndWait();
    }
    @FXML private void novoCliente()
    {
    	isEditing=false;
    	ClienteSheet("Novo Cliente");
    }
    @FXML private void excluirSelecionado()
    {
    	if (Selecionado !=null) 
    	{
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Confirmação");
    		alert.setHeaderText("Deseja mesmo deletar esse registro?");
    		alert.setContentText("Deletar "+Selecionado.getNome()+"("+Selecionado.getId()+")?");
    		Optional<ButtonType> result = alert.showAndWait();
    		if (result.get() == ButtonType.OK){
    			ClienteDAO.getInstance().delete(Selecionado);
    			tabelaStatic.getItems().remove(Selecionado);
    			
    		} 
    	}
    	
    }  
    @FXML private void editarSelecionado()
    {
    	Selecionado = Table.getSelectionModel().getSelectedItem();
    	
    	if (Selecionado !=null) 
    	{
    		isEditing=true;
    		ClienteSheet("Editar Cliente");
    	}
    }
    
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
    
    //Parte da ViewNewCliente.fxml
    
    private Cliente toEdit;
    @FXML private Label lbl_ID;
    @FXML private TextField txt_CEP;
    @FXML private TextField txt_Nome;
    @FXML private TextField txt_email;
    @FXML private TextField txt_endereco;
    @FXML private TextField txt_telefone;
    @FXML private Button btnDelete2;
    @FXML private Button btnSave;

    @FXML private void Salvar()
    {
    	if (!isEditing) {
    	Cliente novo = ClienteDAO.getInstance().create(txt_Nome.getText(), 
    												   txt_endereco.getText(), 
    												   txt_CEP.getText(),
    												   txt_email.getText(), 
    												   txt_telefone.getText());
    	tabelaStatic.getItems().add(novo);
    	}
    	else
    	{
    		Cliente selected = toEdit;
    		
    		selected.setNome(txt_Nome.getText());
    		selected.setEndereco(txt_endereco.getText());
    		selected.setEmail(txt_email.getText());
    		selected.setCep( txt_CEP.getText());
    		selected.setTelefone(txt_telefone.getText());
    		
    		selecionadosController.setCliente(Selecionado);
    		ClienteDAO.getInstance().update(selected);
    		tabelaStatic.refresh();
    		
    	}
    	
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
        isEditing = false;

    }
    @FXML private void Excluir()
    {
    	
    	if(isEditing)
    	{
    		if (Selecionado !=null) 
        	{
        		Alert alert = new Alert(AlertType.CONFIRMATION);
        		alert.setTitle("Confirmação");
        		alert.setHeaderText("Deseja mesmo deletar esse registro?");
        		alert.setContentText("Deletar "+Selecionado.getNome()+"("+Selecionado.getId()+")?");
        		Optional<ButtonType> result = alert.showAndWait();
        		if (result.get() == ButtonType.OK){
        			ClienteDAO.getInstance().delete(toEdit);
        			tabelaStatic.getItems().remove(toEdit);
        			
        			Stage stage = (Stage) btnSave.getScene().getWindow();
        	        stage.close();
        	        isEditing = false;
        		} 
        	}
    	}
    	else
    	{
    		Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
            isEditing = false;
    	}
    	
    	
    }
	
	
}
