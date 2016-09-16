package application;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.gsmart.business.HelloFromBussiness;
import com.gsmart.config.ConfigurationDAO;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


@SpringBootApplication
@ContextConfiguration(classes = { ConfigurationDAO.class , ApplicationConfiguration.class})
public class Main extends Application{
	
	ApplicationConfiguration applicationConfiguration;

	private static String[] savedArgs;
	private static FXMLLoader loader;

	public static ConfigurableApplicationContext applicationContext;
	
	public static ConfigurableApplicationContext getConfigurableApplicationContext() {
		return applicationContext;
	}
	
	public static FXMLLoader getFXMLLoader() {
		return loader;
	}

	@Override
	public void init() throws Exception {
		applicationContext = SpringApplication.run(getClass(), savedArgs);
		
		
		applicationConfiguration = applicationContext.getBean(ApplicationConfiguration.class);
		
//		ResourceBundle bundle = ResourceBundle.getBundle("com.gsmart.ui.components.locale.messages",
//				new Locale("vi", "VN"));
//		
//		loader = new FXMLLoader(getClass().getResource("/com/gsmart/ui/components/HelloWorld.fxml"), bundle);
//		loader.setControllerFactory(applicationContext::getBean);
		
		//applicationContext.getAutowireCapableBeanFactory().autowireBean(OrderRoomController.class);
	}

	@Override
	public void stop() throws Exception {

		super.stop();
		applicationContext.close();
	}

	static void launchApp(Class<? extends Application> appClass, String[] args) {

		Main.savedArgs = args;
		Application.launch(appClass, args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			new HelloFromBussiness();
	
//			Parent root = loader.load();
//			root.getStylesheets().add("/com/gsmart/ui/components/application.css");
//
//			Scene scene = new Scene(root, 400, 400);
//			primaryStage.setScene(scene);
//			primaryStage.show();
			applicationConfiguration.setPrimaryStage(primaryStage);
			applicationConfiguration.homeDialog().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launchApp(Main.class, args);
	}

}
