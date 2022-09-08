package ultranomics.enterprisefoundationsproject;

import java.time.LocalDateTime;

public class ErrorReport {
    
    private int errorCode;
    private String errorMessage;
    private String errorTime;
    
    public ErrorReport(int importCode, String importMessage){
        this.errorCode = importCode;
        this.errorMessage = importMessage;
        this.errorTime = LocalDateTime.now().toString();
    }
    
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(String errorTime) {
        this.errorTime = errorTime;
    }
    
    @Override
    public String toString() {
        return "ErrorReport{ errorCode: " + errorCode + 
                "\nerrorMessage: " + errorMessage + 
                "\nerrorTime: " + errorTime + '}';
    }
}
