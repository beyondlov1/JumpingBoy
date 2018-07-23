package com.beyond.entity;

import com.beyond.invisibility.Acceleration;
import com.beyond.invisibility.Position;
import com.beyond.invisibility.RefreshTimer;
import com.beyond.invisibility.Velocity;
import com.beyond.utils.LoggerUtils;
import sun.rmi.runtime.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Item {

    private BigDecimal mass;

    private BigDecimal width;
    private BigDecimal height;
    /**
     * 离原点最近的点
     */
    private Position startPosition;
    /**
     * 离原点最远的点
     */
    private Position endPosition;

    private Acceleration acceleration;
    private List<Acceleration> accelerations;
    private Velocity velocity;

    private RefreshTimer refreshTimer;
    private Container container;

    public Item(BigDecimal mass, Position startPosition, Position endPosition, Velocity velocity) {
        this.mass = mass;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.velocity = velocity;
        this.accelerations = new ArrayList<>();
        this.acceleration = new Acceleration();
        init();
    }

    private void init() {
        this.width = (endPosition.getX().subtract(startPosition.getX())).abs();
        this.height = (endPosition.getY().subtract(startPosition.getY())).abs();
    }

    public Acceleration getAcceleration() {
        return acceleration;
    }

    /**
     * 推荐使用addAcceleration()
     *
     * @param acceleration
     */
    @Deprecated
    public void setAcceleration(Acceleration acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * @param speedFactor 代表动画播放速度, 数值越大越快. 数值为1时, 每个像素代表一米(推荐10)
     */
    public void freeMove(Integer speedFactor) {
        if (mass == null) {
            return;
        }

        calculateAcceleration();
        Long t = refreshTimer.getRefreshLength();

        //set v
        BigDecimal xVelocity = velocity.getX();
        BigDecimal yVelocity = velocity.getY();
        velocity.setX(xVelocity.add(acceleration.getX().multiply(BigDecimal.valueOf(t)).multiply(BigDecimal.valueOf(0.001)).multiply(BigDecimal.valueOf(speedFactor))));
        velocity.setY(yVelocity.add(acceleration.getY().multiply(BigDecimal.valueOf(t)).multiply(BigDecimal.valueOf(0.001)).multiply(BigDecimal.valueOf(speedFactor))));

        //set position
        BigDecimal x = startPosition.getX();
        BigDecimal y = startPosition.getY();
        x = x.add(velocity.getX().multiply(BigDecimal.valueOf(t)).multiply(BigDecimal.valueOf(0.001d)).multiply(BigDecimal.valueOf(speedFactor)));
        y = y.add(velocity.getY().multiply(BigDecimal.valueOf(t)).multiply(BigDecimal.valueOf(0.001d)).multiply(BigDecimal.valueOf(speedFactor)));
        startPosition.setX(x);
        startPosition.setY(y);
        endPosition.setX(x.add(width));
        endPosition.setY(y.add(height));

        handleItem();
    }

    public void handleItem() {

    }

    public void addAcceleration(Acceleration acceleration) {
        this.accelerations.add(acceleration);
        /*if (this.acceleration == null) {
            this.acceleration = new Acceleration();
        }
        this.acceleration.setX((this.acceleration.getX() == null ? BigDecimal.valueOf(0) : this.acceleration.getX()).add(acceleration.getX()));
        this.acceleration.setY((this.acceleration.getY() == null ? BigDecimal.valueOf(0) : this.acceleration.getY()).add(acceleration.getY()));*/
    }

    public void removeAcceleration(Acceleration acceleration) {
        boolean isRemoved = this.accelerations.remove(acceleration);
        if (isRemoved) {
            if (this.acceleration == null) {
                this.acceleration = new Acceleration();
            }
            this.acceleration.setX((this.acceleration.getX() == null ? BigDecimal.valueOf(0) : this.acceleration.getX()).subtract(acceleration.getX()));
            this.acceleration.setY((this.acceleration.getY() == null ? BigDecimal.valueOf(0) : this.acceleration.getY()).subtract(acceleration.getY()));
        }
    }

    private void calculateAcceleration() {
        if (this.accelerations == null) {
            this.acceleration.setX(BigDecimal.valueOf(0));
            this.acceleration.setY(BigDecimal.valueOf(0));
            return;
        }
        this.acceleration.setX(BigDecimal.valueOf(0));
        this.acceleration.setY(BigDecimal.valueOf(0));
        for (Acceleration acceleration : this.accelerations) {
            this.acceleration.setX(this.acceleration.getX().add(acceleration.getX()==null?BigDecimal.valueOf(0):acceleration.getX()));
            this.acceleration.setY(this.acceleration.getY() .add(acceleration.getY()==null?BigDecimal.valueOf(0):acceleration.getY()));
        }
    }


    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public RefreshTimer getRefreshTimer() {
        return refreshTimer;
    }

    public void setRefreshTimer(RefreshTimer refreshTimer) {
        this.refreshTimer = refreshTimer;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Position endPosition) {
        this.endPosition = endPosition;
    }

    public BigDecimal getMass() {
        return mass;
    }

    public void setMass(BigDecimal mass) {
        this.mass = mass;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = mass;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }
}
