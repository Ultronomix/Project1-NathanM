package ultranomics.enterprisefoundationsproject.services;

import java.util.ArrayList;
import java.util.List;
import ultranomics.enterprisefoundationsproject.DAOs.UserDAO;
import ultranomics.enterprisefoundationsproject.DTOs.UserDTO;
import ultranomics.enterprisefoundationsproject.datamodels.User;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    public List<UserDTO> getAllUsers(){
        List<UserDTO> result = new ArrayList<>();
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            result.add(new UserDTO(user));
        }
        return result;
    }
}
