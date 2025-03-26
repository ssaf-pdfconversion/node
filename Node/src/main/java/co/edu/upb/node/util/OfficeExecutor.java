package co.edu.upb.node.util;

import co.edu.upb.node.domain.interfaces.application.IWorkersManager;
import co.edu.upb.node.domain.interfaces.util.IOfficeExecutor;
import co.edu.upb.node.domain.models.Conversion;
import co.edu.upb.node.domain.models.File;

public class OfficeExecutor extends AbstractExecutor implements IOfficeExecutor {

    public OfficeExecutor(IWorkersManager workersManager) {
        super(workersManager);
    }

    @Override
    public File convertFromOfficeToPDF(Conversion file) {
        return null;
    }

}
