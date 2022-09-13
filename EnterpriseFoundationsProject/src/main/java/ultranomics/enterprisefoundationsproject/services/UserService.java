package ultranomics.enterprisefoundationsproject.services;

import java.util.ArrayList;
import java.util.List;
import ultranomics.enterprisefoundationsproject.DAOs.UserDAO;
import ultranomics.enterprisefoundationsproject.DTOs.ResourceCreationDTO;
import ultranomics.enterprisefoundationsproject.DTOs.UserDTO;
import ultranomics.enterprisefoundationsproject.datainsertion.NewUserInsertion;
import ultranomics.enterprisefoundationsproject.datamodels.User;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.InvalidRequestException;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.ResourcePersistenceException;

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
    
    public UserDTO register(NewUserInsertion newUser){
        //logic for checking first/last name, email, username, and password
        //are present and meet formatting requirements
        if(newUser == null){
            throw new InvalidRequestException("ERROR: can not register null payload");
        }
        if(newUser.getGivenName() == null || newUser.getGivenName().equals("")){
            throw new InvalidRequestException("ERROR: no first name provided");
        }
        if(newUser.getSurname() == null || newUser.getSurname().equals("")){
            throw new InvalidRequestException("ERROR: no last name provided");
        }
        if(newUser.getEmail() == null || newUser.getEmail().equals("")){
            throw new InvalidRequestException("ERROR: no email provided");
        }
        if(newUser.getUsername() == null || newUser.getUsername().equals("")){
            throw new InvalidRequestException("ERROR: no username provided");
        }
        if(newUser.getPassword() == null || newUser.getPassword().equals("")){
            throw new InvalidRequestException("ERROR: no password provided");
        }
        
        //logic for checking Username and Email are unique before passing to database
        if(userDAO.isUsernameTaken(newUser.getUsername())){
            throw new ResourcePersistenceException("ERROR: Username is already taken");
        }
        if(userDAO.isEmailTaken(newUser.getEmail())){
            throw new ResourcePersistenceException("ERROR: Email address has already been used");
        }
        
        User target = userDAO.createUser(newUser).orElse(null);
        UserDTO result = new UserDTO(target);
        return result;
    }
    
    //TODO add updateUser method
    //TODO add deactivateUser method
}
