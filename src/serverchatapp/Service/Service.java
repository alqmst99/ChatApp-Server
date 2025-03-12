package serverchatapp.Service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import serverchatapp.Model.Model_Login;
import serverchatapp.Model.Model_Message;
import serverchatapp.Model.Model_Register;
import serverchatapp.Model.Model_User_Account;

/**
 *
 * @author Nahuel Pierini
 * @Enterprise: FSTailSolution
 */
public class Service {

    private static Service instance;
    private SocketIOServer server;
    private UserService userService;
    private JTextArea txtArea;
    private final int PORT_NUMBER = 9999;

    public static Service getInstance(JTextArea txtArea) {
        if (instance == null) {
            instance = new Service(txtArea);
        }
        return instance;
    }

    private Service(JTextArea txtArea) {
        this.txtArea = txtArea;
        userService = new UserService();
    }

    public void startServer() {
        Configuration config = new Configuration();
        config.setPort(PORT_NUMBER);
        server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient sioc) {
                txtArea.append("One client connected \n");
            }
        });

        server.addEventListener("register", Model_Register.class, new DataListener<Model_Register>() {
            @Override
            public void onData(SocketIOClient sioc, Model_Register t, AckRequest ar) throws Exception {

                Model_Message message = userService.register(t);
                ar.sendAckData(message.isAction(), message.getMessage(), message.getData());
                if (message.isAction()) {
                    txtArea.append("User has Register : " + t.getUserName() + ", Pass : " + t.getPassword() + "\n");
                    server.getBroadcastOperations().sendEvent("list_user", (Model_User_Account) message.getData());
                }
            }
        });
        
        server.addEventListener("login",Model_Login.class ,new DataListener<Model_Login>() {
            @Override
            public void onData(SocketIOClient sioc, Model_Login t, AckRequest ar) throws Exception {
               Model_User_Account login = userService.login(t);
                if(login != null){
                    ar.sendAckData(true, login);
                } else {
                    ar.sendAckData(false);
                }
            }
           
        });

        server.addEventListener("list_user", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient sioc, Integer userID, AckRequest ar) throws Exception {
                try {
                    List<Model_User_Account> list = userService.getUser(userID);
                    sioc.sendEvent("list_user", list.toArray());
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        });
        server.start();
        txtArea.append("Server has Started on Port: " + PORT_NUMBER + "\n");
    }

}
