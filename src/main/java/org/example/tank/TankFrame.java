package org.example.tank;


import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;

/**
 * @ClassName TankFram
 * @Description TODO
 * @Author XiZhuangBaoTu
 * Date 2023/5/12 19:50
 * Version 1.0
 **/
public class TankFrame extends Frame {
//    public static final TankFrame INSTANCE = new TankFrame();
//    private int x = 100 ,y = 100; 因此这里就不要直接定义坦克的位置了，因为抽离除了坦克这个对象
//    private static int SPEED = 5; 这也是坦克的属性

    public static int GAME_WIDTH = 800;
    public static int GAME_HEIGHT = 600;

    private GameModel gameModel = new GameModel(this);


    public TankFrame(){
        super("tank war");
        this.setLocation(-900,100);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        // 增加键盘的监听
        this.addKeyListener(new TankKeyListener());
    }

    transient Image offScreenImage = null;
    /**
     *@Author XiZhuangBaoTu
     *@Description 解决双缓冲的问题
     *@Date 02:24 2023/5/13
     *@Param [g]
     *@return void
     **/
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        // 最后才一次性刷到内存中
        g.drawImage(offScreenImage,0,0,null);
    }

    /**
     *@Author XiZhuangBaoTu
     *@Description 这个方法自动会给你调用，当重新绘画页面的时候
     *@Date 19:55 2023/5/12
     *@Param [g]
     *@return void
     **/
    @Override
    public void paint(Graphics g) {
      gameModel.paint(g);
    }

    public void add(Explode explode) {
        gameModel.objects.add(explode);
    }



    /**
     *@Author XiZhuangBaoTu
     *@Description 坦克的监听类,keyadapter这个不是适配器模式
     *@Date 20:13 2023/5/12
     *@Param
     *@return
     **/
    private class TankKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_S) {
                save();
            }else if (e.getKeyCode() == KeyEvent.VK_L) {
                load();
            }else {
                gameModel.getMyTank().keyPressed(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            gameModel.getMyTank().keyReleased(e);
        }
    }

    private void load() {
        try {
            File f = new File("src/test/java/tank.dat");
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            GameModel o = (GameModel) ois.readObject();
            gameModel = o;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBullet(Bullet bullet){
        gameModel.objects.add(bullet);
    }

    private void save(){
        ObjectOutputStream oos = null;
        try {
            File f = new File("src/test/java/tank.dat");
            FileOutputStream fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(gameModel);
            oos.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
