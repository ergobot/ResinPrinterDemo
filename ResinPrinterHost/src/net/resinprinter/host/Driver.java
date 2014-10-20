package net.resinprinter.host;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class Driver {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		Scanner keyboard = new Scanner(System.in);

		Properties prop = new Properties();

		String filelocation = "";

		prop.load(Driver.class.getClassLoader().getResourceAsStream(
				"config.properties"));
		System.out.println(prop.getProperty("sourcedir"));

		filelocation = prop.getProperty("sourcedir");
		System.out.println("Filelocation: " + filelocation);

		// kill it if no properties are avail
		if (filelocation.equals("")) {
			System.out.println("filelocation was empty, exiting application");
			throw new IOException();
		}

		System.out.println(filelocation);

		String baseName = "Replicator_Cathedral";
		String selectedFile = baseName + ".zip";

		String printdir = prop.getProperty("printdir");
		System.out.println("Printdir: " + printdir);
		FileUtils.deleteDirectory(new File(printdir));
		System.out.println("Check yourself");
		keyboard.next();
		// unpack selected file from sourcedir to printdir
		// unZipIt(filelocation + selectedFile,printdir);
		ZipFile zipFile = new ZipFile(filelocation + selectedFile);
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			File entryDestination = new File(printdir, entry.getName());
			entryDestination.getParentFile().mkdirs();
			if (entry.isDirectory())
				entryDestination.mkdirs();
			else {
				InputStream in = zipFile.getInputStream(entry);
				OutputStream out = new FileOutputStream(entryDestination);
				IOUtils.copy(in, out);
				IOUtils.closeQuietly(in);
				IOUtils.closeQuietly(out);
			}
		}

		// read import parts of header

		int totalImages = 872;// <=
		String exten = ".png";

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsConfiguration gc = ge.getDefaultScreenDevice()
				.getDefaultConfiguration();

		// From Code Example 2.
		// VolatileImage vimage = createVolatileImage(bimage.getWidth(),
		// bimage.getHeight(), Transparency.OPAQUE);

		// Graphics2D g = null;

		GraphicsDevice device = ge.getDefaultScreenDevice();
		Window window = new JFrame();

		System.out.println(device.isFullScreenSupported());

		device.setFullScreenWindow(window);
		// g = vimage.createGraphics();
		Graphics2D g = (Graphics2D) window.getGraphics();
		try {

			for (int i = 0; i <= totalImages; i++) {
				// Loads the image from a file using ImageIO.
				String badfile = printdir + baseName + ".slice/" + baseName
						+ String.format("%04d", i) + exten;
				System.out.println(badfile);

				BufferedImage bimage = ImageIO.read(new File(badfile));
				g.drawImage(bimage, null, 0, 0);
				System.out.println("Showing pic: " + i);

			}
		} catch (IIOException iioex) {
			iioex.printStackTrace();

		}
		// Thread.sleep(2000);
		finally {
			// It's always best to dispose of your Graphics objects.
			window.dispose();
			g.dispose();
		}

		// loadFromFile(filelocation);

	}

}