package com.gsmart.ui.components;

import java.text.NumberFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.gsmart.model.Orders;
import com.gsmart.repository.OrdersRepository;
import com.gsmart.ui.controller.HomeController;
import com.gsmart.ui.utils.FXDialogController;
import com.gsmart.ui.utils.ReportController;

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

@Component
public class CalculatePane extends VBox {

	private OrdersRepository ordersRepository;
	private HomeController homeController;

	private Button paymentBtn = new Button();
	private CheckBox printInvoicesCheckBox = new CheckBox("Print invoices");
	
	private Label paneLabel = new Label();

	private Label totalPriceLbl = new Label();
	private Label promotionPercentLbl = new Label();
	private Label paymentPriceLbl = new Label(); 
	private Label customerSentPriceLbl = new Label();
	private Label changePriceLbl = new Label();

	private TextField totalPriceTxt = new TextField();
	private TextField promotionPercentTxt = new TextField();
	private TextField paymentPriceTxt = new TextField();
	private TextField customerSentPriceTxt = new TextField();
	private TextField changePriceTxt = new TextField();
	private Orders orders;

	public CalculatePane() {
		super();
		setPrefWidth(310);
		setPrefHeight(305);
		setSpacing(10);
		getStyleClass().add("card");

		getChildren().add(getTopBar());
		getChildren().add(getContent());

		// Setup event handler for components.
		addEventHandler();
	}

	/**
	 * Used for render information of selected order to form.
	 * <p>
	 * 
	 * @param order
	 *            - Selected order from user.
	 */
	public void setCalculatorInformation(Orders order) {
		this.orders = order;
		this.promotionPercentTxt.setText(NumberFormat.getNumberInstance().format(order.getPromotion()));
		this.totalPriceTxt.setText(NumberFormat.getNumberInstance().format(order.getTotalPrice()));
		if (order.getPrepay() == null)
			orders.setPrepay(0.0);

		Double paymentPrice = (order.getTotalPrice() * (1 - order.getPromotion())) - order.getPrepay();
		this.paymentPriceTxt.setText(NumberFormat.getNumberInstance().format(paymentPrice));

		// Checking for render payment button.

		// If order status equal 0 . it mean this order still not received.
		if (order.getStatus() == 0)
			this.paymentBtn.setDisable(true);
		// Of status equal 2. It mean this order has paid. We must remove
		// payment button.
		else if (order.getStatus() == 2)
			this.paymentBtn.setVisible(false);
		// Else if status equal 1. It mean order is activity.
		else {
			this.paymentBtn.setDisable(false);
			this.paymentBtn.setVisible(true);
		}

	}

	public HBox getTopBar() {
		HBox hb = new HBox();
		VBox vb = new VBox();

		paneLabel.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.CASH_USD, "1.4em"));
		paneLabel.getStyleClass().add("card-title");

		vb.setPrefWidth(160);
		vb.getChildren().add(paneLabel);
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

		vb.getChildren().add(getRowField(totalPriceLbl, totalPriceTxt, " VNĐ", "#ff0000"));
		vb.getChildren().add(getRowField(promotionPercentLbl, promotionPercentTxt, " VNĐ", "#ff0000"));
		vb.getChildren().add(getRowField(paymentPriceLbl, paymentPriceTxt, " VNĐ", "#ff0000"));
		vb.getChildren().add(getRowField(customerSentPriceLbl, customerSentPriceTxt, " VNĐ", "#6600ff"));
		vb.getChildren().add(getRowField(changePriceLbl, changePriceTxt, " VNĐ", "#99ff66"));

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

	public void addEventHandler() {

		// Default we should print invoice when completed payment.
		printInvoicesCheckBox.setSelected(true);

		customerSentPriceTxt.textProperty().addListener((observable, oldValue, newValue) -> {
			// We need check it because user can edit that text field when user
			// not yet select the order.
			if (!newValue.isEmpty() && this.orders != null) {
				Double customerSent = Double.valueOf(newValue);

				Double value = (customerSent - orders.getTotalPrice() * (1 - orders.getPromotion()));
				this.changePriceTxt.setText(NumberFormat.getNumberInstance().format(value));
			}
		});

		// Set action when user press payment button.
		paymentBtn.setOnAction(event -> {
			onClickPaymentButton();
		});
	}

	public void onClickPaymentButton() {
		if (orders != null) {

			// Check whether user was confirmed this action.
			boolean isComfirm = FXDialogController.showConfirmationDialog("Payment Order",
					"Are you sure to payment this order ?", "Order of customer " + orders.getCustomerName());

			if (isComfirm) {
				// Status equal 2 mean this order has payment completed.
				orders.setStatus(2);
				
				orders.setPaidAt(new Date());
				if (orders.getPrepay() == null)
					orders.setPrepay(0.0);

				orders.setPaymentPrice(orders.getTotalPrice() * (1 - orders.getPromotion()) - orders.getPrepay());

				// Printing invoice if user selected.
				if (printInvoicesCheckBox.isSelected()) {
					ReportController.printInvoice(orders);
				}
				ordersRepository.save(orders);
				homeController.updateOrderTable();
			}
		}
	}

	public void setOrderRepository(OrdersRepository or) {
		this.ordersRepository = or;
	}

	public void setHomeController(HomeController ctrl) {
		this.homeController = ctrl;
	}

	/**
	 * Used for render form fields name for multiple languages.
	 * <P>
	 * 
	 * @param resource
	 *            - Resource Bundle file
	 */
	public void initFormFieldName(ResourceBundle resource) {
		paneLabel.setText(resource.getString("UIControls.CalculatePane"));
		paymentBtn.setText(resource.getString("UIControls.CalculatePane.PaymentButton"));
		printInvoicesCheckBox.setText(resource.getString("UIControls.CalculatePane.PrintInvoice"));
		totalPriceLbl.setText(resource.getString("UIControls.CalculatePane.TotalPrice"));
		promotionPercentLbl.setText(resource.getString("UIControls.CalculatePane.Promotion"));
		paymentPriceLbl.setText(resource.getString("UIControls.CalculatePane.PaymentPrice"));
		customerSentPriceLbl.setText(resource.getString("UIControls.CalculatePane.CustomerSent"));
		changePriceLbl.setText(resource.getString("UIControls.CalculatePane.CustomerChange"));
	}
}
