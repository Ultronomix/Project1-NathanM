package ultranomics.enterprisefoundationsproject.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import ultranomics.enterprisefoundationsproject.DAOs.ReimbursementDAO;

public class ReimbursementServiceTest {
    ReimbursementService sut; //what we are testing SUT(System Under Test)
    ReimbursementDAO mockReDAO; //mock injections, never the SUT
    
    @BeforeEach
    public void setup(){
        mockReDAO = Mockito.mock(ReimbursementDAO.class);
        sut = new ReimbursementService(mockReDAO); 
    }
    
    @AfterEach
    public void cleanUp(){
        Mockito.reset(mockReDAO);//reset method counters for verify counting
    }
    
    //TODO test generate
    
    //TODO test getAllReimbs
    
    //TODO test getAllPendingReimbs
    
    //TODO test getOwnedReimbs
    
    //TODO test getOwnedPendingReimbs
    
    
}
