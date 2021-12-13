package view.Consulta;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Consulta;
import model.ConsultaDAO;
import model.Animal;
import model.AnimalDAO;
import model.Cliente;
import model.ClienteDAO;
import model.Especie;
import model.EspecieDAO;
import model.Tratamento;
import model.TratamentoDAO;
import model.Veterinario;
import model.VeterinarioDAO;
import view.ControllerMainView;
import view.Consulta.ConsultaController;
import view.Cliente.FichaCliente;
import view.Especie.FichaEspecie;

public class FichaConsulta extends AnchorPane implements Initializable
{

	private Consulta consulta;
	private ConsultaController consultaCtrl;
	private Animal animal;
	private Veterinario veterinario;
	private Tratamento tratamento;
 

    @FXML private Label lbl_ID;
    @FXML private DatePicker dt_data;
    @FXML private TextField txt_rorario;
    @FXML private TextField txt_animal;
    @FXML private TextField txt_veterinario;
    @FXML private CheckBox ckb_finalizado;
    @FXML private TextArea txt_comentario;
    @FXML private TextField txt_tratamento;
    
	public FichaConsulta(Consulta cl, ConsultaController consultaCtrl)
	{
		consulta = cl;
		this.consultaCtrl = consultaCtrl;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		if (consulta != null)
		{
			AnimalDAO ani = AnimalDAO.getInstance();
			VeterinarioDAO vet = VeterinarioDAO.getInstance();
			TratamentoDAO trat = TratamentoDAO.getInstance();
			
			
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
		Parent root = null;
		FXMLLoader loader = null;
		try
		{
			loader = new FXMLLoader(getClass().getResource("ViewNewConsulta.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			root = loader.load();
		} catch (IOException e)
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

	@FXML
	private void Salvar()
	{
		
		if (consulta == null)
		{
			// salvar novo consulta

			//TODO: consulta = ConsultaDAO.getInstance().create();
			consultaCtrl.getTable().getItems().add(consulta);
			lbl_ID.setText("" + consulta.getId());
			btnSelect.setDisable(false);
		} else
		{
			

			if (consultaCtrl.getSelectCtrl().getConsulta() != null
					&& consultaCtrl.getSelectCtrl().getConsulta().getId() == consulta.getId())
				consultaCtrl.getSelectCtrl().setConsulta(consulta);

			ConsultaDAO.getInstance().update(consulta);
			consultaCtrl.getTable().refresh();

		}

		// Stage stage = (Stage) btnSave.getScene().getWindow();
		// stage.close();
		// editMode = false;

	}

	@FXML
	private void Excluir()
	{

		if (consulta != null)
		{
			if (consultaCtrl.Excluir(consulta))
			{
				Stage stage = (Stage) btnSave.getScene().getWindow();
				stage.close();
			}
		} else
		{
			Stage stage = (Stage) btnSave.getScene().getWindow();
			stage.close();

		}

	}

	@FXML
	private void Selecionar()
	{
		if (consulta != null)
		{
			consultaCtrl.getSelectCtrl().setConsulta(consulta);

		}
	}

	@FXML private Button verAnimal;
	@FXML private void UsarAnimalSelecionado()
	{
		
	}
	@FXML private void VerAnimalSelecionado()
	{
		
	}

	@FXML private Button verVeterinario;
	@FXML private void UsarVeterinarioSelecionado()
	{
		
	}
	@FXML private void VerVeterinariolecionado()
	{
		
	}

	@FXML private Button verTratamento;
	@FXML private void UsarTratamentoSelecionado()
	{
		
	}
	@FXML private void VerTratamentolecionado()
	{
		
	}
	
	
};
