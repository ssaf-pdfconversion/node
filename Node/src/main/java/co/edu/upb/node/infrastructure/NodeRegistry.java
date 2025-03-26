package co.edu.upb.node.infrastructure;

import co.edu.upb.node.config.Environment;
import co.edu.upb.node.domain.interfaces.infrastructure.InterfaceNode;
import co.edu.upb.node.domain.interfaces.infrastructure.InterfacePublisher;
import co.edu.upb.node.domain.models.AppResponse;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class NodeRegistry implements InterfacePublisher {

    private final InterfacePublisher service;

    public NodeRegistry () {
        try {
            this.service = (InterfacePublisher) Naming.lookup("rmi" + Environment.getInstance().getDotenv().get("REGISTRY_URL"));
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AppResponse<Boolean> subscribeNode(InterfaceNode node) throws RemoteException {
        return null;
    }
}
