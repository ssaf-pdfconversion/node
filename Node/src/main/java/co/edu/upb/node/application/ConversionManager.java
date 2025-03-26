package co.edu.upb.node.application;

import co.edu.upb.node.domain.interfaces.infrastructure.InterfaceNode;
import co.edu.upb.node.domain.interfaces.util.IChromeExecutor;
import co.edu.upb.node.domain.interfaces.util.IOfficeExecutor;
import co.edu.upb.node.domain.models.AppResponse;
import co.edu.upb.node.domain.models.Conversion;
import co.edu.upb.node.domain.models.File;
import co.edu.upb.node.domain.models.NodeReport;

import java.rmi.RemoteException;

public class ConversionManager implements InterfaceNode {

    private final IOfficeExecutor officeExecutor;
    private final IChromeExecutor chromeExecutor;

    public ConversionManager(IOfficeExecutor officeExecutor, IChromeExecutor chromeExecutor){
        this.chromeExecutor = chromeExecutor;
        this.officeExecutor = officeExecutor;
    }

    @Override
    public AppResponse<File> dispatchOffice(Conversion file) throws RemoteException {
        return null;
    }

    @Override
    public AppResponse<File> dispatchURL(Conversion url) throws RemoteException {
        return null;
    }

    @Override
    public AppResponse<NodeReport> getReport() throws RemoteException {
        return null;
    }
}
