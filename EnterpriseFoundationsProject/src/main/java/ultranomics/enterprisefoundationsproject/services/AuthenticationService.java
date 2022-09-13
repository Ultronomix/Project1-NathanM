package ultranomics.enterprisefoundationsproject.services;

import ultranomics.enterprisefoundationsproject.DAOs.UserDAO;
import ultranomics.enterprisefoundationsproject.DTOs.CredentialsDTO;
import ultranomics.enterprisefoundationsproject.DTOs.UserDTO;
import ultranomics.enterprisefoundationsproject.exceptionTemplates.*;

public class AuthenticationService {
    private final UserDAO userDAO;

    public AuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    //TODO impliment
    
    public UserDTO authenticate(CredentialsDTO credImport){
        if(credImport == null){
            //will be logged at Servlet level
            throw new InvalidRequestException("Credentials payload found to be null");
        }
        if(credImport.getUsername() == null || credImport.getUsername().length() == 0){
            //will be logged at Servlet level
            throw new InvalidRequestException("Username found to be empty/null");
        }
        if(credImport.getPassword() == null || credImport.getPassword().length() == 0){
            //will be logged at Servlet level
            throw new InvalidRequestException("Password found to be empty/null");
        }
        
        return userDAO.findUserByUsernameAndPassword(credImport.getUsername(), credImport.getPassword())
                .map(UserDTO::new)
                .orElseThrow(AuthenticationException::new);
    }
}
