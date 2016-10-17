package com.gsmart.ui.controls;

import javafx.beans.NamedArg;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FXTextField extends HBox {

	private TextField textField;
	private Label label;
	private Label unit;
	private Text tipText;
	private Text errorMessage;

	private String fieldName;

	private boolean isInlineStyle = true;
	private boolean isError = false;
	private final String ERROR_STYLE_CLASS = "field-error";
	
	private int preWidth = 100;
	
	public FXTextField() {
		this(null, null, null, null, true);
	}
	
	/**
	 * By using @NamedArg("isInlineStyle") we can pass parameter from FXML file
	 * in to constructor of this
	 * 
	 * @param isInlineStyle
	 */
	public FXTextField(@NamedArg("isInlineStyle") boolean isInlineStyle) {
		this(null, null, null, null, isInlineStyle);
	}

	public FXTextField(String labelText, boolean isInlineStyle) {
		this(labelText, null, null, null, isInlineStyle);
	}

	public FXTextField(String labelText, String unitText, boolean isInlineStyle) {
		this(labelText, unitText, null, null, isInlineStyle);
	}

	public FXTextField(String labelText, String unitText, String errorMessageText, boolean isInlineStyle) {
		this(labelText, unitText, errorMessageText, null, isInlineStyle);
	}

	public FXTextField(String labelText, String unitText, String errorMessageText, String tipTextMessage,
			boolean isInlineStyle) {
		super();
		this.isInlineStyle = isInlineStyle;
		setComponentData(labelText, unitText, errorMessageText, tipTextMessage);
		renderComponent(isInlineStyle);
	}

	public void updateTextFieldStyleState() {
		if (isError) {
			this.textField.getStyleClass().add(ERROR_STYLE_CLASS);
		} else {
			this.textField.getStyleClass().remove(ERROR_STYLE_CLASS);
			this.errorMessage.setText("");
		}
	}

	public void initComponents() {
		textField = new TextField();
		label = new Label();
		unit = new Label();
		tipText = new Text();
		errorMessage = new Text();

		this.label.getStyleClass().add("control-label");
		this.label.setPrefSize(this.preWidth, 10);

		this.unit.getStyleClass().add("control-label");
		this.unit.setPrefSize(this.preWidth, 10);
		
		textField.textProperty().addListener((event) -> {
			//When field has error if use typed new one , we need change style and waiting next validation.
			this.isError = false;
			updateTextFieldStyleState();
		});
		
		errorMessage.setFont(new Font(10));
		errorMessage.setFill(Color.RED);
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

	}

	public void renderComponent(boolean isInlineStyle) {
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

	/**
	 * With strut function like this we can access label , text and set value
	 * for it. is called properties.
	 * 
	 * We also choose alternate option is using @NamedArg instead, so i just
	 * want using two ways for show how can i handle this problem.
	 * 
	 */

	public final StringProperty unitTextProperty() {
		if (unit == null) {
			unit = new Label();
			return unit.textProperty();
		}
		return unit.textProperty();
	}

	public final StringProperty errorMessageProperty() {
		if (errorMessage == null) {
			errorMessage = new Text();
			return errorMessage.textProperty();
		}
		return errorMessage.textProperty();
	}

	public final StringProperty tipTextProperty() {
		if (tipText == null) {
			tipText = new Text();
			return tipText.textProperty();
		}
		return tipText.textProperty();
	}

	public final StringProperty labelProperty() {
		if (label == null) {
			label = new Label();
			return label.textProperty();
		}
		return label.textProperty();
	}

	public final String getLabel() {
		return label == null ? "" : label.getText();
	}

	public final String getUnit() {
		return unit == null ? "" : unit.getText();
	}

	public final String getErrorMessage() {
		return errorMessage == null ? "" : errorMessage.getText();
	}

	public final String getTipText() {
		return tipText == null ? "" : tipText.getText();
	}

	public final void setLabel(String label) {
		labelProperty().set(label);
	}

	public final void setUnit(String unitText) {
		unitTextProperty().set(unitText);
	}

	public final void setErrorMessage(String errorMessageText) {
		errorMessageProperty().set(errorMessageText);
	}

	public final void setTipText(String tipTextMessage) {
		tipTextProperty().set(tipTextMessage);
	}

	// -----------------------------------

	public String getText() {
		return this.textField.getText();
	}

	public void setText(String text) {
		this.textField.setText(text);
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

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public void setEditAble(boolean editAble) {
		this.textField.setEditable(!editAble);
	}
	
	public boolean getEditAble() {
		return this.textField.isEditable();
	}

	public TextField getTextField() {
		return textField;
	}

	public void setTextField(TextField textField) {
		this.textField = textField;
	}
}
