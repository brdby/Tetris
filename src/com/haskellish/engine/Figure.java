package com.haskellish.engine;

import java.util.ArrayList;

public class Figure {

    public static final int I = 1;
    public static final int J = 2;
    public static final int L = 3;
    public static final int SQ = 4;
    public static final int S = 5;
    public static final int T = 6;
    public static final int Z = 7;

    public enum Shape {
        IShape(new int[][]{{I, I, I, I}}),
        JShape(new int[][]{{J, 0, 0}, {J, J, J}}),
        LShape(new int[][]{{0, 0, L}, {L, L, L}}),
        Square(new int[][]{{SQ, SQ}, {SQ, SQ}}),
        SShape(new int[][]{{0, S, S}, {S, S, 0}}),
        ZShape(new int[][]{{Z, Z, 0}, {0, Z, Z}}),
        TShape(new int[][]{{0, T, 0}, {T, T, T}});

        private int[][] coords;

        Shape(int coords[][]) {
            this.coords = coords;
        }

        public int[][] getCoords() {
            return coords;
        }
    }

    private int[][] coords;

    private ArrayList<Tile> tiles = new ArrayList<>();
    private int x,y;

    Figure(Shape shape, int x, int y){
        this.coords = shape.getCoords();
        this.x = x;
        this.y = y;

        for (int i = 0; i < coords.length; i++){
            for (int j = 0; j < coords[i].length; j++){
                if (coords[i][j] != 0) tiles.add(new Tile(x + j, y + i, coords[i][j]));
            }
        }
    }

    public void rotate(){
        //updating coords
        int[][] newCoords = new int[coords[0].length][coords.length];
        for (int i = 0; i < coords.length; i++) {
            for (int j = 0; j < coords[i].length; j++) {
                newCoords[coords[i].length-j][i] = coords[i][j];
            }
        }
        coords = newCoords;

        //updating tiles
        tiles = new ArrayList<>();
        for (int i = 0; i < coords.length; i++){
            for (int j = 0; j < coords[i].length; j++){
                if (coords[i][j] != 0) tiles.add(new Tile(x + j, y + i, coords[i][j]));
            }
        }
    }

    public int[][] getLowerCoords(){
        int[][] lowerCoords = new int[coords[0].length][2];
        for (int i = 0; i < coords[0].length; i++){
            for (int j = coords.length-1; j >= 0; j--){
                if (coords[j][i] != 0) {
                    lowerCoords[i][0] = x + i;
                    lowerCoords[i][1] = y + j+1;
                    break;
                }
            }
        }
        return lowerCoords;
    }

    public boolean checkCoords(int x, int y){
        for (Tile tile : tiles){
            if (tile.getX() == x && tile.getY() == y) return false;
        }
        return true;
    }

    public boolean checkCoords(int[][] coords){
        for (Tile tile : tiles){
            for (int i = 0; i < coords.length; i++){
                if (tile.getX() == coords[i][0] && tile.getY() == coords[i][1]) return false;
            }
        }
        return true;
    }

    public boolean checkFloor(int maxY){
        for (Tile tile : tiles){
            for (int i = 0; i < coords.length; i++){
                if (tile.getY() == maxY) return false;
            }
        }
        return true;
    }

    public void move(int x, int y){
        this.x+=x;
        this.y+=y;
        tiles.forEach((tile -> {
            tile.setX(tile.getX() + x);
            tile.setY(tile.getY() + y);
        }));
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }
}
