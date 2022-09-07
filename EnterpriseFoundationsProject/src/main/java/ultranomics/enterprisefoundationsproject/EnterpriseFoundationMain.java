
package ultranomics.enterprisefoundationsproject;

import ultranomics.enterprisefoundationsproject.servlets.UserServlet;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import ultranomics.enterprisefoundationsproject.DAOs.UserDAO;
import ultranomics.enterprisefoundationsproject.services.UserService;

public class EnterpriseFoundationMain {
    public static void main(String[] args) throws LifecycleException{
        String docBase = System.getProperty("java.io.tmpdir");
        
        Tomcat webServer = new Tomcat();
        webServer.setBaseDir(docBase);
        webServer.setPort(5000); //default is 8080
        webServer.getConnector(); //formality, connects server requests to application
        
        UserDAO userDAO = new UserDAO();
        
        //TODO add AuthService
        UserService userServ = new UserService(userDAO);
        //TODO add AuthServlet
        UserServlet userSlet = new UserServlet(userServ);
        
        final String rootContext = "/p1";
        webServer.addContext(rootContext, docBase);
        webServer.addServlet(rootContext, "UserServlet", userSlet).addMapping("/users");
        //webServer.addServlet(rootContext, "ReimbursementServlet", new UserServlet()).addMapping("/reimbursements");
        
        
        webServer.start();
        webServer.getServer().await();
        System.out.println("Web application started successfully");
        
        
    }
}
