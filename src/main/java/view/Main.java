package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Animal;
import model.AnimalDAO;

public class Main extends Application {

	
	
	public void start(Stage primaryStage) {
		try
		{
			Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
			
			Scene scene = new Scene(root);
			primaryStage.setTitle("VetSys");
			
			primaryStage.setScene(scene);
			primaryStage.show();
	
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		Application.launch(args);
		
		/*AnimalDAO adao = AnimalDAO.getInstance();
		System.out.println(adao.retrieveAll());
		Animal an=adao.retrieveByID(1);
		adao.delete(an);
		System.out.println(adao.retrieveAll());*/
	}
}









