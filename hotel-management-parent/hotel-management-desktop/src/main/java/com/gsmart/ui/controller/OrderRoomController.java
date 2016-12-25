package com.gsmart.ui.controller;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

@Component
public class OrderRoomController implements DialogController, Initializable {

	private FXMLDialog dialog;
	@Autowired
	private ApplicationConfiguration applicationConfiguration;
	@Autowired
	private Validator validator;
	@Autowired
	private HomeController homeController;
	@Autowired
	private QuickSearchRoomController quickSearchRoomController;
	@Autowired
	private OrdersRepository ordersRepository;

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
	Button quickSearchBtn;

	private Orders selectedOrder;
	private ResourceBundle resource;

	@FXML
	public void clicked(ActionEvent event) {

		this.dialog.close();
	}

	public void setOrderInformation(Orders order) {
		// We need to hold selected order by user from table, because we need it
		// ID for update data.
		this.selectedOrder = order;
		renderViewComponent(order);
	}

	/**
	 * Update form data after user used Quick Search Room method. Must turn off
	 * date time picker and disable seletecd room again.
	 * <p>
	 * 
	 * @param room
	 * @param timeIn
	 * @param timeCheckOut
	 */
	public void updateCheckInInformation(Room room, Date timeIn, Date timeCheckOut) {
		roomTxt.setUserData(room);
		roomTxt.setText(room.getName());
		dateTimePicker.setDateTime(timeIn, timeCheckOut);
		this.dateTimePicker.disableDateTimePicker(true);

		updateRoomOrderTable(room);
	}

	/**
	 * Get data of current selected room by user.
	 * <p>
	 */
	public void updateRoomOrderTable(Room roomSelected) {
		roomOrderTable.setItems(FXCollections.observableArrayList(ordersRepository.findByRoom(roomSelected)));
	}

	/**
	 * Using for reset all fields if user want to create new one.
	 */
	public void preparedForCreateNewOne() {
		// Firstly , we need reset table by way set empty array.
		if (roomOrderTable != null)
			roomOrderTable.setItems(FXCollections.observableArrayList());

		// Need to call method reset to empty all fields and turn on Quick
		// Search Room.
		resetOrderForm();
	}

	/**
	 * If take data from {@Order} object and binding to form.
	 * <p>
	 * 
	 * @param order
	 *            - Selected order for showing by user.
	 */
	public void renderViewComponent(Orders order) {

		// For safe we need to check.
		if (order != null) {
			//Set stage title for update order information.
			titleStage.setText(resource.getString("OrderRoomStage.Label.UpdateOrder"));

			dateTimePicker.setDateTime(order.getCreatedAt(), order.getCheckOutAt());
			prepayTxt.setText(NumberFormat.getNumberInstance().format(order.getPrepay()));
			promotionTxt.setText(NumberFormat.getNumberInstance().format(order.getPromotion()));
			registrationNotice.setText(order.getDescription());
			roomTxt.setText(order.getRoomName());
			roomTxt.setUserData(order.getRoom());
			customerNameTxt.setText(order.getCustomerName());
			customerIDTxt.setText(order.getCustomerId());
			customerAddress.setText(order.getCustomerAddress());
			customerTelephone.setText(order.getCustomerTelephone());
			customerNotice.setText(order.getCustomerNotice());

			if (order.getRoom() != null)
				updateRoomOrderTable(order.getRoom());

			// Need to verify some field important.
			disableChangeImportantInfo(order);

			// We need disable date time picker when order already have room
			// because it will safer.
			this.dateTimePicker.disableDateTimePicker(true);
		}
	}

	/**
	 * Used for bean validation, it's help we bind error message to form.
	 * <p>
	 * 
	 * @return TRUE if data is valid and FALSE if not.
	 */
	public boolean isValidOrderInformation() {

		// Firstly , we need to validated date time.
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

	/**
	 * Get order information from form data user entry.
	 * <p>
	 * 
	 * @return Orders - Information user entry.
	 */
	public Orders getOrder() {
		Orders order;

		// We must do this, because if not. JPA will be create new one object
		// because it don't have ID.
		if (this.selectedOrder != null)
			order = this.selectedOrder;
		else
			order = new Orders();

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

		if (roomTxt.getUserData() != null)
			order.setRoom((Room) roomTxt.getUserData());
		else
			order.setRoom(null);

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
		applicationConfiguration.quickSearchRoomDialog().show();
		// This method will send current date time selected to quick search
		// stage.
		quickSearchRoomController.setDateTimeForSearch(dateTimePicker.getFirstDate(), dateTimePicker.getSecondDate());
	}

	@FXML
	public void resetOrderForm() {
		dateTimePicker.reset();
		prepayTxt.setText("");
		promotionTxt.setText("");
		registrationNotice.setText("");
		roomTxt.setText("");
		roomTxt.setUserData(null);
		customerNameTxt.setText("");
		customerIDTxt.setText("");
		customerAddress.setText("");
		customerTelephone.setText("");
		customerNotice.setText("");
		this.dateTimePicker.disableDateTimePicker(false);
		this.roomTxt.setDisable(false);
		this.quickSearchBtn.setVisible(true);
	}

	/**
	 * Using for disable some important field not permit user changing when
	 * order has created.
	 */
	private void disableChangeImportantInfo(Orders orderSelected) {

		// If have date time we must turn off select date time picker.
		if (this.dateTimePicker.getFirstDate() != null) {
			this.dateTimePicker.disableDateTimePicker(true);
		}

		// If have room, mustn't choose new one.
		if (orderSelected.getRoom() != null) {
			this.roomTxt.setDisable(true);
			this.quickSearchBtn.setVisible(false);
		}

	}

	@FXML
	public void saveOrder(ActionEvent event) {
		if (isValidOrderInformation()) {
			ordersRepository.save(getOrder());
			// Call this method will be update order table with new record.
			homeController.updateOrderTable();
			resetOrderForm();
			this.dialog.close();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.resource = resources;

		// Set Reset form action when stage has closed.
		this.dialog.setOnCloseRequest(event -> {
			resetOrderForm();
		});

		// Set columns name for multiple languages.
		if (this.roomOrderTable != null)
			this.roomOrderTable.setColumnLabel(resources);

		// Default when we open order room stage, it need set title for create
		// new one, we can update correct late.
		if (this.titleStage != null)
			titleStage.setText(resources.getString("OrderRoomStage.Label.CreateNewOrder"));
	}

}
