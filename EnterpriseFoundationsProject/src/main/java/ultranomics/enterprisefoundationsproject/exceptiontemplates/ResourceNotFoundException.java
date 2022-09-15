package ultranomics.enterprisefoundationsproject.exceptiontemplates;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){
        super("ERROR: Resource Not Found");
    }
    
    public ResourceNotFoundException(String message){
        super(message);
    }
}