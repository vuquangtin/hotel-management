package com.gsmart.ui.controller;

import com.gsmart.ui.components.FXMLDialog;

public class QuickSearchRoomController implements DialogController{
	@SuppressWarnings("unused")
	private FXMLDialog dialog;
	
	@Override
	public void setDialog(FXMLDialog dialog) {
		this.dialog = dialog;
	}

}
