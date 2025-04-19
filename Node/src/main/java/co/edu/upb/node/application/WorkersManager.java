package co.edu.upb.node.application;

import co.edu.upb.node.domain.interfaces.application.IManagerStats;
import co.edu.upb.node.domain.interfaces.application.IWorkersManager;
import co.edu.upb.app.domain.models.AppResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkersManager implements IManagerStats, IWorkersManager {

    private final ExecutorService executor;

    public WorkersManager(int threadCount) {
        this.executor = Executors.newFixedThreadPool(threadCount);
    }


    @Override
    public AppResponse<Boolean> submitTask(Runnable task) {
        executor.submit(task);
        return new AppResponse<>(true, "Task accepted", null);
    }



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



}
