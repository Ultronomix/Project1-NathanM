package ultranomics.enterprisefoundationsproject.exceptiontemplates;

public class ResourcePersistenceException extends RuntimeException {

    //logging done at Servlet level
    
    public ResourcePersistenceException() {
        super("The provided resource could not be persisted.");
    }

    public ResourcePersistenceException(String message) {
        super(message);
    }

}
