package nova.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener {

    public InputHandler(Game game) {
        game.addKeyListener(this);
    }

    public class Key {
        private int numTimesPressed = 0;
        private  boolean pressed = false;

        public boolean isPressed() {
        return pressed;
        }
        public void toggle(boolean isPressed) {
            pressed = isPressed;
            if(numTimesPressed>=3)
            {
                numTimesPressed=0;
            }
            if (isPressed)
            {
                numTimesPressed++;
            }

        }

        public int getNumTimesPressed() {
            return numTimesPressed;
        }
    }

  //  public List<Key> keys = new ArrayList<>();

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        toggleKey(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {

        toggleKey(e.getKeyCode(), false);
    }

    public void toggleKey(int keyKode, boolean isPressed) {
        if (keyKode == KeyEvent.VK_W||keyKode == KeyEvent.VK_UP) {
            up.toggle(isPressed);
        }
        if (keyKode == KeyEvent.VK_A||keyKode == KeyEvent.VK_LEFT) {
            left.toggle(isPressed);
        }
        if (keyKode == KeyEvent.VK_S||keyKode == KeyEvent.VK_DOWN) {
            down.toggle(isPressed);
        }
        if (keyKode == KeyEvent.VK_D||keyKode == KeyEvent.VK_RIGHT) {
            right.toggle(isPressed);
        }

    }
}
