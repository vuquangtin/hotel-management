package com.gsmart.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import com.gsmart.model.Orders;
import com.gsmart.model.Room;
import com.gsmart.repository.OrdersRepository;
import com.gsmart.repository.RoomCategoryRepository;
import com.gsmart.service.RoomService;
import com.gsmart.ui.components.FXMLDialog;
import com.gsmart.ui.components.OrderInfoPane;
import com.gsmart.ui.components.OrderTablePane;

import application.ApplicationConfiguration;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import com.gsmart.ui.components.CalculatePane;
import com.gsmart.ui.components.RoomInfoPane;

public class HomeController implements DialogController {
	private FXMLDialog dialog;

	@Autowired
	private Validator validator;

	@Autowired
	private ApplicationConfiguration applicationConfiguration;

	@Autowired
	private OrdersRepository ordersRepository;

	@Autowired
	private RoomCategoryRepository roomCategoryRepository;

	@Autowired
	private RoomService roomService;

	@FXML
	VBox content;

	@FXML
	OrderInfoPane orderInfoPane;

	@FXML
	@Autowired
	OrderTablePane orderTablePane;

	@FXML CalculatePane calculatorPane;

	@FXML RoomInfoPane roomInfoPane;

	@FXML
	public void initialize() {
		if (orderTablePane != null) {
			// Set date for table.
			orderTablePane.getTableView().setItems(FXCollections.observableArrayList(ordersRepository.findAll()));

			// Set date for combo box.
			orderTablePane.getRoomType().setItems(FXCollections.observableArrayList(roomCategoryRepository.findAll()));

			orderTablePane.setController(this);

			Room room = new Room();
			BindException errors = new BindException(room, "room");

			validator.validate(room, errors);

			for (FieldError error : (List<FieldError>) errors.getFieldErrors()) {

				System.out.println("invalid value for: '" + error.getField() + "': " + error.getDefaultMessage());
			}

		}
	}

	@FXML
	public void openEditOrderStage(ActionEvent event) {
		roomService.SearchRoom();
		
		applicationConfiguration.orderRoomDialog().show();
		
		// Before open order room stage, we need call controller and setup date for it.
		applicationConfiguration.orderRoomController().setOrderInformation(orderTablePane.getSeletedOrder());
		
	}

	public void setOrderInfoItem(Orders order) {
		orderInfoPane.setOrderInfomation(order);
	}

	public void closeDialog() {
		dialog.close();
	}

	@Override
	public void setDialog(FXMLDialog dialog) {
		this.dialog = dialog;
	}

	@FXML
	public void openOrderRoomStage(ActionEvent event) {
		applicationConfiguration.orderRoomDialog().show();
	}

	public CalculatePane getCalculatorPane() {
		return calculatorPane;
	}

	public RoomInfoPane getRoomInfoPane() {
		return roomInfoPane;
	}

}
