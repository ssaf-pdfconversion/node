package co.edu.upb.node.util;

import co.edu.upb.node.domain.interfaces.application.IWorkersManager;
import co.edu.upb.node.domain.interfaces.util.IChromeExecutor;
import co.edu.upb.node.domain.models.Conversion;
import co.edu.upb.node.domain.models.File;

public class ChromeExecutor extends AbstractExecutor implements IChromeExecutor {
    public ChromeExecutor(IWorkersManager workersManager) {
        super(workersManager);
    }

    @Override
    public File convertFromURLToPDF(Conversion url) {
        return null;
    }
}
