package com.billhillapps.audiomerge.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ThemedAlert extends Alert {

	public ThemedAlert(AlertType alertType) {
		super(alertType);
		initTheming();
	}

	public ThemedAlert(AlertType alertType, String contentText, ButtonType... buttons) {
		super(alertType, contentText, buttons);
		initTheming();
	}

	private void initTheming() {
		this.getDialogPane().getStylesheets().add(AudioMergeUI.STYLESHEET);
	}
}
