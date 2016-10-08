package com.gsmart.ui.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;

import com.gsmart.model.Orders;
import com.gsmart.repository.OrdersRepository;
import com.gsmart.ui.components.FXMLDialog;
import com.gsmart.ui.components.RoomOrderTable;
import com.gsmart.ui.utils.DateUtils;
import com.gsmart.ui.utils.FxDatePickerConverter;

import application.ApplicationConfiguration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;

@Component
public class OrderRoomController implements DialogController {

	private FXMLDialog dialog;

	@Autowired
	private ApplicationConfiguration applicationConfiguration;
	
	@Autowired
	private Validator validator;

	private String datePickerPattern = "dd/MM/yyyy";

	private Calendar calendar = GregorianCalendar.getInstance();

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

	@FXML Text errorTextMessage;

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

			titleStage.setText("Update Order Information");

			calendar.setTime(this.order.getCreatedAt());
			String timeInValue = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

			calendar.setTime(this.order.getCheckOutAt());
			String timeCheckOutValue = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

			timeInTxt.setText(timeInValue);
			timeOutTxt.setText(timeCheckOutValue);

			prepayTxt.setText(this.order.getPrepay().toString());
			promotionTxt.setText(this.order.getPromotion().toString());
			registrationNotice.setText(this.order.getDescription());
			roomTxt.setText(this.order.getRoomName());
			customerNameTxt.setText(this.order.getCustomerName());
			customerIDTxt.setText(this.order.getCustomerId());
			customerAddress.setText(this.order.getCustomerAddress());
			customerTelephone.setText(this.order.getCustomerTelephone());
			customerNotice.setText(this.order.getCustomerNotice());

			FxDatePickerConverter converter = new FxDatePickerConverter(datePickerPattern);

			dateIn.setConverter(converter);
			dateCheckout.setConverter(converter);

			dateIn.setValue(DateUtils.asLocalDate(this.order.getCreatedAt()));
			dateCheckout.setValue(DateUtils.asLocalDate(this.order.getCheckOutAt()));
		}
	}

	public boolean isValidOrderInformation() {
		
		BindException errors = new BindException(getOrder(), "orders");

		validator.validate(getOrder(), errors);

		if(errors.getErrorCount() != 0) {
			//Show first message to stage.
			errorTextMessage.setText(errors.getFieldErrors().iterator().next().getDefaultMessage());
			return false;
		}
		
		errorTextMessage.setText("");
		return true;
	}

	public Orders getOrder() {
		if(this.order == null) this.order = new Orders();
		
		//Set default value for some field
		promotionTxt.setText("0");
		prepayTxt.setText("0");

		calendar.setTime(DateUtils.asDate(dateIn.getValue()));
		String[] timeInArray = timeInTxt.getText().split(":");
		calendar.set(Calendar.HOUR, Integer.parseInt(timeInArray[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(timeInArray[1]));
		order.setCreatedAt(calendar.getTime());

		calendar.setTime(DateUtils.asDate(dateCheckout.getValue()));
		String[] timeCheckOutArray = timeOutTxt.getText().split(":");
		calendar.set(Calendar.HOUR, Integer.parseInt(timeCheckOutArray[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(timeCheckOutArray[1]));
		order.setCheckOutAt(calendar.getTime());

		order.setPrepay(Double.parseDouble(prepayTxt.getText()));
		order.setPromotion(Double.parseDouble(promotionTxt.getText()));
		order.setDescription(registrationNotice.getText());
		order.setCustomerNotice(customerNotice.getText());
		order.setCustomerName(customerNameTxt.getText());
		order.setCustomerId(customerIDTxt.getText());
		order.setCustomerAddress(customerAddress.getText());
		order.setCustomerTelephone(customerTelephone.getText());

		return order;
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

	@FXML public void saveOrder(ActionEvent event) {
		if(isValidOrderInformation()) {
			ordersRepository.save(getOrder());
			//Call this method will be update order table with new record.
			homeController.updateOrderTable();
		}
	}
}
