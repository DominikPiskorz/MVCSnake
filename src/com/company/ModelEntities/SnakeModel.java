package com.company.ModelEntities;

import com.company.ModelEntities.Apple;
import com.company.ModelEntities.Block;

import java.util.Random;

/**
 * Created by Dominik on 2016-06-11.
 */
public class SnakeModel extends java.util.Observable {
    private int counter;
    private int size = 4;
    private SnakeBody snake;
    Random random = new Random();

    public Block blockmap[][] = new Block[25][25];

    public SnakeModel(){
        System.out.println("Model()");
        walls();
        apple();
        snake();
    }

    private void walls(){
        for (int i=0; i<25; i++){
            blockmap[i][0] = new Block(i,0);
            blockmap[i][24] = new Block(i,24);
            blockmap[0][i] = new Block(0,i);
            blockmap[24][i] = new Block(24,i);
        }
    }

    private void apple(){
        int x = random.nextInt(24);
        int y = random.nextInt(24);
        while (blockmap[x][y] != null){
            x = random.nextInt(24);
            y = random.nextInt(24);
        }
        blockmap[x][y] = new Apple(x,y);
    }

    private void snake(){
        snake = new SnakeBody(15, 12, 4);
        BodyPart tempb = snake.getHead();
        for (int i=0; i<size; i++)
        {
            blockmap[15+i][12] = tempb;
            tempb = tempb.next;
        }
    }

    public boolean move(Direction dir){
        int x = snake.tail.getX();
        int y = snake.tail.getY();
        snake.move(dir);
        Block tempb = blockmap[snake.head.getX()][snake.head.getY()];
        if (tempb == null) {
            blockmap[snake.head.getX()][snake.head.getY()]
                    = blockmap[x][y];
            if (snake.tail.getX() == x && snake.tail.getY() == y)
                blockmap[x][y] = snake.tail;
            else
                blockmap[x][y] = null;
            return true;
        }
        else if (tempb.isApple()){
            blockmap[snake.head.getX()][snake.head.getY()]
                    = blockmap[x][y];
            blockmap[x][y] = null;
            snake.eat();
            apple();
            setChanged();
            notifyObservers();
            return true;
        }
        else
            return false;
    }

    public void setValue(int value) {

        this.counter = value;
        System.out.println("Model init: counter = " + counter);
        setChanged();
        //model Push - send counter as part of the message
        notifyObservers(counter);
        //if using Model Pull, then can use notifyObservers()
        //notifyObservers()

    } //setValue()

    public void incrementValue() {

        ++counter;
        System.out.println("Model     : counter = " + counter);
        setChanged();
        //model Push - send counter as part of the message
        notifyObservers(counter);
        //if using Model Pull, then can use notifyObservers()
        //notifyObservers()

    } //incrementValue()

    /*public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/
}
