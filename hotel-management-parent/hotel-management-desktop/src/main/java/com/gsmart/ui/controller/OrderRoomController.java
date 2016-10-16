package com.gsmart.ui.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;

import com.gsmart.model.Orders;
import com.gsmart.model.Room;
import com.gsmart.repository.OrdersRepository;
import com.gsmart.ui.components.FXMLDialog;
import com.gsmart.ui.components.RoomOrderTable;
import com.gsmart.ui.controls.FXDateTimePicker;
import com.gsmart.ui.controls.FXTextField;

import application.ApplicationConfiguration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;

@Component
public class OrderRoomController implements DialogController {

	private FXMLDialog dialog;
	@Autowired
	private ApplicationConfiguration applicationConfiguration;
	@Autowired
	private Validator validator;
	@Autowired
	private HomeController homeController;
	@Autowired
	private OrdersRepository ordersRepository;
	private Orders order;

	@FXML
	RoomOrderTable roomOrderTable;
	@FXML
	Label titleStage;
	@FXML
	FXTextField prepayTxt;
	@FXML
	FXTextField promotionTxt;
	@FXML
	TextArea registrationNotice;
	@FXML
	FXTextField roomTxt;
	@FXML
	FXTextField customerNameTxt;
	@FXML
	FXTextField customerIDTxt;
	@FXML
	FXTextField customerAddress;
	@FXML
	FXTextField customerTelephone;
	@FXML
	TextArea customerNotice;
	@FXML
	Button saveBtn;
	@FXML
	Button resetBtn;
	@FXML
	FXDateTimePicker dateTimePicker;

	@FXML
	public void clicked(ActionEvent event) {
		this.dialog.close();
	}

	public void setOrderInformation(Orders order) {
		this.order = order;
		updateViewComponent();
	}

	public void updateCheckInInformation(Room room, Date timeIn, Date timeCheckOut) {
		roomTxt.setUserData(room);
		roomTxt.setText(room.getName());
		dateTimePicker.setDateTime(timeIn, timeCheckOut);
		this.dateTimePicker.disableDateTimePicker(true);
	}

	public void updateViewComponent() {
		if (this.order != null) {
			titleStage.setText("Update Order Information");

			dateTimePicker.setDateTime(this.order.getCreatedAt(), this.order.getCheckOutAt());
			prepayTxt.setText(this.order.getPrepay().toString());
			promotionTxt.setText(this.order.getPromotion().toString());
			registrationNotice.setText(this.order.getDescription());
			roomTxt.setText(this.order.getRoomName());
			roomTxt.setUserData(this.order.getRoom());
			customerNameTxt.setText(this.order.getCustomerName());
			customerIDTxt.setText(this.order.getCustomerId());
			customerAddress.setText(this.order.getCustomerAddress());
			customerTelephone.setText(this.order.getCustomerTelephone());
			customerNotice.setText(this.order.getCustomerNotice());

			// We need disable date time picker when order already have room
			// because it will safer.
			this.dateTimePicker.disableDateTimePicker(true);
		}
	}

	public boolean isValidOrderInformation() {

		// Firstly , we need to validated date time.
		System.out.println("STATE ::: " + dateTimePicker.isValidDateTime());

		if (!dateTimePicker.isValidDateTime())
			return false;

		BindException errors = new BindException(getOrder(), "orders");
		validator.validate(getOrder(), errors);

		if (errors.getErrorCount() != 0) {
			for (FXTextField textField : getListFXTextField()) {
				if (errors.getFieldErrorCount(textField.getFieldName()) != 0) {
					textField.setError(true);
					textField.setErrorMessage(errors.getFieldError(textField.getFieldName()).getDefaultMessage());
				} else {
					textField.setError(false);
					textField.setErrorMessage("");
				}
			}
			return false;
		}
		return true;
	}

	public List<FXTextField> getListFXTextField() {
		return Arrays.asList(prepayTxt, promotionTxt, roomTxt, customerNameTxt, customerIDTxt, customerAddress,
				customerTelephone);
	}

	public Orders getOrder() {
		if (this.order == null)
			this.order = new Orders();

		order.setCreatedAt(dateTimePicker.getFirstDate());
		order.setCheckOutAt(dateTimePicker.getSecondDate());
		order.setPrepay(getDoubleValueFromTextField(prepayTxt));
		order.setPromotion(getDoubleValueFromTextField(promotionTxt));
		order.setDescription(registrationNotice.getText());
		order.setCustomerNotice(customerNotice.getText());
		order.setCustomerName(customerNameTxt.getText());
		order.setCustomerId(customerIDTxt.getText());
		order.setCustomerAddress(customerAddress.getText());
		order.setCustomerTelephone(customerTelephone.getText());
		order.setRoom((Room) roomTxt.getUserData());
		return order;
	}
	
	public Double getDoubleValueFromTextField(FXTextField textField) {
		try {
			return Double.parseDouble(textField.getText());
		} catch (Exception e) {
			return 0.0;
		}
	}

	@Override
	public void setDialog(FXMLDialog dialog) {
		this.dialog = dialog;
	}

	@FXML
	public void openQuickSeachRoomStage(ActionEvent event) {
		applicationConfiguration.quickSearchRoomDialog().initModality(Modality.APPLICATION_MODAL);
		applicationConfiguration.quickSearchRoomDialog().show();
	}

	@FXML
	public void saveOrder(ActionEvent event) {
		if (isValidOrderInformation()) {
			ordersRepository.save(getOrder());
			// Call this method will be update order table with new record.
			homeController.updateOrderTable();
		}
	}
}
