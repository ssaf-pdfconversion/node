package co.edu.upb.node.domain.interfaces.application;

import co.edu.upb.app.domain.models.AppResponse;

public interface IWorkersManager {
    AppResponse<Boolean> submitTask(Runnable task);
}
