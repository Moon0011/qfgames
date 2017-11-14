package com.etsdk.app.huov7.model;


import java.util.List;

public class TjMustPlay {
    public TjMustPlay(List<GameBean> gameBeans) {
        this.gameBeans = gameBeans;
    }

    private List<GameBean> gameBeans;

    public List<GameBean> getGameBeans() {
        return gameBeans;
    }

    public void setGameBeans(List<GameBean> gameBeans) {
        this.gameBeans = gameBeans;
    }
}