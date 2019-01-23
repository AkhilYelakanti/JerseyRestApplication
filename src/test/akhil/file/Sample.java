/**
 * 
 */
package test.akhil.file;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Admin
 *
 */
@Path("/Sample")
public class Sample {
	
	@Path("/get")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSample() {
		Response response=null;
		String message="hello this is sample rest api to get method";
		response= Response.ok().entity(message).build();
		return response;
		
	}
	
	@Path("/post")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postSample(@FormParam("name") String name) {
		Response response=null;
		String message="hello this is sample rest api to get method";
		response= Response.ok().entity(message).build();
		return response;
		
	}

}
