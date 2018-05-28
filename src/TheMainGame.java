/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David
 */
import javax.swing.JFrame;

public class TheMainGame {

    /**
     * @param args the command line arguments
     */
    
    static int width = 800, height = 500;
    
    public static void main(String[] args) {
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setTitle("Senior Project: THE GAME");
        frame.add(new GameComponent(width,height));
        System.out.println(frame.getContentPane().getSize().width);
        frame.setVisible(true);

    }
}
