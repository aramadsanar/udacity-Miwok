package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NumbersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NumbersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumbersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
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
                    releaseMediaPlayer();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    releaseMediaPlayer();
                    break;
            }
        }
    };

    public NumbersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NumbersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NumbersFragment newInstance(String param1, String param2) {
        NumbersFragment fragment = new NumbersFragment();
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
        View v = inflater.inflate(R.layout.fragment_numbers, container, false);
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

        WordAdapter udItems = new WordAdapter(getContext(), udWords, R.color.category_numbers);
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

    ArrayList<String> numbersArr  = null;
    private MediaPlayer mMediaPlayer = null;

    MediaPlayer.OnCompletionListener mpOc = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
            am.abandonAudioFocus(amofcl);
        }
    };


/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);


    }*/

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
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
