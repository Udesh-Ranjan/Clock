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
        frame.add(new AnalogClock(width,height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
