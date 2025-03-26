package co.edu.upb.node.domain.interfaces.application;

public interface IManagerStats {
    Integer getActiveTasks();
    Double getCPUUsage();
    Integer getQueueLength();
}
