package application;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.gsmart.business.HelloFromBussiness;
import com.gsmart.config.ConfigurationDAO;
import com.gsmart.service.config.ServiceModuleConfiguration;

import javafx.application.Application;
import javafx.stage.Stage;

@SpringBootApplication
@ContextConfiguration(classes = { ConfigurationDAO.class, ApplicationConfiguration.class,
		ServiceModuleConfiguration.class })
public class Main extends Application {

	ApplicationConfiguration applicationConfiguration;

	private static String[] savedArgs;
	public static ConfigurableApplicationContext applicationContext;

	private JFrame loadingFrame;

	public void showLoadingFrame() {
		loadingFrame = new JFrame();
		loadingFrame.setUndecorated(true);
		loadingFrame.setSize(400, 400);

		Dimension screenSize, frameSize;
		int x, y;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frameSize = loadingFrame.getSize();
		x = (screenSize.width - frameSize.width) / 2;
		y = (screenSize.height - frameSize.height) / 2;
		loadingFrame.setLocation(x, y);

		loadingFrame.setVisible(true);
	}

	public void closeLoadingFrame() {
		loadingFrame.dispose();
	}

	@Autowired
	public void injectTetingData(InjectTestingData injectTestingData) {
		// TODO : Remove it on production version.
		// injectTestingData.doInject();

		// try {
		// injectTestingData.preparedDataForTestQuickSearchRoom();
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	@Override
	public void init() throws Exception {
		showLoadingFrame();
		applicationContext = SpringApplication.run(getClass(), savedArgs);
		applicationConfiguration = applicationContext.getBean(ApplicationConfiguration.class);
		closeLoadingFrame();
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
