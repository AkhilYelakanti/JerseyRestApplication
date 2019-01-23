package test.akhil.json.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.UUID;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.HashMap;


import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.postgresql.util.PGobject;



public class HelperClass {

	@SuppressWarnings("unchecked")
	public static Object convertToJSON(ResultSet resultSet) throws SQLException{
		 JSONArray jsonArray = new JSONArray();
		 while (resultSet.next()) {
             HashMap<String, Object> obj = new HashMap<String, Object>();
	            int total_rows = resultSet.getMetaData().getColumnCount();
	            for (int i = 0; i < total_rows; i++) {
	            	String columnname=resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
	            	Object columnvalue=null;
	            	 int type = resultSet.getMetaData().getColumnType(i+1);
	            	 
	            	 // column type 1111 indicates the json format.
	            	 if(type==1111) {
	            		 PGobject pgobject= new PGobject();
	            		 pgobject=(PGobject) resultSet.getObject(i + 1);
	            		 String value=pgobject.getValue();
	            		 JSONParser parser= new JSONParser();
	            		 try {
							columnvalue=parser.parse(value);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            		 
	            	 }else {
	            		 columnvalue=resultSet.getObject(i + 1);
	            	 }
	            	 obj.put(columnname, columnvalue);
	            }
	            jsonArray.add(obj);
	        }
		return jsonArray;
	}
	
	
	public static Object convertToJSONObject(ResultSet resultSet) throws SQLException{
		HashMap<String, Object> obj = new HashMap<String, Object>();
		 while (resultSet.next()) {
	            int total_rows = resultSet.getMetaData().getColumnCount();
	            for (int i = 0; i < total_rows; i++) {
	            	String columnname=resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
	            	Object columnvalue=null;
	            	 int type = resultSet.getMetaData().getColumnType(i+1);
	            	 // column type 1111 indicates the json format.
	            	 if(type==1111) {
	            		 PGobject pgobject= new PGobject();
	            		 pgobject=(PGobject) resultSet.getObject(i + 1);
	            		 String value=pgobject.getValue();
	            		 JSONParser parser= new JSONParser();
	            		 try {
							columnvalue=parser.parse(value);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            		 
	            	 }else {
	            		 columnvalue=resultSet.getObject(i + 1);
	            		 
	            	 }
	            	 obj.put(columnname, columnvalue);
	            }
	        }
		return obj;
		
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject generateResponce(int status,Object dataObject,String errorObject){
		JSONObject responseObject=new JSONObject();
		if(dataObject!=null)
		responseObject.put("Data",dataObject);
		else if(errorObject==null){
			HashMap<String, Object> hashMap=new HashMap<String, Object>();
			hashMap.put("Message", errorObject);
			responseObject.put("Data",hashMap);
}
		
			JSONArray array=new JSONArray();
			if(errorObject!=null)
			array.add(errorObject);
		responseObject.put("Error",array);
		
		responseObject.put("Status",status);
		
		
		return responseObject;
		
	}

	public static Response convertObjectToResponce(Object responseObject,int status){
		ResponseBuilder builder = Response.ok(responseObject).status(status);
		
		return builder.build();
	}

	
/*
 * DB Connection Close	
 */
public static void closeConnection(Connection connection) throws SQLException {
	if(connection!=null&&!connection.isClosed())
		connection.close();
}



}
