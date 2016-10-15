package com.gsmart.ui.controls;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FXTextField extends HBox {

	private TextField textField;
	private Label label;
	private Label unit;
	private Text tipText;
	private Text errorMessage;
	
	private StringProperty labelText;
	private StringProperty unitText;
	private StringProperty errorMessageText;
	private StringProperty tipTextMessage;
	
	private boolean isInlineStyle = false;
	private boolean isError = false;
	private final String ERROR_STYLE_CLASS = "error-text-field";

	private int preWidth = 100;

	public FXTextField() {
		this(null, null, null, null);
	}

	public FXTextField(String labelText) {
		this(labelText, null, null, null);
	}

	public FXTextField(String labelText, String unitText) {
		this(labelText, unitText, null, null);
	}

	public FXTextField(String labelText, String unitText, String errorMessageText) {
		this(labelText, unitText, errorMessageText, null);
	}

	public FXTextField(String labelText, String unitText, String errorMessageText, String tipTextMessage) {
		super();
		setComponentData(labelText, unitText, errorMessageText, tipTextMessage);
		renderComponent();
	}

	public void updateTextFieldStyleState() {
		if (isError) {
			this.textField.getStyleClass().add(ERROR_STYLE_CLASS);
		} else {
			this.textField.getStyleClass().remove(ERROR_STYLE_CLASS);
		}
	}

	public void initComponents() {
		textField = new TextField();
		label = new Label();
		unit = new Label();
		tipText = new Text();
		errorMessage = new Text();
	}

	public void setComponentData(String label, String unit, String errorMessage, String tipText) {
		initComponents();

		if (label != null)
			this.label.setText(label);
		if (errorMessage != null)
			this.errorMessage.setText(errorMessage);
		if (tipText != null)
			this.tipText.setText(tipText);
		if (unit != null)
			this.unit.setText(tipText);
		
		this.label.setText(getLabelText());
		
		this.label.getStyleClass().add("control-label");
		this.label.setPrefSize(this.preWidth, 10);

		this.unit.getStyleClass().add("control-label");
		this.unit.setPrefSize(this.preWidth, 10);
	}

	public void renderComponent() {
		if (isInlineStyle) {
			getChildren().add(getHorizontalStyleBox());
		} else {
			getChildren().add(getVerticalStyleBox());
		}
	}

	public VBox getVerticalStyleBox() {
		VBox vb = new VBox();
		HBox hb = new HBox();
		hb.getChildren().addAll(textField, unit);
		vb.getChildren().addAll(label, hb, errorMessage);

		return vb;
	}

	public VBox getHorizontalStyleBox() {
		HBox hb = new HBox();
		hb.getChildren().addAll(label, textField, unit);
		VBox vb = new VBox();
		vb.getChildren().addAll(hb, errorMessage);

		return vb;
	}
	
	//---------------------------------
	
	public final StringProperty labelTextProperty() {
		if(labelText == null) {
			return new SimpleStringProperty(this, "labelText", "");
		}
		return labelText;
	}
	
	public final StringProperty unitTextProperty() {
		if(unitText == null) {
			return new SimpleStringProperty(this, "unitText", "");
		}
		return unitText;
	}
	
	
	public final StringProperty errorMessageTextProperty() {
		if(errorMessageText == null) {
			return new SimpleStringProperty(this, "errorMessageText", "");
		}
		return errorMessageText;
	}
	
	public final StringProperty tipTextMessageProperty() {
		if(tipTextMessage == null) {
			return new SimpleStringProperty(this, "tipTextMessage", "");
		}
		return tipTextMessage;
	}
	
	public final StringProperty LabelTextProperty() {
		if(labelText == null) {
			return new SimpleStringProperty(this, "text", "");
		}
		return labelText;
	}
	
	public final String getLabelText() {
		return labelText == null ? "" : labelText.getValue();
	}

	public final String getUnitText() {
		return unitText == null ? "" : unitText.getValue();
	}

	public final String getErrorMessageText() {
		return errorMessageText == null ? "" : errorMessageText.getValue();
	}

	public final String  getTipTextMessage() {
		return tipTextMessage == null ? "" : tipTextMessage.getValue();
	}

	public final void setLabelText(String labelText) {
		labelTextProperty().set(labelText);
	}

	public final void setUnitText(String unitText) {
		unitTextProperty().set(unitText);
	}

	public final void setErrorMessageText(String errorMessageText) {
		errorMessageTextProperty().set(errorMessageText);
	}

	public final void setTipTextMessage(String tipTextMessage) {
		tipTextMessageProperty().set(tipTextMessage);
	}
	
	//-----------------------------------

	public String getText() {
		return this.textField.getText();
	}

	public boolean isInlineStyle() {
		return isInlineStyle;
	}

	public void setInlineStyle(boolean isInlineStyle) {
		this.isInlineStyle = isInlineStyle;
	}

	public boolean isError() {
		return isError;
	}

	public int getPreWidth() {
		return preWidth;
	}

	public void setError(boolean isError) {
		this.isError = isError;
		updateTextFieldStyleState();
	}

	public void setPreWidth(int preWidth) {
		this.preWidth = preWidth;
	}

}
