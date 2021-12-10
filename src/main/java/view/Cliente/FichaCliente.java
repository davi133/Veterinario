package view.Cliente;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Cliente;
import model.ClienteDAO;
import view.SelecionadosController;

public class FichaCliente extends AnchorPane implements Initializable {

	private Cliente cliente;
	private ClienteController clienteCtrl;
	
	@FXML private Label lbl_ID;
	@FXML private TextField txt_CEP;
	@FXML private TextField txt_Nome;
	@FXML private TextField txt_email;
	@FXML private TextField txt_endereco;
	@FXML private TextField txt_telefone;
	
	@FXML private Button btnDelete;
	@FXML private Button btnSave;
	@FXML private Button btnSelect;

	public FichaCliente(Cliente cl, ClienteController clienteCtrl)
	{
		cliente = cl;
		this.clienteCtrl= clienteCtrl;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (cliente != null) {
			lbl_ID.setText("" + cliente.getId());
			txt_Nome.setText(cliente.getNome());
			txt_email.setText(cliente.getEmail());
			txt_endereco.setText(cliente.getEndereco());
			txt_CEP.setText(cliente.getCep());
			txt_telefone.setText(cliente.getTelefone());
			
		}
		else
		{
			btnSelect.setDisable(true);
		}

			
		
		
	}

	public void Show(String nome)
	{
		
		Stage stage = new Stage();
		Scene scene;
		Parent root=null;
		FXMLLoader loader= null;
		try 
		{
			loader = new FXMLLoader(getClass().getResource("ViewNewCliente.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			root = loader.load();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	
		scene = new Scene(root);
		stage.setTitle(nome);
		stage.setScene(scene);
		stage.show();

	}
	
	@FXML private void Salvar() 
	{
		
		if (cliente==null) 
		{
			//salvar novo cliente
			cliente = ClienteDAO.getInstance().create(txt_Nome.getText(), 
													  txt_endereco.getText(),
													  txt_CEP.getText(), 
													  txt_email.getText(), 
													  txt_telefone.getText());
			clienteCtrl.getTable().getItems().add(cliente);
			lbl_ID.setText("" + cliente.getId());
			btnSelect.setDisable(false);
		} 
		else 
		{
			//salvar edição
			cliente.setNome(txt_Nome.getText());
			cliente.setEndereco(txt_endereco.getText());
			cliente.setEmail(txt_email.getText());
			cliente.setCep(txt_CEP.getText());
			cliente.setTelefone(txt_telefone.getText());

			
			if(clienteCtrl.getSelectCtrl().getCliente()!=null &&
			   clienteCtrl.getSelectCtrl().getCliente().getId()==cliente.getId())
				clienteCtrl.getSelectCtrl().setCliente(cliente);
			
			
			ClienteDAO.getInstance().update(cliente);
			clienteCtrl.getTable().refresh();

		}

		//Stage stage = (Stage) btnSave.getScene().getWindow();
		//stage.close();
		//editMode = false;

	}

	@FXML private void Excluir() 
	{

		if (cliente !=null) 
		{
			if (clienteCtrl.Excluir(cliente))
			{
				Stage stage = (Stage) btnSave.getScene().getWindow();
				stage.close();
			}
			
			
		}
		else 
		{
			Stage stage = (Stage) btnSave.getScene().getWindow();
			stage.close();
			
		}

	}

	@FXML private void Selecionar()
	{
		if (cliente != null) 
		{
			clienteCtrl.getSelectCtrl().setCliente(cliente);
			
		}
	}
}
