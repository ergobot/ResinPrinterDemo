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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 

import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
 
@Path("files")
public class FileResource {
 
	private static final String BASE_PATH = HostProperties.getSourceDir();
 
	@POST
	@Path("upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public String uploadFile(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDisposition)
			throws FileNotFoundException, IOException {
 
		String fileName = fileDisposition.getFileName();
		System.out.println("***** fileName " + fileDisposition.getFileName());
		String filePath = BASE_PATH + fileName;
		try (OutputStream fileOutputStream = new FileOutputStream(filePath)) {
			int read = 0;
			final byte[] bytes = new byte[1024];
			while ((read = fileInputStream.read(bytes)) != -1) {
				fileOutputStream.write(bytes, 0, read);
			}
		}
 
		return "File Upload Successfully !!";
	}
	
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws IOException 
     */
    @GET
    @Path("list")
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