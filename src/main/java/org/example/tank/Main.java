package org.example.tank;

public class Main {
    public static void main(String[] args) {
        for (;;){
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            TankFrame.INSTANCE.repaint();
        }
    }
}