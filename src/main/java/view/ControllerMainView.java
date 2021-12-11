package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import model.AnimalDAO;
import view.Animal.AnimalController;
import view.Cliente.ClienteController;
import view.Consulta.ConsultaController;
import view.Especie.EspecieController;
import view.Exame.ExameController;
import view.Tratamento.TratamentoController;
import view.Veterinario.VeterinarioController;

public class ControllerMainView implements Initializable {

	@FXML
	private SelecionadosController VerSelecionadosController;
	@FXML
	private ClienteController ClienteTableController;
	@FXML
	private AnimalController AnimalTableController;
	@FXML
	private EspecieController EspecieTableController;
	@FXML
	private ExameController ExameTableController;
	@FXML
	private ConsultaController ConsultaTableController;
	@FXML
	private VeterinarioController VeterinarioTableController;
	@FXML
	private TratamentoController TratamentoTableConroller;
	
	@FXML 
	private Button btnTest;
	
	
	
	@FXML private void testar()
	{
		System.out.println("funciona");
		
	}



	
	public void initialize(URL arg0, ResourceBundle arg1) {
		ClienteTableController.InjectSelecionadosController(VerSelecionadosController);
		EspecieTableController.InjectSelecionadosController(VerSelecionadosController);
		AnimalTableController.InjectSelecionadosController(VerSelecionadosController);
		
		AnimalDAO adao = AnimalDAO.getInstance();
		System.out.println(adao.retrieveAll());
	}

		
	
}
	
	
	
	
