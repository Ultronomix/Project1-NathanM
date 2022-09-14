package ultranomics.enterprisefoundationsproject.services;

import ultranomics.enterprisefoundationsproject.DAOs.ReimbursementDAO;
import ultranomics.enterprisefoundationsproject.DTOs.ReimbursementDTO;
import ultranomics.enterprisefoundationsproject.datainsertion.NewReimbursementInsertion;

public class ReimbursementService {
    private final ReimbursementDAO reimbursementDAO;

    public ReimbursementService(ReimbursementDAO reimbursementDAO) {
        this.reimbursementDAO = reimbursementDAO;
    }

    public ReimbursementDTO generate(NewReimbursementInsertion reimbImport){
        //TODO add if logic for field validation before passing to DAO
        //TODO pass to DAO
        //TODO return
    }
    //TODO impliment
}
