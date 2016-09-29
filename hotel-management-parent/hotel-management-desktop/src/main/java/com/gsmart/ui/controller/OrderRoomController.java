package com.gsmart.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gsmart.model.Orders;
import com.gsmart.ui.components.FXMLDialog;
import com.gsmart.ui.components.RoomOrderTable;

import application.ApplicationConfiguration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;

@Component
public class OrderRoomController implements DialogController {

	private FXMLDialog dialog;

	@Autowired
	private ApplicationConfiguration applicationConfiguration;

	// @Autowired
	// private HomeController homeController;

	private Orders order;

	@FXML
	RoomOrderTable roomOrderTable;

	@FXML
	Label titleStage;

	@FXML
	DatePicker dateIn;

	@FXML
	TextField timeInTxt;

	@FXML
	DatePicker dateCheckout;

	@FXML
	TextField timeOutTxt;

	@FXML
	TextField prepayTxt;

	@FXML
	TextField promotionTxt;

	@FXML
	TextArea registrationNotice;

	@FXML
	TextField roomTxt;

	@FXML
	TextField customerNameTxt;

	@FXML
	TextField customerIDTxt;

	@FXML
	TextField customerAddress;

	@FXML
	TextField customerTelephone;

	@FXML
	TextArea customerNotice;

	@FXML
	Button saveBtn;

	@FXML
	Button resetBtn;

	@FXML
	public void clicked(ActionEvent event) {
		this.dialog.close();
	}

	public void setOrderInformation(Orders order) {
		this.order = order;
		updateViewComponent();
	}

	public void updateViewComponent() {
		if (this.order != null) {
			// TODO : Set Date in and Date out for date time picker.

			titleStage.setText("Update Order Information");
			timeInTxt.setText(this.order.getCreatedAt().toString());
			timeOutTxt.setText(this.order.getCheckOutAt().toString());
			prepayTxt.setText(this.order.getPrepay().toString());
			promotionTxt.setText(this.order.getPromotion().toString());
			registrationNotice.setText(this.order.getDescription());
			roomTxt.setText(this.order.getRoomName());
			customerNameTxt.setText(this.order.getCustomerName());
			customerIDTxt.setText(this.order.getCustomerId());
			customerAddress.setText(this.order.getCustomerAddress());
			customerTelephone.setText(this.order.getCustomerTelephone());
			customerNotice.setText(this.order.getCustomerNotice());
		}
	}

	@Override
	public void setDialog(FXMLDialog dialog) {
		this.dialog = dialog;
	}

	@FXML
	public void openQuickSeachRoomStage(ActionEvent event) {
		applicationConfiguration.quickSearchRoomDialog().show();
	}
}
