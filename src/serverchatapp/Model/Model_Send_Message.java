

package serverchatapp.Model;



/**
 *
 * @author Nahuel Pierini
 * @Enterprise: FSTailSolution
 */
public class Model_Send_Message {
    
    private int fromUserId;
    private int toUserId;
    private String text;
    
    //constructor

    public Model_Send_Message() {
    }

    
    public Model_Send_Message(int fromUserId, int toUserId, String text) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.text = text;
    }
    
   
    
    //getters and setters

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    

}
