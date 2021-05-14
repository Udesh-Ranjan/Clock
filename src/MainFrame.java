import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author :   devpar
 * @date :   02-May-2021
 */
public class MainFrame {
    public static void main(String $[]) {
        int width, height;
        width = 500;
        height = 500;
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        AnalogClock analogClock = new AnalogClock(width, height);
        frame.add(analogClock);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Clock");
        frame.setVisible(true);
        Thread t = new Thread(new RunnableClass(analogClock));
        t.start();

    }
}

class RunnableClass implements Runnable {
    AnalogClock analogClock;

    public RunnableClass(AnalogClock clock) {
        analogClock = clock;
    }

    @Override
    public void run() {
        int seconds=(new GregorianCalendar()).get(Calendar.SECOND);
        while (true) {
            analogClock.update();
            try {
                if(seconds!=(new GregorianCalendar()).get(Calendar.SECOND)){
                    Sound.tone(5000, 5);
                    seconds=(new GregorianCalendar()).get(Calendar.SECOND);
                }
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }
}
