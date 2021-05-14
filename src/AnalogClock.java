import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.util.*;
import java.util.List;

/**
 * @dev :   devpar
 * @date :   02-May-2021
 */
public class AnalogClock extends JPanel implements ComponentListener {
    private final static PrintStream out;
    private static final boolean LOGGING;
    static {
        out=System.out;
        LOGGING=false;
    }
    private BufferedImage img;
    private Graphics2D g2d;
    private final Stroke hourHandStroke,minuteHandStroke,secondHandStroke,outerCircleStroke;
    private Map<Integer,GlyphVector> hoursToGlyph;
    public void setDefaultGraphicsPro(){

    }
    public AnalogClock(int width, int height){
        hourHandStroke=new BasicStroke(10,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER);
        minuteHandStroke=new BasicStroke(5);
        secondHandStroke=new BasicStroke(2);
        outerCircleStroke=new BasicStroke(1);
        setSize(width,height);
        img=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        g2d=(Graphics2D)img.getGraphics();
        this.addComponentListener(this);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.setBackground(Color.white);
        hoursToGlyph =new HashMap<>();
        Font font=new Font(Font.MONOSPACED,Font.BOLD,20);
        Font def=g2d.getFont();
        for(int i=1;i<13;i++){
            if(i==12 || i==6 || i==3 || i==9)
                hoursToGlyph.put(i,font.createGlyphVector(g2d.getFontRenderContext(),String.valueOf(i)));
            else
                hoursToGlyph.put(i,def.createGlyphVector(g2d.getFontRenderContext(),String.valueOf(i)));
        }
    }

    public void paint(Graphics g){
        g2d.clearRect(0,0,getWidth(),getHeight());
        drawOuterCircle(g2d);
        drawInnerCircle(g2d);
        drawOuterNumbers(g2d);
        drawTicks(g2d);
        drawHoursHand(g2d);
        drawMinutesHand(g2d);
        drawSecondsHand(g2d);
        var col=g2d.getColor();
        Color c=new Color(0.0f,0.0f,1f,0.2f);
        g2d.setColor(c);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(col);
        col=g2d.getColor();
        c=new Color(0.0f,0.0f,0f,0.1f);
        g2d.setColor(c);
        int dia=Math.min(getWidth(),getHeight());
        fillOval(0,0,dia,dia,g2d);
        g2d.setColor(col);
        g.drawImage(img,0,0,this);
    }
    public void update(){
        repaint();
    }
    public void drawOuterCircle(Graphics2D g){
        int width=getWidth();
        int height=getHeight();
        int diameter=Math.min(width,height)/50;
        var def=g.getStroke();
        g.setColor(Color.gray);
        g.setStroke(outerCircleStroke);
        g.drawOval(width/2-diameter/2,height/2-diameter/2,diameter,diameter);
        g.setColor(Color.black);
        g.setStroke(def);
    }
    public void drawInnerCircle(Graphics2D g){
        int width=getWidth();
        int height=getHeight();
        int diameter=Math.min(width,height);
        var def=g.getStroke();
        g.setColor(Color.gray);
        g.setStroke(outerCircleStroke);
        g.drawOval(width/2-diameter/2,height/2-diameter/2,diameter,diameter);
        g.setColor(Color.black);
        g.setStroke(def);
    }
    public void drawOuterNumbers(Graphics2D g){
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
                println("degree "+degree);
                double slop=Math.tan(Math.toRadians(degree));
                println("slop "+slop);
//                slop=Math.round(slop);
                println("round "+slop);
                if(degree == 90 || degree == 270){
                    println("infinite : "+degree);
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
                println("x "+x+" y "+y);
//                Font font=g.getFont();
//                g.setFont(new Font(Font.MONOSPACED,Font.BOLD,20));
//                drawString(hours+"",(int)x,(int)y,g);
                drawGlyph(hours,(int)x,(int)y,g);
//                g.setFont(font);
            }else if(degree %30 == 0){
                double x,y;
                int hours=list.get(hrIndex);
                println("degree "+degree);
                double slop=Math.tan(Math.toRadians(degree));
                println("slop "+slop);
//                slop=Math.round(slop);
                println("round "+slop);
                if(degree == 90 || degree == 270){
                    println("infinite : "+degree);
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
                println("x "+x+" y "+y);
//                drawString(hours+"",(int)x,(int)y,g);
                drawGlyph(hours,(int)x,(int)y,g);
            }
            if(degree%30==0)
                hrIndex++;
        }
    }
    public void drawGlyph(int time,int x,int y,Graphics2D g){
        int centerX=getWidth()/2;
        int centerY=getHeight()/2;
        int w,h;
        FontMetrics metrics=g.getFontMetrics();
        w=metrics.stringWidth(String.valueOf(time));
        h=metrics.getHeight();
        println("x "+(centerX+x)+" y "+(centerY-y));
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//        g.drawString(str,centerX+x-w/2,centerY-y+h/4);
        g.drawGlyphVector(hoursToGlyph.get(time),centerX+x-w/2,centerY-y+h/4);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    }
    public void drawString(String str,int x,int y,Graphics2D g){
        int centerX=getWidth()/2;
        int centerY=getHeight()/2;
        int w,h;
        FontMetrics metrics=g.getFontMetrics();
        w=metrics.stringWidth(str);
        h=metrics.getHeight();
        println("x "+(centerX+x)+" y "+(centerY-y));
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString(str,centerX+x-w/2,centerY-y+h/4);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    }
    public void fillOval(int x,int y,int width,int height,Graphics2D g){
        int centerX=getWidth()/2;
        int centerY=getHeight()/2;
        println("x "+x+" y "+y);
        g.fillOval(centerX+x-width/2,centerY-y-height/2,width,height);
    }
    boolean isInt(double val){
        return val==(int)val;
    }
    public void drawTicks(Graphics2D g){
        int width=getWidth();
        int height=getHeight();
        int diameter=Math.min(width,height)-10;
        for(double degree=0;degree<360;degree+=0.5){
            if(isInt(degree) && degree%90==0){
                double x,y;
//                println("degree "+degree);
                double slop=Math.tan(Math.toRadians(degree));
//                println("slop "+slop);
//                slop=Math.round(slop);
                println("round "+slop);
                if(degree == 90 || degree == 270){
//                    println("infinite : "+degree);
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
                println("x "+x+" y "+y);
//                Font font=g.getFont();
//                g.setFont(new Font(Font.MONOSPACED,Font.BOLD,20));
                int radius=10;
                fillOval((int)x,(int)y,radius,radius,g);
//                drawString(hours+"",(int)x,(int)y,g);
//                g.setFont(font);
            }else if(isInt(degree) && degree %30 == 0){
                double x,y;
                println("degree "+degree);
                double slop=Math.tan(Math.toRadians(degree));
                println("slop "+slop);
//                slop=Math.round(slop);
                println("round "+slop);
                if(degree == 90 || degree == 270){
                    println("infinite : "+degree);
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
                println("x "+x+" y "+y);
//                drawString(hours+"",(int)x,(int)y,g);
            }else if(isInt(degree) && degree % 6==0){
                double x,y;
                println("Minute degree "+degree);
                double slop=Math.tan(Math.toRadians(degree));
                println("slop "+slop);
//                slop=Math.round(slop);
                println("round "+slop);
                if(degree == 90 || degree == 270){
                    println("infinite : "+degree);
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
    public void drawHoursHand(Graphics2D g) {
        Calendar calendar = new GregorianCalendar();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        int diameter = Math.min(getWidth(), getHeight())-100;
        hours = hours % 12;
        println("Time"+hours+":"+minutes+":"+seconds);
        Map<Integer, Integer> hoursToDegree = new HashMap<>();
        for (int i = 12, degree = 90; i > 0; i--, degree += 30) {
            int deg = degree;
            hoursToDegree.put(i, deg);
        }
        println(hoursToDegree);
        int degree = (hoursToDegree.get(hours) - (int) (minutes * 0.5)) % 180;
        println(degree);

        double x, y;
        if (degree == 90 || degree == 270) {
            println("infinite : " + degree);
            x = 0;
            if (degree == 90) {
                y = diameter / 2;
            } else {
                y = -diameter / 2;
            }
        } else {
            println("degree " + degree);
            double slop = Math.tan(Math.toRadians(degree));
            println("slop " + slop);
//            slop=Math.round(slop);
            println("round " + slop);
            x = Math.pow((diameter / 2 * diameter / 2) / (1 + slop * slop), 0.5);
            if (hours>=6)
                x = -x;
            y = slop * x;
        }
        Stroke defaultStroke=g.getStroke();
        g2d.setStroke(hourHandStroke);
        drawLine(0,0,(int)x,(int)y,g);
        g2d.setStroke(defaultStroke);
    }
    public void drawLine(int x,int y,int x1,int y1,Graphics2D g){
        int centerX=getWidth()/2;
        int centerY=getHeight()/2;
        g.drawLine(centerX+x,centerY-y,centerX+x1,centerY-y1);
    }
    public void drawMinutesHand(Graphics2D g){
        Calendar calendar = new GregorianCalendar();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        int diameter = Math.min(getWidth(), getHeight())-60;
        hours = hours % 12;
        println("Time"+hours+":"+minutes+":"+seconds);
        Map<Integer, Double> minutesToDegree = new HashMap<>();
        double degree = 90+6;
        for (int i = 59 ; i >=0; i--, degree += 6) {
            double deg = degree;
            minutesToDegree.put(i, deg);
        }
        println(minutesToDegree);
        degree = (int)((double)minutesToDegree.get(minutes)) % 180;
        println(degree);

        double x, y;
        if (minutes == 30 || minutes == 0) {
            println("infinite : " + degree);
            x = 0;
            if (minutes == 0) {
                y = diameter / 2;
            } else {
                y = -diameter / 2;
            }
        } else {
            println("degree " + degree);
            double slop = Math.tan(Math.toRadians(degree));
            println("slop " + slop);
//            slop=Math.round(slop);
            println("round " + slop);
            x = Math.pow((diameter / 2 * diameter / 2) / (1 + slop * slop), 0.5);
            if (minutes>30)
                x = -x;
            y = slop * x;
        }
        Stroke defaultStroke=g.getStroke();
        g.setStroke(minuteHandStroke);
        drawLine(0,0,(int)x,(int)y,g);
        g.setStroke(defaultStroke);
    }
    public void drawSecondsHand(Graphics2D g){
        Calendar calendar = new GregorianCalendar();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        int diameter = Math.min(getWidth(), getHeight())-30;
        hours = hours % 12;
        println("Time"+hours+":"+minutes+":"+seconds);
        Map<Integer, Double> secondsToDegree = new HashMap<>();
        double degree = 90+6;
        for (int i = 59 ; i >=0; i--, degree += 6) {
            double deg = degree;
            secondsToDegree.put(i, deg);
        }
        println(secondsToDegree);
        degree = (int)((double)secondsToDegree.get(seconds)) % 180;
        println(degree);

        double x, y;
        if (seconds == 30 || seconds == 0) {
            println("infinite : " + degree);
            x = 0;
            if (seconds == 0) {
                y = diameter / 2;
            } else {
                y = -diameter / 2;
            }
        } else {
            println("degree " + degree);
            double slop = Math.tan(Math.toRadians(degree));
            println("slop " + slop);
//            slop=Math.round(slop);
            println("round " + slop);
            x = Math.pow((diameter / 2 * diameter / 2) / (1 + slop * slop), 0.5);
            if (seconds>30)
                x = -x;
            y = slop * x;
        }
        drawLine(0,0,(int)x,(int)y,g);
    }
    private void println(final Object str){
        if(LOGGING)
            out.println(str);
    }
    private void print(final String str){
        if(LOGGING)
            out.print(str);
    }
    @Override
    public void componentResized(ComponentEvent e) {
        img.flush();
        img=new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
        g2d=(Graphics2D)img.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.setBackground(Color.white);
        repaint();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
