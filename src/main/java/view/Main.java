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

/*
 * @Override
	public void start(Stage primaryStage) {
		Group root = new Group();
		Scene scene = new Scene(root,600,420,Color.LIGHTBLUE);
		Stage stage = new Stage();
		
		stage.setTitle("Primeiro progerama");
		Image icon = new Image("C:\\Users\\Davi\\davi\\Estudo\\SI401 Prog Web\\Campo minado\\icon.png");
		stage.setY(50);
		stage.setX(50);
		
		Text texto = new Text("whooa");
		texto.setY(60);
		texto.setX(60);
		texto.setFont(Font.font(20f));
		texto.setFill(Color.hsb(1f,1f,1f));
		
		root.getChildren().add(texto);
		
		Line linha = new Line();
		
		
		stage.getIcons().add(icon);
		stage.setScene(scene);
		stage.show();
		
	}
 * 
 * */







