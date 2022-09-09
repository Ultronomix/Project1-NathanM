package ultranomics.enterprisefoundationsproject.datamodels;

public interface Request<T> {
    
    T extractEntity();
}
