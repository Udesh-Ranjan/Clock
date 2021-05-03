import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

/**
 * @dev :   devpar
 * @date :   02-May-2021
 */
public class MainFrame {
    public static void main(String $[]){
        int width,height;
        width=500;
        height=500;
        JFrame frame=new JFrame();
        frame.setSize(500,500);
        AnalogClock analogClock=new AnalogClock(width,height);
        frame.add(analogClock);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Thread t=new Thread(new RunnableClass(analogClock));
        t.start();

    }
}
class RunnableClass implements Runnable{
    AnalogClock analogClock;
    public RunnableClass(AnalogClock clock){
        analogClock=clock;
    }
    @Override
    public void run(){
        try{
            while(true) {
                analogClock.update();
                Thread.sleep(1000);
                Sound.tone(5000,5);
            }
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }catch(LineUnavailableException e){
            e.printStackTrace();
        }
    }
}
