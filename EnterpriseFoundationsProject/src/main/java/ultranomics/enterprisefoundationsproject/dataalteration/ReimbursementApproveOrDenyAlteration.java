package ultranomics.enterprisefoundationsproject.dataalteration;

public class ReimbursementApproveOrDenyAlteration {
    private int reimbursementID;
    private boolean statusUpdate;//true if approved, false if denied

    //make Jackson happy with default constructor
    public ReimbursementApproveOrDenyAlteration(){
        super();
    }
    
    public ReimbursementApproveOrDenyAlteration(int reimbIDImport, boolean statusUpdateImport){
        this.reimbursementID = reimbIDImport;
        this.statusUpdate = statusUpdateImport;
    }
    
    //getters and setters
    public int getReimbursementID() {
        return reimbursementID;
    }

    public void setReimbursementID(int reimbursementID) {
        this.reimbursementID = reimbursementID;
    }

    public boolean getStatusUpdate() {
        return statusUpdate;
    }

    public void setStatusUpdate(boolean statusUpdate) {
        this.statusUpdate = statusUpdate;
    }
    
    
}
