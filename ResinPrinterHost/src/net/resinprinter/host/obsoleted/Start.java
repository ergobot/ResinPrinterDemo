package net.resinprinter.host.obsoleted;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.resinprinter.host.GCodeParseThread;
import net.resinprinter.host.HostProperties;
import net.resinprinter.host.JobManager;
import net.resinprinter.host.JobManagerException;
import net.resinprinter.host.JobManager.MachineAction;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("start")
public class Start {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws IOException 
     */
    @GET
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
}

