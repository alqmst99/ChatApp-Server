

package serverchatapp.Model;

/**
 *
 * @author Nahuel Pierini
 * @Enterprise: FSTailSolution
 */
public class Model_Login {

    String userName;
    String Password;
    
    //constructors

    public Model_Login() {
    }

    public Model_Login(String username, String Password) {
        this.userName = username;
        this.Password = Password;
    }
    
    //getters & setters

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
    
    
}
