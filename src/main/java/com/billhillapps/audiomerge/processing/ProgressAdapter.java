package com.billhillapps.audiomerge.processing;

import java.util.function.BiConsumer;

public class ProgressAdapter implements ProgressAware {

	private final ProgressManager progressAdapter = new ProgressManager();

	protected void setProgress(double progress) {
		progressAdapter.setProgress(progress);
	}

	protected void setCurrentOperation(String currentOperation) {
		progressAdapter.setCurrentOperation(currentOperation);
	}

	@Override
	public double getProgress() {
		return progressAdapter.getProgress();
	}

	@Override
	public void addProgressListener(BiConsumer<Double, String> listener) {
		progressAdapter.addProgressListener(listener);
	}

	@Override
	public void removeProgressListener(BiConsumer<Double, String> listener) {
		progressAdapter.removeProgressListener(listener);
	}

}
