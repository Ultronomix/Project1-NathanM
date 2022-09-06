package ultranomics.enterprisefoundationsproject.common.datamodels;

public class Reimbursement {
    private String reimbID;
    private double amount;
    private String timeSub;
    private String timeResolved;
    private String description;
    //ask about blob(bytea) datatype PostgreSQL vs Java
    private String paymentID;
    private String authorID;
    private String resolverID;
    private String statusID;
    private String typeID;

    public String getReimbID() {
        return reimbID;
    }

    public void setReimbID(String reimbID) {
        this.reimbID = reimbID;
    }

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

    public String getTimeResolved() {
        return timeResolved;
    }

    public void setTimeResolved(String timeResolved) {
        this.timeResolved = timeResolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getResolverID() {
        return resolverID;
    }

    public void setResolverID(String resolverID) {
        this.resolverID = resolverID;
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
}
