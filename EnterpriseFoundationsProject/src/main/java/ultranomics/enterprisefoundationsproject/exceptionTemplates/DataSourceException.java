package ultranomics.enterprisefoundationsproject.exceptionTemplates;

public class DataSourceException extends RuntimeException{
    public DataSourceException(){
        super("ERROR: Invalid Data Source");
    }
}
