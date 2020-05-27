package com.haskellish.graphics;

import com.haskellish.engine.Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

public class MainFrame extends JFrame implements KeyListener {


    Core core = new Core();
    TetrisComponent game = new TetrisComponent(core);
    Timer timer = new Timer();

    public MainFrame(String name){
        super(name);

        //initializing game
        add(game);

        addKeyListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screensize = kit.getScreenSize();
        setSize(screensize.width, screensize.height);
        setVisible(true);
    }

    public void startRendering(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                game.repaint();
            }
        },0, 50);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_RIGHT:
                core.moveActiveFigure(1, 0);
                break;
            case KeyEvent.VK_LEFT:
                core.moveActiveFigure(-1, 0);
                break;
            case KeyEvent.VK_DOWN:
                core.moveActiveFigure(0, 1);
                break;
            case KeyEvent.VK_UP:
                core.rotateActiveFigure();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
