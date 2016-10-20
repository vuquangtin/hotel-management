package com.gsmart.ui.components;

import java.text.NumberFormat;

import com.gsmart.model.Orders;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CalculatePane extends VBox {

	private Button paymentBtn = new Button();
	private CheckBox printInvoicesCheckBox = new CheckBox("Print invoices");

	private TextField totalPriceTxt = new TextField();
	private TextField promotionPercentTxt = new TextField();
	private TextField paymentPriceTxt = new TextField();
	private TextField customerSentPriceTxt = new TextField();
	private TextField changePriceTxt = new TextField();
	private Orders orders;

	public CalculatePane() {
		super();
		setPrefWidth(310);
		setSpacing(10);
		getStyleClass().add("card");

		getChildren().add(getTopBar());
		getChildren().add(getContent());

		// Setup event handler for text field.
		addEventHandler();
	}

	public void setCalculatorInformation(Orders order) {
		this.orders = order;
		this.promotionPercentTxt.setText(NumberFormat.getNumberInstance().format(order.getPromotion()));
		this.totalPriceTxt.setText(NumberFormat.getNumberInstance().format(order.getTotalPrice()));
		Double paymentPrice = (order.getTotalPrice() * (1 - order.getPromotion()));
		this.paymentPriceTxt.setText(NumberFormat.getNumberInstance().format(paymentPrice));
	}

	public HBox getTopBar() {
		HBox hb = new HBox();
		VBox vb = new VBox();

		Label label = new Label("Payment");
		label.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.CASH_USD, "1.2em"));
		label.getStyleClass().add("card-title");

		vb.setPrefWidth(160);
		vb.getChildren().add(label);
		vb.getChildren().add(printInvoicesCheckBox);
		// Set image for payment button.
		paymentBtn.getStyleClass().add("button-raised");
		paymentBtn.setDefaultButton(true);
		paymentBtn.setMnemonicParsing(false);
		paymentBtn.setText("Payment");
		paymentBtn.setPadding(new Insets(10));
		paymentBtn.setFont(new Font("Material-Design-Iconic-Font", 12));
		Text materialDesignIcon = GlyphsDude.createIcon(MaterialDesignIcon.CALCULATOR, "2.5em");
		paymentBtn.setGraphic(materialDesignIcon);
		materialDesignIcon.setFill(Color.WHITE);
		hb.getChildren().add(vb);
		hb.getChildren().add(paymentBtn);
		return hb;
	}

	public VBox getContent() {
		VBox vb = new VBox();
		vb.setSpacing(2);

		vb.getChildren().add(getRowField("Total Price", totalPriceTxt, " VNĐ", "#ff0000"));
		vb.getChildren().add(getRowField("Promotion Percent ", promotionPercentTxt, " VNĐ", "#ff0000"));
		vb.getChildren().add(getRowField("Payment Price", paymentPriceTxt, " VNĐ", "#ff0000"));
		vb.getChildren().add(getRowField("Customer Sent", customerSentPriceTxt, " VNĐ", "#6600ff"));
		vb.getChildren().add(getRowField("Customer Change", changePriceTxt, " VNĐ", "#99ff66"));

		return vb;
	}

	public HBox getRowField(String label, TextField textField, String unitName, String textColor) {
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

	public void addEventHandler() {
		this.customerSentPriceTxt.textProperty().addListener((observable, oldValue, newValue) -> {
			// We need check it because user can edit that text field when user
			// not yet select the order.
			if (!newValue.isEmpty() && this.orders != null) {
				Double customerSent = Double.valueOf(newValue);

				Double value = (customerSent - orders.getTotalPrice() * (1 - orders.getPromotion()));
				this.changePriceTxt.setText(NumberFormat.getNumberInstance().format(value));
			}
		});
	}
}
