import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.time.Month;
import java.time.DayOfWeek;
import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DigitalClock extends JPanel implements ComponentListener,Clock{
	public DigitalClock(int width,int height){
		this.addComponentListener(this);
		setSize(width,height);
	}
	@Override
	public void paint(Graphics g){
		Color col=g.getColor();
		Color black=Color.black;
		g.setColor(black);
		g.fillRect(0,0,getWidth(),getHeight());
		Color cyan=Color.cyan;
		g.setColor(cyan);
		final Font font=g.getFont();
		Font timeFont=new Font("Monospaced",Font.PLAIN,30);
		g.setFont(timeFont);
		final String date=getDate(),time=getFormattedTime();
		final int timeWidth=g.getFontMetrics().stringWidth(time),timeHeight=g.getFontMetrics().getHeight();
		Font dateFont=new Font("Monospaced",Font.PLAIN,20);
		g.setFont(dateFont);
		final int dateWidth=g.getFontMetrics().stringWidth(date),dateHeight=g.getFontMetrics().getHeight();
		final int theight=dateHeight+timeHeight;	
		g.setFont(timeFont);
		g.drawString(getTime(),getWidth()/2-timeWidth/2,getHeight()/2-theight/2);
		g.setFont(dateFont);
		g.drawString(getDate(),getWidth()/2-dateWidth/2,getHeight()/2-theight/2+timeHeight);
		g.setColor(col);
		g.setFont(font);
	}
	public void update(){
		repaint();
	}
	public static String getDate(){
		final Calendar calendar=new GregorianCalendar();
		final int week=calendar.get(Calendar.DAY_OF_WEEK);
		final int date=calendar.get(Calendar.DATE);
		final int month=calendar.get(Calendar.MONTH);
		final int year=calendar.get(Calendar.YEAR);
		final String dateStr=toCamelCase(DayOfWeek.of(week-1).name())+", "+date+DigitalClock.getDateSuffix(date)+" "+
			toCamelCase(Month.of(month).name())+" "+year;
		return dateStr;
	}
	public static String getDateSuffix(final int date){
		switch(date%10){
			case 1 : return "st";
			case 2 : return "nd";
			case 3 : return "rd";
			default :return "th";
		}
	}
	public static String toCamelCase(final String str){
		return str.substring(0,1).toUpperCase()+str.substring(1,str.length()).toLowerCase();
	}

	public static String getTime(){
		final Calendar calendar=new GregorianCalendar();
		final int hr=calendar.get(Calendar.HOUR);
		final int min=calendar.get(Calendar.MINUTE);
		final int sec=calendar.get(Calendar.SECOND);
		String strHr=String.valueOf(hr);
		String strMin=String.valueOf(min);
		String strSec=String.valueOf(sec);
		if(strHr.length()==1)
			strHr=0+strHr;
		if(strMin.length()==1)
			strMin=0+strMin;
		if(strSec.length()==1)
			strSec=0+strSec;
		return strHr+":"+strMin+":"+strSec;
	}
	public static String getFormattedTime(){
		final Date date=new Date();
		final SimpleDateFormat formatter=new SimpleDateFormat("HH:mm:ss");
		final String _date=formatter.format(date);
		return _date;
	}
	public static String getHours(){
		return getFormattedTime().split(":")[0];
	}
	public static String getMinutes(){
		return getFormattedTime().split(":")[1];
	}
	public static String getSeconds(){
		return getFormattedTime().split(":")[2];
	}
	public void componentMoved(ComponentEvent event){}
	public void componentResized(ComponentEvent event){
		System.out.println("resized");
	}
	public void componentClosed(ComponentEvent event){}
	public void componentHidden(ComponentEvent event){}
	public void componentShown(ComponentEvent event){}
	/*public static void main(String $[]){
	//DigitalClock clock=new DigitalClock();
	System.out.println(getDate());
	System.out.println(getTime());
	}*/
}
