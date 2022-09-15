package ultranomics.enterprisefoundationsproject.DTOs;

import java.io.Serializable;
import ultranomics.enterprisefoundationsproject.datamodels.User;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.ResourceNotFoundException;

public class UserDTO implements Serializable{
    
    private String userID;
    private String username;
    private String email;
    private String givenName;
    private String surname;
    private String role;
    private String active;
    
    public UserDTO(User subject){
        //TODO move this to UserService.getUserByUsername 
        if (subject == null){
            throw new ResourceNotFoundException("ERROR: Username not found");
        }
        
        this.userID = subject.getUserID();
        this.username = subject.getUsername();
        this.email = subject.getEmail();
        this.givenName = subject.getGivenName();
        this.surname = subject.getSurname();
        this.role = subject.getRole();
    }
    
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
    
    
    @Override
    public String toString() {
        return "UserDTO{"+
                "\nuserID='"+userID+'\''+
                ", \nusername=" + username +'\''+ 
                ", \nemail=" + email +'\''+ 
                ", \ngivenName=" + givenName +'\''+ 
                ", \nsurname=" + surname +'\''+ 
                ", \nrole=" + role +'\''+ 
                ", \nactive=" + active +'\''+ 
                '}';
    }
}
