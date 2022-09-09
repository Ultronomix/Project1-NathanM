package ultranomics.enterprisefoundationsproject.exceptionTemplates;

public class InvalidRequestException extends RuntimeException{
    public InvalidRequestException(){
        super("ERROR: Request is Invalid");
    }
    
    public InvalidRequestException(String message){
        super(message);
    }
}
