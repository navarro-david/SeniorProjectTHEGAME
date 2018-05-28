package utilities;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author David
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.*;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class GameDriverV3 extends Canvas implements KeyListener, Runnable, MouseListener, MouseMotionListener, MouseWheelListener {

    protected boolean[] keys;
    protected BufferedImage back;
    protected int timer = 6;
    protected int mousePosX, mousePosY;

    public GameDriverV3() {
        //set up all variables related to the game


        keys = new boolean[19];				// number of key possibilities


        setBackground(Color.WHITE);
        setVisible(true);

        new Thread(this).start();
        addKeyListener(this);				//starts the key thread to log key strokes
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    public void update(Graphics window) {
        paint(window);
    }

    public void setTimer(int value) {
        timer = value;
    }

    public void paint(Graphics window) {
        if (back == null) {
            back = (BufferedImage) (createImage(getWidth(), getHeight()));
        }
        Graphics2D graphToBack = (Graphics2D) back.createGraphics();

        draw(graphToBack);

        Graphics2D win2D = (Graphics2D) window;
        win2D.drawImage(back, null, 0, 0);

    }

    public abstract void draw(Graphics2D win);

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                keys[0] = true;
                break;
            case KeyEvent.VK_S:
                keys[1] = true;
                break;
            case KeyEvent.VK_A:
                keys[2] = true;
                break;
            case KeyEvent.VK_D:
                keys[3] = true;
                break;
            case KeyEvent.VK_F:
                keys[4] = true;
                break;
            case KeyEvent.VK_J:
                keys[5] = true;
                break;
            case KeyEvent.VK_K:
                keys[6] = true;
                break;
            case KeyEvent.VK_L:
                keys[7] = true;
                break;
            case KeyEvent.VK_8:
                keys[8] = true;
                break;
            case KeyEvent.VK_5:
                keys[9] = true;
                break;
            case KeyEvent.VK_4:
                keys[10] = true;
                break;
            case KeyEvent.VK_6:
                keys[11] = true;
                break;
            case KeyEvent.VK_PLUS:
                keys[12] = true;
                break;
            case KeyEvent.VK_ENTER:
                keys[13] = true;
                break;
            case KeyEvent.VK_SPACE:
                keys[14] = true;
                break;
            case KeyEvent.VK_UP:
                keys[15] = true;
                break;
            case KeyEvent.VK_DOWN:
                keys[16] = true;
                break;
            case KeyEvent.VK_LEFT:
                keys[17] = true;
                break;
            case KeyEvent.VK_RIGHT:
                keys[18] = true;
                break;
        }

    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                keys[0] = false;
                break;
            case KeyEvent.VK_S:
                keys[1] = false;
                break;
            case KeyEvent.VK_A:
                keys[2] = false;
                break;
            case KeyEvent.VK_D:
                keys[3] = false;
                break;
            case KeyEvent.VK_F:
                keys[4] = false;
                break;
            case KeyEvent.VK_J:
                keys[5] = false;
                break;
            case KeyEvent.VK_K:
                keys[6] = false;
                break;
            case KeyEvent.VK_L:
                keys[7] = false;
                break;
            case KeyEvent.VK_8:
                keys[8] = false;
                break;
            case KeyEvent.VK_5:
                keys[9] = false;
                break;
            case KeyEvent.VK_4:
                keys[10] = false;
                break;
            case KeyEvent.VK_6:
                keys[11] = false;
                break;
            case KeyEvent.VK_PLUS:
                keys[12] = false;
                break;
            case KeyEvent.VK_ENTER:
                keys[13] = false;
                break;
            case KeyEvent.VK_SPACE:
                keys[14] = false;
                break;
            case KeyEvent.VK_UP:
                keys[15] = false;
                break;
            case KeyEvent.VK_DOWN:
                keys[16] = false;
                break;
            case KeyEvent.VK_LEFT:
                keys[17] = false;
                break;
            case KeyEvent.VK_RIGHT:
                keys[18] = false;
                break;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void run() {
        try {
            while (true) {
                Thread.currentThread().sleep(timer);
                repaint();
            }
        } catch (Exception e) {
        }
    }

    public BufferedImage addImage(String name) {

        BufferedImage img = null;

        try {
            img = ImageIO.read(new File(name));

        } catch (IOException e) {
            System.out.println(e);
        }

        return img;



    }
    //MouseListener Methods

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    //MouseMotionListener Methods
    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        mousePosX = e.getX();
        mousePosY = e.getY();
    }

    //MouseWheelListener Moethod
    public void mouseWheelMoved(MouseWheelEvent e) {
    }
}
