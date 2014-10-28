package net.resinprinter.host;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
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
	
	}

	public void Show(BufferedImage bImage){
		
		graphics.drawImage(bImage,null,0,0);
	}
	
	public void Close(){
		window.dispose();
		graphics.dispose();
	}
}
