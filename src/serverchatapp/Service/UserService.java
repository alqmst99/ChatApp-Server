package serverchatapp.Service;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.util.List;
import serverchatapp.Connection.ConnectionDB;
import serverchatapp.Model.Model_Message;
import serverchatapp.Model.Model_Register;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import serverchatapp.Model.Model_Login;
import serverchatapp.Model.Model_User_Account;

/**
 *
 * @author Nahuel Pierini
 * @Enterprise: FSTailSolution
 */
public class UserService {

    public UserService() {
        this.con = (Connection) ConnectionDB.getInstance().getCon();
    }

    public Model_Message register(Model_Register reg) {

        Model_Message message = new Model_Message();
        try {
            con.setAutoCommit(false);
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(checkQuery);
            ps.setString(1, reg.getUserName());

            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                message.setAction(false);
                message.setMessage("User Already Exit");
            } else {
                message.setAction(true);
            }
            rs.close();
            ps.close();
            if (message.isAction()) {

                //insert user register
                ps = (PreparedStatement) con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, reg.getUserName());
                ps.setString(2, reg.getPassword());
                ps.execute();

                rs = ps.getGeneratedKeys();
                if (rs.first()) {
                    int userID = rs.getInt(1);
                    rs.close();
                    ps.close();
                    //create user account
                    ps = (PreparedStatement) con.prepareStatement(query2);
                    ps.setInt(1, userID);
                    ps.setString(2, reg.getUserName());
                    ps.execute();
                    ps.close();

                    con.commit();
                    con.setAutoCommit(true);
                    message.setAction(true);
                    message.setMessage("Ok");
                    message.setData(new Model_User_Account(userID, reg.getUserName(), "", "", true));

                }

            }

        } catch (SQLException e) {
            System.out.println(e);
            message.setAction(false);
            message.setMessage("Server Error");
            try {
                if (con.getAutoCommit() == false) {
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (SQLException e1) {
                System.out.println(e1);
            }
        }
        return message;
    }

    public Model_User_Account login(Model_Login log) throws SQLException {
            Model_User_Account d= null;
     
        try (PreparedStatement ps = (PreparedStatement) con.prepareStatement(LOGIN)) {
            ps.setString(1, log.getUserName());
            ps.setString(2, log.getPassword());
            System.out.println(log.getUserName());
            System.out.println(log.getPassword());
            
            ResultSet rs = ps.executeQuery();

            if (rs.first()) {
                int id = rs.getInt(1);
                String userName = rs.getString(2);
                String gender = rs.getString(3);
                String image = rs.getString(4);
                
                
                d = new Model_User_Account(id, userName, gender, image, true);
              
            }
            
            rs.close();
        } catch (SQLException e){
            System.err.println(e.toString());
        }
           

          
   return d ;

    }

    //get user 
    public List<Model_User_Account> getUser(int exitUser) throws SQLException {
        List<Model_User_Account> list = new ArrayList<>();
        System.out.println(exitUser);
        try {

            PreparedStatement ps = (PreparedStatement) con.prepareStatement(USER_ACCOUNT);
            ps.setInt(1, exitUser);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt(1);
                String userName = rs.getString(2);
                String gerder = rs.getString(3);
                String imageString = rs.getString(4);

                list.add(new Model_User_Account(userID, userName, gerder, imageString, true));

            }
            rs.close();
            ps.close();

            System.out.println(list);
        } catch (SQLException e) {
            System.out.print(e);
        }
        return list;
    }

    //SQL
    private final String LOGIN = "SELECT id, user_account.userName, gender, imageString FROM user JOIN user_account USING (id) WHERE user.userName= BINARY(?) AND user.password = BINARY(?) AND user_account.status ='1'";

    private final String USER_ACCOUNT = "SELECT id, userName, gender, imageString FROM user_account WHERE  status = '1' AND id <> ?";

    private final String query = "INSERT INTO user (userName, `password`) VALUES (?, ?)";

    private final String checkQuery = "SELECT id FROM user WHERE userName = ? LIMIT 1";

    private final String query2 = "INSERT INTO user_account (id, userName) VALUES (?, ?)";

    //Instance
    private final Connection con;
}
