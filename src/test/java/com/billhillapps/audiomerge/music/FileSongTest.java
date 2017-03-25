package com.billhillapps.audiomerge.music;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileSongTest {

	@Rule
	public TemporaryFolder destinationFolder = new TemporaryFolder();
	private Path destinationPath;

	private Path cornerCasesDir;

	@Before
	public void setUp() throws Exception {
		destinationPath = destinationFolder.getRoot().toPath();

		cornerCasesDir = Paths.get(ClassLoader.getSystemResource("corner-cases").toURI());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void canLoadFilesWithNoTag() throws Exception {
		Song noTagSong = new FileSong(cornerCasesDir.resolve("no-tag.mp3"));

		assertThat(noTagSong.getArtistName(), isEmptyOrNullString());
	}

	@Test
	public void canOverrideArtistOfID3v1() throws Exception {
		final Song id3v1Song = new FileSong(cornerCasesDir.resolve("id3v1.mp3"));

		id3v1Song.setArtistName("foobar");

		assertThat(id3v1Song.getArtistName(), is("foobar"));

		id3v1Song.saveTo(destinationPath);
		final FileSong reloadedSong = new FileSong(destinationPath.resolve("id3v1.mp3"));

		assertThat(reloadedSong.getArtistName(), is("foobar"));
	}

	@Test
	public void copyOfWriteProtectedCanBeOverridden() throws Exception {
		final Path writeProtectedFile = cornerCasesDir.resolve("write-protected.mp3");
		writeProtectedFile.toFile().setReadOnly();

		final Song writeProtectedSong = new FileSong(writeProtectedFile);

		assertThat(writeProtectedSong.getArtistName(), is(not("foobar")));
		writeProtectedSong.setArtistName("foobar");
		assertThat(writeProtectedSong.getArtistName(), is("foobar"));

		writeProtectedSong.saveTo(destinationPath);

		final Song reloadedSong = new FileSong(destinationPath.resolve(writeProtectedFile.getFileName()));

		assertThat(reloadedSong.getArtistName(), is("foobar"));
	}
}
