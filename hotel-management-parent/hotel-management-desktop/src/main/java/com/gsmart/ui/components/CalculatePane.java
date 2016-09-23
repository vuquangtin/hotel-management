package com.gsmart.ui.components;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CalculatePane extends VBox{
	
	private Button paymentBtn = new Button();
	private CheckBox printInvoicesCheckBox = new CheckBox("Print invoices");
	
	private TextField totalPriceTxt = new TextField();
	private TextField promotionPercentTxt = new TextField();
	private TextField paymentPriceTxt = new TextField();
	private TextField customerSentPriceTxt = new TextField();
	private TextField changePriceTxt = new TextField();
	
	public CalculatePane() {
		super();
		setPrefWidth(300);
		setSpacing(10);
		getStyleClass().add("content-block-background");
		
		getChildren().add(getTopBar());
		getChildren().add(getContent());
	}
	
	public HBox getTopBar() {
		HBox hb = new HBox();
		VBox vb = new VBox();
		//Set Style CSS.
		hb.getStyleClass().add("top-bar");
		printInvoicesCheckBox.setStyle("-fx-padding: 0 0 0 8;");
		hb.setPadding(new Insets(5, 5, 5, 5));
		
		Label label = new Label("Payment Room");
		label.getStyleClass().add("header-label");
		vb.setPrefWidth(160);
		vb.getChildren().add(label);
		vb.getChildren().add(printInvoicesCheckBox);
		
		//Set image for payment button.
		paymentBtn.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-weight: bold;");
		
		ImageView buttonImage = new ImageView(new Image("com/gsmart/ui/components/images/payment-button.png"));
		buttonImage.setFitWidth(75);
		buttonImage.setFitHeight(50);
		
		paymentBtn.setText("Payment");
		paymentBtn.setGraphic(buttonImage);
		
		hb.getChildren().add(vb);
		hb.getChildren().add(paymentBtn);
		
		return hb;
	}
	
	public VBox getContent() {
		VBox vb = new VBox();
		vb.setSpacing(10);
		vb.setPadding(new Insets(10, 10 , 10 , 10));
		
		vb.getChildren().add(getRowField("Total Price", totalPriceTxt, "VNĐ" , "#ff0000" ));
		vb.getChildren().add(getRowField("Promotion Percent", promotionPercentTxt, "VNĐ" , "#ff0000"));
		vb.getChildren().add(getRowField("Payment Price", paymentPriceTxt, "VNĐ", "#ff0000"));
		vb.getChildren().add(getRowField("Customer Sent", customerSentPriceTxt, "VNĐ", "#6600ff"));
		vb.getChildren().add(getRowField("Customer Change", changePriceTxt, "VNĐ", "#99ff66"));
		
		return vb;
	}
	
	public HBox getRowField(String label, TextField textField, String unitName , String textColor){
		Label _label = new Label(label);
		_label.setPrefWidth(100);
		
		Label _unitName = new Label(unitName);
		
		textField.setStyle("-fx-text-fill:" + textColor + ";");
		textField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		
		HBox hb = new HBox();
		hb.setSpacing(5);
		
		hb.getChildren().add(_label);
		hb.getChildren().add(textField);
		hb.getChildren().add(_unitName);
		
		return hb;
	}
}
