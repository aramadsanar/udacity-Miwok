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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer = null;
    MediaPlayer.OnCompletionListener mpOc = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
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
        setContentView(R.layout.activity_family);

        final ArrayList<Word> udWords = new ArrayList<Word>();
        udWords.add(new Word("father", "әpә", R.drawable.family_father, R.raw.family_father));
        udWords.add(new Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        udWords.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        udWords.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        udWords.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        udWords.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        udWords.add(new Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        udWords.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        udWords.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        udWords.add(new Word("grandfather", "na’aacha", R.drawable.family_grandfather, R.raw.family_grandfather));

        WordAdapter udItems = new WordAdapter(this, udWords, R.color.category_family);
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
