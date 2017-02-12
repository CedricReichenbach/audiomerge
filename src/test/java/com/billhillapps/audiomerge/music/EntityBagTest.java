package com.billhillapps.audiomerge.music;

import static com.billhillapps.audiomerge.test.MockEntityMatcher.isTestEntity;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.billhillapps.audiomerge.similarity.Decider;
import com.billhillapps.audiomerge.test.MockEntity;

public class EntityBagTest {

	EntityBag<MockEntity> entityBag;

	@Before
	public void setUp() throws Exception {
		Decider<MockEntity> decider = new Decider<MockEntity>() {
			@Override
			public boolean areSimilar(MockEntity a, MockEntity b) {
				if (a.name.length() == 0 | b.name.length() == 0)
					// true iff both empty
					return a.name.length() == b.name.length();

				// true iff first chars match
				return a.name.charAt(0) == b.name.charAt(0);
			}

			@Override
			public int resolve(MockEntity a, MockEntity b) {
				// take lexicographically first, but avoid 0 on equality
				return a.name.compareTo(b.name) * 2 - 1;
			}
		};
		entityBag = new EntityBag<>(decider);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAdd() {
		entityBag.add(new MockEntity("abc", new String[] {}));
		entityBag.add(new MockEntity("def", new String[] {}));
		entityBag.add(new MockEntity("wxyz", new String[] { "foo" }));
		entityBag.add(new MockEntity("wxyz", new String[] { "bar" }));

		assertThat(entityBag.asCollection(), hasItem(isTestEntity("abc")));
		assertThat(entityBag.asCollection(), hasItem(isTestEntity("def")));
		assertThat(entityBag.asCollection(), hasItem(isTestEntity("wxyz", new String[] { "foo", "bar" })));
	}

	@Test
	public void testMergeSimilars() {
		final String[] content = {};
		entityBag.add(new MockEntity("A_1", content));
		entityBag.add(new MockEntity("B_1", content));
		entityBag.add(new MockEntity("B_2", content));
		entityBag.add(new MockEntity("A_2", content));

		entityBag.mergeSimilars();

		assertThat(entityBag.asCollection().size(), is(2));
	}
}
