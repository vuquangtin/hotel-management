package com.gsmart.ui.components;

import java.util.ResourceBundle;

import com.gsmart.model.Orders;
import com.gsmart.model.Room;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RoomInfoPane extends VBox {
	
	private Label paneLabel = new Label();
	
	private Label roomNameLbl = new Label();
	private Label priceLbl = new Label();
	private Label acreageLbl = new Label();
	private Label roomTypeLbl = new Label();
	private Label capacityLbl = new Label();

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
		// Set Style CSS.
		paneLabel.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.FILE_DOCUMENT, "1.2em"));
		paneLabel.getStyleClass().add("card-title");
		hb.getChildren().add(paneLabel);
		return hb;
	}

	public void setRoomInformation(Orders order) {
		Room room = order.getRoom();
		
		if (room != null) {
			roomNameTxt.setText(room.getName());
			roomTypeTxt.setText(room.getRoomCategory().getName());
			acreageTxt.setText(room.getAcreage());
			priceTxt.setText(room.getRoomCategory().getPrice().toString());
			capacityTxt.setText(room.getRoomCategory().getCapacity().toString());
		}
	}

	public VBox getContent() {
		VBox vb = new VBox();
		vb.setSpacing(2);

		vb.getChildren().add(getRowField(roomNameLbl, roomNameTxt, "", "#000000"));
		vb.getChildren().add(getRowField(priceLbl, priceTxt, " /Date", "#000000"));
		vb.getChildren().add(getRowField(acreageLbl, acreageTxt, " m²", "#000000"));
		vb.getChildren().add(getRowField(roomTypeLbl, roomTypeTxt, "", "#000000"));
		vb.getChildren().add(getRowField(capacityLbl, capacityTxt, " Person", "#000000"));

		return vb;
	}

	public HBox getRowField(Label label, TextField textField, String unitName, String textColor) {
		Label _unitName = new Label(unitName);
		label.setPrefWidth(100);

		label.getStyleClass().add("control-label");
		_unitName.getStyleClass().add("control-label");

		textField.setStyle("-fx-text-fill:" + textColor + ";");
		textField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		HBox hb = new HBox();

		hb.getChildren().add(label);
		hb.getChildren().add(textField);
		hb.getChildren().add(_unitName);

		return hb;
	}
	
	/**
	 * Used for render form fields name for multiple languages.
	 * <P>
	 * 
	 * @param resource
	 *            - Resource Bundle file
	 */
	public void initFormFieldName(ResourceBundle resource) {
		paneLabel.setText(resource.getString("UIControls.RoomInfoPane"));
		roomNameLbl.setText(resource.getString("UIControls.RoomInfoPane.RoomName"));
		priceLbl.setText(resource.getString("UIControls.RoomInfoPane.Price"));
		acreageLbl.setText(resource.getString("UIControls.RoomInfoPane.Acreage"));
		roomTypeLbl.setText(resource.getString("UIControls.RoomInfoPane.RoomType"));
		capacityLbl.setText(resource.getString("UIControls.RoomInfoPane.Capacity"));
	}
}
