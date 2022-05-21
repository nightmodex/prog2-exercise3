package weather.ctrl;
import tk.plogitech.darksky.forecast.ForecastException;
import tk.plogitech.darksky.forecast.GeoCoordinates;

import java.util.List;

public class SequentialDownloader extends Downloader {

    @Override
    public int process(List<GeoCoordinates> geoCoordinatesList) throws ForecastException {
        long startTime = System.nanoTime();
        int count = 0;
        int cityCount = 1;
        for (GeoCoordinates coordinates : geoCoordinatesList) {
            String fileName = save(coordinates, false, cityCount);
            if(fileName != null){
                count++;
            }
            cityCount++;
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Milliseceonds: "+duration/1000000);
        return count;
    }
}


