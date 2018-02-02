package com.company;

/**
 * Created by Dominik on 2016-06-11.
 */
import com.company.Controller.SnakeController;
import com.company.ModelEntities.Direction;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;	//for CloseListener()
import java.awt.event.WindowAdapter;	//for CloseListener()
import java.lang.Integer;		//int from Model is passed as an Integer
import java.util.Observable;		//for update();
import java.awt.event.ActionListener;	//for addController()

public class SnakeView implements java.util.Observer{

    private static final int WIDTH = 600, HEIGHT = 700;
    private static SnakeController controller;

    private JFrame frame;
    private JPanel GameOver;
    private JLabel score;
    private JButton button;
    private JPanel topPanel;
    private JPanel midPanel;
    private JPanel botPanel;
    private JPanel map;
    private JLabel label[][] = new JLabel[25][25];
    private JLabel finalScore;

    private static int lenght = 3;
    private static boolean playing = false;

    public BlockType blocks[][] = new BlockType[25][25];

    SnakeView() {
        //frame in constructor and not an attribute as doesn't need to be visible to whole class
        frame = new JFrame("Snake");
        frame.setResizable(false);
        topPanel = new JPanel();
        midPanel = new JPanel();
        botPanel = new JPanel();
        topPanel.setSize(500,100);
        midPanel.setSize(500,500);
        botPanel.setSize(500,100);

        /***********************************          Top panel           *************************************/
        score = new JLabel();
        score.setPreferredSize(new Dimension(80,50));
        score.setText("Score: " + Integer.toString(lenght+1));
        topPanel.add("Center", score);

        /***********************************          Middle panel        *************************************/
        //frame.add("Center", myTextField);
        map = new JPanel();
        map.setLayout(new GridLayout(25,0));
        map.setBorder(BorderFactory.createMatteBorder(0,0,1,1,Color.black));
        for (int i=0; i<25; i++)
            for (int j=0; j<25; j++){
                label[j][i] = new JLabel();
                label[j][i].setBorder(BorderFactory.createMatteBorder(1,1,0,0,Color.black));
                label[j][i].setPreferredSize(new Dimension(20,20));
                map.add(label[j][i]);
            }
        midPanel.setBorder(BorderFactory.createEmptyBorder(20,50,20,50));
        midPanel.add(map);

        finalScore = new JLabel();
        finalScore.setPreferredSize(new Dimension(150,500));
        finalScore.setVisible(false);
        midPanel.add(finalScore);

        /***********************************          Bottom panel        *************************************/
        //panel in constructor and not an attribute as doesn't need to be visible to whole class
        JPanel panel = new JPanel();
        button = new JButton("Start Game");
        button.addActionListener(new Restart());
        panel.add(button);
        botPanel.add(panel);

        frame.add("North", topPanel);
        frame.add("Center", midPanel);
        frame.add("South", botPanel);

        //frame.addWindowListener(new CloseListener());
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(100,100);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new CloseListener());
        frame.setVisible(true);
        botPanel.setVisible(false);
        KeyBindings();

    } //View()

    private class Restart implements ActionListener{
        public void actionPerformed(ActionEvent e){
            playing = true;
            System.out.println("---------");
            System.out.print(playing);
        }
    }

    public void refreshMap(){
        for (int i=0; i<25; i++)
            for (int j=0; j<25; j++){
                if (blocks[j][i] == BlockType.EMPTY)
                    label[j][i].setOpaque(false);
                else if (blocks[j][i] == BlockType.WALL){
                    label[j][i].setOpaque(true);
                    label[j][i].setBackground(Color.black);
                }
                else if (blocks[j][i] == BlockType.APPLE){
                    label[j][i].setOpaque(true);
                    label[j][i].setBackground(Color.green);
                }
                else if (blocks[j][i] == BlockType.BODY){
                    label[j][i].setOpaque(true);
                    label[j][i].setBackground(Color.gray);
                }
            }
        frame.repaint();
    }


    private void KeyBindings() {
        JPanel content = (JPanel) frame.getContentPane();
        InputMap inputMap = content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("W"), "up arrow");
        inputMap.put(KeyStroke.getKeyStroke("S"), "down arrow");
        inputMap.put(KeyStroke.getKeyStroke("A"), "left arrow");
        inputMap.put(KeyStroke.getKeyStroke("D"), "right arrow");

        inputMap.put(KeyStroke.getKeyStroke("UP"), "up arrow");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "down arrow");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "left arrow");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "right arrow");

        content.getActionMap().put("up arrow", upAction);
        content.getActionMap().put("down arrow", downAction);
        content.getActionMap().put("left arrow", leftAction);
        content.getActionMap().put("right arrow", rightAction);
    }

    Action upAction = new AbstractAction(){
        @Override
        public void actionPerformed(ActionEvent e){
            controller.changeDir(Direction.UP);
        }
    };
    Action downAction = new AbstractAction(){
        @Override
        public void actionPerformed(ActionEvent e){
            controller.changeDir(Direction.DOWN);
        }
    };
    Action leftAction = new AbstractAction(){
        @Override
        public void actionPerformed(ActionEvent e){
            controller.changeDir(Direction.LEFT);
        }
    };
    Action rightAction = new AbstractAction(){
        @Override
        public void actionPerformed(ActionEvent e){
            controller.changeDir(Direction.RIGHT);
        }
    };

    // Called from the Model
    public void update(Observable obs, Object obj) {
        score.setText("Score: " + Integer.toString(++lenght));
    } //update()


    public void addController(SnakeController contr){
        controller = contr;
        //button.addActionListener(contr);	//need instance of controller before can add it as a listener
    } //addController()

    public boolean GameOver() {
        topPanel.setVisible(false);
        map.setVisible(false);
        finalScore.setFont(new Font("Serif", Font.PLAIN, 20));
        if (lenght == 3)
            finalScore.setText("Press the button!");
        else
            finalScore.setText("Final score: " + Integer.toString(lenght));
        finalScore.setVisible(true);
        botPanel.setVisible(true);
        controller.forceDir(Direction.LEFT);
        return playing;
    }
    public void StartOver(){
        System.out.println("+++++++++++++");
        finalScore.setVisible(false);
        botPanel.setVisible(false);
        topPanel.setVisible(true);
        map.setVisible(true);
        playing = false;
        lenght = 3;
        score.setText("Score: " + Integer.toString(++lenght));
    }

    public static class CloseListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            e.getWindow().setVisible(false);
            System.exit(0);
        } //windowClosing()
    } //CloseListener
}
