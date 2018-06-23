package com.example.android.miwok;

public class FlashCard extends Word {

    protected int imageResId = 0;
    public FlashCard(String defaultTranslation, String miwokTranslation, int imageResId) {
        super(defaultTranslation, miwokTranslation);
        this.imageResId = imageResId;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
