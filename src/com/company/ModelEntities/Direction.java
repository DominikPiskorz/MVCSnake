package com.company.ModelEntities;

/**
 * Created by Dominik on 2016-06-12.
 */
public enum Direction {
    UP(0), LEFT(1), DOWN(0), RIGHT(1);

    private int numVal;

    Direction(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
    }
