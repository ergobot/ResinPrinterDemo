package net.resinprinter.host.obsoleted;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

public class ReadGcodeDemo {

	public static void main(String[] args) throws IOException {
		// 2step Goal to read the gcode file

		// Start by reading the file
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
	}

}
