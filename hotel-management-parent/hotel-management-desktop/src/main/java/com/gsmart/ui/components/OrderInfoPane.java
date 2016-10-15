package com.gsmart.ui.components;

import com.gsmart.model.Orders;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class OrderInfoPane extends GridPane{
	private GridPane column1;
	private GridPane column2;
	
	private TextField nameTxt = new TextField();
	private TextField addressTxt = new TextField();
	private TextField telephoneTxt = new TextField();
	private TextField idTxt = new TextField();
	private TextField fromDateTxt = new TextField();
	private TextField toDateTxt = new TextField();
	private TextField genderTxt = new TextField();
	
	private Label nameLbl = new Label("Name");
	private Label addressLbl = new Label("Address");
	private Label telephoneLbl = new Label("Telephone");
	private Label idLbl = new Label("ID");
	private Label fromDateLbl = new Label("From Date");
	private Label toDateLbl = new Label("To Date");
	private Label genderLbl = new Label("Gender");
	
	
	public OrderInfoPane () {
		super();
		setPadding(new Insets(5));

		renderColumn1();
		renderColumn2();
	}
	
	public void setOrderInfomation(Orders order) {
		this.nameTxt.setText(getValidString(order.getCustomerName()));
		this.addressTxt.setText(getValidString(order.getCustomerAddress()));
		this.telephoneTxt.setText(getValidString(order.getCustomerTelephone()));
		this.idTxt.setText(getValidString(order.getCustomerId()));
		this.fromDateTxt.setText(getValidString(order.getCreatedAt().toString()));
		this.toDateTxt.setText(getValidString(order.getCheckOutAt().toString()));
	}
	
	public String getValidString(String value) {
		if(value.isEmpty() || value == null) return "";
		return value;
	}
	
	public void renderColumn1 () {
		column1 = new GridPane();
		column1.setPadding(new Insets(5));
		
		addNodeToGridPane(column1 , getRow(nameLbl, nameTxt) , 0 , 0);
		addNodeToGridPane(column1 , getRow(addressLbl, addressTxt) , 0 , 1);
		addNodeToGridPane(column1 , getRow(telephoneLbl, telephoneTxt) , 0 , 2);
		addNodeToGridPane(column1 , getRow(idLbl, idTxt) , 0 , 3);
			
		setConstraints(column1, 0, 0);
		getChildren().add(column1);
	}
	
	
	public void renderColumn2 () {
		column2 = new GridPane();
		column2.setPadding(new Insets(5));
			
		addNodeToGridPane(column2 , getRow(fromDateLbl, fromDateTxt) , 0 , 0);
		addNodeToGridPane(column2 , getRow(toDateLbl, toDateTxt) , 0 , 1);
		addNodeToGridPane(column2 , getRow(genderLbl, genderTxt) , 0 , 2);
		
		setConstraints(column2, 1, 0);
		getChildren().add(column2);
	}
	
	@SuppressWarnings("static-access")
	public void addNodeToGridPane(GridPane gridPane , HBox hbox , int columnIndex , int rowIndex) {
		gridPane.setConstraints(hbox, columnIndex, rowIndex);
		gridPane.getChildren().add(hbox);
	}
	
	public HBox getRow(Label label , TextField textField) {
		HBox hb = new HBox();
		label.getStyleClass().add("control-label");
		label.setPrefSize(100, 10);
		hb.getChildren().addAll(label, textField);
		return hb;
	}
	
}
