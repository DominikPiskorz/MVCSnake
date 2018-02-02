package com.company.ModelEntities;

/**
 * Created by Dominik on 2016-06-12.
 */
public class BodyPart extends Block{
    public BodyPart prev, next;

    BodyPart(int x, int y){
        super(x, y);
    }


    public void move(int x, int y){
        super.xCoor = x;
        super.yCoor = y;
    }

    public boolean isSnake(){
        return true;
    }
}
