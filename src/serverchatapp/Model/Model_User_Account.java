package serverchatapp.Model;

/**
 *
 * @author Nahuel Pierini
 * @Enterprise: FSTailSolution
 */
public class Model_User_Account {
    int id;
    String userName;
    String gender;
    String image;
    boolean status;
    
    //constructors

    public Model_User_Account() {
    }

    public Model_User_Account(int id, String userName, String gender, String image, boolean status) {
        this.id = id;
        this.userName = userName;
        this.gender = gender;
        this.image = image;
        this.status = status;
    }
    
    //getter and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
