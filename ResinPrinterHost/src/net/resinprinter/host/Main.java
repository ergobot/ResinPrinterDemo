package net.resinprinter.host;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {

	 // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://0.0.0.0:8080/cwhost/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.demo.RestDemo package
        final ResourceConfig rc = new ResourceConfig().packages("net.resinprinter.host");
        rc.register(FileResource.class);
        rc.register(MachineResource.class);
        
        //Stuff for fileuploading
        rc.packages("org.glassfish.jersey.examples.multipart");
        rc.register(MultiPartFeature.class);
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
    	HostProperties.init();
    	
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
	
//	public static void main(String[] args) throws IOException, JobManagerException {
//		
//		
//		
//		
//		
//		// Read property file
//		Properties properties = HostProperties.getHostProperties();
//		
//		// Create job
//		File selectedFile = new File(properties.getProperty("sourcedir"),"Replicator_Cathedral.zip");
//		File workingDir = new File(properties.getProperty("printdir"));
//		// Delete and Create handled in jobManager
//		JobManager jobManager = new JobManager(selectedFile, workingDir);
//		
//		// Parse File
//		ExecutorService executor = Executors.newSingleThreadExecutor();
////		Future future = new GCodeParseThread(jobManager.getGCode());
//		
//		Runnable worker = new GCodeParseThread(jobManager.getGCode());
////		executor.execute(worker);
//		
//		Future future = executor.submit(worker);
//		
//		try {
//			future.get();
//		} catch (InterruptedException | ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		while(!executor.isTerminated()){
////			
////		}
//		
//		System.out.println("Finished parsing Gcode file");
//		System.out.println("Exiting");
//		
//		
//
//	}
	
	
}
