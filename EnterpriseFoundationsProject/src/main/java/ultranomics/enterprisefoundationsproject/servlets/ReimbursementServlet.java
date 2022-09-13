package ultranomics.enterprisefoundationsproject.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ultranomics.enterprisefoundationsproject.services.ReimbursementService;

public class ReimbursementServlet extends HttpServlet{
    
    private final ReimbursementService reimbursementServ;
    
    public ReimbursementServlet(ReimbursementService reimbursementServ){
        this.reimbursementServ = reimbursementServ;
    }

    //doGet:get all reimbursements(require not Employee), 
    //      get owned reimbursements(user_ID matches author_id),
    //      get pending reimbursements(require not employee, )
    //TODO complete doGet method
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");
    }//end of doGet method
       
    //doPost:create new reimbursement(author_id to equal sessionID)
    //TODO complete doPost method
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");
    }//end of doPost method
    
    //TODO complete doPut method
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");
    }//end of doPut method
    
}
