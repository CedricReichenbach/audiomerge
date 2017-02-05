package com.billhillapps.audiomerge.ui.choosers;

import java.util.function.Consumer;

import com.billhillapps.audiomerge.music.Album;
import com.billhillapps.audiomerge.ui.GridDecisionOption;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

public class AlbumChooser extends DecisionChooser<Album> {

	public AlbumChooser(Runnable focusTrigger, Consumer<EventHandler<? super KeyEvent>> keyPressedRegistrar) {
		super("Duplicate album detected",
				"Which one to proceed with? Contents of the other will be merged in, no songs will be lost.",
				focusTrigger, keyPressedRegistrar);
	}

	@Override
	protected GridDecisionOption buildOption(Album album) {
		GridDecisionOption grid = new GridDecisionOption();

		Label label = new Label("Album with title");
		grid.add(label, 0, 0);

		Label title = new Label(album.getAlbumTitle());
		title.getStyleClass().add("bold");
		grid.add(title, 0, 1);

		return grid;
	}

}
