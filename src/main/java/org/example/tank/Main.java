package org.example.tank;

public class Main {
    public static void main(String[] args) {
        TankFrame tf = new TankFrame();
        tf.setVisible(true);
        for (;;){
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            tf.repaint();
        }
    }
}