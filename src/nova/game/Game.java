package nova.game;

import nova.game.gfx.Colors;
import nova.game.gfx.Screen;
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

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    private int[] colors = new int[6 * 6 * 6];


    private Screen screen;

    public InputHandler inputHandler;

    public Game() {
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        frame = new JFrame(NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void init() {
        int index = 0;
        for (int r = 0; r < 6; r++) {
            for (int g = 0; g < 6; g++) {
                for (int b = 0; b < 6; b++) {
                    int rr = (r * 255 / 5);
                    int gg = (g * 255 / 5);
                    int bb = (b * 255 / 5);
                    colors[index] = rr << 16 | gg << 8 | bb;

                }
            }
        }

        screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("res/empty_frame.png"));
        inputHandler = new InputHandler(this);
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
        double nsPerTick = 1000000000D / 60D;
        int ticks = 0;
        int frames = 0;
        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        init();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = false;

            while (delta >= 1) {
                ticks++;
                tick();
                delta -= 1;
                shouldRender = true;
            }

            if (shouldRender) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                System.out.println(frames + " frames, " + ticks + " ticks");
                frames = 0;
                ticks = 0;
            }

        }

    }

    public void tick() {
        tickCounter++;

        if (inputHandler.up.isPressed()) {
            screen.yOffset--;
        }
        if (inputHandler.down.isPressed()) {
            screen.yOffset++;//moving down
        }
        if (inputHandler.left.isPressed()) {
            screen.xOffset--;
        }
        if (inputHandler.right.isPressed()) {
            screen.xOffset++; //moving right
        }


    }

    public void render() {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(3);//rendering rate  the higher is rate, than more power it needs
            return;
        }



        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                screen.render(j << 3, i << 3, 0, Colors.get(555, 505, 050, 005));
            }
        }

        for (int i = 0; i < screen.height; i++) {
            for (int j = 0; j < screen.width; j++) {
int colorCode = screen.pixels[j+i*screen.width];
if(colorCode<255)
    pixels[j+i*WIDTH] = colors[colorCode];
            }
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);


        graphics.dispose();
        bufferStrategy.show();
    }

    public static void main(String[] args) {
        new Game().start();
    }

}
