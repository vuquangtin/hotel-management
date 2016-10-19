package com.gsmart.ui.components;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import com.gsmart.ui.controller.DialogController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Callback;

public class FXMLDialog extends Stage {
	
	public FXMLDialog(DialogController controller, URL fxml, Window owner , String[] css , Modality modality) {
        this(controller, fxml, owner, StageStyle.DECORATED , css , modality);
    }
	
	public FXMLDialog(DialogController controller, URL fxml, Window owner , String[] css) {
        this(controller, fxml, owner, StageStyle.DECORATED , css , null);
    }
	
	public FXMLDialog(DialogController controller, URL fxml, Window owner , Modality modality) {
        this(controller, fxml, owner, StageStyle.DECORATED , null , modality);
    }
	
    public FXMLDialog(DialogController controller, URL fxml, Window owner) {
        this(controller, fxml, owner, StageStyle.DECORATED , null , null);
    }

    public FXMLDialog(final DialogController controller, URL fxml, Window owner, StageStyle style , String[] css , Modality modality) {
        super(style);
   
		ResourceBundle bundle = ResourceBundle.getBundle("com.gsmart.ui.components.locale.messages",
				new Locale("vi", "VN"));
		
        initOwner(owner);
        initModality(modality);
        
        getIcons().add(new Image(getClass().getResource("/com/gsmart/ui/components/images/logo.png").toExternalForm()));
        setTitle("Hotel Management - Version 1.0.0");
        
        FXMLLoader loader = new FXMLLoader(fxml, bundle);
        try {
            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> aClass) {
                    return controller;
                }
            });
            controller.setDialog(this);
            
            Scene scene  = new Scene((Parent) loader.load());
            if(css != null) {
            	for(String url : css) {
            		scene.getStylesheets().add(getClass().getResource(url).toString());
            	}
            }
            
            setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}