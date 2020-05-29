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

    private int maxX, maxY;
    private ArrayList<Tile> tiles = new ArrayList<>();
    private int x,y;

    Figure(Shape shape, int maxX, int maxY, int x, int y){
        this.coords = shape.getCoords();
        this.maxX = maxX;
        this.maxY = maxY;
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
                newCoords[coords[i].length-j-1][i] = coords[i][j];
            }
        }

        //check for floor and walls
        for (int i = 0; i < newCoords.length; i++){
            for (int j = 0; j < newCoords[i].length; j++){
                if (newCoords[i][j] != 0) {
                    if (y + i > maxY) return;
                    else if (x + j > maxX) {
                        x--;
                        rotate();
                        return;
                    }
                    else if (x + j < 0) {
                        x++;
                        rotate();
                        return;
                    }
                }
            }
        }


        //updating tiles
        coords = newCoords;
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

    public int[][] getRightCoords(){
        int[][] lowerCoords = new int[coords.length][2];
        for (int i = 0; i < coords.length; i++){
            for (int j = coords[i].length-1; j >= 0; j--){
                if (coords[i][j] != 0) {
                    lowerCoords[i][0] = x + j+1;
                    lowerCoords[i][1] = y + i;
                    break;
                }
            }
        }
        return lowerCoords;
    }

    public int[][] getLeftCoords(){
        int[][] lowerCoords = new int[coords.length][2];
        for (int i = 0; i < coords.length; i++){
            for (int j = 0; j < coords[i].length; j++){
                if (coords[i][j] != 0) {
                    lowerCoords[i][0] = x + j-1;
                    lowerCoords[i][1] = y + i;
                    break;
                }
            }
        }
        return lowerCoords;
    }

    public boolean checkCoords(int[][] coords){
        for (Tile tile : tiles){
            for (int i = 0; i < coords.length; i++){
                if (tile.getX() == coords[i][0] && tile.getY() == coords[i][1]) return false;
            }
        }
        return true;
    }

    public boolean move(int x, int y){
        //check walls
        if (y > 0) if (!checkFloor()) return false;
        if (x > 0) if (!checkRightWall()) return false;
        if (x < 0) if (!checkLeftWall()) return false;

        //move
        this.x+=x;
        this.y+=y;
        tiles.forEach((tile -> {
            tile.setX(tile.getX() + x);
            tile.setY(tile.getY() + y);
        }));
        return true;
    }

    private boolean checkFloor(){
        for (Tile tile : tiles){
            for (int i = 0; i < coords.length; i++){
                if (tile.getY() == maxY) return false;
            }
        }
        return true;
    }

    private boolean checkRightWall(){
        for (Tile tile : tiles){
            for (int i = 0; i < coords.length; i++){
                if (tile.getX() == maxX) return false;
            }
        }
        return true;
    }

    private boolean checkLeftWall(){
        for (Tile tile : tiles){
            for (int i = 0; i < coords.length; i++){
                if (tile.getX() == 0) return false;
            }
        }
        return true;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }
}
