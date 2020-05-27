package com.haskellish.engine;

public class Tile {

    private int x;
    private int y;
    private int type;

    public Tile(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
