package com.gsmart.ui.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.gsmart.model.Orders;
import com.gsmart.repository.OrdersRepository;
import com.gsmart.repository.RoomCategoryRepository;
import com.gsmart.service.RoomService;
import com.gsmart.ui.components.CalculatePane;
import com.gsmart.ui.components.FXMLDialog;
import com.gsmart.ui.components.OrderInfoPane;
import com.gsmart.ui.components.OrderTablePane;
import com.gsmart.ui.components.RoomInfoPane;

import application.ApplicationConfiguration;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

public class HomeController implements DialogController {
	private FXMLDialog dialog;

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
	OrderTablePane orderTablePane;

	@FXML
	CalculatePane calculatorPane;

	@FXML
	RoomInfoPane roomInfoPane;

	@FXML Button receiveRoomBtn;
	
	@FXML Button removeRoom;

	@FXML
	public void initialize() {
		updateOrderTable();
	}

	public void updateOrderTable() {
		if (orderTablePane != null) {
			// Set date for table.
			orderTablePane.getTableView().setItems(FXCollections.observableArrayList(ordersRepository.findAll()));

			// Set date for combo box.
			orderTablePane.getRoomType().setItems(FXCollections.observableArrayList(roomCategoryRepository.findAll()));
			orderTablePane.setController(this);
			orderTablePane.setOrdersRepository(this.ordersRepository);
		}
	}

	@FXML
	public void openEditOrderStage(ActionEvent event) {
		roomService.SearchRoom();
		if (orderTablePane.getSeletedOrder() == null)
			return;

		applicationConfiguration.orderRoomDialog().show();
		// Before open order room stage, we need call controller and setup data
		applicationConfiguration.orderRoomController().setOrderInformation(orderTablePane.getSeletedOrder());

	}
	

	public void closeDialog() {
		dialog.close();
	}

	@Override
	public void setDialog(FXMLDialog dialog) {
		this.dialog = dialog;
	}
	
	@FXML
	public void removeOrder() {
		
		Orders orders = orderTablePane.getSeletedOrder();
		
		if(orders != null){
			//Must set order equal null because we don't want to remove 
			//this room when delete this order.
			//orders.setRoom(null);
			
			ordersRepository.removeById(orders.getId());
			updateOrderTable();
		}
		else {
			System.out.println("no select...");
		}
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

	public OrderInfoPane getOrderInfoPane() {
		return orderInfoPane;
	}
	
	public void updateReceiveRoomButton(Orders order) {
		if(order.getStatus() == 1 || order.getStatus() == 2) this.receiveRoomBtn.setVisible(false);
		else this.receiveRoomBtn.setVisible(true);
	}

	@FXML public void receiveRoom(ActionEvent event) {
		Orders order = orderTablePane.getSeletedOrder();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Receive Room");
		alert.setHeaderText("For customer " + order.getCustomerName());
		alert.setContentText("Are you Ok ?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    order.setStatus(1);
		    ordersRepository.save(order);
		    updateOrderTable();
		    this.receiveRoomBtn.setVisible(false);
		} else {
		    // ... User chose CANCEL or closed the dialog
		}
	}

}
