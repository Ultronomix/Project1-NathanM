
package ultranomics.enterprisefoundationsproject;

import ultranomics.enterprisefoundationsproject.servlets.UserServlet;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import ultranomics.enterprisefoundationsproject.DAOs.*;
import ultranomics.enterprisefoundationsproject.services.*;

public class EnterpriseFoundationMain {
    public static void main(String[] args) throws LifecycleException{
        String docBase = System.getProperty("java.io.tmpdir");
        
        //Creating webServer
        Tomcat webServer = new Tomcat();
        webServer.setBaseDir(docBase);
        webServer.setPort(5000); //default is 8080
        webServer.getConnector(); //formality, connects server requests to application
        
        //Creating Data Access Objects (DAOs)
        UserDAO userDAO = new UserDAO();
        ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
        
        //Service methods
        UserService userServ = new UserService(userDAO);
        RegistrationService regServ = new RegistrationService(userDAO);
        AuthenticationService authServ = new AuthenticationService(userDAO);
        ReimbursementService reimbServ = new ReimbursementService(reimbursementDAO);
        
        //Connecting Servlets to Service layer
        //TODO add AuthServlet
        UserServlet userSlet = new UserServlet(userServ);
        
        
        //Connecting Servlets to webServer
        final String rootContext = "/p1";
        webServer.addContext(rootContext, docBase);
        webServer.addServlet(rootContext, "UserServlet", userSlet).addMapping("/users");
        //webServer.addServlet(rootContext, "ReimbursementServlet", new UserServlet()).addMapping("/reimbursements");
        
        
        webServer.start();
        webServer.getServer().await();
        System.out.println("Web application started successfully");
        
        
    }
}
