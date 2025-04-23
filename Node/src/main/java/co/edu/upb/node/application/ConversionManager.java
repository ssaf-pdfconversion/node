package co.edu.upb.node.application;

import co.edu.upb.node.domain.interfaces.infrastructure.InterfaceNode;
import co.edu.upb.app.domain.models.AppResponse;
import co.edu.upb.node.domain.models.File;
import co.edu.upb.node.domain.models.Iteration;
import co.edu.upb.node.domain.models.NodeReport;
import co.edu.upb.node.util.ChromeExecutor;
import co.edu.upb.node.util.OfficeExecutor;
import co.edu.upb.node.util.SystemInfo;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class ConversionManager extends UnicastRemoteObject implements InterfaceNode {

    private final OfficeExecutor officeExec;
    private final ChromeExecutor chromeExec;
    private final ThreadPoolExecutor pool;
    private final String nodeId = SystemInfo.getMachineUUID();


    public ConversionManager(
            String sofficePath,
            String chromePath,
            int threadCount
    ) throws Exception {
        super();
        this.officeExec = new OfficeExecutor(sofficePath);
        this.chromeExec = new ChromeExecutor(chromePath);
        this.pool       = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
    }

    @Override
    public AppResponse<Map<File,Iteration>> dispatchOffice(
            String base64File,
            String originalFilename
    ) {
        Instant start = Instant.now();

        System.out.println("Office conversion requested at " + start);

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

            String timestamp = Instant.now().toString();
            System.out.println("Office conversion on " + timestamp);

            return new AppResponse<>(

                    true,
                    "Office to PDF conversion successful",
                    Collections.singletonMap(result, iter)
            );
        } catch (Exception e) {
            return new AppResponse< Map<File,Iteration> >(
                    false,
                    "Office conversion failed: " + e.getMessage(),
                    Collections.emptyMap()
            );
        }
    }

    @Override
    public AppResponse<Map<File,Iteration>> dispatchURL(String url) {
        Instant start = Instant.now();

        System.out.println("URL conversion requested at " + start);

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

            String timestamp = Instant.now().toString();
            System.out.println("URL conversion on " + timestamp);

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

        int activeTasks  = pool.getActiveCount();
        int queueLength  = pool.getQueue().size();

        //get CPU usage
        OperatingSystemMXBean osBean =
                ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        double cpuLoad = osBean.getProcessCpuLoad();
        double cpuUsage = cpuLoad < 0 ? 0.0 : cpuLoad * 100; // negative means “not available”

        String msg = String.format(
                "Nodo %s → active=%d, queued=%d, cpu=%.1f%%",
                nodeId, activeTasks, queueLength, cpuUsage
        );

        System.out.println(msg);

        NodeReport report = new NodeReport(activeTasks, cpuUsage, queueLength);
        return new AppResponse<>(true, msg, report);
    }

    //TODO: Remove this private function when implementation is done
    private String getTimestamp(){
        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        return now.format(formatter);
    }
}
