package com.beyond.entity;

import com.beyond.invisibility.Position;
import com.beyond.invisibility.Velocity;

import java.math.BigDecimal;

public class Block extends Item {


    public Block(BigDecimal mass, Position startPosition, Position endPosition, Velocity velocity) {
        super(mass, startPosition, endPosition, velocity);
    }

    @Override
    public BigDecimal getMass() {
        return BigDecimal.valueOf(0d);
    }

}
