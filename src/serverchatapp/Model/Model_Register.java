
package serverchatapp.Model;



/**
 *
 * @author Nahuel Pierini
 * @Enterprise: FSTailSolution
 */
public class Model_Register {

   //getters and setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

  
    public String getPassword() {
        return password;
    }

  
    public void setPassword(String password) {
        this.password = password;
    }

    //constructors void and complete 
    public Model_Register() {
    }

    public Model_Register(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    
    
    
    private String userName;
    private String password;
    

}
