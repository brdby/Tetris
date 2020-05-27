package com.haskellish.graphics;

import com.haskellish.engine.Core;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class MainFrame extends JFrame {

    TetrisComponent game;
    Timer timer = new Timer();

    public MainFrame(String name){
        super(name);

        //initializing game
        Core core = new Core();
        game = new TetrisComponent(core);
        add(game);

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
}
