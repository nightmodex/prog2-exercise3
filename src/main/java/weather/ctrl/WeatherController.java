package weather.ctrl;


import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;
import tk.plogitech.darksky.forecast.*;
import tk.plogitech.darksky.forecast.model.Forecast;

import java.util.ArrayList;
import java.util.List;

public class WeatherController {
    
    private String apiKey = "ab5c55091bfde0864c41b337f1c66af5";
    

    public void process(GeoCoordinates location) throws MyExecption {
        //ToDo: Liste an GeoCoordinates
        System.out.println("process "+location.latitude().value().toString() + " " +location.longitude().value().toString()); //$NON-NLS-1$
		Forecast data = getData(location);
        List<String> listOfCity = new ArrayList<>();
		try{
            Double highestTemp = getHighestTemp(data);
            Double averageTemp = getAverageTemp(data);
            Double highestWind = getHighestWind(data);

            listOfCity.add("Highest temp "+highestTemp);
            listOfCity.add("Average temp "+averageTemp);
            listOfCity.add("Highest wind "+highestWind);

            SequentialDownloader sequentialDownloader = new SequentialDownloader();
            sequentialDownloader.process(listOfCity);

            System.out.println("Daily values: " + data.getDaily().getData().stream().count());
        }catch (Exception ex){
            throw new MyExecption(ex.getMessage());
        }
	}

    private double  getHighestTemp(Forecast data) {
        return data.getDaily().getData().stream().mapToDouble(temp-> temp.getTemperatureHigh()).max().getAsDouble();
    }

    private double  getAverageTemp(Forecast data) {
        return data.getDaily().getData().stream().mapToDouble(temp-> temp.getTemperatureHigh()).average().getAsDouble();
    }

    private double  getHighestWind(Forecast data) {
        return data.getHourly().getData().stream().mapToDouble(temp-> temp.getWindSpeed()).max().getAsDouble();
    }


    public Forecast getData(GeoCoordinates location) {
		ForecastRequest request = new ForecastRequestBuilder()
                .key(new APIKey(apiKey))
                .location(location)
                .build();

        DarkSkyJacksonClient client = new DarkSkyJacksonClient();
        try {
            Forecast forecast = client.forecast(request);
            return forecast;
        } catch (ForecastException e) {
            e.printStackTrace();
        }

        return null;
    }
}
