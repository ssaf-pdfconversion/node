package co.edu.upb.node.factory;

import co.edu.upb.node.application.ConversionManager;
import co.edu.upb.node.application.WorkersManager;
import co.edu.upb.node.config.Environment;
import co.edu.upb.app.domain.interfaces.infrastructure.InterfacePublisher;
import co.edu.upb.node.domain.interfaces.infrastructure.InterfaceNode;
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

    public NodeServer getNodeServerInstance() throws Exception {

            //String sofficePath = "C:\\Program Files\\LibreOffice\\program\\soffice.exe";
            String sofficePath= Environment.getInstance().getDotenv().get("SOFFICE_PATH");

            //String chromePath  = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
            String chromePath=Environment.getInstance().getDotenv().get("CHROME_PATH");
            int threadCount = 3;

        InterfaceNode service =
                new ConversionManager(sofficePath, chromePath, threadCount);


            //Look for the implementation of the RMI registry of the App Server.
            registry = lookupServer();

            return new NodeServer(service, registry);
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
