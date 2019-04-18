package com.github.alex15190.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainPanel extends JPanel implements ActionListener {
    private GamePanel gamePanel;
    private JPanel rightPanel;
    private JLabel lblScore;
    private Timer timer;

    public MainPanel(){

        timer = new Timer(250,this);
        timer.start();

        setBackground(Color.white);
        setLayout(new GridLayout(1,2));



        gamePanel = new GamePanel();
        gamePanel.setSize(320,320);
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(3,1));
        rightPanel.setBackground(Color.white);
        rightPanel.setSize(100,320);
        rightPanel.setVisible(true);

        this.addKeyListener(new FieldKeyListener());


        lblScore = new JLabel("Счет: " + gamePanel.parts,SwingConstants.CENTER);
        lblScore.setBackground(Color.blue);
        rightPanel.add(lblScore);
        JButton pause = new JButton("Пауза");
        pause.addActionListener(e -> {
            gamePanel.switchInGame();
            this.requestFocusInWindow();
        });
        rightPanel.add(pause);

        JButton restart = new JButton("Рестарт");
        restart.addActionListener(e -> {
            //restart
            gamePanel.initGame();
            gamePanel.inGame = true;
            this.requestFocusInWindow();
        });
        rightPanel.add(restart);

        this.add(gamePanel);
        this.add(rightPanel);

        this.setFocusable(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        lblScore.setText("Счет: " + this.gamePanel.parts);
        rightPanel.repaint();
    }


    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && gamePanel.direction != SnkDirection.Right){
                gamePanel.direction = SnkDirection.Left;
            }
            if(key == KeyEvent.VK_RIGHT && gamePanel.direction != SnkDirection.Left){
                gamePanel.direction = SnkDirection.Right;
            }
            if(key == KeyEvent.VK_UP && gamePanel.direction != SnkDirection.Down){
                gamePanel.direction = SnkDirection.Up;
            }
            if(key == KeyEvent.VK_DOWN && gamePanel.direction != SnkDirection.Up){
                gamePanel.direction = SnkDirection.Down;
            }
        }
    }
}

