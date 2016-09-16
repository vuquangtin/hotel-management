package com.gsmart.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gsmart.service.RoomService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

@Component
public class OrderRoomController {

	@Autowired
	private RoomService roomService;

	@FXML
	public void clicked(ActionEvent event) {
		roomService.SearchRoom();
	}
}
