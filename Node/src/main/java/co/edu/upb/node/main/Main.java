package co.edu.upb.node.main;

import co.edu.upb.node.factory.NodeFactory;
import co.edu.upb.node.infrastructure.NodeServer;

public class Main {
    public static void main(String[] args) throws Exception {
        NodeFactory factory = new NodeFactory();
        NodeServer nodeServer = factory.getNodeServerInstance();
        nodeServer.run();
    }
}
