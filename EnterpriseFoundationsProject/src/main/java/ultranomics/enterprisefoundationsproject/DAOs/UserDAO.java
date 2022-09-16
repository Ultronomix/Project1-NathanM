package ultranomics.enterprisefoundationsproject.DAOs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ultranomics.enterprisefoundationsproject.datainsertion.NewUserInsertion;
import ultranomics.enterprisefoundationsproject.datamodels.User;
import ultranomics.enterprisefoundationsproject.datasource.ConnectionFactory;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.DataSourceException;

public class UserDAO {
    private final String baseSelect = "SELECT EU.user_id, EU.username, EU.email, EU.given_name, EU.surname, EU.is_active, EUR.role " +
                                      "FROM ERS_USERS EU " +
                                      "JOIN ERS_USER_ROLES EUR " +
                                      "ON EU.role_id = EUR.role_id ";
    
    public List<User> getAllUsers(){
         List<User> allUsers = new ArrayList<>();
         
         try(Connection conn =ConnectionFactory.getInstance().getConnection()){
             
            PreparedStatement pstmt = conn.prepareStatement(baseSelect+ "ORDER BY EU.user_id");
            ResultSet rs = pstmt.executeQuery();
             
            allUsers = mapResultSet(rs);
             
         }catch(SQLException e){
            //TODO Log Exception
            System.out.println("Error with ConnectionFactory");
            e.printStackTrace();
         }
         
         return allUsers;
    }
    
    //might not need but not going to remove until sure 
    //not currently used by Service Layer
    public Optional<User> findUserByID(String idImport){
        String sql = baseSelect + "WHERE EU.user_id = ?";
        
        try(Connection conn = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, idImport);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
            
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataSourceException (e);
        }
    }//end findUserByID method
    
    public Optional<User> findUserByUsername(String usernameImport){
        String sql = baseSelect + "WHERE EU.username = ?";
        
        try(Connection conn = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, usernameImport);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
            
        }catch(SQLException e){
            //TODO Log Exception
            e.printStackTrace();
            throw new DataSourceException (e);
        }
    }//end findUserByUsername method
    
    public Optional<User> findUserByEmail(String emailImport){
        String sql = baseSelect + "WHERE EU.email = ?";
        
        try(Connection conn = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, emailImport);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
            
        }catch(SQLException e){
            //TODO Log Exception
            e.printStackTrace();
            throw new DataSourceException (e);
        }
    }//end findUserByEmail method
    
    
    public Optional<User> createUser(NewUserInsertion userImport){
        String sql = "INSERT INTO ERS_USERS (username, email, password, given_name, surname, role_id) "+
                "VALUES (?, ?, ?, ?, ?, ?) ";
        
        try(Connection conn = ConnectionFactory.getInstance().getConnection()){
            
            if(userImport.getRole().equalsIgnoreCase("admin")){
                userImport.setRole("1");
            }
            if(userImport.getRole().equalsIgnoreCase("finance manager")){
                userImport.setRole("2");
            }
            if(userImport.getRole().equalsIgnoreCase("employee")){
                userImport.setRole("3");
            }
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userImport.getUsername());
            pstmt.setString(2, userImport.getEmail());
            pstmt.setString(3, userImport.getPassword());
            pstmt.setString(4, userImport.getGivenName());
            pstmt.setString(5, userImport.getSurname());
            pstmt.setString(6, userImport.getRole());
            pstmt.executeUpdate();
            
            //prepping query to confirm update
            sql = baseSelect +
                  "WHERE EU.username = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, userImport.getUsername());
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
            
        }catch(SQLException e){
            //TODO add error log per 9/9
            e.printStackTrace();
            throw new DataSourceException (e);
        }
    }//end of createUser method
    
    public boolean isUsernameTaken(String usernameImport){
        return findUserByUsername(usernameImport).isPresent();
    }//end isUsernameFree method
   
    public boolean isEmailTaken(String emailImport){
        return findUserByEmail(emailImport).isPresent();
    }//end isUsernameFree method
    
    public Optional<User> findUserByUsernameAndPassword(String usernameImport, String passwordImport){
        String sql = baseSelect + "WHERE EU.username = ? AND EU.password = ?";
        
        try(Connection conn = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, usernameImport);
            pstmt.setString(2, passwordImport);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
            
        }catch(SQLException e){
            //TODO Log Exception
            e.printStackTrace();
            throw new DataSourceException (e);
        }
    }//end findByUsernameAndPassword method
    
    public Optional<User> deactivateUser(String usernameImport){
        String sql = "UPDATE ers_users SET is_active = false WHERE username = ?";
        try(Connection conn = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, usernameImport);
            pstmt.executeUpdate();
            
            //prepping query to confirm update
            sql = baseSelect +
                  "WHERE EU.username = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, usernameImport);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
            
        }catch(SQLException e){
            //TODO Log Exception
            e.printStackTrace();
            throw new DataSourceException (e);
        }
        
        
    }//end deactivateUser method

    //formats query results to array of table items
    private List<User> mapResultSet(ResultSet rs) throws SQLException{
        List<User> users = new ArrayList<>();
        
        while(rs.next()){
            User user = new User();
            
            user.setUserID(rs.getInt("user_id"));
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
    }//end mapResultSet method
    
    //TODO update logging based on 9/9 lecture
    public void log(String level, String message){
        try{
            File logFile = new File("logs/app.log");
            logFile.createNewFile();
            BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFile));
            logWriter.write(String.format("[%s] at %s logged: [%s] %s\n", 
                    Thread.currentThread().getName(), LocalDate.now(), level.toUpperCase(), message));
        }catch(IOException e){
            //TODO Log Exception
            throw new RuntimeException(e);
        }
    }
}
