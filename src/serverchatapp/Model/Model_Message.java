package serverchatapp.Model;

/**
 *
 * @author Nahuel Pierini
 * @Enterprise: FSTailSolution
 */
public class Model_Message {

    boolean action;
    String message;
    
    //constructor 

    public Model_Message() {
    }

    public Model_Message(boolean action, String message) {
        this.action = action;
        this.message = message;
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
