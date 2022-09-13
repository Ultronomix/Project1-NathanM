package ultranomics.enterprisefoundationsproject.exceptiontemplates;

public class DataSourceException extends RuntimeException{
    public DataSourceException(Throwable cause){
        super("ERROR: Invalid Data Source", cause);
    }
    
    public DataSourceException(String message, Throwable cause){
        super(message, cause);
    }
}