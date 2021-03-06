import javax.swing.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author :   devpar
 * @date :   02-May-2021
 */
public class MainFrame {
	public static void main(String $[]) {
		boolean analog=false;
		if($.length!=0)
			if($[0].equalsIgnoreCase("analog"))
				analog=true;
		int width, height;
		width = 500;
		height = 500;
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		Clock clock = null;
		if(analog){
			clock=new AnalogClock(width, height);
			frame.add((AnalogClock)clock);
		}
		else{
			clock=new DigitalClock(width,height);
			frame.add((DigitalClock)clock);
		}
		//frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clock");
		frame.setVisible(true);
		Thread t = new Thread(new RunnableClass(clock,analog));
		t.start();

	}
}

class RunnableClass implements Runnable {
	Clock clock;
	boolean tickSound;

	public RunnableClass(Clock clock,boolean tickSound) {
		this.clock = clock;
		this.tickSound=tickSound;
	}

	@Override
	public void run() {
		while (true) {
			clock.update();
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
}
