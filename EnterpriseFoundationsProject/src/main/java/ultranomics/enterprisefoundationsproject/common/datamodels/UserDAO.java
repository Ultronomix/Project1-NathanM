package ultranomics.enterprisefoundationsproject.common.datamodels;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ultranomics.enterprisefoundationsproject.common.datasource.ConnectionFactory;

public class UserDAO {
    private final String baseSelect = "SELECT EU.user_id, EU.username, EU.email, EU.given_name, EU.surname, EU.is_active, EUR.role " +
                                      "FROM ERS_USERS EU " +
                                      "JOIN ERS_USER_ROLES EUR " +
                                      "ON EU.role_id = EUR.role_id ";
    
    public List<User> getAllUsers(){
         List<User> allUsers = new ArrayList<>();
         
         try(Connection conn =ConnectionFactory.getInstance().getConnection()){
             
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(baseSelect);
             
             allUsers = mapResultSet(rs);
             
         }catch(SQLException e){
             //TODO Log Exception
             System.out.println("Error with ConnectionFactory");
             e.printStackTrace();
         }
         
         
         return allUsers;
    }
    
    //TODO add individual Username/Password Lookup method
    //TODO add Save User method

    private List<User> mapResultSet(ResultSet rs) throws SQLException{
        List<User> users = new ArrayList<>();
        
        while(rs.next()){
            User user = new User();
            
            user.setUserID(rs.getString("user_id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword("********");
            user.setGivenName(rs.getString("given_name"));
            user.setSurname(rs.getString("surname"));
            user.setIsActive(rs.getBoolean("is_active"));
            user.setRole(rs.getString("role"));
            users.add(user);
        }
        
        return users;
    }
    
    //TODO add Logging method
}
