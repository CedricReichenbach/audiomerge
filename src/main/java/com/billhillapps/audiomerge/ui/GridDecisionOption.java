package com.billhillapps.audiomerge.ui;

import static com.billhillapps.audiomerge.ui.AudioMergeUI.SPACING;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

// XXX: Maybe extract interface extending Pane and Toggle
public class GridDecisionOption extends GridPane implements Toggle {

	private static final String GRID_OPTION_CLASS = "grid-option";
	private static final String SELECTED_CLASS = "selected";

	private final SimpleBooleanProperty selectedProperty = new SimpleBooleanProperty(false);

	private ToggleGroup toggleGroup;
	private Object userData;

	public GridDecisionOption() {
		super();
		
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(SPACING));

		this.getStyleClass().add(GRID_OPTION_CLASS);

		selectedProperty.addListener((observerable, oldValue, newValue) -> {
			toggleGroup.selectToggle(this);

			if (newValue) {
				if (!this.getStyleClass().contains(SELECTED_CLASS))
					this.getStyleClass().add(SELECTED_CLASS);
			} else
				this.getStyleClass().remove(SELECTED_CLASS);
		});

		this.setOnMouseClicked(event -> {
			this.setSelected(!this.isSelected());
		});
	}

	@Override
	public ToggleGroup getToggleGroup() {
		return toggleGroup;
	}

	@Override
	public void setToggleGroup(ToggleGroup toggleGroup) {
		this.toggleGroup = toggleGroup;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObjectProperty<ToggleGroup> toggleGroupProperty() {
		JavaBeanObjectPropertyBuilder<ToggleGroup> builder = new JavaBeanObjectPropertyBuilder<ToggleGroup>();
		try {
			return builder.bean(toggleGroup).build();
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isSelected() {
		return selectedProperty.get();
	}

	@Override
	public void setSelected(boolean selected) {
		selectedProperty.set(selected);
	}

	@Override
	public BooleanProperty selectedProperty() {
		return selectedProperty;
	}

	@Override
	public Object getUserData() {
		return userData;
	}

	@Override
	public void setUserData(Object value) {
		this.userData = value;
	}
}
