package application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.gsmart.ui.components.FXMLDialog;
import com.gsmart.ui.controller.HomeController;
import com.gsmart.ui.controller.OrderRoomController;

import javafx.scene.Parent;
import javafx.scene.Scene;
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
	
	@Bean
	@Scope("prototype")
	public OrderRoomController orderRoomController() {
		return new OrderRoomController();
	}
	
	
	@Bean
    @Scope("prototype")
    public FXMLDialog orderRoomDialog() {
        return new FXMLDialog(orderRoomController(), getClass().getResource("/com/gsmart/ui/components/Test2.fxml"), primaryStage);
    }
	
	@Bean
    @Scope("singleton")
	public HomeController homeController() {
        return new HomeController();
    }
	
	@Bean
    @Scope("prototype")
	public FXMLDialog homeDialog() {
        return new FXMLDialog(homeController(), getClass().getResource("/com/gsmart/ui/components/HelloWorld.fxml"), primaryStage);
    }
}
