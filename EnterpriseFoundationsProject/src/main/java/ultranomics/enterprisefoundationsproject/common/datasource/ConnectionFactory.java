package ultranomics.enterprisefoundationsproject.common.datasource;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static ConnectionFactory connFactory;
    private Properties dbprops = new Properties(); 
        
    public ConnectionFactory(){
        try{    
            Class.forName("org.postgresql.Driver");
            dbprops.load(new FileReader("src/main/resources/application.properties"));  
        }catch(IOException e){
            System.err.println("Error: could not read from application file");
        }
        catch(ClassNotFoundException e){
            System.err.println("Error: PostgreSQL JDBC Driver failed to load");
        }
    }
    
    public static ConnectionFactory getInstance(){
        if(connFactory == null){
            connFactory = new ConnectionFactory();
        }
        return connFactory;
    }
    
    
    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(dbprops.getProperty("db-url"), dbprops.getProperty("db-username"), dbprops.getProperty("db-password"));     
    }
}
