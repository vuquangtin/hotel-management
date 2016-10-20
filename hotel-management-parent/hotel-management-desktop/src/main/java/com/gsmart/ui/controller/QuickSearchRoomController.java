package com.gsmart.ui.controller;

import java.text.NumberFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import com.gsmart.service.RoomService;
import com.gsmart.service.model.SearchRoomResult;
import com.gsmart.ui.components.FXMLDialog;
import com.gsmart.ui.components.QuickSearchRoomTable;
import com.gsmart.ui.controls.FXDateTimePicker;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class QuickSearchRoomController implements DialogController {

	private FXMLDialog dialog;

	@FXML
	QuickSearchRoomTable quickSearchRoomTable;
	@Autowired
	RoomService roomService;
	@Autowired
	OrderRoomController orderRoomController;

	@FXML
	Text roomNameTxt;
	@FXML
	Text roomTypeTxt;
	@FXML
	Text priceTxt;
	@FXML
	FXDateTimePicker dateTimePicker;

	public void setDateTimeForSearch(Date dataIn, Date dateCheckOut) {
		dateTimePicker.setDateTime(dataIn, dateCheckOut);
	}

	@Override
	public void setDialog(FXMLDialog dialog) {
		this.dialog = dialog;
	}

	@FXML
	public void initialize() {
		if (quickSearchRoomTable != null) {
			quickSearchRoomTable.setController(this);
		}
	}

	public void updateSeletedRoomPane(SearchRoomResult searchRoomResult) {
		this.roomNameTxt.setText(searchRoomResult.getRoom().getName());
		this.roomTypeTxt.setText(searchRoomResult.getRoom().getRoomCategory().getName());
		this.priceTxt.setText(
				NumberFormat.getNumberInstance().format(searchRoomResult.getRoom().getRoomCategory().getPrice()));
	}

	@FXML
	public void seletecRoomAction(ActionEvent event) {
		if (quickSearchRoomTable.getRoomSeleted() != null) {
			orderRoomController.updateCheckInInformation(quickSearchRoomTable.getRoomSeleted(),
					dateTimePicker.getFirstDate(), dateTimePicker.getSecondDate());
			this.dialog.close();
		}
	}

	@FXML
	public void searchRoomByDateAction(ActionEvent event) {
		if (dateTimePicker.isValidDateTime())
			quickSearchRoomTable.setItems(FXCollections.observableArrayList(
					roomService.findRoomByDate(dateTimePicker.getFirstDate(), dateTimePicker.getSecondDate())));
	}

}
