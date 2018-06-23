package com.example.android.miwok;

import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WordAdapter extends ArrayAdapter<Word> {
    private int colorId = -1;

    public WordAdapter(android.content.Context context, java.util.ArrayList<Word> arr, int colorId) {
        super(context, 0, arr);
        this.colorId = colorId;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        //View listItemView = convertView;
        View listItemView = convertView; //android.view.LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        Word w = getItem(position);

        if (listItemView == null) {
//            if (w.getImgResId() == 0)
//                listItemView = android.view.LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
//            else
            listItemView = android.view.LayoutInflater.from(getContext()).inflate(R.layout.list_item_flash_card, parent, false);
            //listItemView = android.view.LayoutInflater.from(getContext()).inflate(
            // R.layout.list_item, parent, false);
        }


        android.widget.TextView miwok = (android.widget.TextView) listItemView.findViewById(R.id.miwokText);
        miwok.setText(w.getMiwokTranslation());

        android.widget.TextView def = (android.widget.TextView) listItemView.findViewById(R.id.englishText);
        def.setText(w.getDefaultTranslation());

        ImageView img = (ImageView) listItemView.findViewById(R.id.flashPic);
        LinearLayout texts = (LinearLayout) listItemView.findViewById(R.id.texts);
        ImageView playButton = (ImageView) listItemView.findViewById(R.id.playButton);

        if (w.getImgResId() != 0)
            img.setImageResource(w.getImgResId());

        else
            img.setVisibility(View.GONE);

        texts.setBackgroundColor(getContext().getResources().getColor(colorId));
        playButton.setBackgroundColor(getContext().getResources().getColor(colorId));
        if (getItem(position).getAudioResId() == 0)
            getItem(position).setAudioResId(R.raw.quack_5);
        /*listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final MediaPlayer mp = MediaPlayer.create(getContext(), getItem(position).getAudioResId());
                //mp.start();
                //mp.release();

                new Thread(new Runnable() {
                    public void run() {
                        MediaPlayer mp = MediaPlayer.create(getContext(), getItem(position).getAudioResId());
                        mp.start();
                        while (mp.isPlaying()) { }
                        mp.release();
                    }

                }).start();

            }
        });*/

        return listItemView;

    }


}
