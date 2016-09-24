package com.gsmart.ui.components;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import com.gsmart.ui.controller.DialogController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Callback;

public class FXMLDialog extends Stage {
	
	public FXMLDialog(DialogController controller, URL fxml, Window owner , String[] css) {
        this(controller, fxml, owner, StageStyle.DECORATED , css);
    }
	
    public FXMLDialog(DialogController controller, URL fxml, Window owner) {
        this(controller, fxml, owner, StageStyle.DECORATED , null);
    }

    public FXMLDialog(final DialogController controller, URL fxml, Window owner, StageStyle style , String[] css) {
        super(style);
        
        setAlwaysOnTop(true);
		ResourceBundle bundle = ResourceBundle.getBundle("com.gsmart.ui.components.locale.messages",
				new Locale("vi", "VN"));
		
        initOwner(owner);
        initModality(Modality.WINDOW_MODAL);
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