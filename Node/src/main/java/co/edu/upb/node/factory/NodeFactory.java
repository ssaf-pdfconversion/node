package co.edu.upb.node.factory;

import co.edu.upb.node.application.ConversionManager;
import co.edu.upb.node.application.WorkersManager;
import co.edu.upb.node.infrastructure.NodeRegistry;
import co.edu.upb.node.infrastructure.NodeServer;
import co.edu.upb.node.util.ChromeExecutor;
import co.edu.upb.node.util.OfficeExecutor;

public class NodeFactory {

    public NodeServer getNodeServerInstance(){

        WorkersManager workersManager = new WorkersManager();
        OfficeExecutor officeExecutor = new OfficeExecutor(workersManager);
        ChromeExecutor chromeExecutor = new ChromeExecutor(workersManager);

        ConversionManager conversionManager = new ConversionManager(officeExecutor, chromeExecutor);

        NodeRegistry nodeRegistry = new NodeRegistry();

        return new NodeServer(conversionManager, nodeRegistry);
    }

}
