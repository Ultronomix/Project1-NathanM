package ultranomics.enterprisefoundationsproject.servlets;

import javax.servlet.http.HttpServlet;
import ultranomics.enterprisefoundationsproject.services.AuthenticationService;

public class AuthenticationServlet extends HttpServlet{
    
    private final AuthenticationService authenticationServ;
    
    public AuthenticationServlet(AuthenticationService authenticationServ){
        this.authenticationServ = authenticationServ;
    }

    //TODO add doGet method
    //TODO add doPost method
}
