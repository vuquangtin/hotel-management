package com.gsmart.ui.components;

import com.gsmart.service.model.SearchRoomResult;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class QuickSearchRoomTable extends TableView<SearchRoomResult> {
	public QuickSearchRoomTable() {
		super();
		init();
	}
	
	@SuppressWarnings("unchecked")
	public void init() {
		TableColumn<SearchRoomResult, Integer> indexCol = new TableColumn<SearchRoomResult, Integer>("ID");
		TableColumn<SearchRoomResult, String> roomNameCol = new TableColumn<SearchRoomResult, String>("Room Name");
		TableColumn<SearchRoomResult, String> endedTimeCol = new TableColumn<SearchRoomResult, String>("Ended Time");
		TableColumn<SearchRoomResult, String> distanceCol = new TableColumn<SearchRoomResult, String>("Distance");
		
		indexCol.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Integer>(getItems().indexOf(column.getValue()) + 1));
		roomNameCol.setCellValueFactory(new PropertyValueFactory<SearchRoomResult, String>("name"));
		endedTimeCol.setCellValueFactory(new PropertyValueFactory<SearchRoomResult, String>("endedTime"));
		distanceCol.setCellValueFactory(new PropertyValueFactory<SearchRoomResult, String>("distance"));

		this.getColumns().addAll(indexCol, roomNameCol, endedTimeCol, distanceCol);

		this.setMaxHeight(180);
	}
}
