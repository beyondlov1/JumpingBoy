package com.beyond.test;

import com.beyond.entity.Block;
import com.beyond.entity.Container;
import com.beyond.entity.Item;
import com.beyond.entity.Player;
import com.beyond.face.MyCanvas;
import com.beyond.invisibility.*;
import com.beyond.listener.DownBlockedListener;
import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.math.BigDecimal;
import java.math.MathContext;

public class Test1 {
    public static void main(String[] args) {
        Acceleration acceleration = new GravityAcceleration();
        RefreshTimer refreshTimer = new RefreshTimer(20L);

        //player
        Position startPosition = new Position();
        startPosition.setX(BigDecimal.valueOf(20d));
        startPosition.setY(BigDecimal.valueOf(220d));
        Position endPosition = new Position();
        endPosition.setX(BigDecimal.valueOf(40d));
        endPosition.setY(BigDecimal.valueOf(240d));
        Velocity velocity = new Velocity();
        velocity.setX(BigDecimal.valueOf(0));
        velocity.setY(BigDecimal.valueOf(0));
        Item player = new Player(BigDecimal.valueOf(10d), startPosition, endPosition, velocity);
        FrictionAcceleration frictionAcceleration = new FrictionAcceleration(player, BigDecimal.valueOf(1));
        ((Player) player).setFrictionAcceleration(frictionAcceleration);
        Acceleration acceleration1 = new Acceleration();
        acceleration1.setX(frictionAcceleration.getX().multiply(BigDecimal.valueOf(0)));
        ((Player) player).setSelfPowerAcceleration(acceleration1);

        //block
        Position startPosition2 = new Position();
        startPosition2.setX(BigDecimal.valueOf(0d));
        startPosition2.setY(BigDecimal.valueOf(0d));
        Position endPosition2 = new Position();
        endPosition2.setX(BigDecimal.valueOf(10000d));
        endPosition2.setY(BigDecimal.valueOf(20d));
        Item block = new Block(null, startPosition2, endPosition2, null);

        //block2
        Position startPosition3 = new Position();
        startPosition3.setX(BigDecimal.valueOf(200d));
        startPosition3.setY(BigDecimal.valueOf(0d));
        Position endPosition3 = new Position();
        endPosition3.setX(BigDecimal.valueOf(220d));
        endPosition3.setY(BigDecimal.valueOf(80d));
        Item block2 = new Block(null, startPosition3, endPosition3, null);

        //block3
        Position startPosition4 = new Position();
        startPosition4.setX(BigDecimal.valueOf(580d));
        startPosition4.setY(BigDecimal.valueOf(0d));
        Position endPosition4 = new Position();
        endPosition4.setX(BigDecimal.valueOf(600d));
        endPosition4.setY(BigDecimal.valueOf(700d));
        Item block3 = new Block(null, startPosition4, endPosition4, null);

        //block4
        Position startPosition5 = new Position();
        startPosition5.setX(BigDecimal.valueOf(30d));
        startPosition5.setY(BigDecimal.valueOf(0d));
        Position endPosition5 = new Position();
        endPosition5.setX(BigDecimal.valueOf(50d));
        endPosition5.setY(BigDecimal.valueOf(30d));
        Item block4 = new Block(null, startPosition5, endPosition5, null);

        //block5
        Position startPosition6 = new Position();
        startPosition6.setX(BigDecimal.valueOf(10d));
        startPosition6.setY(BigDecimal.valueOf(0d));
        Position endPosition6 = new Position();
        endPosition6.setX(BigDecimal.valueOf(20d));
        endPosition6.setY(BigDecimal.valueOf(700d));
        Item block5= new Block(null, startPosition6, endPosition6, null);

        //add items to container
        Container container = new Container(acceleration,refreshTimer);
        container.addItem(player);
        container.addItem(block);
        container.addItem(block2);
        container.addItem(block3);
        container.addItem(block4);
        container.addItem(block5);

//        double tmp =0;
//        for (int i = 0; i < 20; i++) {
//            //block
//            Position startPosition7 = new Position();
//            double randomStartPositionX = 500d * Math.random()+200;
//            tmp+=randomStartPositionX;
//            startPosition7.setX(BigDecimal.valueOf(tmp+randomStartPositionX));
//            startPosition7.setY(BigDecimal.valueOf(0d));
//            Position endPosition7 = new Position();
//            endPosition7.setX(BigDecimal.valueOf(tmp+randomStartPositionX+20d));
//            endPosition7.setY(BigDecimal.valueOf(90d*Math.random()));
//            Item block6= new Block(null, startPosition7, endPosition7, null);
//            container.addItem(block6);
//        }

        //init canvas
        Canvas canvas = new MyCanvas(container);
        canvas.setFocusable(true);

        //init jFrame
        JFrame jFrame = new JFrame("JumpingBoy");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocation(500,200);
        canvas.setSize(700, 600);
        jFrame.add(canvas);
        jFrame.pack();
        jFrame.setVisible(true);

        //Graphic 只有在加到jFrame中后才会创建所以在此处激活
        container.setCanvas(canvas);
        container.active();

        //监听器
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ((Player) player).jump(40d);
            }
        });

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode==KeyEvent.VK_UP){
                    ((Player) player).jump(50d);
                }
                if (keyCode==KeyEvent.VK_LEFT){
                    ((Player) player).uniformRun();
                    ((Player) player).run(-40d);
                }
                if (keyCode==KeyEvent.VK_RIGHT){
                    //((Player) player).uniformRun();
                    ((Player) player).run(40d);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode==KeyEvent.VK_LEFT){
                    if (!((Player) player).getPlayerState().isFlying()){
                        ((Player) player).slowDown();
                    }else {
                        ((Player) player).uniformRun();
                    }
                    //((Player) player).stop();
                }
                if (keyCode==KeyEvent.VK_RIGHT){
                    if (!((Player) player).getPlayerState().isFlying()){
                        ((Player) player).slowDown();
                    }else {
                        ((Player) player).uniformRun();
                    }
                    //((Player) player).stop();
                }
            }

        });
    }
}
