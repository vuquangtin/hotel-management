package com.gsmart.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gsmart.ui.components.FXMLDialog;
import com.gsmart.ui.components.RoomOrderTable;

import application.ApplicationConfiguration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

@Component
public class OrderRoomController implements DialogController{
	
	private FXMLDialog dialog;
	
	@Autowired
	private ApplicationConfiguration applicationConfiguration;
	
//	@Autowired
//	private HomeController homeController;

	@FXML RoomOrderTable roomOrderTable;

	@FXML
	public void clicked(ActionEvent event) {
		this.dialog.close();
	}
	
	@Override
	public void setDialog(FXMLDialog dialog) {
		this.dialog = dialog;
	}

	@FXML public void openQuickSeachRoomStage(ActionEvent event) {
		applicationConfiguration.quickSearchRoomDialog().show();
	}
}
