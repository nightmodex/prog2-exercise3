package weather.ctrl;


import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;
import tk.plogitech.darksky.forecast.*;
import tk.plogitech.darksky.forecast.model.Forecast;

public class WeatherController {
    
    private String apiKey = "ab5c55091bfde0864c41b337f1c66af5";
    

    public void process(GeoCoordinates location) {
        System.out.println("process "+location.latitude().value().toString() + " " +location.longitude().value().toString()); //$NON-NLS-1$
		Forecast data = getData(location);

		try{
           data.getDaily().getData().stream().mapToDouble(temp-> temp.getTemperatureHigh()).max().ifPresent(maxTemp-> System.out.println("Highest temperature was: " + maxTemp));
           data.getDaily().getData().stream().mapToDouble(temp-> temp.getTemperatureHigh()).average().ifPresent(avgTemp-> System.out.println("Average temperature was: " + avgTemp));
           data.getHourly().getData().stream().mapToDouble(temp-> temp.getWindSpeed()).max().ifPresent(maxWind-> System.out.println("Highest wind speed was: " + maxWind));
           System.out.println("Daily values: " + data.getDaily().getData().stream().count());
        }catch (Exception ex){
            ex.printStackTrace();
        }
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
