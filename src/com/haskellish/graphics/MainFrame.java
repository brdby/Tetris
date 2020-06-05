package com.haskellish.graphics;

import com.haskellish.engine.Core;
import com.haskellish.scoring.Player;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    Player player = new Player();
    TetrisComponent game;

    public MainFrame(String name){
        super(name);

        //initializing layout
        Container mainContainer = this.getContentPane();
        mainContainer.setLayout(new BorderLayout(0,0));
        mainContainer.setBackground(Color.GRAY);

        //initializing panels and buttons
        JButton newGameButton = new JButton("New game");
        JLabel score = new JLabel("Score: ");
        score.setForeground(Color.white);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.darkGray);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(newGameButton);
        rightPanel.add(score);
        mainContainer.add(rightPanel, BorderLayout.EAST);

        //initializing game
        game = new TetrisComponent(player, score);
        mainContainer.add(game, BorderLayout.CENTER);
        game.addKeyListener(game);
        game.startRendering();
        game.setFocusable(true);

        //initializing frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize((Core.MAX_X+1) * TetrisComponent.TILE_SIZE + 95, (Core.MAX_Y+1) * TetrisComponent.TILE_SIZE + 35);
        setVisible(true);
    }
}
