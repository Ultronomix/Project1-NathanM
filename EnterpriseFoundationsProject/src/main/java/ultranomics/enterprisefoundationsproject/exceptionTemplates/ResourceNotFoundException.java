package ultranomics.enterprisefoundationsproject.exceptionTemplates;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){
        super("ERROR: Resource Not Found");
    }
}
