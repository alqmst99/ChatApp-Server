package serverchatapp.Connection;

import java.sql.*;

/**
 *
 * @author Nahuel Pierini
 * @Enterprise: FSTailSolution
 */
public class ConnectionDB {

    private static ConnectionDB instance;
    private Connection con;

    public static ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }

    public void connectDB() throws SQLException {
        try {//"jdbc:mysql://localhost:3306/chatapp?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC"
            String server = "localhost";//the values is't found 
            String port = "3305";//the values is't found
            String db = "chatpp";//the values is't found
            String query = "?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";//the values is't found
            String username = "root";
            String password = "";
            con = (java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/chatapp?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC", username, password));
            System.out.println("the data base has connected");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * @return the con
     */
    public Connection getCon() {
        return con;
    }

    /**
     * @param con the con to set
     */
    public void setCon(Connection con) {
        this.con = con;
    }

}
