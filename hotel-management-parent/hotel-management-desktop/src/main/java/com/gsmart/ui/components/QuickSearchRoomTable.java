package com.gsmart.ui.components;

import com.gsmart.model.Room;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class QuickSearchRoomTable extends TableView<Room> {
	public QuickSearchRoomTable() {
		super();
		init();
	}
	
	@SuppressWarnings("unchecked")
	public void init() {
		TableColumn<Room, Integer> indexCol = new TableColumn<Room, Integer>("ID");
		TableColumn<Room, String> roomNameCol = new TableColumn<Room, String>("Room Name");
		TableColumn<Room, String> endedTimeCol = new TableColumn<Room, String>("Ended Time");
		TableColumn<Room, String> distanceCol = new TableColumn<Room, String>("Distance");

		indexCol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("id"));
		roomNameCol.setCellValueFactory(new PropertyValueFactory<Room, String>("name"));
		endedTimeCol.setCellValueFactory(new PropertyValueFactory<Room, String>("endedTime"));
		distanceCol.setCellValueFactory(new PropertyValueFactory<Room, String>("distance"));

		this.getColumns().addAll(indexCol, roomNameCol, endedTimeCol, distanceCol);

		this.setMaxHeight(180);
	}
}
