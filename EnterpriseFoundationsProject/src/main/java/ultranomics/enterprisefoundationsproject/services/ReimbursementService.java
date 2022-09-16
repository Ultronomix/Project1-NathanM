package ultranomics.enterprisefoundationsproject.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import ultranomics.enterprisefoundationsproject.DAOs.ReimbursementDAO;
import ultranomics.enterprisefoundationsproject.DTOs.ReimbursementDTO;
import ultranomics.enterprisefoundationsproject.datainsertion.NewReimbursementInsertion;
import ultranomics.enterprisefoundationsproject.datamodels.Reimbursement;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.InvalidRequestException;

public class ReimbursementService {
    private final ReimbursementDAO reimbursementDAO;

    public ReimbursementService(ReimbursementDAO reimbursementDAO) {
        this.reimbursementDAO = reimbursementDAO;
    }

    public ReimbursementDTO generate(NewReimbursementInsertion reimbImport) throws InvalidRequestException{
        
        
        reimbImport.setTimeSub(Timestamp.valueOf(LocalDateTime.now()).toString());
        reimbImport.setStatusID("1");
        
        if(reimbImport == null){
            throw new InvalidRequestException("ERROR: can not register null payload");
        }
        if(reimbImport.getAmount() <= 0){
            throw new InvalidRequestException("ERROR: can not register reimbursement amount less than $0.01");
        }
        if(reimbImport.getDescription() == null || reimbImport.getDescription().length() <= 0 ){
            throw new InvalidRequestException("ERROR: reimbursement description must be provided");
        }
        if(reimbImport.getTypeID() == null || reimbImport.getTypeID().length() <= 0 ){
            throw new InvalidRequestException("ERROR: TypeID must be listed");
        }
        
        Reimbursement target = reimbursementDAO.create(reimbImport).orElse(null);
        ReimbursementDTO result = new ReimbursementDTO(target);
        return result;
    }//end generate method
    
    public List<ReimbursementDTO> getAllReimbs(String usernameImport){
        List<ReimbursementDTO> result = new ArrayList<>();
        List<Reimbursement> reimbList = reimbursementDAO.getAll(usernameImport);
        for(Reimbursement transfer : reimbList){
            result.add(new ReimbursementDTO(transfer));
        }
        
        return result;
    }
    
    public List<ReimbursementDTO> getAllPendingReimbs(String usernameImport){
        List<ReimbursementDTO> result = new ArrayList<>();
        List<Reimbursement> reimbList = reimbursementDAO.getAllPending(usernameImport);
        for(Reimbursement transfer : reimbList){
            result.add(new ReimbursementDTO(transfer));
        }
        
        return result;
    }
    
    public List<ReimbursementDTO> getOwnedReimbs(String usernameImport){
        List<ReimbursementDTO> result = new ArrayList<>();
        List<Reimbursement> reimbList = reimbursementDAO.getOwned(usernameImport);
        for(Reimbursement transfer : reimbList){
            result.add(new ReimbursementDTO(transfer));
        }
        
        return result;
    }
    
    public List<ReimbursementDTO> getOwnedPendingReimbs(String usernameImport){
        List<ReimbursementDTO> result = new ArrayList<>();
        List<Reimbursement> reimbList = reimbursementDAO.getOwnedPending(usernameImport);
        for(Reimbursement transfer : reimbList){
            result.add(new ReimbursementDTO(transfer));
        }
        
        return result;
    }
}//end ReimbursementService class
