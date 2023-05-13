package org.example.tank;

public class Main {
    public static void main(String[] args) {
        TankFrame tankFrame = new TankFrame();
        for (;;){
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            tankFrame.repaint();
        }
    }
}