package com.beyond.face;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.math.MathContext;
import java.util.List;

import com.beyond.entity.Container;
import com.beyond.entity.Item;
import com.beyond.entity.Player;
import com.beyond.invisibility.Position;

public class MyCanvas extends Canvas {

    private Container container;

    public MyCanvas() {
    }

    public MyCanvas(Container container) {
        this.container = container;
    }

    @Override
    public void paint(Graphics g) {
        List<Item> items = container.getItems();

        //保证player的x不变,一直保持在窗口中间
        for (Item item :items) {
            if (item instanceof Player){
                Position startPosition = item.getStartPosition();
                Position endPosition = item.getEndPosition();
                Integer x = startPosition.getX().round(MathContext.UNLIMITED).intValue();
                Integer y = startPosition.getY().round(MathContext.UNLIMITED).intValue();
                g.translate(-x+this.getWidth()/2,0);  // 如果xy都保持不懂, 则改为(-x,-y)
            }
        }
        for (Item item : items) {
            Position startPosition = item.getStartPosition();
            Position endPosition = item.getEndPosition();
            Integer x = startPosition.getX().round(MathContext.UNLIMITED).intValue();
            Integer y = startPosition.getY().round(MathContext.UNLIMITED).intValue();
            Integer width = item.getWidth().round(MathContext.UNLIMITED).intValue();
            Integer height = item.getHeight().round(MathContext.UNLIMITED).intValue();
            g.fillRect(x, y, width, height);
        }
    }

    @Override
    public Graphics getGraphics() {
        //对纵坐标进行反转
        AffineTransform transform = AffineTransform.getScaleInstance(1, -1);
        transform.translate(0, -this.getHeight());
        Graphics2D g1 = (Graphics2D) super.getGraphics();
        g1.setTransform(transform);
        return g1;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }


    public static void main(String[] args) {
        Canvas canvas = new MyCanvas();

        JFrame jFrame = new JFrame("JumpingBoy");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        canvas.setSize(400, 400);
        jFrame.add(canvas);
        jFrame.pack();
        jFrame.setVisible(true);
    }

}
