package com.example.mediaplayerpreferences;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Debug;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

    private boolean shuffle;
    private boolean continue_after_track;
    private boolean source_ringtone;

    /**
     * List of Sounds that can be played in the form of SongObjects
     */
    private static ArrayList<SongObject> songsList = new ArrayList<SongObject>();

    private Button mPlayPauseButton;
    private SharedPreferences prefs;

    private static final String TAG = "MyMediaPlayerActivity";
    private static final int SONG_CHOOSER = 1;
    private static final int PREFERENCES = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_player_main);

        songTitleLabel = (TextView) findViewById(R.id.songTitle);
        mPlayPauseButton = (Button) findViewById(R.id.playpausebutton);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        /*prefs.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                loadPreferences(sharedPreferences);
            }
        });*/
        loadPreferences(prefs);

        // Initialize the media player
        mp = new MediaPlayer();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (continue_after_track) {
                    nextClick(null);
                } else {
                    updateUI();
                }
            }
        });

        // Getting all songs in a list
        populateSongsList();

        // By default play first song if there is one in the list
        playSong(0);
    }

    private void loadPreferences(SharedPreferences prefs) {
        Resources res = getResources();
        shuffle = prefs.getBoolean(res.getString(R.string.mp_shuffle_pref), false);
        continue_after_track = prefs.getBoolean(res.getString(R.string.mp_continue_pref), true);
        Log.i(TAG, "source: " + prefs.getString(res.getString(R.string.mp_source_pref), "music"));
        if (prefs.getString(res.getString(R.string.mp_source_pref), "music")
                .equals("music")) {
            source_ringtone = false;
        } else {
            source_ringtone = true;
        }
        populateSongsList();
        Log.v(TAG, "Shuffle:" + Boolean.toString(shuffle));
        Log.v(TAG, "Continue after track:" + continue_after_track);
        Log.v(TAG, "Ringtone source:" + source_ringtone);
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
            Intent songChooser = new Intent(this, SongList.class);
            startActivityForResult(songChooser, SONG_CHOOSER);

            return true;
        case R.id.menu_preferences:
            // Display Settings page
            //TODO
            Intent preferences = new Intent(this, MediaPreferences.class);
            startActivityForResult(preferences, PREFERENCES);

            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    

    /**
     * Helper function to play a song at a specific index of songsList
     * @param songIndex - index of song to be played
     */
    public void playSong(int songIndex){
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

                updateUI();

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
        String[] mProjection = new String[] {
                MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA};
        String mSelectionClause;

        if (source_ringtone) {
            mSelectionClause = MediaStore.Audio.Media.IS_RINGTONE + " = 1";
        } else {
            mSelectionClause = MediaStore.Audio.Media.IS_MUSIC + " = 1";
        }

        // Get a Cursor object from the content URI
        Cursor mCursor = getContentResolver().query(
                MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
                mProjection,
                mSelectionClause,
                null,
                null);
        
        // Use the cursor to loop through the results and add them to 
        //        the songsList as SongObjects
        songsList.clear();
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    //Log.v(TAG, mCursor.getString(0));
                    songsList.add(new SongObject(mCursor.getString(0), mCursor.getString(1)));
                } while (mCursor.moveToNext());
            }
            mCursor.close();
        }
    }

    /**
     * Get song list for display in ListView
     * @return list of Songs 
     */
    public static ArrayList<SongObject> getSongsList(){
        return songsList;
    }

    public void nextClick(View v) {
        if (shuffle) {
            Random rand = new Random();
            currentSongIndex = rand.nextInt(songsList.size());
        } else {
            currentSongIndex = (currentSongIndex + 1) % songsList.size();
        }
        playSong(currentSongIndex);
    }

    public void prevClick(View v) {
        currentSongIndex = (currentSongIndex - 1) % songsList.size();
        playSong(currentSongIndex);
    }

    public void playPauseClick(View v) {
        if (mp.isPlaying()) {
            mp.pause();
        } else {
            mp.start();
        }
        updateUI();
    }

    private void updateUI() {
        if (mp.isPlaying()) {
            // Changing Button Image to pause image
            mPlayPauseButton.setBackgroundResource(R.drawable.btn_pause);
        } else {
            // Changing Button Image to play image
            mPlayPauseButton.setBackgroundResource(R.drawable.btn_play);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SONG_CHOOSER:
                    currentSongIndex = data.getIntExtra("songIndex", 0);
                    playSong(currentSongIndex);
                    break;
            }
        } else {
            switch (requestCode) {
                case PREFERENCES:
                    loadPreferences(prefs);
                    break;
            }
        }
    }
}
