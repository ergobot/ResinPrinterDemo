package net.resinprinter.host;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class DisplayManager {

	private static DisplayManager m_instance = null;

	public static DisplayManager Instance() {
		if (m_instance == null) {
			m_instance = new DisplayManager();
		}
		return m_instance;
	}
	
	private DisplayManager(){
		setup();
	}
	
	GraphicsEnvironment ge;
	GraphicsConfiguration gc;
	GraphicsDevice device;
	JFrame window;
	Graphics2D graphics;
	
	private void setup(){
	 ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
	gc = ge.getDefaultScreenDevice()
				.getDefaultConfiguration();
		
	device = ge.getDefaultScreenDevice();
	window = new JFrame();
		System.out.println(device.isFullScreenSupported());
		window.setUndecorated(true);
		device.setFullScreenWindow(window);
		
	graphics = (Graphics2D) window.getGraphics();	
	ShowBlank();
	
	}

	public void Show(BufferedImage bImage){
	
		graphics.drawImage(bImage,null,0,0);
	}
	
	public void ShowBlank(){
		graphics.setPaint(new GradientPaint(0,0,Color.RED,100, 0,Color.BLACK));
//		Rectangle2D rect = new Rectangle2D.Double(0, 0,40,100);
		Rectangle2D bounds = graphics.getDeviceConfiguration().getBounds();
		graphics.draw(bounds);
		graphics.fill(bounds);
//		graphics.setBackground(Color.BLACK);
//		window.setBackground(Color.BLACK);
	}
	
	public void Close(){
		window.dispose();
		graphics.dispose();
	}
}
