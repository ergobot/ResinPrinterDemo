package net.resinprinter.host;

import java.io.IOException;
import java.util.Properties;

public class HostProperties {

	public HostProperties(){}
	
	static String sourceDir = "";
	public static String getSourceDir(){
		return sourceDir;
	}
	static String workingDir = "";
	public static String getWorkingDir(){
		return workingDir;
	}
	
	public static void init() throws IOException{
		workingDir = HostProperties.getHostProperties().getProperty("printdir");
		sourceDir = HostProperties.getHostProperties().getProperty("sourcedir");
	}
	
	public static Properties getHostProperties() throws IOException{
		Properties properties = new Properties();
		properties.load(HostProperties.class.getClassLoader().getResourceAsStream("config.properties"));
		return properties;
	}
	
}
