package net.resinprinter.host;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.resinprinter.host.JobManager.Status;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("stop")
public class Stop {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws IOException 
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String stop() {
    	
    	JobManager.Status = Status.STOPPED;
    	return "Status changed to stopped";
        
    }
}

