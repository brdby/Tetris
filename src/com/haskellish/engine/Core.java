package com.haskellish.engine;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Core {

    public static final int INITIAL_X = 5;
    public static final int INITIAL_Y = 0;

    public static final int MAX_X = 9;
    public static final int MAX_Y = 19;

    private Timer timer = new Timer();
    private CopyOnWriteArrayList<Figure> figures = new CopyOnWriteArrayList<Figure>();
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
                    lineClear();
                    changeActiveFigure();
                }
            }
        }, 0, 500);
    }

    private void changeActiveFigure() {
        activeFigure = figureQueue.poll();
        figureQueue.add(genFigure(INITIAL_X, INITIAL_Y));
        figures.add(activeFigure);
    }

    public void stop() {
        timer.cancel();
    }

    public void moveActiveFigure(int x, int y) {
        int[][] lowerCoords = activeFigure.getLowerCoords();
        int[][] rightCoords = activeFigure.getRightCoords();
        int[][] leftCoords = activeFigure.getLeftCoords();

        boolean canMove;
        for (Figure f : figures) {
            if (f != activeFigure) {
                if (y > 0) {
                    canMove = f.checkCoords(lowerCoords);
                    if (!canMove) {
                        lineClear();
                        changeActiveFigure();
                        return;
                    }
                }
                if (x > 0) {
                    canMove = f.checkCoords(rightCoords);
                    if (!canMove) return;
                }
                if (x < 0) {
                    canMove = f.checkCoords(leftCoords);
                    if (!canMove) return;
                }
            }
        }
        activeFigure.move(x, y);
    }

    public void rotateActiveFigure() {
        activeFigure.rotate();
    }

    public Figure genFigure(int x, int y) {
        int fig = r.nextInt(Figure.Shape.values().length);
        return new Figure(Figure.Shape.values()[fig], MAX_X, MAX_Y, x, y);
    }

    public CopyOnWriteArrayList<Figure> getFigures() {
        return figures;
    }

    public Queue<Figure> getFigureQueue() {
        return figureQueue;
    }

    private int lineClear() {
        int clearedLines = 0;
        for (int i = MAX_Y; i >= 0; i--) {
            int tilesCount = 0;
            for (Figure f : figures) {
                tilesCount += f.numOfTiles(i);
            }
            if (tilesCount == 0) break;
            if (tilesCount == MAX_X + 1) {
                for (Figure f : figures) {
                    Figure oneTile = f.deleteTiles(i);
                    if (oneTile != null) figures.add(oneTile);
                    if (f.getTiles().size() == 0) figures.remove(f);
                }
                clearedLines++;
            }
        }
        if (figureFall() != 0) {
            int figureFall;
            do {
                figureFall = figureFall();
            } while (figureFall != 0);
            clearedLines += lineClear();
        }
        return clearedLines;
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
                if (figure.move(0, 1)) movedFigures++;
            }
        }
        return movedFigures;
    }
}
