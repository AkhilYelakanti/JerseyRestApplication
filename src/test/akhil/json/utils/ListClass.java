/**
 * 
 */
package test.akhil.json.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import test.akhil.json.helper.DBConnection;
import test.akhil.json.helper.HelperClass;




/**
 * @author Akhil Yelakanti
 *
 */
@Path("ListClass")
public class ListClass {
	
	private static Log log=LogFactory.getLog(ListClass.class);

	private static final String organizationList="select * from mstorganisation";
	
	/**
	 * 
	 * @param param
	 * @return response
	 * @author Akhil Yelakanti
	 * @date 09/18/2018
	 * @version v1.0
	 * @errorcodes 200-Success; 201-Failed; 202- SQL Exception or JSON exception ;203-Database Connection Exception;204-Database property Exception;
	 */
	@POST
    @Path("/organizationList")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response organizationList() {	
		JSONObject responseObject=new JSONObject();
			
		Connection connection=null;	
		PreparedStatement preparedStmt=null;
		ResultSet resultSet = null;
		try {			
			connection=DBConnection.sqlConnection();
			preparedStmt=connection.prepareStatement(organizationList);
			 resultSet = preparedStmt.executeQuery();
			Object object=HelperClass.convertToJSON(resultSet);
		responseObject=HelperClass.generateResponce(200, object,null);
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
			} catch (ClassNotFoundException e) {
				log.error(e.getMessage());
			responseObject=HelperClass.generateResponce(203,MessageConfig.API_203,""+e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
			responseObject=HelperClass.generateResponce(204,MessageConfig.API_204,""+e.getMessage());
		}
		
		finally {
			try {
				DBConnection.closeConnection(connection, preparedStmt, resultSet);
			} catch (SQLException e) {
				log.error(e.getMessage());
				responseObject=HelperClass.generateResponce(202,MessageConfig.API_202,""+e.getMessage());
			}
		}
			Response response=HelperClass.convertObjectToResponce(responseObject, 200);			
	return response;	
		}
	
}
