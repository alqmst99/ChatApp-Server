
package serverchatapp.Service;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import serverchatapp.Connection.ConnectionDB;
import serverchatapp.Model.Model_Message;
import serverchatapp.Model.Model_Register;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Nahuel Pierini
 * @Enterprise: FSTailSolution
 */
public class UserService {

    public UserService(){
        this.con = (Connection) ConnectionDB.getInstance().getCon();
    }
    
    public Model_Message register(Model_Register reg){
        
        Model_Message message= new Model_Message();
        try {
               PreparedStatement ps= (PreparedStatement) con.prepareStatement(checkQuery);
        ps.setString(1, reg.getUserName());
       
        ResultSet rs= ps.executeQuery();
        if(rs.first()){
            message.setMessage("User Already Exit");
        }else {
            message.setAction(true);
        }
        rs.close();
        ps.close();
        if (message.isAction()){
            //insert user register
          
         ps=  (PreparedStatement) con.prepareStatement(query);
         ps.setString(1, reg.getUserName());
         ps.setString(2, reg.getPassword());
         ps.execute();
         ps.close();
            message.setAction(true);
            message.setMessage("OK");
            
        }
        
        } catch (SQLException e) {
            message.setAction(false);
                message.setMessage("Server Error");
                System.out.println(e);
            System.out.println(e);
        }
      return message;
    }
    
    
    //SQL
    private final String query="INSERT INTO user (userName, `password`) VALUES (?, ?)";
    
    private final String checkQuery= "SELECT id FROM user WHERE userName = ? LIMIT 1";
    
    
    //Instance
    private final Connection con;
}
