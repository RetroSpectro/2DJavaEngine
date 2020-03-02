package nova.game;

import nova.game.gfx.SpriteSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedWriter;

public class Game extends Canvas implements Runnable {
    public static final long serialVersionUID = 1L;
    public static final int WIDTH = 160;
    public static final int SCALE = 3;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final String NAME = "NovaGame";

    public boolean running = false;
    private JFrame frame;
    private int tickCounter = 0;

    private BufferedImage image =  new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
private SpriteSheet spriteSheet = new SpriteSheet("");

    public Game() {
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        frame = new JFrame(NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    private synchronized void stop() {
        running = false;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick =   1000000000D/60D;
        int ticks = 0;
        int frames=0;
        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta+=(now-lastTime)/nsPerTick;
            lastTime = now;
            boolean shouldRender = false;

            while  (delta>=1)
            {
                ticks++;
                tick();
                delta-=1;
                shouldRender = true;
            }

            if(shouldRender) {
                frames++;
                render();
            }

            if(System.currentTimeMillis()-lastTimer>=1000)
            {
                lastTimer+=1000;
                System.out.println(frames+" frames, "+ticks+ " ticks");
                frames= 0;
                ticks =0;
            }

        }

    }

    public void tick() {
    tickCounter++;
//        for (int i = 0; i < pixels.length; i++) {
//            pixels[i] = i+tickCounter;
//        }
    }

    public void render() {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if(bufferStrategy == null)
        {
            createBufferStrategy(3);//rendering rate  the higher is rate, than more power it needs
       return;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(image,0,0,getWidth(),getHeight(),null);


        graphics.dispose();
        bufferStrategy.show();
    }

    public static void main(String[] args) {
        new Game().start();
    }

}
