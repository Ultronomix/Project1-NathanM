package ultranomics.enterprisefoundationsproject.servlets;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ultranomics.enterprisefoundationsproject.DTOs.ReimbursementDTO;
import ultranomics.enterprisefoundationsproject.DTOs.UserDTO;
import ultranomics.enterprisefoundationsproject.ErrorReport;
import ultranomics.enterprisefoundationsproject.dataalteration.ReimbursementApproveOrDenyAlteration;
import ultranomics.enterprisefoundationsproject.datainsertion.NewReimbursementInsertion;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.DataSourceException;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.InvalidRequestException;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.ResourceNotFoundException;
import ultranomics.enterprisefoundationsproject.services.ReimbursementService;

public class ReimbursementServlet extends HttpServlet{
    
    private final ReimbursementService reimbursementServ;
    
    public ReimbursementServlet(ReimbursementService reimbursementServ){
        this.reimbursementServ = reimbursementServ;
    }

    //doGet:get owned pending reimbursements (user_ID matches author_id with pending condition),
    //      get owned reimbursements(user_ID matches author_id),
    //      get all reimbursements(require not employee),
    //      get pending reimbursements(require not employee)
    //      get identified single reimbursement(require match employee or not employee)
    //TODO complete doGet method
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");
        
        //HTTPS session (might not exist)
        HttpSession userSession = req.getSession(false);
        
        //Confirm a user is logged in
        if(userSession == null){
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(401, "ERROR 401: Authorization Missing")));
            return;
        }
        
        //Gathering submitted username to be verified for authorization
        String usernameSubmission = req.getParameter("username");
        String searchStatus = req.getParameter("status");
        String searchIDField = req.getParameter("searchID");
        int searchID = 0;
        
        //avoid null field errors on mothod logic below
        if(usernameSubmission == null){
            usernameSubmission = "";
        }
        if(searchStatus == null){
            searchStatus = "";
        }
        if(searchIDField != null){
            searchID = Integer.parseInt(searchIDField);
        }
        
        //userSession set in AuthenticationServlet which sets "authUser" after someone has logged in
        UserDTO requester = (UserDTO) userSession.getAttribute("authUser");
        
        //skipping employee authorization if a single record is being serched for, will verify before displaying
        if(searchID <=0){
            //Username verification logic: must either have Role of finance manager or admin  
            //or authUser must match the username of user requested
            if(requester.getRole().equals("employee") && (!requester.getUsername().equals(usernameSubmission))){
                resp.setStatus(403);
                resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(403, "ERROR 403: Authorization Insufficent to Access")));
                return;
            }
        }
        
        try{
            //if logic for which doGet method call is needed
            
            //get identified single reimbursement(require a searchable ReimbursementID)
            if(searchID > 0){
                ReimbursementDTO specificReimbursement = reimbursementServ.getIdentifiedReimb(searchID);
                
                if(!requester.getRole().equals("employee") || requester.getUserID() == specificReimbursement.getAuthorID()){
                    resp.getWriter().write(jsonMapper.writeValueAsString(specificReimbursement));
                }else{
                    resp.getWriter().write(jsonMapper.writeValueAsString("You do not have access to the details of the searched Reimbursement"));
                }
                
            }else
            
            //get owned pending reimbursements (user_ID matches author_id with pending condition)
            if(requester.getRole().equals("employee") && requester.getUsername().equals(usernameSubmission) && searchStatus.equals("pending")){
                List<ReimbursementDTO> ownedReimb = reimbursementServ.getOwnedPendingReimbs(usernameSubmission);
                //Translates List to json and sends back to server
                resp.getWriter().write(jsonMapper.writeValueAsString(ownedReimb));
            }else
                
            //get owned reimbursements(user_ID matches author_id)
            if(requester.getRole().equals("employee") && requester.getUsername().equals(usernameSubmission)){
                List<ReimbursementDTO> ownedReimb = reimbursementServ.getOwnedReimbs(usernameSubmission);
                //Translates List to json and sends back to server
                resp.getWriter().write(jsonMapper.writeValueAsString(ownedReimb));
            }else
                
            //get pending reimbursements(require not employee)
            if(!requester.getRole().equals("employee") && searchStatus.equals("pending")){
                List<ReimbursementDTO> ownedReimb = reimbursementServ.getAllPendingReimbs(usernameSubmission);
                //Translates List to json and sends back to server
                resp.getWriter().write(jsonMapper.writeValueAsString(ownedReimb));
            }else 
                
            //get all reimbursements(require not employee)
            if(!requester.getRole().equals("employee")){
                List<ReimbursementDTO> ownedReimb = reimbursementServ.getAllReimbs(usernameSubmission);
                //Translates List to json and sends back to server
                resp.getWriter().write(jsonMapper.writeValueAsString(ownedReimb));
            }else{
                throw new InvalidRequestException("Request format does not match any doGet method");
            }
        }catch(InvalidRequestException e){
            //TODO add logging based on 9/9 lecture
            resp.setStatus(400);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(400, e.getMessage())));
        }catch(ResourceNotFoundException e){
            //TODO add logging based on 9/9 lecture
            resp.setStatus(404);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(404, e.getMessage())));
        }catch(DataSourceException e){
            //TODO add logging based on 9/9 lecture
            resp.setStatus(500);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(500, e.getMessage())));
        }
            
    }//end of doGet method
       
    //doPost:create new reimbursement(author_id to equal sessionID)
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
        
        //userSession set in AuthenticationServlet which sets "authUser" after someone has logged in
        UserDTO requester = (UserDTO) userSession.getAttribute("authUser");
        
        
        try{
            //Servlet layer new Reimbursement creation
            //reimbursementServ.register is passed reimbusement to insert, will return with 
            //ResourceCreationDTO to confirm new User was created
            NewReimbursementInsertion requestBody = jsonMapper.readValue(req.getInputStream(), NewReimbursementInsertion.class);
            //setting Auth_ID to match sesssion so that no one can submit impersonated reimbursement requests
            requestBody.setAuthorID(requester.getUserID());
            ReimbursementDTO responseBody = reimbursementServ.generate(requestBody);
            resp.getWriter().write(jsonMapper.writeValueAsString(responseBody));
            
        } catch (InvalidRequestException | JsonMappingException e) {
            //TODO add logging based on 9/9 lecture
            resp.setStatus(400); // BAD REQUEST
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(400, e.getMessage())));

        } catch (DataSourceException e) {
            //TODO add logging based on 9/9 lecture
            resp.setStatus(500); // INTERNAL SERVER ERROR; general error indicating a problem with the server
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(500, e.getMessage())));

        }
    }//end of doPost method
    
    //doPut:approve/deny single reimbursement(requires Finance Manager/Admin and the ReimbID)
    //      [TODO]update owned reimbursement (owned and still pending)  
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
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
        
        //userSession set in AuthenticationServlet which sets "authUser" after someone has logged in
        UserDTO requester = (UserDTO) userSession.getAttribute("authUser");
        
        //block employees from approving reimbursements
        if(requester.getRole().equals("employee")){
            String response = "Employees may not approve or deny reimbursements. Contact an Admin or Finance Manager";
            resp.getWriter().write(jsonMapper.writeValueAsString(response));
            return;
        }
        
        
        try{
            ReimbursementApproveOrDenyAlteration requestBody = jsonMapper.readValue(req.getInputStream(), ReimbursementApproveOrDenyAlteration.class);
            ReimbursementDTO responseBody = reimbursementServ.updateStatusApproveOrDeny(requestBody);
            resp.getWriter().write(jsonMapper.writeValueAsString(responseBody));
            
            
        }catch (InvalidRequestException | JsonMappingException e) {
            //TODO add logging based on 9/9 lecture
            resp.setStatus(400); // BAD REQUEST
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(400, e.getMessage())));

        } catch (DataSourceException e) {
            //TODO add logging based on 9/9 lecture
            resp.setStatus(500); // INTERNAL SERVER ERROR; general error indicating a problem with the server
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(500, e.getMessage())));

        }
    }//end of doPut method
    
}
