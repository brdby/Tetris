package com.haskellish;

import com.haskellish.graphics.MainFrame;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainFrame tetris = new MainFrame("Tetris");
        });
    }
}
