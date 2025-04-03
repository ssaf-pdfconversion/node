package co.edu.upb.node.application;

import co.edu.upb.node.domain.interfaces.application.IManagerStats;
import co.edu.upb.node.domain.interfaces.application.IWorkersManager;
import co.edu.upb.app.domain.models.AppResponse;

public class WorkersManager implements IManagerStats, IWorkersManager {
    @Override
    public Integer getActiveTasks() {
        return 0;
    }

    @Override
    public Double getCPUUsage() {
        return 0.0;
    }

    @Override
    public Integer getQueueLength() {
        return 0;
    }

    @Override
    public AppResponse<Boolean> submitTask(Runnable task) {
        return null;
    }
}
