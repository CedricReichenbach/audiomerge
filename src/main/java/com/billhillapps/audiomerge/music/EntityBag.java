package com.billhillapps.audiomerge.music;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.billhillapps.audiomerge.processing.ProgressAdapter;
import com.billhillapps.audiomerge.similarity.Decider;

/**
 * Bag of {@link Entity}s. It's able to find items based on
 * {@link Entity#shallowEquals} and detect/resolve potential matches based on a
 * provided {@link Decider}.
 * 
 * @author Cedric Reichenbach
 *
 * @param <T>
 *            Type of items in the set
 */
public class EntityBag<T extends Entity> extends ProgressAdapter {

	private final List<T> items = new ArrayList<>();
	private final Decider<T> decider;

	public EntityBag(Decider<T> decider) {
		this.decider = decider;
	}

	/**
	 * Get a collection of items in this bag.
	 * 
	 * The returned collection is a shallow copy, so adding or removing items
	 * won't affect this bag, but modifying contained items will be reflected
	 * here.
	 * 
	 * @return A shallow copy of items in this bag in the form of a collection
	 */
	public Collection<T> asCollection() {
		return new ArrayList<>(items);
	}

	/**
	 * Insert a new item. If the item has no shallow equality match, simply add
	 * it as is. In case of a collision, merge the two.
	 * 
	 * Similarity is not checked yet, everything is added naively. Merging
	 * similars happens in {@link #mergeSimilars}.
	 * 
	 * @param item
	 */
	public void add(T newItem) {
		// XXX: What if two items are #equals, but not #shallowEquals nor
		// #areSimilar? Might lead to inconsistencies (but should never occur
		// though)
		for (T item : items) {
			if (item.shallowEquals(newItem)) {
				item.mergeIn(newItem);
				return;
			}
		}

		items.add(newItem);
	}

	/**
	 * Search repeatedly for similar items and merge them, one pair at a time.
	 * Once no more pairs are found, the algorithm stops and returns the total
	 * number of merges.
	 * 
	 * @return Number of items reduced by merges
	 */
	public int mergeSimilars() {
		int count = 0;
		while (mergeOneSimilarPair())
			count++;
		return count;
	}

	/**
	 * Search though the Cartesian product of this set (all possible combination
	 * of pairs) with itself to find and merge similar items, based on the
	 * {@link Decider} of this {@link EntityBag}.
	 * 
	 * @return boolean indicating whether a pair was found and removed
	 */
	private boolean mergeOneSimilarPair() {
		T toBeRemoved = null;

		mainloop: for (int a = 0; a < items.size(); a++) {
			for (int b = 0; b < items.size(); b++) {
				if (a >= b)
					continue;

				T itemA = items.get(a);
				T itemB = items.get(b);

				if (!decider.areSimilar(itemA, itemB))
					continue;

				int pickIndicator = decider.resolve(itemA, itemB);
				if (pickIndicator == 0)
					continue;

				if (pickIndicator < 0) {
					itemA.mergeIn(itemB);
					toBeRemoved = itemB;
					break mainloop;
				} else {
					itemB.mergeIn(itemA);
					toBeRemoved = itemA;
					break mainloop;
				}
			}
		}

		if (toBeRemoved != null) {
			items.remove(toBeRemoved);
			return true;
		}

		return false;
	}

	public void addAll(EntityBag<T> otherBag) {
		// XXX: Maybe consider weight for progress
		setProgress(0);
		for (int i = 0; i < otherBag.items.size(); i++) {
			this.add(otherBag.items.get(i));
			setProgress(1d * i / otherBag.size());
		}
	}

	public int size() {
		return items.size();
	}

	public void forEach(Consumer<T> consumer) {
		items.forEach(consumer);
	}

	public Stream<T> stream() {
		return items.stream();
	}

	@Override
	public double getWeight() {
		return this.items.stream().map(item -> item.getWeight()).reduce(0d, (a, b) -> a + b);
	}
}
