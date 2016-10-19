package com.gsmart.ui.components;

import com.gsmart.model.Orders;
import com.gsmart.model.Room;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RoomInfoPane extends VBox{
		
	private TextField roomNameTxt = new TextField();
	private TextField priceTxt = new TextField();
	private TextField acreageTxt = new TextField();
	private TextField roomTypeTxt = new TextField();
	private TextField capacityTxt = new TextField();
	
	public RoomInfoPane() {
		super();
		setPrefWidth(320);
		getStyleClass().add("card");
		getChildren().add(getTopBar());
		getChildren().add(getContent());
	}
	
	public HBox getTopBar() {
		HBox hb = new HBox();
		//Set Style CSS.
		Label label = new Label("Room Information");
		label.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.FILE_DOCUMENT, "1.2em"));
		label.getStyleClass().add("card-title");
		hb.getChildren().add(label);
		return hb;
	}
	
	public void setRoomInformation(Orders order) {
		Room room = order.getRoom();
		
		roomNameTxt.setText(room.getName());
		roomTypeTxt.setText(room.getRoomCategory().getName());
		acreageTxt.setText(room.getAcreage());
		priceTxt.setText(room.getRoomCategory().getPrice().toString());
		capacityTxt.setText(room.getRoomCategory().getCapacity().toString());
	}
	
	public VBox getContent() {
		VBox vb = new VBox();
		vb.setSpacing(2);
		//vb.setPadding(new Insets(10, 10 , 10 , 10));
		
		vb.getChildren().add(getRowField("Room Name ", roomNameTxt, "", "#000000"));
		vb.getChildren().add(getRowField("Price", priceTxt, " /Date" , "#000000" ));
		vb.getChildren().add(getRowField("Acreage", acreageTxt, " m²" , "#000000"));
		vb.getChildren().add(getRowField("Room Type ", roomTypeTxt, "", "#000000"));
		vb.getChildren().add(getRowField("Capacity", capacityTxt, " Person", "#000000"));
		
		return vb;
	}
	
	public HBox getRowField(String label, TextField textField, String unitName , String textColor){
		Label _label = new Label(label);
		Label _unitName = new Label(unitName);
		_label.setPrefWidth(100);
		_label.getStyleClass().add("control-label");
		_unitName.getStyleClass().add("control-label");
		
		textField.setStyle("-fx-text-fill:" + textColor + ";");
		textField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		
		HBox hb = new HBox();
	
		hb.getChildren().add(_label);
		hb.getChildren().add(textField);
		hb.getChildren().add(_unitName);
		
		return hb;
	}
}
