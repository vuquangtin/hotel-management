package com.gsmart.ui.controls;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.gsmart.ui.utils.DateUtils;
import com.gsmart.ui.utils.FxDatePickerConverter;

import javafx.beans.NamedArg;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The must has pattern HH:mm
 * 
 * @author Nguyen Huu Quyen , 16-10-2016
 *
 */
public class FXDateTimePicker extends VBox {
	private final String NOT_EMPTY_DATE_MESSAGE = "Date can't empty ";
	private final String NOT_EMPTY_TIME_MESSAGE = "Time can't empty";

	private final String ERROR_STYLE_CLASS = "field-error";

	private final String datePickerPattern = "dd/MM/yyyy";
	private final Calendar calendar = GregorianCalendar.getInstance();

	private Label firstDateLabel = new Label();
	private Label secondDateLabel = new Label();

	private Label firstTimeLabel = new Label();
	private Label secondTimeLabel = new Label();

	private DatePicker firstDateSelected = new DatePicker();
	private DatePicker secondDateSelected = new DatePicker();
	private TextField firstTimeTxt = new TextField();
	private TextField secondTimeTxt = new TextField();

	private Text firstDateMessage = new Text();
	private Text secondDateMessage = new Text();

	private Text firstTimeMessage = new Text();
	private Text secondTimeMessage = new Text();
	
	private int labelPreWidth = 100;
	private boolean editAble = true;

	public FXDateTimePicker() {
		this(true, null, null, null, null);
	}

	public FXDateTimePicker(@NamedArg("firstDateLabel") String firstDateLabel,
			@NamedArg("firstTimeLabel") String firstTimeLabel, @NamedArg("secondDateLabel") String secondDateLabel,
			@NamedArg("secondTimeLabel") String secondTimeLabel) {
		this(true, firstDateLabel, firstTimeLabel, secondDateLabel, secondTimeLabel);
	}

	public FXDateTimePicker(@NamedArg("isInlineStyle") boolean isInlineStyle) {
		this(isInlineStyle, null, null, null, null);
	}

	public FXDateTimePicker(@NamedArg("isInlineStyle") boolean isInlineStyle,
			@NamedArg("firstDateLabel") String firstDateLabel, @NamedArg("firstTimeLabel") String firstTimeLabel,
			@NamedArg("secondDateLabel") String secondDateLabel, @NamedArg("secondTimeLabel") String secondTimeLabel) {
		renderComponent(isInlineStyle);
		setTextForComponents(firstDateLabel, firstTimeLabel, secondDateLabel, secondTimeLabel);
	}

	public void renderComponent(boolean isInlineStyle) {
		if (isInlineStyle) {
			getHorizontalStyleBox();
		} else {
			getVerticalStyleBox();
		}
		//Default preWidth is 100.
		setLabelPreWidthForAll(this.labelPreWidth);
	}

	public void setTextForComponents(String firstDateLabel, String firstTimeLabel, String secondDateLabel,
			String secondTimeLabel) {
		if (firstDateLabel != null)
			this.firstDateLabel.setText(firstDateLabel);
		if (firstTimeLabel != null)
			this.firstTimeLabel.setText(firstTimeLabel);
		if (secondDateLabel != null)
			this.secondDateLabel.setText(secondDateLabel);
		if (secondTimeLabel != null)
			this.secondTimeLabel.setText(secondTimeLabel);

		FxDatePickerConverter converter = new FxDatePickerConverter(datePickerPattern);

		firstDateSelected.setConverter(converter);
		secondDateSelected.setConverter(converter);

		setErrorStyleText(firstDateMessage, firstTimeMessage, secondDateMessage, secondTimeMessage);
	}

	public void setErrorStyleText(Text... texts) {
		for (Text text : texts) {
			text.setFill(Color.RED);
			text.setFont(new Font(10));
		}
	}

	public void getVerticalStyleBox() {
		getChildren().addAll(
				getRowDateTime(firstDateLabel, firstDateSelected, firstTimeLabel, firstTimeTxt, firstDateMessage,
						firstTimeMessage),
				getRowDateTime(secondDateLabel, secondDateSelected, secondTimeLabel, secondTimeTxt, secondDateMessage,
						secondTimeMessage));
	}

	public void getHorizontalStyleBox() {
		getChildren().addAll(new HBox(
				getRowDateTime(firstDateLabel, firstDateSelected, firstTimeLabel, firstTimeTxt, firstDateMessage,
						firstTimeMessage),
				getRowDateTime(secondDateLabel, secondDateSelected, secondTimeLabel, secondTimeTxt, secondDateMessage,
						secondTimeMessage)));
	}

	public HBox getRowDateTime(Label dateLabel, DatePicker datePicker, Label timeLabel, TextField timeTextField,
			Text dateErrorMessage, Text timeErrorMesssge) {
		HBox row = new HBox();
		dateLabel.getStyleClass().add("control-label");
		timeLabel.getStyleClass().add("control-label");
		row.setSpacing(10);
		row.getChildren().addAll(new VBox(new HBox(dateLabel, datePicker), dateErrorMessage),
				new VBox(new HBox(timeLabel, timeTextField), timeErrorMesssge));

		return row;
	}

	public void setDateTime(Date firstDate, Date secondDate) {
		calendar.setTime(firstDate);
		String firstValue = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

		calendar.setTime(secondDate);
		String secondTimeValue = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

		firstTimeTxt.setText(firstValue);
		secondTimeTxt.setText(secondTimeValue);

		firstDateSelected.setValue(DateUtils.asLocalDate(firstDate));
		secondDateSelected.setValue(DateUtils.asLocalDate(secondDate));
	}
	
	public void reset() {
		firstDateSelected.getEditor().clear();
		firstDateSelected.setValue(null);
		
		secondDateSelected.getEditor().clear();
		secondDateSelected.setValue(null);
		
		firstTimeTxt.clear();
		secondTimeTxt.clear();
	}

	public Date getFirstDate() {
		calendar.setTime(DateUtils.asDate(firstDateSelected.getValue()));
		String[] timeInArray = firstTimeTxt.getText().split(":");
		calendar.set(Calendar.HOUR, Integer.parseInt(timeInArray[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(timeInArray[1]));

		return calendar.getTime();
	}

	public Date getSecondDate() {
		calendar.setTime(DateUtils.asDate(secondDateSelected.getValue()));
		String[] timeCheckOutArray = secondTimeTxt.getText().split(":");
		calendar.set(Calendar.HOUR, Integer.parseInt(timeCheckOutArray[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(timeCheckOutArray[1]));

		return calendar.getTime();
	}

	public boolean isValidDateTime() {
		byte errorCount = 0;

		if (firstDateSelected.getValue() == null) {
			errorCount++;
			firstDateMessage.setText(NOT_EMPTY_DATE_MESSAGE);
			firstDateSelected.getStyleClass().add(ERROR_STYLE_CLASS);
		} else {
			firstDateMessage.setText("");
			firstDateSelected.getStyleClass().remove(ERROR_STYLE_CLASS);
		}

		if (firstTimeTxt.getText() == null || firstTimeTxt.getText().isEmpty()) {
			errorCount++;
			firstTimeMessage.setText(NOT_EMPTY_TIME_MESSAGE);
			firstTimeTxt.getStyleClass().add(ERROR_STYLE_CLASS);
		} else {
			firstTimeMessage.setText("");
			firstTimeTxt.getStyleClass().remove(ERROR_STYLE_CLASS);
		}

		if (secondDateSelected.getValue() == null) {
			errorCount++;
			secondDateMessage.setText(NOT_EMPTY_DATE_MESSAGE);
			secondDateSelected.getStyleClass().add(ERROR_STYLE_CLASS);
		} else {
			secondDateMessage.setText("");
			secondDateSelected.getStyleClass().remove(ERROR_STYLE_CLASS);
		}

		if (secondTimeTxt.getText() == null || secondTimeTxt.getText().isEmpty()) {
			errorCount++;
			secondTimeMessage.setText(NOT_EMPTY_TIME_MESSAGE);
			secondTimeTxt.getStyleClass().add(ERROR_STYLE_CLASS);
		} else {
			secondTimeMessage.setText("");
			secondTimeTxt.getStyleClass().remove(ERROR_STYLE_CLASS);
		}

		return errorCount == 0 ? true : false;
	}
	
	public void setLabelPreWidthForAll(int preWidth) {
		this.firstDateLabel.setPrefWidth(preWidth);
		this.secondDateLabel.setPrefWidth(preWidth);
		this.firstTimeLabel.setPrefWidth(preWidth);
		this.secondTimeLabel.setPrefWidth(preWidth);
	}
	
	public void disableDateTimePicker(boolean isDisabled) {
		//Disable just only prevent user select a date.
		this.firstDateSelected.setDisable(isDisabled);
		this.secondDateSelected.setDisable(isDisabled);
		this.firstTimeTxt.setDisable(isDisabled);
		this.secondTimeTxt.setDisable(isDisabled);
		
		this.firstDateSelected.setEditable(!isDisabled);
		this.secondDateSelected.setEditable(!isDisabled);
		this.firstTimeTxt.setEditable(!isDisabled);
		this.secondTimeTxt.setEditable(!isDisabled);
	}

	public int getLabelPreWidth() {
		return labelPreWidth;
	}

	public void setLabelPreWidth(int labelPreWidth) {
		this.labelPreWidth = labelPreWidth;
		setLabelPreWidthForAll(labelPreWidth);
	}

	public boolean isEditAble() {
		return editAble;
	}

	public void setEditAble(boolean editAble) {
		this.editAble = editAble;
		disableDateTimePicker(!editAble);
	}
}
