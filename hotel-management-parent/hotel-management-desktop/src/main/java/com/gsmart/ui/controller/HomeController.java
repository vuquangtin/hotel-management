package com.gsmart.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.gsmart.model.Orders;
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
	@Autowired
	OrderTablePane orderTablePane;

	@FXML
	public void initialize() {
		if (orderTablePane != null) {
			//Set date for table.
			orderTablePane.getTableView().setItems(FXCollections
					.observableArrayList(ordersRepository.findAll()));
			
			//Set date for combo box.
			orderTablePane.getRoomType().setItems(FXCollections
					.observableArrayList(roomCategoryRepository.findAll()));
			
			orderTablePane.setController(this);
		}
	}

	@FXML
	public void clicked(ActionEvent event) {
		System.out.println("Say Hello World From Main Form !!!");
		roomService.SearchRoom();
		applicationConfiguration.orderRoomDialog().show();

		Orders order = new Orders();
		order.setCustomerName("Nguyen Huu Quyen");
		order.setCustomerAddress("Da Nang , Viet Nam");

		orderInfoPane.setOrderInfomation(order);
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


	@FXML public void openOrderRoomStage(ActionEvent event) {
		applicationConfiguration.orderRoomDialog().show();
	}

}
