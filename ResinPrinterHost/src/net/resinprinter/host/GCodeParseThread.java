package net.resinprinter.host;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

import net.resinprinter.host.JobManager.Status;

public class GCodeParseThread  implements Runnable {

	File gCode = null;
	public GCodeParseThread(File gCode){
		this.gCode = gCode;
	}
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " Start");
		JobManager.Status = Status.RUNNING;
		parse();
		System.out.println(Thread.currentThread().getName() + " End.");
	}
	
	private void parse(){
		System.out.println("Parsing the file");
		 BufferedReader stream = null;
         try {
                stream = new BufferedReader(new FileReader(gCode));
                String currentLine;
                Integer sliceCount = null;
                Pattern slicePattern = Pattern.compile("\\s*;\\s*<\\s*Slice\\s*>\\s*(\\d+|blank)\\s*", Pattern.CASE_INSENSITIVE);
                Pattern delayPattern = Pattern.compile("\\s*;\\s*<\\s*Delay\\s*>\\s*(\\d+)\\s*", Pattern.CASE_INSENSITIVE);
                Pattern sliceCountPattern = Pattern.compile("\\s*;\\s*Number\\s*of\\s*Slices\\s*=\\s*(\\d+)\\s*", Pattern.CASE_INSENSITIVE);
                while ((currentLine = stream.readLine()) != null && JobManager.Status == Status.RUNNING) {
               	 if(currentLine.startsWith(";")){
                      Matcher matcher = slicePattern.matcher(currentLine);
                      if (matcher.matches()) {
                             if (sliceCount == null) {
                                    throw new IllegalArgumentException("No 'Number of Slices' line in gcode file");
                             }
                             
                             if (matcher.group(1).toUpperCase().equals("BLANK")) {
                                    //TODO: call method to blank the screen.
                           	  System.out.println("Show Blank");
                             } else {
                                    //TODO: call slice method
                           	  	int incoming = Integer.parseInt(matcher.group(1));
                           	  	System.out.println("Show Picture: " +incoming);
                           	  	String imageNumber = String.format("%0"+padLength(sliceCount) + "d",incoming);
                           	  	System.out.println("Formatted: " + imageNumber);
//                           	  	System.out.println("Show picture: " + baseName + imageNumber+ ".png");
                           	 File imageLocation = new File(gCode.getParentFile(),FilenameUtils.removeExtension(gCode.getName()) + imageNumber+ ".png");
                           	 System.out.println("ImageLocation: " + imageLocation.getAbsolutePath());
                           	 System.out.println("Exists: " + imageLocation.exists());
                           	 
                           	 BufferedImage bimage = ImageIO.read(imageLocation);
                           	 System.out.println("Show picture: " + FilenameUtils.removeExtension(gCode.getName()) + imageNumber+ ".png");
                           	 DisplayManager.Instance().Show(bimage);
                           	  	// close bufferedimage
//                           	 bimage.
                           	 
                             }
                             continue;
                      }
                      matcher = delayPattern.matcher(currentLine);
                      if (matcher.matches()) {
                             try {
                           	  System.out.println("Sleep");
                                    Thread.sleep(Integer.parseInt(matcher.group(1)));
                             } catch (InterruptedException e) {
                                    e.printStackTrace();
                             }
                             continue;
                      }
                      matcher = sliceCountPattern.matcher(currentLine);
                      if (matcher.matches()) {
                             sliceCount = Integer.parseInt(matcher.group(1));
                             System.out.println(sliceCount);
                             continue;
                      }
                      // print out comments
                      System.out.println("Comment: " + currentLine);
               	 }else
               	 {
                      //TODO: send gcode to rxtx...
                      System.out.println("Send to gcode: " + currentLine);
               	 }
                }
         } catch (FileNotFoundException e) {
                e.printStackTrace();
         } catch (IOException e) {
                e.printStackTrace();
         } finally {
                if (stream != null) {
                      try {
                             stream.close();
                      } catch (IOException e) {}
                }
         }
		
	}
	


	 public Integer padLength(Integer number){
 	   if(number == null){return null;}
 	   return number.toString().length() + 1;
 	   
    }
	
}
