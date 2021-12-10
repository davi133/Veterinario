package view.Especie;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Cliente;
import model.ClienteDAO;
import model.Especie;
import model.EspecieDAO;
import view.Criterio;
import view.SelecionadosController;
import view.Cliente.FichaCliente;

public class EspecieController implements Initializable {

	private SelecionadosController selecionadosController;
	public void InjectSelecionadosController(SelecionadosController sc) 
	{
		selecionadosController = sc;
	}
	public SelecionadosController getSelectCtrl() {
		return selecionadosController;
	}
	public TableView<Especie> getTable() {
		return Table;
	}
	
	@FXML private TableView<Especie> Table;
	@FXML private TableColumn<Especie, Integer> table_id;
	@FXML private TableColumn<Especie, String> table_nome;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		table_id.setCellValueFactory(new PropertyValueFactory<Especie, Integer>("id"));
		table_nome.setCellValueFactory(new PropertyValueFactory<Especie, String>("nome"));

		ObservableList<Especie> lista = FXCollections
				.observableArrayList(EspecieDAO.getInstance().retrieveAll(limitePadrao));
		Table.setItems(lista);

		Table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			selecionadosController.setEspecie(newValue);
			btnEdit.setDisable(newValue == null);
			btnDelete.setDisable(newValue == null);
		});

		txtLimite.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && !newValue.matches("\\d*")) {
				txtLimite.setText(oldValue);
			} else if (newValue != null && !newValue.isBlank() && Integer.parseInt(newValue) > 1000) {
				txtLimite.setText("" + 1000);
			}

		});

	}

	public boolean Excluir(Especie especie) 
	{
		if (especie != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Deseja mesmo deletar esse registro?");
			alert.setContentText("Deletar " + especie.getNome() + "(id: " + especie.getId() + ")?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {

				if (selecionadosController.getEspecie() != null
						&& selecionadosController.getEspecie().getId() == especie.getId())
					selecionadosController.setEspecie(null);

				EspecieDAO.getInstance().delete(especie);
				Table.getItems().remove(especie);
				Table.refresh();

				return true;
			}

			return false;
		} else {
			return true;
		}
	}

	// BOTOES
	@FXML private Button btnDelete;
	@FXML private Button btnEdit;
	@FXML private Button btnNew;

	@FXML private void novoRegistro() 
	{
		FichaEspecie fc = new FichaEspecie(null, this);
		fc.Show("nova especie");
	}

	@FXML private void excluirSelecionado() 
	{
		Excluir(selecionadosController.getEspecie());
	}

	@FXML private void editarSelecionado() 
	{
		
		if (selecionadosController.getEspecie() == null)
			return;
		FichaEspecie fc = new FichaEspecie(selecionadosController.getEspecie(), this);
		fc.Show("Editar Cliente");
		
	}

	// BUSCA
	@FXML private Button btnSearch;
	@FXML private TextField txtFSearch;
	@FXML private TextField txtLimite;
	private int limitePadrao = 10;

	@FXML private void procurar() 
	{

		EspecieDAO DaoInstance = EspecieDAO.getInstance();

		String oTexto = txtFSearch.getText();
		String limite = txtLimite.getText().isBlank() ? "" + limitePadrao : txtLimite.getText();

		ObservableList<Especie> listaOBS;
		if (onlyDigits(oTexto)) {
			listaOBS = FXCollections.observableArrayList(DaoInstance.retrieveByID(Integer.valueOf(oTexto)));
		} else {
			listaOBS = FXCollections
					.observableArrayList(DaoInstance.retrieveBySimilarName(oTexto, Integer.valueOf(limite)));
		}

		Table.setItems(listaOBS);
		Table.refresh();
	}

	@FXML void procurarPorEnter(KeyEvent event) 
	{
		if (event.getCode() == KeyCode.ENTER) {
			procurar();
		}
	}

	// função tirada de
	// https://www.geeksforgeeks.org/how-to-check-if-string-contains-only-digits-in-java/
	private static boolean onlyDigits(String str) 
	{
		// Regex to check string
		// contains only digits
		String regex = "[0-9]+";

		// Compile the ReGex
		Pattern p = Pattern.compile(regex);

		// If the string is empty
		// return false
		if (str == null || str.isBlank()) {
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
