package ultranomics.enterprisefoundationsproject.DAOs;

import ultranomics.enterprisefoundationsproject.datainsertion.NewReimbursementInsertion;
import ultranomics.enterprisefoundationsproject.datamodels.Reimbursement;

public class ReimbursementDAO {
    String baseSelect = "SELECT ER.reimb_id, ER.amount, ER " +
                                      "FROM ERS_REIMBURSEMENTS ER " +
                                      "JOIN ERS_USERS EU " +
                                      "ON EU.role_id = EUR.role_id ";

    //TODO impliment
    public Reimbursement create(NewReimbursementInsertion reimbImport){
        
        //TODO return
    }
}
