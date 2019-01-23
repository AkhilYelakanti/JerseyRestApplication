/**
 * 
 */
package test.akhil.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONObject;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

/**
 * @author Admin
 *
 */
@Path("ExcelUpload")
@SuppressWarnings({"unchecked","unused","rawtypes","static-access","null"})
public class ExcelUpload {
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		Response response=null;
		JSONObject map= new JSONObject();;
		// validate the format of file
		String filetype=getFileExtension(fileDetail.getFileName());
		filetype=filetype.toLowerCase();
		if(filetype.contains("xlsx")||filetype.contains("xls")) {
			
		}else {
			map.put("ERROR", "File type with extension"+filetype+" is not allowed");	
		}
		
	    try {
	    	map=convertInputStreamToHasMap(uploadedInputStream);
		} catch (EncryptedDocumentException e) {
			map.put("ERROR", "EncryptedDocumentException: "+e.getMessage());
		} catch (InvalidFormatException e) {
			map.put("ERROR", "InvalidFormatException: "+e.getMessage());
		} catch (IOException e) {
			map.put("ERROR", "IOException: "+e.getMessage());
		}
		return response.ok().entity(map).build();
	}
	
	
	/**
	 * Method allow the inputstream as input and and returns object with sheet names as key and sheet data as value in array format.
	 * @param uploadedInputStream
	 * @return Object 
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static JSONObject convertInputStreamToHasMap(InputStream uploadedInputStream) throws EncryptedDocumentException, InvalidFormatException, IOException {
		JSONObject sheetmap=new JSONObject();
		// Creating a Workbook from an Excel file inputstream (.xls or .xlsx)
		Workbook workbook = WorkbookFactory.create(uploadedInputStream);

	       // Retrieving Sheets using for-each loop
	        for(Sheet sheet: workbook) {
	          
	            // Create a DataFormatter to format and get each cell's value as String
	            DataFormatter dataFormatter = new DataFormatter();
	            // Iterating over Rows and Columns using for-each loop 
	            ArrayList array= new ArrayList();
	            for(int i=1;i<sheet.getLastRowNum();i++) {
	            	Map map=new HashMap();
	            	Row row=sheet.getRow(i);
	            	System.out.println();
	            	for(int j=0;j<row.getLastCellNum();j++) {
	            		
	            		// getting the column names 
	            		Cell columnnamecell=sheet.getRow(0).getCell(j);
	            		 String columnname = dataFormatter.formatCellValue(columnnamecell);
	            		
	            		Cell cell=row.getCell(j);
	            		 Object cellValue =dataFormatter.formatCellValue(cell);
	            		 map.put(columnname, cellValue);
	            	}
	            	array.add(map);
	            }
	            sheetmap.put(sheet.getSheetName(), array);
	        }
		return sheetmap;
	}
	public static String getFileExtension(String fullName) {
	    String fileName = new File(fullName).getName();
	    int dotIndex = fileName.lastIndexOf('.');
	    return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}
	
}
