package weather.ctrl;

import tk.plogitech.darksky.forecast.ForecastException;
import tk.plogitech.darksky.forecast.GeoCoordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ParallelDownloader extends Downloader {
    @Override
    public int process(List<GeoCoordinates> coordinates) throws ForecastException {
        long startTime = System.nanoTime();
        ExecutorService service = Executors.newFixedThreadPool(4);
        int count = 0;
        List<Future> downloadFutures = new ArrayList<>();
        int i = 1;
            for (GeoCoordinates coordinate : coordinates) {
                Future<String> future = service.submit(new Task());
                String fileName = save(coordinate, true, i);
                fileName += future;
                    if(fileName != null){
                        count++;
                    }
                i++;
            }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Milliseconds: "+duration/1000000);
        return count;
    }

    static class Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "downloadfile" + System.currentTimeMillis();
        }
    }
}