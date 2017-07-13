import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Date;

public class DukeAndDuchess extends Applet implements Runnable {

	private static final long SLEEP = 1000 / 30;
	
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	
	private Image image;
	
	private Thread thread;
	private boolean running;
	
	public void init() {
		resize(WIDTH, HEIGHT);
		image = createImage(WIDTH, HEIGHT);
	}
	
	public void start() {
		thread = new Thread(this);
		running = true;
		thread.start();
	}
	
	public void update(Graphics graphics) {
		Graphics imageGraphics = image.getGraphics();
		imageGraphics.setColor(Color.black);
		imageGraphics.fillRect(0, 0, size().width, size().height);
		imageGraphics.setColor(Color.white);
		
		Date date = new Date(System.currentTimeMillis());
		imageGraphics.drawString("The Time is " + date.toLocaleString(), 0, 10);
		imageGraphics.dispose();
		
		graphics.drawImage(image, 0, 0, size().width, size().height, this);
	}
	
	public void stop() {
		
	}
	
	public void destroy() {
		if(!running) {
			return;
		}
		if(this.thread == null) {
			return;
		}
		Thread thread = this.thread;
		while(true) {
			try {
				thread.join();
				break;
			} catch(InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
		
	}
	
	public void run() {
		long systemTime = System.currentTimeMillis();
		long updateTime = systemTime;
		long elapseTime = 0;
		long snoozeTime = 0;
		
		while(running) {
			snoozeTime = SLEEP - elapseTime;
			
			if(snoozeTime <= 0) {
				snoozeTime = 1;
			}
			
			try {
				Thread.sleep(snoozeTime);
			} catch(InterruptedException e) {
				System.err.println(e.getMessage());
			}
			
			systemTime = System.currentTimeMillis();
			elapseTime = systemTime - updateTime;
			System.out.println(elapseTime);
			//TODO: update();
			repaint();
		}
	}
	
}
