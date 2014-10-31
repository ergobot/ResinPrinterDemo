package net.resinprinter.host.obsoleted;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;

import net.resinprinter.host.Files;
import net.resinprinter.host.HostProperties;
import net.resinprinter.host.JobManager.MachineAction;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("projects")
public class Projects {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws IOException 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Files getProjects() {
    	System.out.println("Getting projects");
    	System.out.println(HostProperties.getSourceDir());
    	File dir = new File(HostProperties.getSourceDir());
    	System.out.println(dir.exists());
    	
		String[] extensions = new String[] { "zip" };
		
		List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
		ArrayList<String> names = new ArrayList<String>();
		for(File file : files){
			System.out.println("Found file: " + file.getAbsolutePath());
			names.add(file.getName());
		}
		for(String name: names){
			System.out.println("Project Name: " + name);
		}
		if(names.isEmpty()){System.out.println("Didn't find any files in the directory");}
    	Files projects = new Files(names);
    	
        return projects;
    }
}

