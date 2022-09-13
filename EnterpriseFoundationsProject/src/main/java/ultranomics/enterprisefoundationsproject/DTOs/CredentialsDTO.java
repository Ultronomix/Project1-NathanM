package ultranomics.enterprisefoundationsproject.DTOs;

public class CredentialsDTO {

    
    private String username;
    private String password;
    
    //make Jackson happy with default constructor
    public CredentialsDTO(){
        super();
    }
    
    public CredentialsDTO(String importUsername, String importPassword){
        this.username = importUsername;
        this.password = importPassword;
    }//end real constructor
    
    //getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}//end CredentialsDTO class
