package view.Animal;

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Animal;
import model.AnimalDAO;
import model.Cliente;
import model.ClienteDAO;
import model.Especie;
import model.EspecieDAO;
import view.ControllerMainView;
import view.Animal.AnimalController;
import view.Cliente.FichaCliente;
import view.Especie.FichaEspecie;

public class FichaAnimal extends AnchorPane implements Initializable {
	private Animal animal;
	private AnimalController animalCtrl;
	private Cliente dono=null;
	private Especie especie=null;
	//private Cliente dono;
	
	
	@FXML private Label lbl_ID;
	@FXML private TextField txt_Nome;
	@FXML private ToggleGroup sexGrou;
	@FXML private RadioButton rb_F;
    @FXML private RadioButton rb_M;
	@FXML private TextField txt_Especie;
	@FXML private TextField txt_Nascimento;
	@FXML private TextField txt_Dono;


	public FichaAnimal(Animal cl, AnimalController animalCtrl)
	{
		animal = cl;
		this.animalCtrl= animalCtrl;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (animal != null) {
			ClienteDAO cli = ClienteDAO.getInstance();
			EspecieDAO esp = EspecieDAO.getInstance();
			lbl_ID.setText("" + animal.getId());
			txt_Nome.setText(animal.getNome());
			sexGrou.selectToggle(animal.getSexo()=="F"?rb_F:rb_M);
			
			Especie especie = esp.retrieveByID(animal.getEspecieId());
			if (especie!=null)
			{
				txt_Especie.setText(esp.retrieveByID(animal.getEspecieId()).getNome());
				this.especie=especie;
				verEspecie.setDisable(false);
			}
			else
				txt_Especie.setText("");
				
			txt_Nascimento.setText(""+animal.getAnoNasc());
			
			Cliente dono = cli.retrieveByID(animal.getDonoId());
    		if (dono!=null)
    		{
    			txt_Dono.setText(dono.getNome()+"(id: "+dono.getId()+")");
    			this.dono=dono;
    			verDono.setDisable(false);
    			
    		}
    			
    		else
    			txt_Dono.setText("");
		}
		else
		{
			btnSelect.setDisable(true);
		}
		
		txt_Nascimento.textProperty().addListener(
				(observable, oldValue, newValue)->
				{							
					if (newValue!=null && !newValue.matches("\\d*"))
					{
						txt_Nascimento.setText(oldValue);
					}
				}
				);

			
		
		
	}

	public void Show(String nome)
	{
		
		Stage stage = new Stage();
		Scene scene;
		Parent root=null;
		FXMLLoader loader= null;
		try 
		{
			loader = new FXMLLoader(getClass().getResource("ViewNewAnimal.fxml"));
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
		RadioButton rb = (RadioButton) sexGrou.getSelectedToggle();
		char sexo = rb==null?'F':rb.getText().charAt(0);
		int nasc = txt_Nascimento.getText().isBlank()?0:Integer.valueOf(txt_Nascimento.getText());
		if (animal==null) 
		{
			//salvar novo animal
			
			animal = AnimalDAO.getInstance().create(txt_Nome.getText(), 
													nasc,
													sexo,
													especie==null?0:especie.getId(),
													dono==null?0:dono.getId()							 
													);
			animalCtrl.getTable().getItems().add(animal);
			lbl_ID.setText("" + animal.getId());
			btnSelect.setDisable(false);
		} 
		else 
		{
			//salvar edição
			animal.setNome(txt_Nome.getText());
			animal.setSexo(""+sexo);
			animal.setAnoNasc(nasc);
			
			animal.setEspecieId(especie==null?0:especie.getId());
			animal.setDonoId(dono==null?0:dono.getId());

			
			if(animalCtrl.getSelectCtrl().getAnimal()!=null &&
					animalCtrl.getSelectCtrl().getAnimal().getId()==animal.getId())
				animalCtrl.getSelectCtrl().setAnimal(animal);
			
			
			AnimalDAO.getInstance().update(animal);
			animalCtrl.getTable().refresh();

		}

		//Stage stage = (Stage) btnSave.getScene().getWindow();
		//stage.close();
		//editMode = false;

	}
	@FXML private void Excluir() 
	{

		if (animal !=null) 
		{
			if (animalCtrl.Excluir(animal))
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
		if (animal != null) 
		{
			animalCtrl.getSelectCtrl().setAnimal(animal);
			
		}
	}

	
	@FXML private Button verDono;
    @FXML private Button verEspecie;
	
	@FXML private void UsarClienteSelecionado()
	{
		dono = animalCtrl.getSelectCtrl().getCliente();
		
		if (dono==null)
		{
			txt_Dono.setText("");
			verDono.setDisable(true);
			
		}
		else
		{
			txt_Dono.setText(dono.getNome()+"(id: "+dono.getId()+")");
			verDono.setDisable(false);
		}
	}
	@FXML private void VerClienteSelecionado()
	{
		FichaCliente cl = new FichaCliente(dono,ControllerMainView.getPrincipal().getClienteTableController());
		cl.Show("Ver Dono");
	}
	
	@FXML private void UsarEspecieSelecionado()
	{
		especie = animalCtrl.getSelectCtrl().getEspecie();
		
		if (especie==null)
		{
			txt_Especie.setText("");
			verEspecie.setDisable(true);
		}
		else
		{
			txt_Especie.setText(especie.getNome()+"(id: "+especie.getId()+")");
			verEspecie.setDisable(false);
		}
	}
	@FXML private void VerEspecielecionado()
	{
		FichaEspecie cl = new FichaEspecie(especie,ControllerMainView.getPrincipal().getEspecieTableController());
		cl.Show("Ver Especie");
	}

}
