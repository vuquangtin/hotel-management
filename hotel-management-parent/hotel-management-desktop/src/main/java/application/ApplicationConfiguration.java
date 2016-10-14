package application;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.gsmart.ui.components.FXMLDialog;
import com.gsmart.ui.components.OrderTablePane;
import com.gsmart.ui.controller.HomeController;
import com.gsmart.ui.controller.OrderRoomController;
import com.gsmart.ui.controller.QuickSearchRoomController;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Lazy
@Configuration
@ComponentScan(basePackages = {"com.gsmart"})
public class ApplicationConfiguration {
	
	private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showScreen(Parent screen) {
        primaryStage.setScene(new Scene(screen, 777, 500));
        primaryStage.show();
    }
    
    
    @Bean( name = "validationMessageSource" )
    public ReloadableResourceBundleMessageSource validationMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:validation-messages/ValidationMessages_en_US");
        messageSource.setCacheSeconds(10); // reload messages every 10 seconds
        messageSource.setDefaultEncoding("UTF-8");
    
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
    @Scope("prototype")
    public FXMLDialog quickSearchRoomDialog() {
        return new FXMLDialog(quickSearchRoomController(), getClass()
        		.getResource("/com/gsmart/ui/components/QuickSearchRoomStage.fxml"), orderRoomDialog()
        		, new String[]{"css/quick-search-room.css","css/application.css" , "css/material-fx-v0_3.css"}, Modality.APPLICATION_MODAL);
    }
	
	@Bean
    public FXMLDialog orderRoomDialog() {
        return new FXMLDialog(orderRoomController(), getClass()
        		.getResource("/com/gsmart/ui/components/OrderRoomStage.fxml"), primaryStage
        		, new String[]{"css/order-room-stage.css","css/application.css" , "css/material-fx-v0_3.css"}, Modality.APPLICATION_MODAL);
    }
	
	@Bean
    @Scope("singleton")
	public HomeController homeController() {
        return new HomeController();
    }
	
	@Bean
    @Scope("prototype")
	public FXMLDialog homeDialog() {
        return new FXMLDialog(homeController(), getClass().getResource("/com/gsmart/ui/components/Home.fxml")
        		, primaryStage , new String[]{"css/home.css" , "css/order-info.css" , "css/order-table-pane.css" , "css/material-fx-v0_3.css"
        				,"css/application.css"} );
    }
}
