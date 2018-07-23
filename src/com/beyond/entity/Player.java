package com.beyond.entity;

import com.beyond.controller.PlayerState;
import com.beyond.invisibility.Acceleration;
import com.beyond.invisibility.Position;
import com.beyond.invisibility.Velocity;
import com.beyond.listener.DownBlockedListener;
import com.beyond.utils.LoggerUtils;

import java.math.BigDecimal;
import java.util.List;

public class Player extends Item {

    private PlayerState playerState = new PlayerState();

    private Acceleration frictionAcceleration;
    private Acceleration selfPowerAcceleration;

    private BigDecimal lastXFrictionAcceleration;

    public Player(BigDecimal mass, Position startPosition, Position endPosition, Velocity velocity) {
        super(mass, startPosition, endPosition, velocity);
    }

    @Override
    public void handleItem() {
        playerState.clear();
        Container container = this.getContainer();
        List<Item> items = container.getItems();
        for (Item item : items) {
            if (item != this) {
                BigDecimal playerLeft = this.getStartPosition().getX();
                BigDecimal playerRight = this.getEndPosition().getX();
                BigDecimal playerUp = this.getEndPosition().getY();
                BigDecimal playerDown = this.getStartPosition().getY();

                BigDecimal itemLeft = item.getStartPosition().getX();
                BigDecimal itemRight = item.getEndPosition().getX();
                BigDecimal itemUp = item.getEndPosition().getY();
                BigDecimal itemDown = item.getStartPosition().getY();

//                System.out.println(playerDown.compareTo(itemUp)<0&&playerUp.compareTo(itemDown)>0&&playerRight.compareTo(itemLeft)>=0&&playerRight.compareTo(itemRight)<0&&playerLeft.compareTo(itemLeft)<0);
//                System.out.println(playerDown.compareTo(itemUp)<0&&playerUp.compareTo(itemDown)>0&&playerLeft.compareTo(itemRight)<=0&&playerLeft.compareTo(itemLeft)>0&&playerRight.compareTo(itemRight)>0);
//                System.out.println(playerRight.compareTo(itemLeft)>0&&playerLeft.compareTo(itemRight)<0&&playerUp.compareTo(itemDown)>=0&&playerUp.compareTo(itemUp)<0&&playerDown.compareTo(itemDown)<0);
//                System.out.println(playerRight.compareTo(itemLeft)>0&&playerLeft.compareTo(itemRight)<0&&playerDown.compareTo(itemUp)<=0&&playerDown.compareTo(itemDown)>0&&playerUp.compareTo(itemUp)>0);

                //to right block
                if (playerDown.compareTo(itemUp) < 0 && playerUp.compareTo(itemDown) > 0 && playerRight.compareTo(itemLeft) >= 0 && playerRight.compareTo(itemRight) < 0 && playerLeft.compareTo(itemLeft) < 0) {
                    if (this.getVelocity().getX().compareTo(BigDecimal.valueOf(0)) > 0) {
                        if(playerRight.subtract(itemLeft).compareTo(itemUp.subtract(playerDown))<0&&playerRight.subtract(itemLeft).compareTo(playerUp.subtract(itemDown))<0){
                            this.getStartPosition().setX(itemLeft.subtract(this.getWidth()));
                            this.getEndPosition().setX(itemLeft);
                            this.getVelocity().setX(this.getVelocity().getX().multiply(BigDecimal.valueOf(-1)));
                            this.playerState.setRightBlocked(true);
                        }
                    }
                }
                //to left block
                if (playerDown.compareTo(itemUp) < 0 && playerUp.compareTo(itemDown) > 0 && playerLeft.compareTo(itemRight) <= 0 && playerLeft.compareTo(itemLeft) > 0 && playerRight.compareTo(itemRight) > 0) {
                    if (this.getVelocity().getX().compareTo(BigDecimal.valueOf(0)) < 0) {
                        if(itemRight.subtract(playerLeft).compareTo(itemUp.subtract(playerDown))<0&&itemRight.subtract(playerLeft).compareTo(playerUp.subtract(itemDown))<0) {
                            this.getStartPosition().setX(itemRight);
                            this.getEndPosition().setX(itemRight.add(this.getWidth()));
                            this.getVelocity().setX(this.getVelocity().getX().multiply(BigDecimal.valueOf(-1)));
                            this.playerState.setLeftBlocked(true);
                        }
                    }
                }
                //to up block
                if (playerRight.compareTo(itemLeft) > 0 && playerLeft.compareTo(itemRight) < 0 && playerUp.compareTo(itemDown) >= 0 && playerUp.compareTo(itemUp) < 0 && playerDown.compareTo(itemDown) < 0) {
                    if (this.getVelocity().getY().compareTo(BigDecimal.valueOf(0)) > 0) {
                        if(playerUp.subtract(itemDown).compareTo(playerRight.subtract(itemLeft))<0&&playerUp.subtract(itemDown).compareTo(itemRight.subtract(playerLeft))<0) {
                            this.getStartPosition().setY(itemDown.subtract(this.getHeight()));
                            this.getEndPosition().setY(itemDown);
                            //this.getVelocity().setY(this.getVelocity().getY().multiply(BigDecimal.valueOf(-1)));
                            this.getVelocity().setY(BigDecimal.valueOf(0));
                            this.playerState.setUpBlocked(true);
                        }
                    }
                }
                //to down block
                if (playerRight.compareTo(itemLeft) > 0 && playerLeft.compareTo(itemRight) < 0 && playerDown.compareTo(itemUp) <= 0 && playerDown.compareTo(itemDown) > 0 && playerUp.compareTo(itemUp) > 0) {
                    if (this.getVelocity().getY().compareTo(BigDecimal.valueOf(0)) < 0) {
                        if(itemUp.subtract(playerDown).compareTo(playerRight.subtract(itemLeft))<0&&itemUp.subtract(playerDown).compareTo(itemRight.subtract(playerLeft))<0) {
                            this.getStartPosition().setY(itemUp);
                            this.getEndPosition().setY(itemUp.add(this.getHeight()));
                            //this.getVelocity().setY(this.getVelocity().getY().multiply(BigDecimal.valueOf(-1)));
                            this.getVelocity().setY(BigDecimal.valueOf(0));
                            this.playerState.setDownBlocked(true);
                        }
                    }
                }
            }
        }

        if (playerState.isFlying()){
            this.uniformRun();
        }

        LoggerUtils.log("X" + this.getStartPosition().getX());
        LoggerUtils.log("Y" + this.getStartPosition().getY());
    }

    public void jump(Double v) {
        if (this.playerState.isDownBlocked()) {
            this.getVelocity().setY(BigDecimal.valueOf(v));
        }
        this.playerState.clear();
    }

    public void run(Double v){
        this.getVelocity().setX(BigDecimal.valueOf(v));
    }

    public void stop(){
        if (this.playerState.isDownBlocked()) {
            this.getVelocity().setX(BigDecimal.valueOf(0));
        }
    }

    public void slowDown(){
        if (this.playerState.isDownBlocked()) {
            this.getSelfPowerAcceleration().setX(BigDecimal.valueOf(0));
        }
    }

    public void uniformRun(){
        if (this.playerState.isDownBlocked()) {
            this.getFrictionAcceleration().setX(BigDecimal.valueOf(0));
            this.getSelfPowerAcceleration().setX(BigDecimal.valueOf(0));
        }
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public Acceleration getFrictionAcceleration() {
        return frictionAcceleration;
    }

    public void setFrictionAcceleration(Acceleration frictionAcceleration) {
        this.frictionAcceleration = frictionAcceleration;
        this.addAcceleration(frictionAcceleration);
    }

    public Acceleration getSelfPowerAcceleration() {
        return selfPowerAcceleration;
    }

    public void setSelfPowerAcceleration(Acceleration selfPowerAcceleration) {
        this.selfPowerAcceleration = selfPowerAcceleration;
        this.addAcceleration(selfPowerAcceleration);
    }
}
