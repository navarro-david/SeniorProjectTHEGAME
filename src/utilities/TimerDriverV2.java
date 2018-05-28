/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author David
 */
public class TimerDriverV2 extends Timer implements ActionListener {

    boolean timeReached;
    int count;
    
    //delay - in milliseconds... e.g. 1000 = 1 second
    //repeat - set timer to repeat after delay
    public TimerDriverV2(int delay, boolean repeat) {
        super(delay, null);
        super.setRepeats(repeat);
        super.addActionListener(this);
        timeReached = false;
        count = 0;
    }
    
    //called when delayed time reached
    public void actionPerformed(ActionEvent e) {
        count++;
        timeReached = true;
        //System.out.println(count + " cycle(s) completed");
    }
    
    
    //use to know how many cycles passed
    //meant to be used when timer is repeatable
    public final int getCount() {
        return count;
    }
    
    public final void resetCount(){
        count = 0;
    }
    
    //use to know when time is reached
    //meant to be used if timer is not repeatable
    public final boolean getTimeReached() {
        return timeReached;
    }

    public final void setTimeReached(boolean value) {
        timeReached = value;
    }
    
    public final void restart(){
        timeReached = false;
        count = 0;
        super.restart();
    }
    
    public final String toString(){
        return count + " cycle(s) completed";
    }
}
