package com.beyond.invisibility;

import com.beyond.f.F;

import java.math.BigDecimal;

public class GravityAcceleration extends Acceleration {
    @Override
    public BigDecimal getX() {
       return BigDecimal.valueOf(0);
    }

    @Override
    public BigDecimal getY() {
        return BigDecimal.valueOf(F.GRAVITY_CONSTANT);
    }
}
