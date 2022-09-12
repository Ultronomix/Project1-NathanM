package ultranomics.enterprisefoundationsproject.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ultranomics.enterprisefoundationsproject.services.AuthenticationService;

public class AuthenticationServlet extends HttpServlet{
    
    private final AuthenticationService authenticationServ;
    
    public AuthenticationServlet(AuthenticationService authenticationServ){
        this.authenticationServ = authenticationServ;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //TODO complete doPost method
    }//end doPost method
    
    
    //logging out
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate(); // this effectively "logs out" the requester by invalidating the session within the server
    }//end doDelete method
}
