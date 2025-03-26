package co.edu.upb.node.util;

import co.edu.upb.node.domain.interfaces.application.IWorkersManager;

import java.io.IOException;
import java.util.List;

public abstract class AbstractExecutor {

    private IWorkersManager workersManager;

    public AbstractExecutor(IWorkersManager workersManager){
        this.workersManager = workersManager;
    }

    private boolean executeProcessBuild(List<String> commands) {
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

}
