package co.edu.upb.node.application;

import co.edu.upb.node.domain.interfaces.infrastructure.InterfaceNode;
import co.edu.upb.node.domain.interfaces.util.IChromeExecutor;
import co.edu.upb.node.domain.interfaces.util.IOfficeExecutor;
import co.edu.upb.app.domain.models.AppResponse;
import co.edu.upb.node.domain.models.File;
import co.edu.upb.node.domain.models.Iteration;
import co.edu.upb.node.domain.models.NodeReport;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ConversionManager extends UnicastRemoteObject implements InterfaceNode {

    private final IOfficeExecutor officeExecutor;
    private final IChromeExecutor chromeExecutor;

    public ConversionManager(IOfficeExecutor officeExecutor, IChromeExecutor chromeExecutor) throws RemoteException{
        this.chromeExecutor = chromeExecutor;
        this.officeExecutor = officeExecutor;
    }

    @Override
    public AppResponse<Map<File, Iteration>> dispatchOffice(String file) throws RemoteException {
        return null;
    }

    @Override
    public AppResponse<Map<File, Iteration>> dispatchURL(String url) throws RemoteException {
        return null;
    }

    @Override
    public AppResponse<NodeReport> getReport() throws RemoteException {
        //TODO: Change implementation for getReport
        System.out.println("Ejecución de getReport con timestamp " + getTimestamp());
        return new AppResponse<>(true, "Reporte del nodo con timestamp " + getTimestamp(), new NodeReport(0, 0.0, 0));
    }

    //TODO: Remove this private function when implementation is done
    private String getTimestamp(){
        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        return now.format(formatter);
    }
}
