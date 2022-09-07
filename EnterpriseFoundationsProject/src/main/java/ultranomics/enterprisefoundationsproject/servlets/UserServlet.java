package ultranomics.enterprisefoundationsproject.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ultranomics.enterprisefoundationsproject.DTOs.UserDTO;
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
        List<UserDTO> allUsers = userServ.getAllUsers();
        
        resp.getWriter().write(jsonMapper.writeValueAsString(allUsers));
    }
    
    //TODO add doPost method
}
