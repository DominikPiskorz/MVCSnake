package com.company.Controller;

import com.company.ModelEntities.Direction;
import com.company.ModelEntities.SnakeModel;
import com.company.SnakeView;

import static com.company.BlockType.*;

/**
 * Created by Dominik on 2016-06-11.
 */
public class SnakeController implements java.awt.event.ActionListener{

    //Joe: Controller has Model and View hardwired in
    SnakeModel model;
    SnakeView view;
    Direction direction = Direction.LEFT, target = Direction.LEFT;
    boolean running = true;

    public SnakeController() {
        System.out.println ("Controller()");
    } //Controller()

    //invoked when a button is pressed
    public void actionPerformed(java.awt.event.ActionEvent e){
        //uncomment to see what action happened at view

		System.out.println ("Controller: The " + e.getActionCommand()
			+ " button is clicked at " + new java.util.Date(e.getWhen())
			+ " with e.paramString " + e.paramString() );

        System.out.println("Controller: acting on Model");
        model.incrementValue();
    } //actionPerformed()

    //Joe I should be able to add any model/view with the correct API
    //but here I can only add Model/View
    public void addModel(SnakeModel m){
        System.out.println("Controller: adding model");
        this.model = m;
    } //addModel()

    public void addView(SnakeView v){
        System.out.println("Controller: adding view");
        this.view = v;
    } //addView()

    public void initModel(int x){
        model.setValue(x);
    } //initModel()

    public void refreshView(){
        for (int i=0; i<25; i++)
            for (int j=0; j<25; j++) {
                if (model.blockmap[j][i] == null)
                    view.blocks[j][i] = EMPTY;
                else if (model.blockmap[j][i].isApple())
                    view.blocks[j][i] = APPLE;
                else if (model.blockmap[j][i].isSnake())
                    view.blocks[j][i] = BODY;
                else
                    view.blocks[j][i] = WALL;
            }

        view.refreshMap();
    }

    public boolean tick(){
        if (target.getNumVal() != direction.getNumVal())
            direction = target;
        running = model.move(direction);
        refreshView();
        return running;
    }

    public void changeDir(Direction dir){
        target = dir;
    }
    public void forceDir(Direction dir){
        target = dir;
        direction = dir;
    }
}