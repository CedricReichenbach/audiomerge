package com.billhillapps.audiomerge.ui;

import com.billhillapps.audiomerge.processing.MergeManager;
import com.billhillapps.audiomerge.ui.pages.FinishPage;
import com.billhillapps.audiomerge.ui.pages.OperationPage;
import com.billhillapps.audiomerge.ui.pages.StartPage;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AudioMergeUI extends Application {

	public static final double SPACING = 12;

	public static final double DEFAULT_WIDTH = 800;
	public static final double DEFAULT_HEIGHT = 600;

	public static final String STYLESHEET = ClassLoader.getSystemResource("application.css").toExternalForm();
	public static final Image LOGO_LARGE = new Image(ClassLoader.getSystemResourceAsStream("logo_circled.png"));
	public static final Image LOGO_TINY = new Image(ClassLoader.getSystemResourceAsStream("logo.png"));

	private Stage primaryStage;
	private StartPage startPage;
	private OperationPage operationPage;
	private FinishPage finishPage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		startPage = new StartPage(primaryStage);
		startPage.onStart(this::showOperationPage);

		operationPage = new OperationPage(this::showFinishPage);

		finishPage = new FinishPage(getHostServices()::showDocument);

		showStartPage();

		primaryStage.setTitle("AudioMerge");
		primaryStage.getIcons().addAll(LOGO_TINY, LOGO_LARGE);
		primaryStage.show();
	}

	private void showStartPage() {
		primaryStage.setScene(startPage.getScene());
	}

	private void showOperationPage(MergeManager mergeManager) {
		primaryStage.setScene(operationPage.getScene());
		operationPage.runMergeManager(mergeManager);
	}

	private void showFinishPage(MergeManager mergeManager) {
		primaryStage.setScene(finishPage.getScene());
		finishPage.showFinish(mergeManager);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
