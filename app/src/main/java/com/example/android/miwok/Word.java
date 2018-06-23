package com.example.android.miwok;

public class Word {
    protected String miwokTranslation = "";
    protected String defaultTranslation = "";
    private int imgResId = 0;
    private int audioResId = 0;

    public int getImgResId() {
        return imgResId;
    }

    public int getAudioResId() {
        return audioResId;
    }

    public void setAudioResId(int audioResId) {
        this.audioResId = audioResId;
    }

    public Word(String defaultTranslation, String miwokTranslation) {
        this.miwokTranslation = miwokTranslation;
        this.defaultTranslation = defaultTranslation;
    }

    public Word(String miwokTranslation, String defaultTranslation, int imgResId) {
        this.miwokTranslation = miwokTranslation;
        this.defaultTranslation = defaultTranslation;
        this.imgResId = imgResId;
    }


    public Word(String miwokTranslation, String defaultTranslation, int imgResId, int audioResId) {
        this.miwokTranslation = miwokTranslation;
        this.defaultTranslation = defaultTranslation;
        this.imgResId = imgResId;
        this.audioResId = audioResId;
    }

    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }


}
