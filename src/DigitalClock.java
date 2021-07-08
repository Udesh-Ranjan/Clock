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
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.GraphicsEnvironment;
import java.io.File;

public class DigitalClock extends JPanel implements ComponentListener,Clock{
	BufferedImage img;
	Font goblin;
	boolean goblinFontRegistered;
	public DigitalClock(final int width,final int height){
		//registerFont();
		this.addComponentListener(this);
		img=new BufferedImage(width,height,BufferedImage.TYPE_4BYTE_ABGR);
		setSize(width,height);
	}
	private void registerFont(){
		try{
			goblin=Font.createFont(Font.TRUETYPE_FONT,
					new File("/home/dev/Documents/javaPro/Clock/src/a-goblin-appears-font/AGoblinAppears-o2aV.ttf"))
				.deriveFont(12f);
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(goblin);
			goblinFontRegistered=true;
		}catch(Exception exception){
			exception.printStackTrace();
		}

	}

	@Override
	public void paint(Graphics g){
		/*Color col=g.getColor();
		  Color black=Color.black;
		  g.setColor(black);
		  g.fillRect(0,0,getWidth(),getHeight());
		  Color cyan=Color.cyan;
		  g.setColor(cyan);
		  final Font font=g.getFont();
		  final int fontSize=getFontSize(g);
		//System.out.println("fontSize : "+fontSize);
		Font timeFont=new Font("Monospaced",Font.PLAIN,fontSize);
		g.setFont(timeFont);
		final String date=getDate(),time=getFormattedTime();
		final int timeWidth=g.getFontMetrics().stringWidth(time),timeHeight=g.getFontMetrics().getHeight();
		Font dateFont=new Font("Monospaced",Font.PLAIN,fontSize-10);
		g.setFont(dateFont);
		final int dateWidth=g.getFontMetrics().stringWidth(date),dateHeight=g.getFontMetrics().getHeight();
		final int theight=dateHeight+timeHeight;	
		g.setFont(timeFont);
		g.drawString(getTime(),getWidth()/2-timeWidth/2,getHeight()/2-theight/2);
		g.setFont(dateFont);
		g.drawString(getDate(),getWidth()/2-dateWidth/2,getHeight()/2-theight/2+timeHeight);
		g.setColor(col);
		g.setFont(font);
		*/
		if(img==null){
			System.out.println("img is null returning from paint");
			return;
		}
		g.drawImage(img,0,0,getWidth(),getHeight(),this);
	}
	public int getFontSize(Graphics g){
		System.out.println("getFontSize");
		int size=20;
		while(true){
			int newSize=size+1;
			final Font font=g.getFont();
			Font timeFont=new Font("Monospaced",Font.PLAIN,newSize);
			//Font timeFont=goblin.deriveFont(newSize);
			g.setFont(timeFont);
			final String date=getDate(),time=getFormattedTime();
			final double timeWidth=g.getFontMetrics().stringWidth(time),timeHeight=g.getFontMetrics().getHeight();
			Font dateFont=new Font("Monospaced",Font.PLAIN,newSize-10);
			//Font dateFont=goblin.deriveFont(newSize-10);
			g.setFont(dateFont);
			final double dateWidth=g.getFontMetrics().stringWidth(date),dateHeight=g.getFontMetrics().getHeight();
			final double theight=dateHeight+timeHeight;	
			g.setFont(font);
			if((Math.max(timeWidth,dateWidth)/getWidth())*100>=70)
				break;
			size=newSize;
			System.out.println("size : "+size);
		}
		System.out.println("Exiting getFontSize");
		return size;
	}
	public void update(){
		if(img==null){
			System.out.println("Img is null cannot update");
			return;
		}
		final Graphics2D g=(Graphics2D)img.getGraphics();
		if(g==null){
			System.out.println("graphics is null cannot update");
			return;
		}
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		final Color col=g.getColor();
		Color black=Color.black;
		g.setColor(black);
		g.fillRect(0,0,getWidth(),getHeight());
		Color timeColor=Color.green;
		g.setColor(timeColor);
		final Font font=g.getFont();
		final int fontSize=getFontSize(g);
		System.out.println("fontSize : "+fontSize);
		final Font timeFont=new Font("Monospaced",Font.PLAIN,fontSize);
		//Font timeFont=goblin.deriveFont(fontSize);
		g.setFont(timeFont);
		final String date=getDate(),time=getFormattedTime();
		final int timeWidth=g.getFontMetrics().stringWidth(time),timeHeight=g.getFontMetrics().getHeight();
		final Font dateFont=new Font("Monospaced",Font.PLAIN,fontSize-10);
		//Font dateFont=goblin.deriveFont(fontSize-10);
		g.setFont(dateFont);
		final int dateWidth=g.getFontMetrics().stringWidth(date),dateHeight=g.getFontMetrics().getHeight();
		final int theight=dateHeight+timeHeight;	
		g.setFont(timeFont);
		g.drawString(getTime(),getWidth()/2-timeWidth/2,getHeight()/2-theight/2);
		g.setFont(dateFont);
		g.drawString(getDate(),getWidth()/2-dateWidth/2,getHeight()/2-theight/2+timeHeight);
		g.setColor(col);
		g.setFont(font);
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
	@Deprecated
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
		if(img!=null){
			img.flush();
			img.getGraphics().dispose();
			img=new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
		}
		update();
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
