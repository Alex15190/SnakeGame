package com.github.alex15190.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener{
    private final int SIZE = 320;
    private final int PART_SIZE = 16;
    private final int ALL_PARTS = 400;
    private final int STEP = 48;
    private Image part;
    private Image imageApple;
    private Point2D apple;
    private ArrayList<Point2D> snake;
    public int parts;
    private Timer timer;
    public SnkDirection direction = SnkDirection.Right;
    public boolean inGame = true;


    public void switchInGame(){
        inGame = !inGame;
    }



    public GamePanel(){

        setBackground(Color.blue);
        loadImages();
        initGame();
        //this.addKeyListener(new FieldKeyListener());
        //setFocusable(true);
    }

    public void initGame(){
        parts = 3;
        snake = new ArrayList();

        if (timer != null) timer.stop();

        direction = SnkDirection.Right;

        for (int i = 0; i < parts; i++) {
            Point2D tmp = new Point2D.Double(STEP - i*PART_SIZE, STEP);
            snake.add(tmp);
        }

        timer = new Timer(250,this);
        timer.start();
        createApple();
    }

    public void createApple(){
        int x = new Random().nextInt(20)*PART_SIZE;
        int y = new Random().nextInt(20)*PART_SIZE;
        apple = new Point2D.Double(x,y);
    }

    public void loadImages(){
        ImageIcon iia = new ImageIcon("apple.png");
        imageApple = iia.getImage();
        ImageIcon iid = new ImageIcon("part.png");
        part = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(imageApple,(int) apple.getX(),(int) apple.getY(),this);
            for (int i = 0; i < parts; i++) {
                Point2D ipart = snake.get(i);
                g.drawImage(part,(int) ipart.getX(),(int) ipart.getY(),this);
            }
        }
        /*else{
            String str = "Game Over";
            //Font f = new Font("Arial",32,Font.BOLD);
            g.setColor(Color.red);
            //g.setFont(f);
            g.drawString(str,125,SIZE/2);
        }*/
    }

    public void move(){
        Point2D tail = snake.get(parts - 1);
        snake.add(tail);
        for (int i = parts - 1; i > 0; i--) {
            snake.set(i,snake.get(i - 1));
        }
        Point2D head = snake.get(0);
        double x = head.getX();
        double y = head.getY();
        switch (direction){
            case Right:
                x += PART_SIZE;
                break;
            case Left:
                x -= PART_SIZE;
                break;
            case Down:
                y += PART_SIZE;
                break;
            case Up:
                y -= PART_SIZE;
                break;
        };
        Point2D newHead = new Point2D.Double(x,y);
        snake.set(0, newHead);
    }

    public void checkApple(){
        Point2D head = snake.get(0);
        if( head.getX() == apple.getX() && head.getY() == apple.getY()){
            parts++;
            createApple();
        }
    }

    public void checkCollisions(){
        Point2D head = snake.get(0);
        for (int i = 1; i < parts ; i++) {
            Point2D ipart = snake.get(i);
            if(i>4 && head.getX() == ipart.getX() && head.getY() == ipart.getY()){
                inGame = false;
            }
        }
        if(head.getX()>SIZE || head.getX()<0 || head.getY()>SIZE || head.getY()<0){
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame) {
            checkApple();
            checkCollisions();
            move();
            repaint();
        }
    }
    /*
    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && direction != SnkDirection.Right){
                direction = SnkDirection.Left;
            }
            if(key == KeyEvent.VK_RIGHT && direction != SnkDirection.Left){
                direction = SnkDirection.Right;
            }
            if(key == KeyEvent.VK_UP && direction != SnkDirection.Down){
                direction = SnkDirection.Up;
            }
            if(key == KeyEvent.VK_DOWN && direction != SnkDirection.Up){
                direction = SnkDirection.Down;
            }
        }
    }*/



}
