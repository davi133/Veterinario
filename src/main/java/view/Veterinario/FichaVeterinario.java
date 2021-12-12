package view.Veterinario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Veterinario;
import model.VeterinarioDAO;

public class FichaVeterinario extends AnchorPane implements Initializable
{
	private Veterinario veterinario;
	private VeterinarioController veterinarioCtrl;
	
	@FXML private Label lbl_ID;
	@FXML private TextField txt_Nome;
	@FXML private TextField txt_email;
	@FXML private TextField txt_telefone;
	

	public FichaVeterinario(Veterinario cl, VeterinarioController veterinarioCtrl)
	{
		veterinario = cl;
		this.veterinarioCtrl= veterinarioCtrl;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (veterinario != null) {
			lbl_ID.setText("" + veterinario.getId());
			txt_Nome.setText(veterinario.getNome());
			txt_email.setText(veterinario.getEmail());
			txt_telefone.setText(veterinario.getTelefone());
			
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
			loader = new FXMLLoader(getClass().getResource("ViewNewVeterinario.fxml"));
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
	
	
	
	
	
	@FXML private Button btnDelete;
	@FXML private Button btnSave;
	@FXML private Button btnSelect;
	
	@FXML private void Salvar() 
	{
		
		if (veterinario==null) 
		{
			//salvar novo veterinario
			veterinario = VeterinarioDAO.getInstance().create(txt_Nome.getText(), 	 
													  txt_email.getText(), 
													  txt_telefone.getText());
			veterinarioCtrl.getTable().getItems().add(veterinario);
			lbl_ID.setText("" + veterinario.getId());
			btnSelect.setDisable(false);
		} 
		else 
		{
			//salvar edição
			veterinario.setNome(txt_Nome.getText());
			veterinario.setEmail(txt_email.getText());
			veterinario.setTelefone(txt_telefone.getText());

			
			if(veterinarioCtrl.getSelectCtrl().getVeterinario()!=null &&
			   veterinarioCtrl.getSelectCtrl().getVeterinario().getId()==veterinario.getId())
				veterinarioCtrl.getSelectCtrl().setVeterinario(veterinario);
			
			
			VeterinarioDAO.getInstance().update(veterinario);
			veterinarioCtrl.getTable().refresh();

		}

		//Stage stage = (Stage) btnSave.getScene().getWindow();
		//stage.close();
		//editMode = false;

	}

	@FXML private void Excluir() 
	{

		if (veterinario !=null) 
		{
			if (veterinarioCtrl.Excluir(veterinario))
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
		if (veterinario != null) 
		{
			veterinarioCtrl.getSelectCtrl().setVeterinario(veterinario);
			
		}
	}

}
