package ultranomics.enterprisefoundationsproject.datainsertion;

import ultranomics.enterprisefoundationsproject.datamodels.Request;
import ultranomics.enterprisefoundationsproject.datamodels.User;

public class NewUserInsertion implements Request<User>{

    private String givenName;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String role;
    
    
    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "NewUserInsertion{ " + "givenName=" + givenName + 
                ", \nsurname=" + surname + 
                ", \nemail=" + email + 
                ", \nusername=" + username + 
                ", \npassword=" + password + '}';
    }
    
    @Override 
    public User extractEntity(){
       User extractedEntity = new User();
       extractedEntity.setGivenName(this.givenName);
       extractedEntity.setSurname(this.surname);
       extractedEntity.setEmail(this.email);
       extractedEntity.setUsername(this.username);
       extractedEntity.setPassword(this.password);
       extractedEntity.setRole(this.role);
       
       return extractedEntity;
    }
}
