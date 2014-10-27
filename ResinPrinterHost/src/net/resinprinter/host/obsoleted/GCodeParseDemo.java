package net.resinprinter.host.obsoleted;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.resinprinter.host.JobManager;

import org.apache.commons.io.FileUtils;


public class GCodeParseDemo {
       public static void main(String[] args) throws IOException {
    	   
    	   Properties prop = new Properties();
   		prop.load(Driver.class.getClassLoader().getResourceAsStream("config.properties"));
   		
   		String printdir = prop.getProperty("printdir");
   		String baseName = "Replicator_Cathedral";
   		String gcodeExt = ".gcode";
   				
   		File file = new File(printdir+baseName+".slice/"+baseName+gcodeExt);
   		String string = FileUtils.readFileToString(file);
   		System.out.println("Read in: " + string);
   		// Step 1: read the header

   		// Step 2: read line-by-line and interpret the text file
   	
   		
    	   
              BufferedReader stream = null;
              try {
                     stream = new BufferedReader(new FileReader(new File(printdir+baseName+".slice/"+baseName+gcodeExt)));
                     String currentLine;
                     Integer sliceCount = null;
                     Pattern slicePattern = Pattern.compile("\\s*;\\s*<\\s*Slice\\s*>\\s*(\\d+|blank)\\s*", Pattern.CASE_INSENSITIVE);
                     Pattern delayPattern = Pattern.compile("\\s*;\\s*<\\s*Delay\\s*>\\s*(\\d+)\\s*", Pattern.CASE_INSENSITIVE);
                     Pattern sliceCountPattern = Pattern.compile("\\s*;\\s*Number\\s*of\\s*Slices\\s*=\\s*(\\d+)\\s*", Pattern.CASE_INSENSITIVE);
                     while ((currentLine = stream.readLine()) != null && JobManager.Status == JobManager.Status.RUNNING) {
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
                                	  	System.out.println("Show picture: " + baseName + imageNumber+ ".png");
                                	  	
                                	  	// close bufferedimage
                                	  	
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
       
       public static Integer padLength(Integer number){
    	   if(number == null){return null;}
    	   return number.toString().length() + 1;
    	   
       }
}
