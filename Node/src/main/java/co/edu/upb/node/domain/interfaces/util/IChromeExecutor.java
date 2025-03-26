package co.edu.upb.node.domain.interfaces.util;

import co.edu.upb.node.domain.models.Conversion;
import co.edu.upb.node.domain.models.File;

public interface IChromeExecutor {
    File convertFromURLToPDF(Conversion url);
}
