package co.edu.upb.node.application;

import co.edu.upb.node.domain.interfaces.infrastructure.InterfaceNode;
import co.edu.upb.node.domain.interfaces.util.IChromeExecutor;
import co.edu.upb.node.domain.interfaces.util.IOfficeExecutor;
import co.edu.upb.app.domain.models.AppResponse;
import co.edu.upb.node.domain.models.Conversion;
import co.edu.upb.node.domain.models.File;
import co.edu.upb.node.domain.models.NodeReport;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ConversionManager extends UnicastRemoteObject implements InterfaceNode {

    private final IOfficeExecutor officeExecutor;
    private final IChromeExecutor chromeExecutor;

    public ConversionManager(IOfficeExecutor officeExecutor, IChromeExecutor chromeExecutor) throws RemoteException{
        this.chromeExecutor = chromeExecutor;
        this.officeExecutor = officeExecutor;
    }

    @Override
    public AppResponse<File> dispatchOffice(Conversion file) throws RemoteException {
        //TODO: Change implementation for office dispatch
        System.out.println("Ejecución de dispatchOffice con timestamp " + getTimestamp());
        return new AppResponse<>(true, "Conversión office de desde el nodo con timestamp " + getTimestamp(), new File("", 0.0, getTimestamp(), 1, 1));
    }

    @Override
    public AppResponse<File> dispatchURL(Conversion url) throws RemoteException {
        //TODO: Change implementation for url dispatch
        System.out.println("Ejecución de dispatchURL con timestamp " + getTimestamp());
        return new AppResponse<>(true, "Conversión url de desde el nodo con timestamp " + getTimestamp(), new File("", 0.0, getTimestamp(), 1, 1));
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
