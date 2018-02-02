package com.company;

import com.company.Controller.SnakeController;
import com.company.ModelEntities.SnakeModel;

public class Main implements Runnable{
    static Thread thread;
    static boolean running = false;
    static SnakeModel myModel;
    static SnakeView myView;
    static SnakeController myController;


    public static void main(String[] args) {

        myModel = new SnakeModel();
        myView 	= new SnakeView();

        myModel.addObserver(myView);

        myController = new SnakeController();
        myController.addModel(myModel);
        myController.addView(myView);
        myController.refreshView();

        //tell View about Controller
        myView.addController(myController);
        running = false;
        (thread = new Thread(new Main())).start();
    }

    public void run() {
        while(true) {
            if (myView.GameOver()) {
                running = true;
                myModel = new SnakeModel();
                myModel.addObserver(myView);
                myController.addModel(myModel);
                myView.StartOver();
                myController.refreshView();
            }
            while (running) {
                try {
                    thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                running = myController.tick();
            }
        }
    }
}
