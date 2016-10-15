package com.gsmart.ui.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.gsmart.service.RoomService;
import com.gsmart.ui.components.FXMLDialog;
import com.gsmart.ui.components.QuickSearchRoomTable;
import com.gsmart.ui.utils.DateUtils;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class QuickSearchRoomController implements DialogController {

	@FXML
	QuickSearchRoomTable quickSearchRoomTable;

	private Calendar calendar = GregorianCalendar.getInstance();

	@Autowired
	RoomService roomService;

	@SuppressWarnings("unused")
	private FXMLDialog dialog;

	@FXML
	DatePicker dateIn;

	@FXML
	TextField timeIn;

	@FXML
	DatePicker dateCheckOut;

	@FXML
	TextField timeCheckOut;

	@Override
	public void setDialog(FXMLDialog dialog) {
		this.dialog = dialog;
	}

	/**
	 * Called when user clicked on row and submit selection by press
	 * "Select Room" button.
	 * <p>
	 * 
	 * @param event
	 */
	@FXML
	public void seletecRoomAction(ActionEvent event) {

	}

	@FXML
	public void searchRoomByDateAction(ActionEvent event) {
		Date dateInSelected;
		Date dateCheckOutSelected;

		calendar.setTime(DateUtils.asDate(dateIn.getValue()));
		String[] timeInArray = timeIn.getText().split(":");
		calendar.set(Calendar.HOUR, Integer.parseInt(timeInArray[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(timeInArray[1]));
		dateInSelected = calendar.getTime();

		calendar.setTime(DateUtils.asDate(dateCheckOut.getValue()));
		String[] timeCheckOutArray = timeCheckOut.getText().split(":");
		calendar.set(Calendar.HOUR, Integer.parseInt(timeCheckOutArray[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(timeCheckOutArray[1]));
		dateCheckOutSelected = calendar.getTime();

		quickSearchRoomTable.setItems(
				FXCollections.observableArrayList(roomService.findRoomByDate(dateInSelected, dateCheckOutSelected)));
	}

}
