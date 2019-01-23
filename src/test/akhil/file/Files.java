/**
 * 
 */
package test.akhil.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;

import com.sun.jersey.core.header.ContentDisposition;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

/**
 * @author Admin
 *
 */
@Path("/files")
@SuppressWarnings({"rawtypes","unused","unchecked"})
public class Files {
	
	private static 	String uploadedFileLocation = "F:\\devolopement\\";

	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail,@FormDataParam("name") String name) {

		String uploadedFileLocation = "F:\\devolopement\\" + fileDetail.getFileName();
		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);

		String output = "File uploaded to : " + uploadedFileLocation ;

		return Response.status(200).entity(output).build();

	}

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
		String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@SuppressWarnings("resource")
	@POST
	@Path("/mutliple")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadMultiple(@FormDataParam("file") FormDataBodyPart body){
		JSONArray array= new JSONArray();	
		
		for(BodyPart part : body.getParent().getBodyParts()){
			Map map=new HashMap();
	        InputStream is = part.getEntityAs(InputStream.class);
	        ContentDisposition meta = part.getContentDisposition();
			// save it
	        String filename=uploadedFileLocation+meta.getFileName();
			File file=null;
			try {
				OutputStream out = new FileOutputStream(new File(filename));
				int read = 0;
				byte[] bytes = new byte[1024];
				file=new File(uploadedFileLocation+meta.getFileName());
				out = new FileOutputStream(file);
				while ((read = is.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String output = "File uploaded to : " + uploadedFileLocation ;
			map.put("absolutepath", filename);
			map.put("path", filename);
			map.put("filename", meta.getFileName());
			map.put("filetype", getFileExtension(meta.getFileName()));
			map.put("filesize", file.length());
			map.put("createddate", meta.getCreationDate());
			array.add(map);	
	    }
		
		return Response.status(200).entity(array).build();
	}
	public static String getFileExtension(String fullName) {
	   
	    String fileName = new File(fullName).getName();
	    int dotIndex = fileName.lastIndexOf('.');
	    return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}
	private String  doUpload(InputStream is, ContentDisposition meta) {
		// TODO Auto-generated method stub
		String uploadedFileLocation = "F:\\devolopement\\" + meta.getFileName();

		// save it
		writeToFile(is, uploadedFileLocation);
		String output = "File uploaded to : " + uploadedFileLocation ;
		return output;
	}

}
