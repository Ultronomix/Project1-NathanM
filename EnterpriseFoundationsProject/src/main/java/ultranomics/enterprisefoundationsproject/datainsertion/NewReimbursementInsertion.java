package ultranomics.enterprisefoundationsproject.datainsertion;

import ultranomics.enterprisefoundationsproject.datamodels.Reimbursement;
import ultranomics.enterprisefoundationsproject.datamodels.Request;

public class NewReimbursementInsertion implements Request<Reimbursement>{
        
    private double amount;
    private String timeSub;
    private String description;
    private int authorID;
    private String statusID;
    private String typeID;

    //getters and setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTimeSub() {
        return timeSub;
    }
    
    public void setTimeSub(String timeSub) {
        this.timeSub = timeSub;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    
    
    @Override
    public String toString() {
        return "NewReimbursementInsertion{" +
                ", \namount=" + amount + 
                ", \ntimeSub=" + timeSub + 
                ", \ndescription=" + description + 
                ", \nauthorID=" + authorID + 
                ", \nstatusID=" + statusID + 
                ", \ntypeID=" + typeID + '}';
    }
    
    @Override 
    public Reimbursement extractEntity(){
       Reimbursement extractedEntity = new Reimbursement();
       extractedEntity.setAmount(this.amount);
       extractedEntity.setTimeSub(this.timeSub);
       extractedEntity.setDescription(this.description);
       extractedEntity.setAuthorID(this.authorID);
       extractedEntity.setStatusID(this.statusID);
       extractedEntity.setTypeID(this.typeID);
       
       return extractedEntity;
    }
}
