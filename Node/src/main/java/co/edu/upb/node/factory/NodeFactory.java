package co.edu.upb.node.factory;

import co.edu.upb.node.application.ConversionManager;
import co.edu.upb.node.application.WorkersManager;
import co.edu.upb.node.config.Environment;
import co.edu.upb.node.domain.interfaces.infrastructure.InterfacePublisher;
import co.edu.upb.node.infrastructure.NodeServer;
import co.edu.upb.node.util.ChromeExecutor;
import co.edu.upb.node.util.OfficeExecutor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class NodeFactory {

    public NodeServer getNodeServerInstance(){

        WorkersManager workersManager = new WorkersManager();
        OfficeExecutor officeExecutor = new OfficeExecutor(workersManager);
        ChromeExecutor chromeExecutor = new ChromeExecutor(workersManager);

        ConversionManager conversionManager = new ConversionManager(officeExecutor, chromeExecutor);

        //Look for the implementation of the RMI registry of the Auth Server.
        InterfacePublisher registry;
        try {
            registry = (InterfacePublisher) Naming.lookup("rmi://" + Environment.getInstance().getDotenv().get("AUTH_URL"));
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            throw new RuntimeException(e);
        }

        return new NodeServer(conversionManager, registry);
    }

}
