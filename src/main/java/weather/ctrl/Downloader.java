package weather.ctrl;

import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;
import tk.plogitech.darksky.forecast.*;
import tk.plogitech.darksky.forecast.model.Forecast;
import weather.ctrl.WeatherController;

import java.io.*;
import java.util.List;
import java.util.Objects;

public abstract class Downloader {

    public static final String DIRECTORY_DOWNLOAD = "./download/";
    private static final String EXTENSION = ".txt";

    public abstract int process(List<GeoCoordinates> coordinates) throws ForecastException;

    public String save(GeoCoordinates coordinates, boolean isParallelDownload, int cityCount) throws ForecastException {
        String fileName = "";
        BufferedWriter writer= null;
        try {
            ForecastRequest request = new ForecastRequestBuilder()
                    .key(new APIKey(WeatherController.apiKey))
                    .location(coordinates)
                    .build();

            DarkSkyJacksonClient client = new DarkSkyJacksonClient();

            Forecast forecast = client.forecast(request);

            WeatherFormatter weatherFormatter = new WeatherFormatter();

            fileName = isParallelDownload ? DIRECTORY_DOWNLOAD +coordinates.toString()  + "_parallelDownload_" + cityCount + EXTENSION : DIRECTORY_DOWNLOAD +coordinates.toString() + "_sequentialDownload_" + cityCount + EXTENSION;


            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(weatherFormatter.format(forecast));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(writer).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }
}
