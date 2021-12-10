package view.Especie;

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
import model.ClienteDAO;
import model.Especie;
import model.EspecieDAO;

public class FichaEspecie extends AnchorPane implements Initializable {

	private Especie especie;
	private EspecieController especieCtrl;

	@FXML private Label lbl_ID;
	@FXML private TextField txt_Nome;

	@FXML private Button btnDelete;
	@FXML private Button btnSave;
	@FXML private Button btnSelect;

	public FichaEspecie(Especie es, EspecieController especieCtrl) 
	{
		especie = es;
		this.especieCtrl = especieCtrl;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		if (especie != null) {
			lbl_ID.setText("" + especie.getId());
			txt_Nome.setText(especie.getNome());
		} else {
			btnSelect.setDisable(true);
		}

	}

	public void Show(String nome) 
	{
		System.out.println("showing ficha especie");
		Stage stage = new Stage();
		Scene scene;
		Parent root = null;
		FXMLLoader loader = null;
		try {
			loader = new FXMLLoader(getClass().getResource("ViewNewEspecie.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		scene = new Scene(root);
		stage.setTitle(nome);
		stage.setScene(scene);
		stage.show();

	}

	@FXML private void Salvar() 
	{
		if (especie==null) 
		{
			//salvar novo cliente
			especie = EspecieDAO.getInstance().create(txt_Nome.getText());
			especieCtrl.getTable().getItems().add(especie);
			lbl_ID.setText("" + especie.getId());
			btnSelect.setDisable(false);
		} 
		else 
		{
			//salvar edição
			especie.setNome(txt_Nome.getText());
			

			
			if(especieCtrl.getSelectCtrl().getEspecie()!=null &&
					especieCtrl.getSelectCtrl().getEspecie().getId()==especie.getId())
				especieCtrl.getSelectCtrl().setEspecie(especie);
			
			
			EspecieDAO.getInstance().update(especie);
			especieCtrl.getTable().refresh();

		}
		
	}

	@FXML private void Excluir() 
	{

	}

	@FXML private void Selecionar() 
	{
		
	}

}
