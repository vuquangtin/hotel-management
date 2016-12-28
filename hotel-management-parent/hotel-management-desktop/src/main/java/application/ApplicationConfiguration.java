package application;

import javax.annotation.PostConstruct;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.gsmart.repository.UserSettingRepository;
import com.gsmart.ui.components.FXMLDialog;
import com.gsmart.ui.components.OrderTablePane;
import com.gsmart.ui.controller.HomeController;
import com.gsmart.ui.controller.OrderRoomController;
import com.gsmart.ui.controller.QuickSearchRoomController;
import com.gsmart.ui.controller.ReportManagedController;
import com.gsmart.ui.controller.SettingStageController;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Lazy
@Configuration
@ComponentScan(basePackages = { "com.gsmart" })
public class ApplicationConfiguration {

	private final String[] cssFiles = { "css/home.css", "css/order-info.css", "css/order-table-pane.css",
			"css/material-fx-v0_3.css", "css/application.css", "css/quick-search-room.css",
			"css/order-room-stage.css" , "css/report-selection-stage.css" };

	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void showScreen(Parent screen) {
		primaryStage.setScene(new Scene(screen, 777, 500));
		primaryStage.show();
	}
	
	@PostConstruct
	public void initApplicationSetting() {
		UserSettingRepository userSettingRepo = new UserSettingRepository();
		//Set user setting for application.
		ApplicationSetting.setUserSetting(userSettingRepo.getUserSetting());
	}

	@Bean(name = "validationMessageSource")
	public ReloadableResourceBundleMessageSource validationMessageSource() {
		String locale = ApplicationSetting.getUserSetting().getLocale();
		if(locale == null || locale.isEmpty())
			locale = "en_US";
		
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:validation-messages/ValidationMessages_" + locale);
		messageSource.setCacheSeconds(10); // reload messages every 10 seconds
		messageSource.setDefaultEncoding("UTF-8");
		
		//Hold message source for change when user selected other language.
		ApplicationSetting.setMessageSource(messageSource);
		return messageSource;
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource((MessageSource) validationMessageSource());
		return validator;
	}

	@Bean
	public OrderTablePane orderTablePane() {
		return new OrderTablePane();
	}
	
	@Bean
	public SettingStageController SettingStageController() {
		return new SettingStageController();
	}

	@Bean
	@Scope("singleton")
	public OrderRoomController orderRoomController() {
		return new OrderRoomController();
	}

	@Bean
	@Scope("singleton")
	public QuickSearchRoomController quickSearchRoomController() {
		return new QuickSearchRoomController();
	}
	
	@Bean
	@Scope("singleton")
	public ReportManagedController ReportManagedController() {
		return new ReportManagedController();
	}
	
	@Bean
	@Scope("prototype")
	public FXMLDialog SettingStageDialog() {
		return new FXMLDialog(SettingStageController(),
				getClass().getResource("/com/gsmart/ui/components/SettingStage.fxml"), homeDialog(),
				cssFiles, Modality.APPLICATION_MODAL);
	}
	
	@Bean
	@Scope("prototype")
	public FXMLDialog reportSelectionManagedDialog() {
		return new FXMLDialog(ReportManagedController(),
				getClass().getResource("/com/gsmart/ui/components/ReportSelectionStage.fxml"), primaryStage,
				cssFiles, Modality.WINDOW_MODAL);
	}

	@Bean
	@Scope("singleton")
	public FXMLDialog quickSearchRoomDialog() {
		return new FXMLDialog(quickSearchRoomController(),
				getClass().getResource("/com/gsmart/ui/components/QuickSearchRoomStage.fxml"), orderRoomDialog(),
				cssFiles, Modality.APPLICATION_MODAL);
	}

	@Bean
	@Scope("singleton")
	public FXMLDialog orderRoomDialog() {
		return new FXMLDialog(orderRoomController(),
				getClass().getResource("/com/gsmart/ui/components/OrderRoomStage.fxml"), primaryStage, cssFiles,
				Modality.APPLICATION_MODAL);
	}

	@Bean
	@Scope("singleton")
	public HomeController homeController() {
		return new HomeController();
	}

	@Bean
	@Scope("singleton")
	public FXMLDialog homeDialog() {
		return new FXMLDialog(homeController(), getClass().getResource("/com/gsmart/ui/components/Home.fxml"),
				primaryStage, cssFiles, Modality.WINDOW_MODAL);
	}
}
