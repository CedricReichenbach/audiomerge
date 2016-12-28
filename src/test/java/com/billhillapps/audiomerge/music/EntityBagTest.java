package com.billhillapps.audiomerge.music;

import static com.billhillapps.audiomerge.test.TestEntityMatcher.isTestEntity;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.billhillapps.audiomerge.similarity.Decider;
import com.billhillapps.audiomerge.test.TestEntity;

public class EntityBagTest {

	EntityBag<TestEntity> entityBag;

	@Before
	public void setUp() throws Exception {
		Decider<TestEntity> decider = new Decider<TestEntity>() {
			@Override
			public boolean areSimilar(TestEntity a, TestEntity b) {
				if (a.name.length() == 0 | b.name.length() == 0)
					// true iff both empty
					return a.name.length() == b.name.length();

				// true iff first chars match
				return a.name.charAt(0) == b.name.charAt(0);
			}

			@Override
			public int resolve(TestEntity a, TestEntity b) {
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
		entityBag.add(new TestEntity("abc", new String[] {}));
		entityBag.add(new TestEntity("def", new String[] {}));
		entityBag.add(new TestEntity("wxyz", new String[] { "foo" }));
		entityBag.add(new TestEntity("wxyz", new String[] { "bar" }));

		assertThat(entityBag.asCollection(), hasItem(isTestEntity("abc")));
		assertThat(entityBag.asCollection(), hasItem(isTestEntity("def")));
		assertThat(entityBag.asCollection(), hasItem(isTestEntity("wxyz", new String[] { "foo", "bar" })));
	}
}
