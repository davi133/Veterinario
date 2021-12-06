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
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Animal;

public class AnimalController implements Initializable{

	@FXML private TableView<Animal> table;
	

	@FXML private void AnimalSheet()
    {		
			Stage stage = new Stage();
			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("ViewNewAnimal.fxml"));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			Scene scene = new Scene(root);
			stage.setTitle("Novo Animal");
			stage.setScene(scene);
			
			stage.show();
    }
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}

