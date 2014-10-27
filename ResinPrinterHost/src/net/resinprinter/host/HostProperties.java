package net.resinprinter.host;

import java.io.IOException;
import java.util.Properties;

public class HostProperties {

	public HostProperties(){}
	
	public static Properties getHostProperties() throws IOException{
		
		Properties properties = new Properties();
		
		properties.load(HostProperties.class.getClassLoader().getResourceAsStream("config.properties"));
		
		return properties;
		
	}
	
}
