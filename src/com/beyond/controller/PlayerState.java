package com.beyond.controller;

public class PlayerState {
    private boolean isLeftBlocked;
    private boolean isRightBlocked;
    private boolean isUpBlocked;
    private boolean isDownBlocked;

    public boolean isLeftBlocked() {
        return isLeftBlocked;
    }

    public void setLeftBlocked(boolean leftBlocked) {
        isLeftBlocked = leftBlocked;
    }

    public boolean isRightBlocked() {
        return isRightBlocked;
    }

    public void setRightBlocked(boolean rightBlocked) {
        isRightBlocked = rightBlocked;
    }

    public boolean isUpBlocked() {
        return isUpBlocked;
    }

    public void setUpBlocked(boolean upBlocked) {
        isUpBlocked = upBlocked;
    }

    public boolean isDownBlocked() {
        return isDownBlocked;
    }

    public void setDownBlocked(boolean downBlocked) {
        isDownBlocked = downBlocked;
    }

    public boolean isFlying(){
        return !this.isDownBlocked() && !this.isLeftBlocked() && !this.isRightBlocked() && !this.isUpBlocked();
    }

    public void clear(){
        this.setLeftBlocked(false);
        this.setRightBlocked(false);
        this.setUpBlocked(false);
        this.setDownBlocked(false);
    }
}
