package application;
	
import java.util.Locale;
import java.util.ResourceBundle;

import com.gsmart.business.HelloFromBussiness;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			new HelloFromBussiness();
			ResourceBundle bundle = ResourceBundle.getBundle("com.gsmart.ui.components.locale.messages" , new Locale("vi", "VN"));
			Parent root = FXMLLoader.load(getClass().getResource("/com/gsmart/ui/components/HelloWorld.fxml"), bundle);
			root.getStylesheets().add("/com/gsmart/ui/components/application.css");
			
			Scene scene = new Scene(root,400,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
