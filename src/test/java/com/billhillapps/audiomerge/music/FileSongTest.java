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

	private Path id3v1File;
	private Path noTagFile;
	private Path writeProtectedFile;

	@Before
	public void setUp() throws Exception {
		destinationPath = destinationFolder.getRoot().toPath();

		Path cornerCasesDir = Paths.get(ClassLoader.getSystemResource("corner-cases").toURI());
		id3v1File = cornerCasesDir.resolve("id3v1.mp3");
		noTagFile = cornerCasesDir.resolve("no-tag.mp3");
		writeProtectedFile = cornerCasesDir.resolve("write-protected.mp3");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void canLoadFilesWithNoTag() throws Exception {
		Song noTagSong = new FileSong(noTagFile);

		assertThat(noTagSong.getArtistName(), isEmptyOrNullString());
	}

	@Test
	public void canOverrideArtistOfID3v1() throws Exception {
		final Song id3v1Song = new FileSong(id3v1File);

		id3v1Song.setArtistName("foobar");

		assertThat(id3v1Song.getArtistName(), is("foobar"));

		id3v1Song.saveTo(destinationPath);
		final FileSong reloadedSong = new FileSong(destinationPath.resolve("id3v1.mp3"));

		assertThat(reloadedSong.getArtistName(), is("foobar"));
	}

	@Test
	public void copyOfWriteProtectedCanBeOverridden() throws Exception {
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
