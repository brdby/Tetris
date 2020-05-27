package com.haskellish.engine;

import java.util.*;

public class Core {

    public static final int INITIAL_X = 5;
    public static final int INITIAL_Y = 0;

    public static final int MAX_X = 20;
    public static final int MAX_Y = 20;

    private Timer timer = new Timer();
    private ArrayList<Figure> figures = new ArrayList<>();
    private Queue<Figure> figureQueue = new ArrayDeque<>();
    private Random r = new Random();

    public Core(){
        //setting first figures
        figureQueue.add(genFigure(INITIAL_X, INITIAL_Y));
        figureQueue.add(genFigure(INITIAL_X, INITIAL_Y));
        figures.add(figureQueue.poll());

        start();
    }

    public void start(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                //move all figures
                int movedFigures = 0;
                for (Figure figure : figures){
                    if (figure.checkFloor(MAX_Y)){
                        int[][] coords = figure.getLowerCoords();
                        boolean canMove = true;
                        for (Figure f : figures){
                            if (f != figure){
                                canMove = f.checkCoords(coords);
                                if (!canMove) break;
                            }
                        }
                        if (canMove) {
                            figure.move(0, 1);
                            movedFigures++;
                        }
                    }
                }

                //change active figure
                if (movedFigures == 0){
                    figureQueue.add(genFigure(INITIAL_X, INITIAL_Y));
                    figures.add(figureQueue.poll());
                }
            }
        }, 0, 1000);
    }

    public void stop(){
        timer.cancel();
    }

    public Figure genFigure(int x, int y){
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
