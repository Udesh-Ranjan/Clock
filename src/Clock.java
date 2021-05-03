import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @dev :   devpar
 * @date :   02-May-2021
 */
public class Clock extends JPanel {
    public Clock(int width,int height){
        setSize(width,height);
    }
    public void paint(Graphics g){
        drawOuterCircle(g);
        drawInnerCircle(g);
        drawOuterNumbers(g);
        drawTicks(g);
        drawHoursHand(g);
        drawMinutesHand(g);
        drawSecondsHand(g);
    }
    public void drawOuterCircle(Graphics g){
        int width=getWidth();
        int height=getHeight();
        int diameter=Math.min(width,height)/50;
        g.drawOval(width/2-diameter/2,height/2-diameter/2,diameter,diameter);
    }
    public void drawInnerCircle(Graphics g){
        int width=getWidth();
        int height=getHeight();
        int diameter=Math.min(width,height);
        g.drawOval(width/2-diameter/2,height/2-diameter/2,diameter,diameter);
    }
    public void drawOuterNumbers(Graphics g){
        List<Integer>list=new LinkedList<>();
        list.add(3);
        list.add(2);
        list.add(1);
        for(int i=12;i>3;i--)
            list.add(i);
        int width=getWidth();
        int height=getHeight();
        int diameter=Math.min(width,height)-50;
        for(int degree=0,hrIndex=0;degree<360;degree++){
            if(degree%90==0){
                double x,y;
                int hours=list.get(hrIndex);
                System.out.println("degree "+degree);
                double slop=Math.tan(Math.toRadians(degree));
                System.out.println("slop "+slop);
//                slop=Math.round(slop);
                System.out.println("round "+slop);
                if(degree == 90 || degree == 270){
                    System.out.println("infinite : "+degree);
                    x=0;
                    if(degree==90)
                        y=diameter/2;
                    else
                        y=-diameter/2;
                }else{
                    x=Math.pow((diameter/2*diameter/2)/(1+slop*slop),0.5);
                    if(degree>90 && degree <270)
                        x=-x;
                    y=slop*x;
                }
                System.out.println("x "+x+" y "+y);
                Font font=g.getFont();
                g.setFont(new Font(Font.MONOSPACED,Font.BOLD,20));
                drawString(hours+"",(int)x,(int)y,g);
                g.setFont(font);
            }else if(degree %30 == 0){
                double x,y;
                int hours=list.get(hrIndex);
                System.out.println("degree "+degree);
                double slop=Math.tan(Math.toRadians(degree));
                System.out.println("slop "+slop);
//                slop=Math.round(slop);
                System.out.println("round "+slop);
                if(degree == 90 || degree == 270){
                    System.out.println("infinite : "+degree);
                    x=0;
                    if(degree==90)
                        y=diameter/2;
                    else
                        y=-diameter/2;
                }else{
                    x=Math.pow((diameter/2*diameter/2)/(1+slop*slop),0.5);
                    if(degree>90 && degree <270)
                        x=-x;
                    y=slop*x;
                }
                System.out.println("x "+x+" y "+y);
                drawString(hours+"",(int)x,(int)y,g);
            }
            if(degree%30==0)
                hrIndex++;
        }
    }
    public void drawString(String str,int x,int y,Graphics g){
        int centerX=getWidth()/2;
        int centerY=getHeight()/2;
        int w,h;
        FontMetrics metrics=g.getFontMetrics();
        w=metrics.stringWidth(str);
        h=metrics.getHeight();
        System.out.println("x "+(centerX+x)+" y "+(centerY-y));
        g.drawString(str,centerX+x-w/2,centerY-y+h/4);
    }
    public void fillOval(int x,int y,int width,int height,Graphics g){
        int centerX=getWidth()/2;
        int centerY=getHeight()/2;
        System.out.println("x "+x+" y "+y);
        g.fillOval(centerX+x-width/2,centerY-y-height/2,width,height);
    }
    boolean isInt(double val){
        return val==(int)val;
    }
    public void drawTicks(Graphics g){
        int width=getWidth();
        int height=getHeight();
        int diameter=Math.min(width,height)-10;
        for(double degree=0;degree<360;degree+=0.5){
            if(isInt(degree) && degree%90==0){
                double x,y;
//                System.out.println("degree "+degree);
                double slop=Math.tan(Math.toRadians(degree));
//                System.out.println("slop "+slop);
//                slop=Math.round(slop);
                System.out.println("round "+slop);
                if(degree == 90 || degree == 270){
//                    System.out.println("infinite : "+degree);
                    x=0;
                    if(degree==90)
                        y=diameter/2;
                    else
                        y=-diameter/2;
                }else{
                    x=Math.pow((diameter/2*diameter/2)/(1+slop*slop),0.5);
                    if(degree>90 && degree <270)
                        x=-x;
                    y=slop*x;
                }
                System.out.println("x "+x+" y "+y);
//                Font font=g.getFont();
//                g.setFont(new Font(Font.MONOSPACED,Font.BOLD,20));
                int radius=10;
                fillOval((int)x,(int)y,radius,radius,g);
//                drawString(hours+"",(int)x,(int)y,g);
//                g.setFont(font);
            }else if(isInt(degree) && degree %30 == 0){
                double x,y;
                System.out.println("degree "+degree);
                double slop=Math.tan(Math.toRadians(degree));
                System.out.println("slop "+slop);
//                slop=Math.round(slop);
                System.out.println("round "+slop);
                if(degree == 90 || degree == 270){
                    System.out.println("infinite : "+degree);
                    x=0;
                    if(degree==90)
                        y=diameter/2;
                    else
                        y=-diameter/2;
                }else{
                    x=Math.pow((diameter/2*diameter/2)/(1+slop*slop),0.5);
                    if(degree>90 && degree <270)
                        x=-x;
                    y=slop*x;
                }
                int radius=5;
                fillOval((int)x,(int)y,radius,radius,g);
                System.out.println("x "+x+" y "+y);
//                drawString(hours+"",(int)x,(int)y,g);
            }else {
                double x,y;
                System.out.println("Minute degree "+degree);
                double slop=Math.tan(Math.toRadians(degree));
                System.out.println("slop "+slop);
//                slop=Math.round(slop);
                System.out.println("round "+slop);
                if(degree == 90 || degree == 270){
                    System.out.println("infinite : "+degree);
                    x=0;
                    if(degree==90)
                        y=diameter/2;
                    else
                        y=-diameter/2;
                }else{
                    x=Math.pow((diameter/2*diameter/2)/(1+slop*slop),0.5);
                    if(degree>90 && degree <270)
                        x=-x;
                    y=slop*x;
                }
                int radius=2;
                fillOval((int)x,(int)y,radius,radius,g);
            }
        }
    }
    public void drawHoursHand(Graphics g) {
        Calendar calendar = new GregorianCalendar();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        int diameter = Math.min(getWidth(), getHeight())-100;
        hours = hours % 12;
        System.out.println("Time"+hours+":"+minutes+":"+seconds);
        Map<Integer, Integer> hoursToDegree = new HashMap<>();
        for (int i = 12, degree = 90; i > 0; i--, degree += 30) {
            int deg = degree;
            hoursToDegree.put(i, deg);
        }
        System.out.println(hoursToDegree);
        int degree = (hoursToDegree.get(hours) - (int) (minutes * 0.5)) % 180;
        System.out.println(degree);

        double x, y;
        if (degree == 90 || degree == 270) {
            System.out.println("infinite : " + degree);
            x = 0;
            if (degree == 90) {
                y = diameter / 2;
            } else {
                y = -diameter / 2;
            }
        } else {
            System.out.println("degree " + degree);
            double slop = Math.tan(Math.toRadians(degree));
            System.out.println("slop " + slop);
//            slop=Math.round(slop);
            System.out.println("round " + slop);
            x = Math.pow((diameter / 2 * diameter / 2) / (1 + slop * slop), 0.5);
            if (hours>=6)
                x = -x;
            y = slop * x;
        }
        drawLine(0,0,(int)x,(int)y,g);
    }
    public void drawLine(int x,int y,int x1,int y1,Graphics g){
        int centerX=getWidth()/2;
        int centerY=getHeight()/2;
        g.drawLine(centerX+x,centerY-y,centerX+x1,centerY-y1);
    }
    public void drawMinutesHand(Graphics g){
        Calendar calendar = new GregorianCalendar();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        int diameter = Math.min(getWidth(), getHeight())-60;
        hours = hours % 12;
        System.out.println("Time"+hours+":"+minutes+":"+seconds);
        Map<Integer, Double> minutesToDegree = new HashMap<>();
        double degree = 90+6;
        for (int i = 59 ; i >=0; i--, degree += 6) {
            double deg = degree;
            minutesToDegree.put(i, deg);
        }
        System.out.println(minutesToDegree);
        degree = (int)((double)minutesToDegree.get(minutes)) % 180;
        System.out.println(degree);

        double x, y;
        if (minutes == 30 || minutes == 0) {
            System.out.println("infinite : " + degree);
            x = 0;
            if (minutes == 0) {
                y = diameter / 2;
            } else {
                y = -diameter / 2;
            }
        } else {
            System.out.println("degree " + degree);
            double slop = Math.tan(Math.toRadians(degree));
            System.out.println("slop " + slop);
//            slop=Math.round(slop);
            System.out.println("round " + slop);
            x = Math.pow((diameter / 2 * diameter / 2) / (1 + slop * slop), 0.5);
            if (minutes>30)
                x = -x;
            y = slop * x;
        }
        drawLine(0,0,(int)x,(int)y,g);
    }
    public void drawSecondsHand(Graphics g){
        Calendar calendar = new GregorianCalendar();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        int diameter = Math.min(getWidth(), getHeight())-30;
        hours = hours % 12;
        System.out.println("Time"+hours+":"+minutes+":"+seconds);
        Map<Integer, Double> secondsToDegree = new HashMap<>();
        double degree = 90+6;
        for (int i = 59 ; i >=0; i--, degree += 6) {
            double deg = degree;
            secondsToDegree.put(i, deg);
        }
        System.out.println(secondsToDegree);
        degree = (int)((double)secondsToDegree.get(seconds)) % 180;
        System.out.println(degree);

        double x, y;
        if (minutes == 30 || minutes == 0) {
            System.out.println("infinite : " + degree);
            x = 0;
            if (minutes == 0) {
                y = diameter / 2;
            } else {
                y = -diameter / 2;
            }
        } else {
            System.out.println("degree " + degree);
            double slop = Math.tan(Math.toRadians(degree));
            System.out.println("slop " + slop);
//            slop=Math.round(slop);
            System.out.println("round " + slop);
            x = Math.pow((diameter / 2 * diameter / 2) / (1 + slop * slop), 0.5);
            if (seconds>30)
                x = -x;
            y = slop * x;
        }
        drawLine(0,0,(int)x,(int)y,g);
    }
}
