package com.billhillapps.audiomerge.ui;

import com.billhillapps.audiomerge.processing.MergeManager;
import com.billhillapps.audiomerge.ui.pages.OperationPage;
import com.billhillapps.audiomerge.ui.pages.StartPage;

import javafx.application.Application;
import javafx.stage.Stage;

public class AudioMergeUI extends Application {

	public static final double SPACING = 12;

	public static final double DEFAULT_WIDTH = 600;
	public static final double DEFAULT_HEIGHT = 400;

	private Stage primaryStage;
	private StartPage startPage;
	private OperationPage operationPage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		startPage = new StartPage(primaryStage);
		startPage.onStart(this::showOperationPage);

		operationPage = new OperationPage(primaryStage);

		showStartPage();

		primaryStage.setTitle("AudioMerge");
		primaryStage.show();
	}

	private void showStartPage() {
		primaryStage.setScene(startPage.getScene());
	}

	private void showOperationPage(MergeManager mergeManager) {
		primaryStage.setScene(operationPage.getScene());
		operationPage.runMergeManager(mergeManager);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
