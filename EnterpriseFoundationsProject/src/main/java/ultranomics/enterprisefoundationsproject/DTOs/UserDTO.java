package ultranomics.enterprisefoundationsproject.DTOs;

import java.io.Serializable;
import java.util.Objects;
import ultranomics.enterprisefoundationsproject.datamodels.User;

public class UserDTO implements Serializable{
    
    private int userID;
    private String username;
    private String email;
    private String givenName;
    private String surname;
    private String role;
    private boolean active;

    
    
    //make Jackson happy with default constructor
    public UserDTO(){
        super();
    }
    
    public UserDTO(User subject){
        
        this.userID = subject.getUserID();
        this.username = subject.getUsername();
        this.email = subject.getEmail();
        this.givenName = subject.getGivenName();
        this.surname = subject.getSurname();
        this.role = subject.getRole();
        this.active = subject.getIsActive();
    }
    
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
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
    
    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
    @Override
    public String toString() {
        return "UserDTO{"+
                "\nuserID= "+userID+
                ", \nusername= " + username + 
                ", \nemail= " + email +
                ", \ngivenName= " + givenName +
                ", \nsurname= " + surname +
                ", \nrole= " + role +
                ", \nactive= " + active + 
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.userID);
        hash = 13 * hash + Objects.hashCode(this.username);
        hash = 13 * hash + Objects.hashCode(this.email);
        hash = 13 * hash + Objects.hashCode(this.givenName);
        hash = 13 * hash + Objects.hashCode(this.surname);
        hash = 13 * hash + Objects.hashCode(this.role);
        hash = 13 * hash + Objects.hashCode(this.active);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserDTO other = (UserDTO) obj;
        if (!Objects.equals(this.userID, other.userID)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.givenName, other.givenName)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        if (!Objects.equals(this.active, other.active)) {
            return false;
        }
        return true;
    }

}