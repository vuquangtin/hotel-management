package com.gsmart.ui.components;

import java.util.Date;

import com.gsmart.model.Orders;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RoomOrderTable extends TableView<Orders>{
	public RoomOrderTable() {
		super();
		init();
	}
	
	@SuppressWarnings("unchecked")
	public void init() {
		TableColumn<Orders, String> indexCol = new TableColumn<Orders, String>("ID");
		TableColumn<Orders, String> statusCol = new TableColumn<Orders, String>("Status");
		TableColumn<Orders, String> customerNameCol = new TableColumn<Orders, String>("Customer Name");
		TableColumn<Orders, String> roomNameCol = new TableColumn<Orders, String>("Room Name");
		TableColumn<Orders, Date> fromDateCol = new TableColumn<Orders, Date>("From Date");
		TableColumn<Orders, Date> toDateCol = new TableColumn<Orders, Date>("To Date");
		TableColumn<Orders, Integer> dateNumberCol = new TableColumn<Orders, Integer>("Date Number");

		indexCol.setCellValueFactory(new PropertyValueFactory<Orders, String>("id"));
		statusCol.setCellValueFactory(new PropertyValueFactory<Orders, String>("roomStatus"));
		customerNameCol.setCellValueFactory(new PropertyValueFactory<Orders, String>("customerName"));
		roomNameCol.setCellValueFactory(new PropertyValueFactory<Orders, String>("roomName"));
		fromDateCol.setCellValueFactory(new PropertyValueFactory<Orders, Date>("createdAt"));
		toDateCol.setCellValueFactory(new PropertyValueFactory<Orders, Date>("checkOutAt"));
		dateNumberCol.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("numberDate"));

		this.getColumns().addAll(indexCol, statusCol, customerNameCol, roomNameCol, fromDateCol, toDateCol,
				dateNumberCol);

		this.setMaxHeight(180);
	}
}
