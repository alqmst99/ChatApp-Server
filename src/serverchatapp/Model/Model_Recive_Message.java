package serverchatapp.Model;

/**
 *
 * @author Nahuel Pierini
 * @Enterprise: FSTailSolution
 */
public class Model_Recive_Message {

    private int fromUserId;

    private String text;

    //constructor
    public Model_Recive_Message(Object json) {

    }

    public Model_Recive_Message(int fromUserId, String text) {
        this.fromUserId = fromUserId;
        this.text = text;
    }

    //getters and setters
    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
