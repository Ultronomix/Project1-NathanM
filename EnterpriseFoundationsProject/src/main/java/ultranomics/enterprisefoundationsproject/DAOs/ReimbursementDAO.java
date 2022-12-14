package ultranomics.enterprisefoundationsproject.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ultranomics.enterprisefoundationsproject.datainsertion.NewReimbursementInsertion;
import ultranomics.enterprisefoundationsproject.datamodels.Reimbursement;
import ultranomics.enterprisefoundationsproject.datasource.ConnectionFactory;
import ultranomics.enterprisefoundationsproject.exceptiontemplates.DataSourceException;

public class ReimbursementDAO {
    String baseSelect = 
            "SELECT REIMB_ID, AMOUNT, SUBMITTED, RESOLVED, DESCRIPTION, AUTHOR_ID, RESOLVER_ID, STATUS_ID, TYPE_ID  " +
            "FROM ERS_REIMBURSEMENTS ER " ;

    
    //formats query results to array of table items
    private List<Reimbursement> mapResultSet(ResultSet rs) throws SQLException{
        List<Reimbursement> reimbursements = new ArrayList<>();
        
        while(rs.next()){
            Reimbursement reimbursement = new Reimbursement();
            
            reimbursement.setReimbID(rs.getString("REIMB_ID"));
            reimbursement.setAmount(rs.getDouble("AMOUNT"));
            reimbursement.setTimeSub(rs.getString("SUBMITTED"));
            reimbursement.setDescription(rs.getString("DESCRIPTION"));
            reimbursement.setAuthorID(rs.getInt("AUTHOR_ID"));
            reimbursement.setStatusID(rs.getString("STATUS_ID"));
            reimbursement.setTypeID(rs.getString("TYPE_ID"));
            reimbursement.setTimeResolved(rs.getString("RESOLVED"));
            reimbursement.setResolverID(rs.getInt("RESOLVER_ID"));
            
            reimbursements.add(reimbursement);
        }
        
        return reimbursements;
    }//end mapResultSet method
    

    //inserts new reimbursement to table
    public Optional<Reimbursement> create(NewReimbursementInsertion reimbImport) throws DataSourceException{
        String sql = 
                "INSERT INTO ERS_REIMBURSEMENTS (AMOUNT, SUBMITTED, DESCRIPTION, AUTHOR_ID, STATUS_ID, TYPE_ID) "+
                "VALUES "+
                "(?, TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'), ?, ?, ?, ?)";
        
        try(Connection conn = ConnectionFactory.getInstance().getConnection()){
                        
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, reimbImport.getAmount());
            pstmt.setString(2, reimbImport.getTimeSub());
            pstmt.setString(3, reimbImport.getDescription());
            pstmt.setInt(4, reimbImport.getAuthorID());
            pstmt.setString(5, reimbImport.getStatusID());
            pstmt.setString(6, reimbImport.getTypeID());
            pstmt.executeUpdate();
            
            //prepping query to confirm update
            sql = baseSelect +
                  "WHERE AUTHOR_ID = ? AND SUBMITTED = TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS')";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reimbImport.getAuthorID());
            pstmt.setString(2, reimbImport.getTimeSub());
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
            
        }catch(SQLException e){
            //TODO add error log per 9/9
            e.printStackTrace();
            throw new DataSourceException (e);
        }
    }//end of create method
    
    public List<Reimbursement> getAll(String usernameImport){
        
        List<Reimbursement> ownedReimb = new ArrayList<>();
        
        try(Connection conn =ConnectionFactory.getInstance().getConnection()){
            String sql = "ORDER BY reimb_id ";
            
            PreparedStatement pstmt = conn.prepareStatement(baseSelect + sql);
            ResultSet rs = pstmt.executeQuery();
             
            ownedReimb = mapResultSet(rs);
             
         }catch(SQLException e){
            //TODO Log Exception
            System.out.println("Error with ConnectionFactory");
            e.printStackTrace();
         }
         
         return ownedReimb;
    }//end of getAll method
    
    public List<Reimbursement> getAllPending(String usernameImport){
        String sql = "WHERE STATUS_ID = '1' "+
                     "ORDER BY REIMB_ID ";
        
        List<Reimbursement> ownedReimb = new ArrayList<>();
        
        try(Connection conn =ConnectionFactory.getInstance().getConnection()){
             
            PreparedStatement pstmt = conn.prepareStatement(baseSelect + sql);
            ResultSet rs = pstmt.executeQuery();
             
            ownedReimb = mapResultSet(rs);
             
         }catch(SQLException e){
            //TODO Log Exception
            System.out.println("Error with ConnectionFactory");
            e.printStackTrace();
         }
         
         return ownedReimb;
    }//end of getAllPending method
    
    
    
    
    public List<Reimbursement> getOwned(String usernameImport){
        String sql = "WHERE AUTHOR_ID = ? "+
                     "ORDER BY REIMB_ID ";
        
        List<Reimbursement> ownedReimb = new ArrayList<>();
        
        try(Connection conn =ConnectionFactory.getInstance().getConnection()){
            
            
            PreparedStatement pstmt = conn.prepareStatement(baseSelect + sql);
            pstmt.setString(1, usernameImport); 
            ResultSet rs = pstmt.executeQuery();
             
            ownedReimb = mapResultSet(rs);
             
         }catch(SQLException e){
            //TODO Log Exception
            System.out.println("Error with ConnectionFactory");
            e.printStackTrace();
         }
         
         return ownedReimb;
    }//end of getOwned method
    
    public List<Reimbursement> getOwnedPending(String usernameImport){
        String sql = "WHERE AUTHOR_ID = ? AND WHERE STATUS_ID = '1' "+
                     "ORDER BY REIMB_ID ";
        
        List<Reimbursement> ownedReimb = new ArrayList<>();
        
        try(Connection conn =ConnectionFactory.getInstance().getConnection()){
             
            PreparedStatement pstmt = conn.prepareStatement(baseSelect + sql);
            pstmt.setString(1, usernameImport); 
            ResultSet rs = pstmt.executeQuery();
             
            ownedReimb = mapResultSet(rs);
             
         }catch(SQLException e){
            //TODO Log Exception
            System.out.println("Error with ConnectionFactory");
            e.printStackTrace();
         }
         
         return ownedReimb;
    }//end of getOwnedPending method
    
    public Optional<Reimbursement> getSingleByReimbID(int reimbIDImport){
        String sql = "WHERE reimb_id = ? ORDER BY reimb_id ";
        
        
        try(Connection conn = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(baseSelect + sql);
            pstmt.setInt(1, reimbIDImport);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
            
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataSourceException (e);
        }
         
    }//end of getSingleByReimbID
    
    public Optional<Reimbursement> updateReimbursementStatus(int reimbIDImport, boolean newStatusImport, int resolverIDImport){
        String sql = "UPDATE ers_reimbursements SET status_id = ?, RESOLVER_ID  = ?, RESOLVED = TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS') WHERE reimb_id = ?"; 
        String sql02 = "WHERE reimb_id = ? ";
        
        try(Connection conn = ConnectionFactory.getInstance().getConnection()){
            //reformatting update from boolean to int for database
            int statusUpdate;
            if(newStatusImport){
                statusUpdate = 3;//if true then approved(status code 3)
            }else{
                statusUpdate = 2;//if false then denied(status code 2)
            }
            
            //updating the database
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, statusUpdate);
            pstmt.setInt(2, resolverIDImport);
            pstmt.setString(3, Timestamp.valueOf(LocalDateTime.now()).toString());
            pstmt.setInt(4, reimbIDImport);
            pstmt.executeUpdate();
            
            //prepping query to confirm update
            pstmt = conn.prepareStatement(baseSelect + sql02);
            pstmt.setInt(1, reimbIDImport);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataSourceException (e);
        }
    }
}//end of ReimbursementDAO class
