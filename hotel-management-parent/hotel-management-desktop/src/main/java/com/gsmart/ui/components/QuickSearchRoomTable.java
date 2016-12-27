package com.gsmart.ui.components;

import java.text.SimpleDateFormat;

import com.gsmart.model.Room;
import com.gsmart.service.model.SearchRoomResult;
import com.gsmart.ui.controller.QuickSearchRoomController;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class QuickSearchRoomTable extends TableView<SearchRoomResult> {
	private QuickSearchRoomController controller;
	
	public QuickSearchRoomTable() {
		super();
		init();
	}
	
	@SuppressWarnings("unchecked")
	public void init() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		TableColumn<SearchRoomResult, Integer> indexCol = new TableColumn<SearchRoomResult, Integer>("ID");
		TableColumn<SearchRoomResult, String> roomNameCol = new TableColumn<SearchRoomResult, String>("Room Name");
		TableColumn<SearchRoomResult, String> endedTimeCol = new TableColumn<SearchRoomResult, String>("Ended Time");
		TableColumn<SearchRoomResult, String> distanceCol = new TableColumn<SearchRoomResult, String>("Distance");
		
		indexCol.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Integer>(getItems().indexOf(column.getValue()) + 1));
		roomNameCol.setCellValueFactory(new PropertyValueFactory<SearchRoomResult, String>("name"));
		distanceCol.setCellValueFactory(new PropertyValueFactory<SearchRoomResult, String>("distance"));
		
		endedTimeCol.setCellValueFactory(row -> {
			return new SimpleObjectProperty<String>(format.format(row.getValue().getEndedTime()));
		});
		
		this.getColumns().addAll(indexCol, roomNameCol, endedTimeCol, distanceCol);
		
		this.getSelectionModel().selectedIndexProperty().addListener((object) -> {
			SearchRoomResult searchRoomResult = this.getSelectionModel().getSelectedItem();
			if (searchRoomResult != null) {
				getController().updateSeletedRoomPane(searchRoomResult);
			}
		});

		this.setMaxHeight(180);
	}
	
	public Room getRoomSeleted() {
		return this.getSelectionModel().getSelectedItem().getRoom();
	}
	
	public QuickSearchRoomController getController() {
		return controller;
	}

	public void setController(QuickSearchRoomController controller) {
		this.controller = controller;
	}
}
