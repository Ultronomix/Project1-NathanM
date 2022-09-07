package ultranomics.enterprisefoundationsproject.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ultranomics.enterprisefoundationsproject.DAOs.UserDAO;
import ultranomics.enterprisefoundationsproject.datamodels.User;

public class UserServlet extends HttpServlet{
    
    private final UserDAO userDAO;
    
    public UserServlet(UserDAO importUserDAO){
        this.userDAO = importUserDAO;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");
        List<User> allUsers = userDAO.getAllUsers();
        
        resp.getWriter().write(jsonMapper.writeValueAsString(allUsers));
    }
    
    //TODO add doPost method
}
