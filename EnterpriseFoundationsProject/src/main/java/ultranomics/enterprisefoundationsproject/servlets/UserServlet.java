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

import ultranomics.enterprisefoundationsproject.DTOs.UserDTO;
import ultranomics.enterprisefoundationsproject.ErrorReport;
import ultranomics.enterprisefoundationsproject.datainsertion.NewUserInsertion;
import ultranomics.enterprisefoundationsproject.services.UserService;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.ResourceNotFoundException;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.InvalidRequestException;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.DataSourceException;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.ResourcePersistenceException;


public class UserServlet extends HttpServlet{
    
    private final UserService userServ;
    
    public UserServlet(UserService importUserServ){
        this.userServ = importUserServ;
    }
    
    //doGet:get all users, get single user by username
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
        
        //Gathering submitted username to be verified for authorization
        String usernameSubmission = req.getParameter("username");
        
        //userSession set in AuthenticationServlet which sets "authUser" after someone has logged in
        UserDTO requester = (UserDTO) userSession.getAttribute("authUser");
        
        //Username verification logic: must either have Role admin  
        //or authUser must match the username of user requested
        if(!requester.getRole().equals("admin") && !requester.getUsername().equals(usernameSubmission)){
            resp.setStatus(403);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(403, "ERROR 403: Authorization Insufficent to Access")));
        }
        
        try{
            //logic to determine if one user is requested or all users
            if(usernameSubmission == null){
                //list of all UserDTOs pulled from Service Layer 
                List<UserDTO> allUsers = userServ.getAllUsers();
                //Custom Header added
                resp.addHeader("Header name", "Header body");
                //Translates List to json and sends back to server
                resp.getWriter().write(jsonMapper.writeValueAsString(allUsers));
            }else{
                //single UserDTO pulled from Service Layer matching submitted username
                UserDTO foundUser = userServ.getUserByUsername(usernameSubmission);
                //Translates single UserDTO to json and sends to server
                resp.getWriter().write(jsonMapper.writeValueAsString(foundUser));
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
        
        
    }//end doGet method
    
    //doPost: create new user
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");

        try {
            //Servlet layer new User creation
            //userServ.register is passed user to insert, will return with 
            //ResourceCreationDTO to confirm new User was created
            NewUserInsertion requestBody = jsonMapper.readValue(req.getInputStream(), NewUserInsertion.class);
            UserDTO responseBody = userServ.register(requestBody);
            resp.getWriter().write(jsonMapper.writeValueAsString(responseBody));

        } catch (InvalidRequestException | JsonMappingException e) {
            //TODO add logging based on 9/9 lecture
            resp.setStatus(400); // BAD REQUEST
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(400, e.getMessage())));

        //TODO create custom ResourcePersistenceException
        } catch (ResourcePersistenceException e) {
            //TODO add logging based on 9/9 lecture
            resp.setStatus(409); // CONFLICT; indicates that the provided resource could not be saved without conflicting with other data
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(409, e.getMessage())));

        } catch (DataSourceException e) {
            //TODO add logging based on 9/9 lecture
            resp.setStatus(500); // INTERNAL SERVER ERROR; general error indicating a problem with the server
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(500, e.getMessage())));

        }
    }//end doPost method
    
    
    //TODO add doPut method (update an existing user)
    //TODO add doDelete method (update an existing user to inactive)
}
