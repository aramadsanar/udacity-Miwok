package com.example.android.miwok;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class FlashCardAdapter extends ArrayAdapter<FlashCard> {
    public FlashCardAdapter(android.content.Context context, java.util.ArrayList<FlashCard> arr) {
        super(context, 0, arr);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        //View listItemView = convertView;
        View listItemView = convertView; //android.view.LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        if (listItemView == null) {
            listItemView = android.view.LayoutInflater.from(getContext()).inflate(R.layout.list_item_flash_card, parent, false);
            //listItemView = android.view.LayoutInflater.from(getContext()).inflate(
                   // R.layout.list_item, parent, false);

         }

            FlashCard w = getItem(position);
            android.widget.TextView miwok = (android.widget.TextView) listItemView.findViewById(R.id.miwokText);
            miwok.setText(w.getMiwokTranslation());

            android.widget.TextView def = (android.widget.TextView) listItemView.findViewById(R.id.englishText);
            def.setText(w.getDefaultTranslation());

            ImageView flashPic = (ImageView) listItemView.findViewById(R.id.flashPic);
            flashPic.setImageResource(w.getImageResId());

            return listItemView;

    }



}
