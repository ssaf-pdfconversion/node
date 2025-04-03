package co.edu.upb.node.domain.interfaces.infrastructure;

import co.edu.upb.app.domain.models.AppResponse;
import co.edu.upb.node.domain.models.Conversion;
import co.edu.upb.node.domain.models.File;
import co.edu.upb.node.domain.models.NodeReport;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceNode extends Remote {
    public AppResponse<File> dispatchOffice(Conversion file) throws RemoteException;
    public AppResponse<File> dispatchURL(Conversion url) throws RemoteException;
    public AppResponse<NodeReport> getReport() throws RemoteException;
}
