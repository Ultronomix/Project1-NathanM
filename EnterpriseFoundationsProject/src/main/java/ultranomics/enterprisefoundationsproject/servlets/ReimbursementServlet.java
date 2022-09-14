package ultranomics.enterprisefoundationsproject.servlets;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ultranomics.enterprisefoundationsproject.DTOs.ReimbursementDTO;
import ultranomics.enterprisefoundationsproject.DTOs.UserDTO;
import ultranomics.enterprisefoundationsproject.ErrorReport;
import ultranomics.enterprisefoundationsproject.datainsertion.NewReimbursementInsertion;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.DataSourceException;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.InvalidRequestException;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.ResourcePersistenceException;
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
        
        //HTTPS session (might not exist)
        HttpSession userSession = req.getSession(false);
        
        //Confirm user is logged in
        if(userSession == null){
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(401, "ERROR 401: Authorization Missing")));
            return;
        }
        
        //Gathering submitted username to be verified for authorization
        String usernameSubmission = req.getParameter("username");
        
        //userSession set in AuthenticationServlet which sets "authUser" after someone has logged in
        UserDTO requester = (UserDTO) userSession.getAttribute("authUser");
        
        if(!usernameSubmission.equals(userSession.getAttribute("authUser"))){
            resp.setStatus(403);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(403, "ERROR 403: Authorization Insufficent to Access")));
        }
        
        try{
            //Servlet layer new Reimbursement creation
            //reimbursementServ.register is passed reimbusement to insert, will return with 
            //ResourceCreationDTO to confirm new User was created
            NewReimbursementInsertion requestBody = jsonMapper.readValue(req.getInputStream(), NewReimbursementInsertion.class);
            ReimbursementDTO responseBody = reimbursementServ.generate(requestBody);
            resp.getWriter().write(jsonMapper.writeValueAsString(responseBody));
            
        } catch (InvalidRequestException | JsonMappingException e) {
            //TODO add logging based on 9/9 lecture
            resp.setStatus(400); // BAD REQUEST
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(400, e.getMessage())));

        } catch (ResourcePersistenceException e) {
            //TODO add logging based on 9/9 lecture
            resp.setStatus(409); // CONFLICT; indicates that the provided resource could not be saved without conflicting with other data
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(409, e.getMessage())));

        } catch (DataSourceException e) {
            //TODO add logging based on 9/9 lecture
            resp.setStatus(500); // INTERNAL SERVER ERROR; general error indicating a problem with the server
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(500, e.getMessage())));

        }
    }//end of doPost method
    
    //TODO complete doPut method
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");
    }//end of doPut method
    
}
