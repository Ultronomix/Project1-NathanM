package ultranomics.enterprisefoundationsproject.exceptionTemplates;

public class DataSourceException extends RuntimeException{
    public DataSourceException(Throwable cause){
        super("ERROR: Invalid Data Source", cause);
    }
    
    public DataSourceException(String message, Throwable cause){
        super(message, cause);
    }
}
