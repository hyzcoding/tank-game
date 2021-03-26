package com.hyzcoding.tankGame;

import com.hyzcoding.tankGame.frame.MainFrame;

public class Main {

    public static void main(String[] args) {
       new Thread(new MainFrame()).start();
    }
}
