package com.beyond.entity;

import com.beyond.f.F;
import com.beyond.invisibility.Acceleration;
import com.beyond.invisibility.RefreshTimer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Container {
    private Acceleration globalAcceleration;
    private List<Item> items = new ArrayList<>();
    private RefreshTimer refreshTimer;
    private Canvas canvas;

    public Container(Acceleration globalAcceleration, RefreshTimer refreshTimer) {
        this.globalAcceleration = globalAcceleration;
        this.refreshTimer = refreshTimer;
    }

    private void initItem(Item item) {
        item.addAcceleration(globalAcceleration);
        item.setRefreshTimer(refreshTimer);
        item.setContainer(this);
    }

    public void active() {
        Timer timer = refreshTimer.getTimer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Item item : items) {
                    item.freeMove(F.DEFAULT_SPEED_FACTOR);
                }
                //To Refresh
                canvas.repaint();
                canvas.paint(canvas.getGraphics());
            }
        }, 0, refreshTimer.getRefreshLength());
    }


    public RefreshTimer getRefreshTimer() {
        return refreshTimer;
    }

    public void setRefreshTimer(RefreshTimer refreshTimer) {
        this.refreshTimer = refreshTimer;
    }

    public void addItem(Item item) {
        items.add(item);
        initItem(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
}
