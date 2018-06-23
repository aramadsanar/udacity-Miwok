package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ColorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ColorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ColorFragment() {
        // Required empty public constructor
    }

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
            if (mMediaPlayer != null ) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        mMediaPlayer.start();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                       releaseMediaPlayer();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        releaseMediaPlayer();
                        break;
                }
            }
        }
    };

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
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ColorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ColorFragment newInstance(String param1, String param2) {
        ColorFragment fragment = new ColorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_color, container, false);

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
        WordAdapter udItems = new WordAdapter(getContext(), udWords, R.color.category_colors);
        ListView lv = (ListView) v.findViewById(R.id.list);
        lv.setAdapter(udItems);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                releaseMediaPlayer();
                int stat = am.requestAudioFocus(amofcl, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (stat == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getContext(), udWords.get(position).getAudioResId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mpOc);
                }
            }
        });

        am = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}


/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
 */

