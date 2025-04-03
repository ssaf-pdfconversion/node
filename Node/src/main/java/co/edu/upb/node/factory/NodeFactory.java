package co.edu.upb.node.factory;

import co.edu.upb.node.application.ConversionManager;
import co.edu.upb.node.application.WorkersManager;
import co.edu.upb.node.config.Environment;
import co.edu.upb.app.domain.interfaces.infrastructure.InterfacePublisher;
import co.edu.upb.node.infrastructure.NodeServer;
import co.edu.upb.node.util.ChromeExecutor;
import co.edu.upb.node.util.OfficeExecutor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

public class NodeFactory {

    private InterfacePublisher registry;

    public NodeServer getNodeServerInstance(){

        WorkersManager workersManager = new WorkersManager();
        OfficeExecutor officeExecutor = new OfficeExecutor(workersManager);
        ChromeExecutor chromeExecutor = new ChromeExecutor(workersManager);

        ConversionManager conversionManager;
        try {
            conversionManager = new ConversionManager(officeExecutor, chromeExecutor);
        } catch (RemoteException e) {
            throw new RuntimeException("Error initializing ConversionManager", e);
        }

        //Look for the implementation of the RMI registry of the App Server.
        registry = lookupServer();

        return new NodeServer(conversionManager, registry);
    }

    private InterfacePublisher lookupServer(){
        InterfacePublisher registry;
        try {
            registry = (InterfacePublisher) Naming.lookup("rmi://" + Environment.getInstance().getDotenv().get("REGISTRY_URL"));
            return registry;
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            this.scheduleConnection();
        }
        return null;
    }


    private void scheduleConnection(){
        Timer timer = new Timer();
        // Schedule the task to run after 1 minute
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                registry = lookupServer();
            }
        }, 15 * 60 * 1000);
    }

}
