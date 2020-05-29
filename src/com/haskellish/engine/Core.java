package com.haskellish.engine;

import com.haskellish.Main;

import java.util.*;

public class Core {

    public static final int INITIAL_X = 5;
    public static final int INITIAL_Y = 0;

    public static final int MAX_X = 9;
    public static final int MAX_Y = 19;

    private Timer timer = new Timer();
    private ArrayList<Figure> figures = new ArrayList<>();
    private Queue<Figure> figureQueue = new ArrayDeque<>();
    private Figure activeFigure;
    private Random r = new Random();

    public Core() {
        //setting first figures
        figureQueue.add(genFigure(INITIAL_X, INITIAL_Y));
        figureQueue.add(genFigure(INITIAL_X, INITIAL_Y));
        activeFigure = figureQueue.poll();
        figures.add(activeFigure);

        start();
    }

    public void start() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //change active figure
                if (figureFall() == 0) {
                    activeFigure = figureQueue.poll();
                    figureQueue.add(genFigure(INITIAL_X, INITIAL_Y));
                    figures.add(activeFigure);
                }
            }

            private int figureFall() {
                int movedFigures = 0;
                for (Figure figure : figures) {
                    int[][] coords = figure.getLowerCoords();
                    boolean canMove = true;
                    for (Figure f : figures) {
                        if (f != figure) {
                            canMove = f.checkCoords(coords);
                            if (!canMove) break;
                        }
                    }
                    if (canMove) {
                        if (figure.move(0, 1, MAX_X, MAX_Y)) movedFigures++;
                    }
                }
                return movedFigures;
            }
        }, 0, 1000);
    }

    public void stop() {
        timer.cancel();
    }

    public void moveActiveFigure(int x, int y) {
        int[][] lowerCoords = activeFigure.getLowerCoords();
        int[][] rightCoords = activeFigure.getRightCoords();
        int[][] leftCoords = activeFigure.getLeftCoords();

        boolean canMove = true;
        for (Figure f : figures) {
            if (f != activeFigure) {
                if (y > 0) canMove = f.checkCoords(lowerCoords);
                if (x > 0) canMove = f.checkCoords(rightCoords);
                if (x < 0) canMove = f.checkCoords(leftCoords);
                if (!canMove) break;
            }
        }
        if (canMove) {
            activeFigure.move(x, y, MAX_X, MAX_Y);
        }
    }

    public void rotateActiveFigure() {
        activeFigure.rotate();
    }

    public Figure genFigure(int x, int y) {
        int fig = r.nextInt(Figure.Shape.values().length);
        return new Figure(Figure.Shape.values()[fig], x, y);
    }

    public ArrayList<Figure> getFigures() {
        return figures;
    }

    public Queue<Figure> getFigureQueue() {
        return figureQueue;
    }
}
