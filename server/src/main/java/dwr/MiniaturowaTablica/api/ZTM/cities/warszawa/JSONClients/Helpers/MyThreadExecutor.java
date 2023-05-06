package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.JSONClients.Helpers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThreadExecutor {

    private ExecutorService executor;

    public MyThreadExecutor() {
        executor = Executors.newFixedThreadPool(10);
    }

    public void executeTask(Runnable task) {
        executor.execute(task);
    }
}