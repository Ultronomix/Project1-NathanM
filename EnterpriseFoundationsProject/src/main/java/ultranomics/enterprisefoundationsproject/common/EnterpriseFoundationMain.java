
package ultranomics.enterprisefoundationsproject.common;

import ultranomics.enterprisefoundationsproject.common.datasource.ConnectionFactory;
import java.sql.Connection;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class EnterpriseFoundationMain {
    public static void main(String[] args) throws LifecycleException{
        String docBase = System.getProperty("java.io.tmpdir");
        
        Tomcat webServer = new Tomcat();
        webServer.setBaseDir(docBase);
        webServer.setPort(5000);
        webServer.getConnector();
        
        
        final String rootContext = "/p1";
        webServer.addContext(rootContext, docBase);
        webServer.addServlet(rootContext, "UserServlet", new UserServlet()).addMapping("/users");
        
        
        webServer.start();
        webServer.getServer().await();
        
        
        
//        System.out.println("Hello World!");
//        
//        ConnectionFactory connection = new ConnectionFactory();
//        
//        try{
//            Connection conn = connection.getConnection();
//            if(conn != null){
//                System.out.println("Connection Established");
//            }
//            
//            
//        }catch(Exception e){
//            System.err.println("Error: problem with the Database");
//            e.printStackTrace();
//        }
    }
}
