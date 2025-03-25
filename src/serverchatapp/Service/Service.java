package serverchatapp.Service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import serverchatapp.Model.Model_Client;
import serverchatapp.Model.Model_Login;
import serverchatapp.Model.Model_Message;
import serverchatapp.Model.Model_Recive_Message;
import serverchatapp.Model.Model_Register;
import serverchatapp.Model.Model_Send_Message;
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

    private List<Model_Client> listClient;
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
        listClient= new ArrayList<>();
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

        //Event Register user
        server.addEventListener("register", Model_Register.class, new DataListener<Model_Register>() {
            @Override
            public void onData(SocketIOClient sioc, Model_Register t, AckRequest ar) throws Exception {

                Model_Message message = userService.register(t);
                ar.sendAckData(message.isAction(), message.getMessage(), message.getData());
                if (message.isAction()) {
                    txtArea.append("User has Register : " + t.getUserName() + ", Pass : " + t.getPassword() + "\n");
                    server.getBroadcastOperations().sendEvent("list_user", (Model_User_Account) message.getData());
                    addClient(sioc, (Model_User_Account) message.getData());
                }
            }
        });
        
        //Event login 
        server.addEventListener("login",Model_Login.class ,new DataListener<Model_Login>() {
            @Override
            public void onData(SocketIOClient sioc, Model_Login t, AckRequest ar) throws Exception {
               Model_User_Account login = userService.login(t);
                if(login != null){
                    ar.sendAckData(true, login);
                    addClient(sioc, login);
                    userConnect(login.getId());
                    
                } else {
                    ar.sendAckData(false);
                }
            }
           
        });
        
        //Event list all users
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
        
        server.addEventListener("send_to_user", Model_Send_Message.class, new DataListener<Model_Send_Message>() {
            @Override
            public void onData(SocketIOClient sioc, Model_Send_Message t, AckRequest ar) throws Exception {
                  
                
                
                sendToClient(t);
            }
        }
            
        );
        
        //event Disconect client 
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient sioc) {
               int userID = removeClient(sioc);
               if(userID !=0 ){
                   //removed
                   userDesconect(userID);
               }
            }
        });
        
        server.start();
        txtArea.append("Server has Started on Port: " + PORT_NUMBER + "\n");
    }

    
    private void userConnect(int id){
        server.getBroadcastOperations().sendEvent("user_status", id, true);
    }
    
    private void userDesconect(int id){
        server.getBroadcastOperations().sendEvent("user_status",id, false);
    }
    
    //client connected
    private void addClient(SocketIOClient client, Model_User_Account user){
        listClient.add(new Model_Client(client, user));
    }

    //send message to the client
    public void sendToClient(Model_Send_Message data){
        for(Model_Client c: listClient){
            c.getClient().sendEvent("recive_ms", new Model_Recive_Message(data.getFromUserId(),data.getText()));
          break;
        }
        
    }
    
    //disconect user (Logout)
    public int removeClient(SocketIOClient cl){
        for (Model_Client u : listClient){
            if(u.getClient()== cl){
                listClient.remove(u);
                return u.getUser().getId();
            }
        }
        return 0;
    }
    
    
    
    //list all users
    public List<Model_Client> getListClient() {
        return listClient;
    }
}
