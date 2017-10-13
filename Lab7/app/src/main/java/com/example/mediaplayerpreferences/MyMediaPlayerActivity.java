package com.example.mediaplayerpreferences;


import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

/**
 * CPRE 388 - Labs
 * 
 * Copyright 2013
 */
public class MyMediaPlayerActivity extends Activity {

	/**
	 * Other view elements
	 */
	private TextView songTitleLabel;

	/**
	 *  media player:
	 *  http://developer.android.com/reference/android/media/MediaPlayer.html 
	 */
	private MediaPlayer mp;

	/**
	 * Index of the current song being played
	 */
	private int currentSongIndex = 0;

	/**
	 * List of Sounds that can be played in the form of SongObjects
	 */
	private static ArrayList<SongObject> songsList = new ArrayList<SongObject>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_player_main);

		songTitleLabel = (TextView) findViewById(R.id.songTitle);

		// Initialize the media player
		mp = new MediaPlayer();

		// Getting all songs in a list
		populateSongsList();

		// By default play first song if there is one in the list
		playSong(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.media_player_menu, menu);
		return true;
	} 

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_choose_song:
			// Open SongList to display a list of audio files to play
			//TODO


			return true;
		case R.id.menu_preferences:
			// Display Settings page
			//TODO


			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	

	/**
	 * Helper function to play a song at a specific index of songsList
	 * @param songIndex - index of song to be played
	 */
	public void  playSong(int songIndex){
		// Play song if index is within the songsList
		if (songIndex < songsList.size() && songIndex >= 0) {
			try {
				mp.stop();
				mp.reset();
				mp.setDataSource(songsList.get(songIndex).getFilePath());
				mp.prepare();
				mp.start();
				// Displaying Song title
				String songTitle = songsList.get(songIndex).getTitle();
				songTitleLabel.setText(songTitle);

				// Changing Button Image to pause image
				((Button)findViewById(R.id.playpausebutton)).setBackgroundResource(R.drawable.btn_pause);

				// Update song index
				currentSongIndex = songIndex;

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} else if (songsList.size() > 0) {
			playSong(0);
		}
	}


	/** 
	 * Get list of info for all sounds to be played
	 */
	public void populateSongsList(){
		//TODO add all songs from audio content URI to this.songsList

		// Get a Cursor object from the content URI 
		
		// Use the cursor to loop through the results and add them to 
		//		the songsList as SongObjects
	
	}

	/**
	 * Get song list for display in ListView
	 * @return list of Songs 
	 */
	public static ArrayList<SongObject> getSongsList(){
		return songsList;
	}

}
