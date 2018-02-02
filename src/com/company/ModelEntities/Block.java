package com.company.ModelEntities;

/**
 * Created by Dominik on 2016-06-12.
 */
public class Block {
    protected int xCoor, yCoor;

    Block(int x, int y){
        xCoor = x;
        yCoor = y;
    }

    public int getX(){
        return xCoor;
    }

    public int getY(){
        return yCoor;
    }

    public boolean isApple(){
        return false;
    }

    public boolean isSnake(){
        return false;
    }
}