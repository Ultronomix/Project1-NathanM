package ultranomics.enterprisefoundationsproject.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ultranomics.enterprisefoundationsproject.DTOs.UserDTO;
import ultranomics.enterprisefoundationsproject.ErrorReport;
import ultranomics.enterprisefoundationsproject.exceptionTemplates.*;
import ultranomics.enterprisefoundationsproject.services.UserService;

public class UserServlet extends HttpServlet{
    
    private final UserService userServ;
    
    public UserServlet(UserService importUserServ){
        this.userServ = importUserServ;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        
        //Gathering submitted ID to be verified for authorization
        String idSubmission = req.getParameter("id");
        
        //userSession set in AuthenticationServlet which sets "authUser" after someone has logged in
        UserDTO requester = (UserDTO) userSession.getAttribute("authUser");
        
        //ID verification logic: must either have Role admin  
        //or authUser must match the ID of user requested
        if(!requester.getRole().equals("admin") && !requester.getUserID().equals(idSubmission)){
            resp.setStatus(403);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(403, "ERROR 403: Authorization Insufficent to Access")));
        }
        
        try{
            //logic to determine if one user is requested or all users
            if(idSubmission == null){
                //list of UserDTOs pulled from Service Layer 
                List<UserDTO> allUsers = userServ.getAllUsers();
                //Custom Header added
                resp.addHeader("Header name", "Header body");
                //Translates List to json and sends back to server
                resp.getWriter().write(jsonMapper.writeValueAsString(allUsers));
            }else{
                UserDTO foundUser = userServ.getUserByID(idSubmission);
                //Translates single UserDTO to json and sends to server
                resp.getWriter().write(jsonMapper.writeValueAsString(foundUser));
            }
        }catch(InvalidRequestException e){
            //TODO create Exceptions
            resp.setStatus(400);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(400, e.getMessage())));
        }catch(ResourceNotFoundException e){
            resp.setStatus(404);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(404, e.getMessage())));
        }catch(DataSourceException e){
            resp.setStatus(500);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(500, e.getMessage())));
        }
        
        
    }
    
    //TODO add doPost method
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        
    }
}
