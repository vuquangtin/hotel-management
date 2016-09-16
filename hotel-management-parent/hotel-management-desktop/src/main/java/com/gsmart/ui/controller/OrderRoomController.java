package com.gsmart.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gsmart.service.RoomService;
import com.gsmart.ui.components.FXMLDialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

@Component
public class OrderRoomController implements DialogController{
	
	private FXMLDialog dialog;

	@Autowired
	private RoomService roomService;

	@FXML
	public void clicked(ActionEvent event) {
		roomService.SearchRoom();
		dialog.close();
	}

	@Override
	public void setDialog(FXMLDialog dialog) {
		this.dialog = dialog;
	}
}
