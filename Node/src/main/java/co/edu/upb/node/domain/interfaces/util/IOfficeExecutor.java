package co.edu.upb.node.domain.interfaces.util;

import co.edu.upb.node.domain.models.File;

public interface IOfficeExecutor extends Runnable {
    File convertFromOfficeToPDF(String file);
}
