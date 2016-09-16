package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.gsmart.business.HelloFromBussiness;
import com.gsmart.config.ConfigurationDAO;

import javafx.application.Application;
import javafx.stage.Stage;


@SpringBootApplication
@ContextConfiguration(classes = { ConfigurationDAO.class , ApplicationConfiguration.class})
public class Main extends Application{
	
	ApplicationConfiguration applicationConfiguration;

	private static String[] savedArgs;
	public static ConfigurableApplicationContext applicationContext;

	@Override
	public void init() throws Exception {
		applicationContext = SpringApplication.run(getClass(), savedArgs);
		applicationConfiguration = applicationContext.getBean(ApplicationConfiguration.class);
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
