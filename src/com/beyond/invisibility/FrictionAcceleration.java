package com.beyond.invisibility;

import com.beyond.entity.Item;
import com.beyond.entity.Player;
import com.beyond.f.F;
import com.sun.org.apache.bcel.internal.generic.BIPUSH;

import java.math.BigDecimal;

public class FrictionAcceleration extends Acceleration {

    private Item item;
    private BigDecimal k;

    public FrictionAcceleration(Item item, BigDecimal k) {
        this.item = item;
        this.k = k;
    }

    @Override
    public BigDecimal getX() {

        if (item instanceof Player) {
            if (((Player) item).getPlayerState().isDownBlocked()) {
                BigDecimal mass = this.item.getMass();
                BigDecimal xVelocity = item.getVelocity().getX();
                if (xVelocity.intValue() == 0) {
                    return BigDecimal.valueOf(0);
                }
                BigDecimal xDirection = xVelocity.divide(xVelocity.abs(), BigDecimal.ROUND_UNNECESSARY).multiply(BigDecimal.valueOf(-1));
                return mass.multiply(k).multiply(xDirection);
            }
        }
        return BigDecimal.valueOf(0);
    }

    @Override
    public BigDecimal getY() {
        return BigDecimal.valueOf(0);
    }
}
