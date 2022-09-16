package ultranomics.enterprisefoundationsproject.services;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import ultranomics.enterprisefoundationsproject.DAOs.UserDAO;
import ultranomics.enterprisefoundationsproject.DTOs.CredentialsDTO;
import ultranomics.enterprisefoundationsproject.DTOs.UserDTO;
import ultranomics.enterprisefoundationsproject.datamodels.User;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.InvalidRequestException;

public class AuthenticationServiceTest {
    
    AuthenticationService sut; //what we are testing SUT(System Under Test)
    UserDAO mockUserDAO; //mock injections, never the SUT
    
    @BeforeEach
    public void setup(){
        mockUserDAO = Mockito.mock(UserDAO.class);
        sut = new AuthenticationService(mockUserDAO); 
    }
    
    @AfterEach
    public void cleanUp(){
        Mockito.reset(mockUserDAO);//reset method counters for verify counting
    }
    
    @Test
    public void test_authenticate_returnSuccess_validCredentials(){
        
        //arrange
        CredentialsDTO creds = new CredentialsDTO("valid", "credentials");
        User dummy = new User(1, "dummyUsername", "dummy@mock.com", "dummyPass", "Dummy", "Name", true, "3");
        
        UserDTO expected = new UserDTO(dummy);
        
        when(mockUserDAO.findUserByUsernameAndPassword(anyString(), anyString())).thenReturn(Optional.of(dummy));
        
        //act
        UserDTO actualResult = sut.authenticate(creds);
        //assert
        assertNotNull(actualResult);
                    //(expected, actual)
        assertEquals(expected, actualResult);
        
    }
    
    @Test
    public void test_authenticate_throwInvalidRequestException_noCredentials(){
        //arrange
        CredentialsDTO creds = null;
        
        //act (no secondary pass as we don't clear the IF logic of the SUT)
        
        //assert
        assertThrows(InvalidRequestException.class, () -> {sut.authenticate(creds);});
        verify(mockUserDAO, times (0)).findUserByUsernameAndPassword(anyString(), anyString());
    }
    
    @Test
    public void test_authenticate_throwInvalidRequestException_invalidUsername(){
        //arrange
        CredentialsDTO creds = new CredentialsDTO("", "credentials");
        
        
        //act (no secondary pass as we don't clear the IF logic of the SUT)
        
        //assert
        assertThrows(InvalidRequestException.class, () -> {sut.authenticate(creds);});
        verify(mockUserDAO, times (0)).findUserByUsernameAndPassword(anyString(), anyString());
    }
    
    @Test
    public void test_authenticate_throwInvalidRequestException_invalidPassword(){
        //arrange
        CredentialsDTO creds = new CredentialsDTO("valid", "");
        
        //act (no secondary pass as we don't clear the IF logic of the SUT)
        
        //assert
        assertThrows(InvalidRequestException.class, () -> {sut.authenticate(creds);});
        verify(mockUserDAO, times (0)).findUserByUsernameAndPassword(anyString(), anyString());
    }
}
