package ultranomics.enterprisefoundationsproject.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import ultranomics.enterprisefoundationsproject.DAOs.UserDAO;

public class UserServiceTest {
    UserService sut; //what we are testing SUT(System Under Test)
    UserDAO mockUserDAO; //mock injections, never the SUT
    
    @BeforeEach
    public void setup(){
        mockUserDAO = Mockito.mock(UserDAO.class);
        sut = new UserService(mockUserDAO); 
    }
    
    @AfterEach
    public void cleanUp(){
        Mockito.reset(mockUserDAO);//reset method counters for verify counting
    }
    
    //TODO test getAllUsers
    
    //TODO test getUserByUsername
    
    //TODO test register
    
    //TODO test updateUser(not yet written)
    
    //TODO test deactivate
}
