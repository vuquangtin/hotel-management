package com.gsmart.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.gsmart.model.Orders;
import com.gsmart.repository.OrdersRepository;
import com.gsmart.service.RoomService;
import com.gsmart.ui.components.FXMLDialog;
import com.gsmart.ui.components.OrderInfoPane;
import com.gsmart.ui.components.OrderTablePane;

import application.ApplicationConfiguration;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class HomeController implements DialogController {
	private FXMLDialog dialog;

	@Autowired
	private ApplicationConfiguration applicationConfiguration;

	@Autowired
	private OrdersRepository ordersRepository;

	@Autowired
	private RoomService roomService;

	@FXML
	BorderPane content;

	@FXML
	OrderInfoPane orderInfoPane;

	@FXML
	OrderTablePane orderTablePane;

	@FXML
	public void initialize() {
		if (orderTablePane != null) {
			orderTablePane.getTableView().setItems(FXCollections
					.observableArrayList(ordersRepository.findAll()));
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

}
