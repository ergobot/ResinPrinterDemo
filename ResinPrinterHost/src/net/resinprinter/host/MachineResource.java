package net.resinprinter.host;
// http://www.javatutorials.co.in/jax-rs-2-jersey-file-upload-example/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
 
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 




import net.resinprinter.host.JobManager.MachineAction;

import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
 
@Path("machine")
public class MachineResource {
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws IOException 
     */
    @GET
    @Path("start")
    @Produces(MediaType.TEXT_PLAIN)
    public String start() {
    	
    	if (JobManager.Status != MachineAction.RUNNING) {
			
			// Create job
			File selectedFile = new File(HostProperties.getSourceDir(),"Replicator_Cathedral.zip");
			
			// Delete and Create handled in jobManager
			JobManager jobManager = null;
			try {
				jobManager = new JobManager(selectedFile);
			} catch (JobManagerException | IOException e) {
				e.printStackTrace();
				return "false:Failed to start job:" + e.getMessage();
			}

			// Parse File
			ExecutorService executor = Executors.newSingleThreadExecutor();
			Runnable worker = new GCodeParseThread(jobManager.getGCode());
			executor.execute(worker);
		
			System.out.println("Finished parsing Gcode file");
			System.out.println("Exiting");
			
			
			return "True:Job Started";
		} else{return "False:Can't start a new job.  Machine is busy.  Job in progress";}
    	
        
    }
    
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws IOException 
     */
    @GET
    @Path("stop")
    @Produces(MediaType.TEXT_PLAIN)
    public String stop() {
    	
    	JobManager.Status = MachineAction.STOPPED;
    	return "Status changed to stopped";
        
    }
    
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws IOException 
     */
    @GET
    @Path("status")
    @Produces(MediaType.TEXT_PLAIN)
    public String status() {
    	return JobManager.Status.toString();
    }
    
    
	
}