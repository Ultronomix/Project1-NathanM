package ultranomics.enterprisefoundationsproject.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ultranomics.enterprisefoundationsproject.DTOs.CredentialsDTO;
import ultranomics.enterprisefoundationsproject.DTOs.UserDTO;
import ultranomics.enterprisefoundationsproject.ErrorReport;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.AuthenticationException;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.DataSourceException;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.InvalidRequestException;
import ultranomics.enterprisefoundationsproject.services.AuthenticationService;

public class AuthenticationServlet extends HttpServlet{
    
    private final AuthenticationService authenticationServ;
    
    public AuthenticationServlet(AuthenticationService authenticationServ){
        this.authenticationServ = authenticationServ;
    }

    //doPost: establish credentials by comparing username and password to all users
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //TODO add logging based on 9/9 lecture
        
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");

        try{
            //req.getInputStream() = reading body of JSON
            CredentialsDTO creds = jsonMapper.readValue(req.getInputStream(), CredentialsDTO.class);
            UserDTO response = authenticationServ.authenticate(creds);
            resp.setStatus(200);//If we make it to this point then the Username/Password matched a user
           
            //TODO add logging here marking successful authentication per 9/9 lecture
            
            //Setting session credentials as a cookie
            HttpSession userSession = req.getSession();
            userSession.setAttribute("authUser", response);//first value header, second is attribute value
            
            resp.getWriter().write(jsonMapper.writeValueAsString(response));

            //TODO add logging here marking successful HTTP Header assignment per 9/9 lecture
            
        }catch(InvalidRequestException e){
            //TODO add logging based on 9/9 lecture
            resp.setStatus(400);//BAD REQUEST
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(400, e.getMessage())));
        }catch(AuthenticationException e){
            //TODO add logging based on 9/9 lecture
            resp.setStatus(401);//UNAUTHORIZED
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(401, e.getMessage())));
        }catch(DataSourceException e){
            //TODO add logging based on 9/9 lecture
            resp.setStatus(500);//SERVER ERROR
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorReport(500, e.getMessage())));
        }
    }//end doPost method
    
    
    //doDelete: log out
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate(); // this effectively "logs out" the requester by invalidating the session within the server
    }//end doDelete method
}
