package com.billhillapps.audiomerge.processing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiConsumer;

public class ProgressManager implements ProgressAware {

	private final Collection<BiConsumer<Double, String>> listeners = new ArrayList<>();

	private double progress = 0;
	private String currentOperation = "";

	@Override
	public double getProgress() {
		return this.progress;
	}

	@Override
	public void addProgressListener(BiConsumer<Double, String> listener) {
		listeners.add(listener);
	}

	public void setProgress(double progress) {
		this.progress = progress;

		listeners.forEach(listener -> listener.accept(this.getProgress(), this.getCurrentOperation()));
	}

	@Override
	public String getCurrentOperation() {
		return currentOperation;
	}

	public void setCurrentOperation(String currentOperation) {
		this.currentOperation = currentOperation;
	}

	@Override
	public void removeProgressListener(BiConsumer<Double, String> listener) {
		listeners.remove(listener);
	}
}
