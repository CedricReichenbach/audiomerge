package com.billhillapps.audiomerge.ui;

import java.nio.file.Path;

import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayer extends Button {

	private static final String PLAY_ICON = "▶";
	private static final String STOP_ICON = "◼";

	private final MediaPlayer mediaPlayer;
	private boolean playing = false;

	public AudioPlayer(final Path path) {
		super(PLAY_ICON);
		
		this.getStyleClass().addAll("dynamic", "play-stop");

		mediaPlayer = new MediaPlayer(new Media(path.toUri().toString()));

		this.setOnAction(event -> {
			if (playing)
				stop();
			else
				play();
		});
	}

	private void play() {
		mediaPlayer.play();
		playing = true;
		this.setText(STOP_ICON);
	}

	private void stop() {
		mediaPlayer.stop();
		playing = false;
		this.setText(PLAY_ICON);
	}
}
