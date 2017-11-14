package com.etsdk.app.huov7.model;


public class TjMustPlay {
    public TjMustPlay(String[] playNames, int[] playImgs) {
        this.playNames = playNames;
        this.playImgs = playImgs;
    }

    private String[] playNames;
    private int[] playImgs;

    public String[] getPlayNames() {
        return playNames;
    }

    public void setPlayNames(String[] playNames) {
        this.playNames = playNames;
    }

    public int[] getPlayImgs() {
        return playImgs;
    }

    public void setPlayImgs(int[] playImgs) {
        this.playImgs = playImgs;
    }
}