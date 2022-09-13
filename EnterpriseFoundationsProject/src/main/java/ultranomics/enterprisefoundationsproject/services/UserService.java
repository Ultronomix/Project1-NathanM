package ultranomics.enterprisefoundationsproject.services;

import java.util.ArrayList;
import java.util.List;
import ultranomics.enterprisefoundationsproject.DAOs.UserDAO;
import ultranomics.enterprisefoundationsproject.DTOs.ResourceCreationDTO;
import ultranomics.enterprisefoundationsproject.DTOs.UserDTO;
import ultranomics.enterprisefoundationsproject.datainsertion.NewUserInsertion;
import ultranomics.enterprisefoundationsproject.datamodels.User;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.InvalidRequestException;

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

    public UserDTO getUserByUsername(String usernameImport) {
        //filter invalid usernames
        if(usernameImport == null || usernameImport.length() == 0){
            throw new InvalidRequestException(
                    "ERROR: submitted Username is null"+
                    " or empty and can not be looked up");
        }
        
        User target = userDAO.findUserByUsername(usernameImport).orElse(null); 
        UserDTO result = new UserDTO(target);
        return result;
    }
    
    public ResourceCreationDTO register(NewUserInsertion newUser){
        //TODO complete method
    }
    
    //TODO add updateUser method
    //TODO add deactivateUser method
}
