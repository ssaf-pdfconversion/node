package co.edu.upb.node.application;

import co.edu.upb.node.domain.interfaces.infrastructure.InterfaceNode;
import co.edu.upb.app.domain.models.AppResponse;
import co.edu.upb.node.domain.models.File;
import co.edu.upb.node.domain.models.Iteration;
import co.edu.upb.node.domain.models.NodeReport;
import co.edu.upb.node.util.ChromeExecutor;
import co.edu.upb.node.util.OfficeExecutor;
import co.edu.upb.node.util.SystemInfo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConversionManager extends UnicastRemoteObject implements InterfaceNode {

    private final OfficeExecutor officeExec;
    private final ChromeExecutor chromeExec;
    private final ExecutorService pool;
    private final String nodeId = SystemInfo.getMachineUUID();


    public ConversionManager(
            String sofficePath,
            String chromePath,
            int threadCount
    ) throws Exception {
        super();
        this.officeExec = new OfficeExecutor(sofficePath);
        this.chromeExec = new ChromeExecutor(chromePath);
        this.pool       = Executors.newFixedThreadPool(threadCount);
    }

    @Override
    public AppResponse<Map<File,Iteration>> dispatchOffice(
            String base64File,
            String originalFilename
    ) {
        Instant start = Instant.now();
        try {
            Future<File> f = pool.submit(() ->
                    officeExec.execute(base64File, originalFilename)
            );
            File result = f.get();
            Instant end = Instant.now();
            Iteration iter = new Iteration(
                    start.toString(),
                    end.toString(),
                    nodeId
            );
            return new AppResponse<>(

                    true,
                    "Office to PDF conversion successful",
                    Collections.singletonMap(result, iter)
            );
        } catch (Exception e) {
            return new AppResponse< Map<File,Iteration> >(
                    false,
                    "office conversion failed: " + e.getMessage(),
                    Collections.emptyMap()
            );
        }
    }

    @Override
    public AppResponse<Map<File,Iteration>> dispatchURL(String url) {
        Instant start = Instant.now();
        try {
            Future<File> f = pool.submit(() ->
                    chromeExec.execute(url)
            );
            File result = f.get();
            Instant end = Instant.now();
            Iteration iter = new Iteration(
                    start.toString(),
                    end.toString(),
                    nodeId
            );

            return new AppResponse<Map<File,Iteration>>(
                    true,
                    "URL to PDF conversion successful",
                    Collections.singletonMap(result, iter)

            );

        } catch (Exception e) {
            return new AppResponse< Map<File,Iteration> >(
                    false,
                    "Url conversion failed: " + e.getMessage(),
                    Collections.emptyMap()
            );
        }
    }




    @Override
    public AppResponse<NodeReport> getReport() throws RemoteException {
        //TODO: Change implementation for getReport
        System.out.println("Ejecución de getReport con timestamp " + getTimestamp());
        return new AppResponse<>(true, "Reporte del nodo con timestamp " + getTimestamp(), new NodeReport(0, 0.0, 0));
    }

    //TODO: Remove this private function when implementation is done
    private String getTimestamp(){
        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        return now.format(formatter);
    }
}
