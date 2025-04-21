package co.edu.upb.node.infrastructure;

import co.edu.upb.node.config.Environment;
import co.edu.upb.node.domain.interfaces.infrastructure.InterfaceNode;
import co.edu.upb.app.domain.interfaces.infrastructure.InterfacePublisher;
import co.edu.upb.app.domain.models.AppResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Timer;
import java.util.TimerTask;

public class NodeServer {

    private final String port;
    private final InterfaceNode conversionManager;
    private final InterfacePublisher nodeRegistry;
    private final String serverIp;

    public NodeServer(InterfaceNode conversionManager, InterfacePublisher nodeRegistry){
        this.conversionManager  =  conversionManager;
        this.port = Environment.getInstance().getDotenv().get("NODESERVER_PORT");
        this.nodeRegistry = nodeRegistry;
        this.serverIp = Environment.getInstance().getDotenv().get("SERVER_IP");
    }

    public void run() {
        try {
            System.setProperty("java.rmi.server.hostname", serverIp);

            LocateRegistry.createRegistry(Integer.parseInt(port));
            try {
                Naming.rebind("//"+serverIp+":"+port+"/node", conversionManager);

                //Subscribe the node to the app server (registry)
                Boolean isSubscribed = this.subscribeNode(1);

                //Schedule the subscription in 15 minutes for trying again.
                if(!isSubscribed){
                    this.scheduleSubscription();
                }

            } catch (RemoteException | MalformedURLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean subscribeNode(int iterations){

        //Safeguard condition to break the recursive function.
        if(iterations > 10){
            return false;
        }

        AppResponse<Boolean> appResponse;
        try {
            appResponse = this.nodeRegistry.subscribeNode(this.conversionManager);

            //Mock printing for message
            System.out.println(appResponse.getMessage());

            if(!appResponse.isSuccess()){
                this.subscribeNode(iterations+1);
            } else {
                return true;
            }

        } catch (Exception e){
            e.printStackTrace();
            this.subscribeNode(iterations+1);
        }
        return false;
    }

    private void scheduleSubscription(){
        Timer timer = new Timer();
        // Schedule the task to run after 15 minutes
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                subscribeNode(1);
            }
        }, 15 * 60 * 1000);
    }
}
