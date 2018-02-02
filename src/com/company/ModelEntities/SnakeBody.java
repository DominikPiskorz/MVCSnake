package com.company.ModelEntities;

import java.util.Random;

import static com.company.ModelEntities.Direction.LEFT;

/**
 * Created by Dominik on 2016-06-12.
 */
public class SnakeBody {
    private Direction direction = LEFT;
    BodyPart head, tail;

    SnakeBody(int x, int y, int size){
        head = new BodyPart(x, y);
        BodyPart tempbody = head;
        for (int i=1; i < size; i++) {
            tempbody.next = new BodyPart(x + i, y);
            tempbody.next.prev = tempbody;
            tempbody = tempbody.next;
        }
        tail = tempbody;
    }

    public void move(){
        switch(direction){
            case UP:
                tail.move(head.getX(), head.getY()-1);
                break;
            case DOWN:
                tail.move(head.getX(), head.getY()+1);
                break;
            case LEFT:
                tail.move(head.getX()-1, head.getY());
                break;
            case RIGHT:
                tail.move(head.getX()+1, head.getY());
                break;
        }
        tail.next = head;
        head.prev = tail;
        head = tail;
        tail = tail.prev;
    }

    public void move(Direction target){
        direction = target;
        move();
    }

    public BodyPart getHead(){
        return head;
    }

    public BodyPart getTail(){
        return tail;
    }

    public void eat(){
        tail.next = new BodyPart(tail.getX(), tail.getY());
        tail.next.prev = tail;
        tail = tail.next;
    }
}
