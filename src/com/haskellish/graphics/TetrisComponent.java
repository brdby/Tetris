package com.haskellish.graphics;

import com.haskellish.engine.Core;
import com.haskellish.engine.Figure;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TetrisComponent extends JComponent {

    public static final int TILE_SIZE = 40;

    private Core game;

    public TetrisComponent(Core game){
        this.game = game;
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.RED);
        Graphics2D g2 = (Graphics2D) g;

        game.getFigures().forEach(figure -> {
            figure.getTiles().forEach(tile -> {
                switch (tile.getType()){
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
                    default: g2.setColor(Color.BLACK);
                }
                g2.fill(new Rectangle2D.Double(tile.getX()*TILE_SIZE, tile.getY()*TILE_SIZE, TILE_SIZE, TILE_SIZE));
            });
        });
    }

}
