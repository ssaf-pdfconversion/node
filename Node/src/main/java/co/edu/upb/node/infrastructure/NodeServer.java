package co.edu.upb.node.infrastructure;

import co.edu.upb.node.config.Environment;
import co.edu.upb.node.domain.interfaces.infrastructure.InterfaceNode;
import co.edu.upb.node.domain.interfaces.infrastructure.InterfacePublisher;
import co.edu.upb.node.domain.models.AppResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class NodeServer {

    private final String port;
    private final InterfaceNode conversionManager;
    private final InterfacePublisher nodeRegistry;

    public NodeServer(InterfaceNode conversionManager, InterfacePublisher nodeRegistry){
        this.conversionManager  =  conversionManager;
        this.port = Environment.getInstance().getDotenv().get("NODESERVER_PORT");
        this.nodeRegistry = nodeRegistry;
    }

    public void run() {
        try {
            LocateRegistry.createRegistry(Integer.parseInt(port));
            try {
                Naming.rebind("//127.0.0.1:"+port+"/node", conversionManager);
                //Subscribe the node to the app server (registry)
                this.subscribeNode(1);
            } catch (RemoteException | MalformedURLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void subscribeNode(int iterations){

        try {
            //Safeguard condition to break the recursive function.
            if(iterations == 10){
                throw new Exception("The node couldn't subscribe to the server after 10 attempts.");
            }

            AppResponse<Boolean> appResponse = this.nodeRegistry.subscribeNode(this.conversionManager);

            if(!appResponse.data()){
                this.subscribeNode(iterations+1);
            }

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
