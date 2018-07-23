package com.beyond.invisibility;

import java.util.Timer;

public class RefreshTimer {

    private Long refreshLength;
    private Timer timer;

    public RefreshTimer(Long refreshLength) {
        this.refreshLength = refreshLength;
    }

    public Long getRefreshLength(){
        return refreshLength;
    }

    public Timer getTimer(){
        if(timer==null){
            timer = new Timer();
        }
        return timer;
    }
}
