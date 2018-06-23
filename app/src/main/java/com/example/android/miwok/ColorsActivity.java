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

public class ColorsActivity extends AppCompatActivity {


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
        setContentView(R.layout.activity_colors);

        final ArrayList<Word> udWords = new ArrayList<Word>();

        udWords.add(new Word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        udWords.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        udWords.add(new Word("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        udWords.add(new Word("grey", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        udWords.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        udWords.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        udWords.add(new Word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        udWords.add(new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        //FlashCardAdapter items = new FlashCardAdapter(this, words);
        WordAdapter udItems = new WordAdapter(this, udWords, R.color.category_colors);
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
