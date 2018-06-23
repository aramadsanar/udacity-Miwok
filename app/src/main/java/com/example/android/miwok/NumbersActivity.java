/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    ArrayList<String> numbersArr  = null;
    private MediaPlayer mMediaPlayer = null;

    MediaPlayer.OnCompletionListener mpOc = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
            am.abandonAudioFocus(amofcl);
        }
    };

    AudioManager am = null;
    AudioManager.OnAudioFocusChangeListener amofcl = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch(focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    mMediaPlayer.start();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    releaseMediaPlayer();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        final ArrayList<Word> udWords = new ArrayList<Word>();

        udWords.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        udWords.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        udWords.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        udWords.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        udWords.add(new Word("five", "massokka", R.drawable.number_five , R.raw.number_five));
        udWords.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        udWords.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        udWords.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        udWords.add(new Word("nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
        udWords.add(new Word("ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));

        WordAdapter udItems = new WordAdapter(this, udWords, R.color.category_numbers);
        ListView lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(udItems);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                releaseMediaPlayer();
                int stat = am.requestAudioFocus(amofcl, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (stat == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getApplicationContext(), udWords.get(position).getAudioResId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mpOc);
                }
            }
        });
        am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
        am.abandonAudioFocus(amofcl);
    }


    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }
}
