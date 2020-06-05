package com.haskellish.graphics;

import com.haskellish.engine.Core;
import com.haskellish.engine.Figure;
import com.haskellish.engine.Tile;
import com.haskellish.scoring.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.Timer;
import java.util.TimerTask;

public class TetrisComponent extends JComponent implements KeyListener {

    public static final int TILE_SIZE = 50;

    private final Core core;
    private final Timer timer = new Timer();
    private final JLabel score;
    private final Player player;

    TetrisComponent(Player player, JLabel score){
        core = new Core(player);
        this.player = player;
        this.score = score;
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.RED);
        Graphics2D g2 = (Graphics2D) g;

        for (Figure figure : core.getFigures()) {
            for (Tile tile : figure.getTiles()) {
                switch (tile.getType()) {
                    case Figure.I: {
                        g2.setColor(Color.CYAN);
                        break;
                    }
                    case Figure.J: {
                        g2.setColor(Color.BLUE);
                        break;
                    }
                    case Figure.L: {
                        g2.setColor(Color.ORANGE);
                        break;
                    }
                    case Figure.SQ: {
                        g2.setColor(Color.YELLOW);
                        break;
                    }
                    case Figure.S: {
                        g2.setColor(Color.GREEN);
                        break;
                    }
                    case Figure.T: {
                        g2.setColor(Color.MAGENTA);
                        break;
                    }
                    case Figure.Z: {
                        g2.setColor(Color.RED);
                        break;
                    }
                    default:
                        g2.setColor(Color.BLACK);
                }
                g2.fill(new Rectangle2D.Double(tile.getX() * TILE_SIZE, tile.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE));
            }
        }
        score.setText("Score: " + player.getScore());
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

    public void startRendering(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        },0, 50);
    }

}
