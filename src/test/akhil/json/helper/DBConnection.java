package test.akhil.json.helper;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;



public class DBConnection {

	
	/*
	 * Connecting to MySql
	 */
	public static Connection setConnection(String DBUrl,String User,String Password) throws SQLException, ClassNotFoundException{
		
		 Connection connection = null;
		   //STEP 2: Register JDBC driver
		      
				Class.forName("com.mysql.cj.jdbc.Driver");
		
		      //STEP 3: Open a connection
		     	connection = DriverManager.getConnection(DBUrl,User,Password);
				      return connection;
		
		
	}
	
	/*
	 * Connecting to postgress
	 */
	public static Connection setConnection(String DBClass,String DBUrl,String User,String Password) throws SQLException, ClassNotFoundException{
		Connection connection = null;
		   //STEP 2: Register JDBC driver
				Class.forName(DBClass);
		      //STEP 3: Open a connection
		     	connection = DriverManager.getConnection(DBUrl,User,Password);
				      return connection;
		
	}
	
public static boolean insertStatus(ResultSet resultSet) throws SQLException {
	boolean status=false;
	while (resultSet.next()) {
		status=resultSet.getBoolean(1);
		
	}
return status;
}	

/*
 * DB Connection Close	
 */
public static void closeConnection(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet) throws SQLException {
	if(connection!=null&&!connection.isClosed())
		connection.close();
	if(preparedStatement!=null&&!preparedStatement.isClosed())
		preparedStatement.close();
	if(resultSet!=null&&!resultSet.isClosed())
		resultSet.close();
}

public static Connection sqlConnection() throws IOException, ClassNotFoundException, SQLException {
	Connection CONNECTION=null;
	Properties prop=new Properties();
	String filename="config.properties";
	InputStream input=DBConnection.class.getClassLoader().getResourceAsStream(filename);
	
		prop.load(input);
	

		Class.forName(prop.getProperty("db.classname"));
	
	
		CONNECTION = DriverManager.getConnection(prop.getProperty("db.drivername"),prop.getProperty("db.username"),prop.getProperty("db.password"));
	
	return CONNECTION;
	
}


public static Connection sqlConnectionArchiveDB() throws IOException, ClassNotFoundException, SQLException {
	Connection CONNECTION=null;
	Properties prop=new Properties();
	String filename="config.properties";
	InputStream input=DBConnection.class.getClassLoader().getResourceAsStream(filename);
	
		prop.load(input);
	

		Class.forName(prop.getProperty("db.classnameArchive"));
	
	
		CONNECTION = DriverManager.getConnection(prop.getProperty("db.drivernameArchive"),prop.getProperty("db.usernameArchive"),prop.getProperty("db.passwordArchive"));
	
	return CONNECTION;
	
}

}
