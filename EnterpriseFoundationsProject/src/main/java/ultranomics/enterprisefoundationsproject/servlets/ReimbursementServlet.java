package ultranomics.enterprisefoundationsproject.servlets;

import javax.servlet.http.HttpServlet;
import ultranomics.enterprisefoundationsproject.services.ReimbursementService;

public class ReimbursementServlet extends HttpServlet{
    
    private final ReimbursementService reimbursementServ;
    
    public ReimbursementServlet(ReimbursementService reimbursementServ){
        this.reimbursementServ = reimbursementServ;
    }

    //TODO add doGet method
    //TODO add doPost method
}
