package view.Consulta;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import model.Consulta;
import model.ConsultaDAO;
import model.Consulta;
import view.Criterio;
import view.SelecionadosController;
import view.Consulta.FichaConsulta;

public class ConsultaController implements Initializable {

	private SelecionadosController selecionadosController;

	public void InjectSelecionadosController(SelecionadosController sc)
	{
		selecionadosController = sc;
	}

	public SelecionadosController getSelectCtrl()
	{
		return selecionadosController;
	}

	public TableView<Consulta> getTable()
	{

		return Table;
	}

	@FXML private TableView<Consulta> Table;
	@FXML private TableColumn<Consulta, Integer> table_id;
	@FXML private TableColumn<Consulta, Date> table_data;
	@FXML private TableColumn<Consulta, Integer> table_animal;
	@FXML private TableColumn<Consulta, Integer> table_veterinario;
    @FXML private TableColumn<Consulta, Boolean> table_terminou;
    @FXML private TableColumn<Consulta, String> table_comentario;
   
    
    
    
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		table_id.setCellValueFactory(new PropertyValueFactory<Consulta, Integer>("id"));
		table_data.setCellValueFactory(new PropertyValueFactory<Consulta, Date>("dtConsulta"));
		table_animal.setCellValueFactory(new PropertyValueFactory<Consulta, Integer>("animalId"));
		table_veterinario.setCellValueFactory(new PropertyValueFactory<Consulta, Integer>("veterinarioId"));
		table_terminou.setCellValueFactory(new PropertyValueFactory<Consulta, Boolean>("terminou"));
		table_comentario.setCellValueFactory(new PropertyValueFactory<Consulta, String>("descricao"));
		
		
		
		ObservableList<Consulta> lista = FXCollections.observableArrayList(ConsultaDAO.getInstance().retrieveAll(limitePadrao));
		Table.setItems(lista);

		Table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			selecionadosController.setConsulta(newValue);
			btnEdit.setDisable(newValue == null);
			btnDelete.setDisable(newValue == null);
		});
		txtLimite.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && !newValue.matches("\\d*"))
			{
				txtLimite.setText(oldValue);
			} else if (newValue != null && !newValue.isBlank() && Integer.parseInt(newValue) > 1000)
			{
				txtLimite.setText("" + 1000);
			}
		});

		cbCriterio.getItems().addAll(criterios);
		cbCriterio.getSelectionModel().selectedItemProperty().addListener(
			(options, oldValue, newValue) -> 
			{
				procurar();
			});

	}

	public boolean Excluir(Consulta consulta)
	{
		if (consulta != null)
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Deseja mesmo deletar esse registro?");
			alert.setContentText("Deletar consulta id: " + consulta.getId() + "?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK)
			{

				if (selecionadosController.getConsulta() != null
						&& selecionadosController.getConsulta().getId() == consulta.getId())
					selecionadosController.setConsulta(null);

				ConsultaDAO.getInstance().delete(consulta);
				Table.getItems().remove(consulta);
				Table.refresh();

				return true;
			}

			return false;
		} else
		{
			return true;
		}
	}

	// BOTOES ->

	@FXML
	private Button btnNew;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnEdit;

	@FXML
	private void novoRegistro()
	{
		FichaConsulta fc = new FichaConsulta(null, this);
		fc.Show("novo consulta");
	}

	@FXML
	private void excluirSelecionado()
	{
		Excluir(selecionadosController.getConsulta());
	}

	@FXML
	private void editarSelecionado()
	{
		if (selecionadosController.getConsulta() == null)
			return;
		FichaConsulta fc = new FichaConsulta(selecionadosController.getConsulta(), this);
		fc.Show("Editar Consulta");
	}

	// PESQUISA ->

	@FXML
	private TextField txtFSearch;
	@FXML
	private Button btnSearch;
	@FXML
	private ComboBox<Criterio> cbCriterio;
	@FXML
	private TextField txtLimite;
	private int limitePadrao = 10;

	private ObservableList<Criterio> criterios = FXCollections.observableArrayList(
				Criterio.CriterioDeID(),
				new Criterio("Data", "data LIKE '%{value}%'"), 
				new Criterio("animal(id)", "id_animal = {value}", true),
				new Criterio("animal(selecionado)", "id_animal = {value}",true),
				new Criterio("veterinario(id)", "id_vet= {value}",true),
				new Criterio("veterinario(selecionado)", "id_vet= {value}",true), 
				new Criterio("terminou(S ou N)", "terminado = {value}",true),
				new Criterio("comentário", "comentario LIKE '%{value}%'"),
				Criterio.CriterioVazio()
			);
	
	

	@FXML
	private void procurar()
	{
		
		ConsultaDAO DaoInstance = ConsultaDAO.getInstance();

		String query=" ";
		String oTexto = txtFSearch.getText();
		String limite = txtLimite.getText().isBlank() ? "" + limitePadrao : txtLimite.getText();
		Criterio crit = cbCriterio.getValue() == null ? Criterio.CriterioVazio() : cbCriterio.getValue();

		try
		{

			if (crit.getNome() == "animal(selecionado)" && selecionadosController.getAnimal()!=null)
			{
				query = "SELECT * FROM consulta WHERE " + crit.getQuery(selecionadosController.getAnimal().getId())
					+ " LIMIT " + limite + ";";
			} 
			else if (crit.getNome() == "veterinario(selecionado)"&&selecionadosController.getVeterinario()!=null)
			{
				query = "SELECT * FROM consulta WHERE " + crit.getQuery(selecionadosController.getVeterinario().getId())
					+ " LIMIT " + limite + ";";
			}
			else
			{
				if (!oTexto.isBlank())
				{
					if (crit.getNome() == "terminou(S ou N)")
					{
						oTexto = oTexto.toLowerCase().charAt(0)=='s'?"1":"0";
					}
					query = "SELECT * FROM consulta WHERE " + crit.getQuery(oTexto) + " LIMIT " + limite + ";";
				} 
				else
				{
					query = "SELECT * FROM consulta LIMIT " + limite + ";";
				}
			}

			//System.out.println(query);
			List<Consulta> lista = DaoInstance.retrieve(query);
			ObservableList<Consulta> listaOBS = FXCollections.observableArrayList(lista);

			Table.setItems(listaOBS);
		} 
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

		Table.refresh();

	}

	@FXML
	void procurarPorEnter(KeyEvent event)
	{
		if (event.getCode() == KeyCode.ENTER)
		{
			procurar();
		}
	}


}
