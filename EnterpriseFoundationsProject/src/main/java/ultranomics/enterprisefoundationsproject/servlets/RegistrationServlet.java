package ultranomics.enterprisefoundationsproject.servlets;

import javax.servlet.http.HttpServlet;
import ultranomics.enterprisefoundationsproject.services.RegistrationService;

public class RegistrationServlet extends HttpServlet{
    
    private final RegistrationService registrationServ;
    
    public RegistrationServlet(RegistrationService registrationServ){
        this.registrationServ = registrationServ;
    }

    //TODO add doGet method
    //TODO add doPost method
}
