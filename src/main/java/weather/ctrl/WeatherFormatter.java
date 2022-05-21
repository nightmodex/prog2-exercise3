package weather.ctrl;

import java.io.Serializable;
import java.util.stream.Stream;

import tk.plogitech.darksky.forecast.model.DailyDataPoint;
import tk.plogitech.darksky.forecast.model.DataPoint;
import tk.plogitech.darksky.forecast.model.Forecast;
import tk.plogitech.darksky.forecast.model.HourlyDataPoint;

import static java.util.stream.Collectors.*;

public class WeatherFormatter {
    public String format(Forecast weather) {
        StringBuilder sb = new StringBuilder();

        sb.append(weather.getDaily().getSummary());
        sb.append("\n\n");
        sb.append("Hourly\n");
        fmt(sb, "Avg cloud cover", avgCloudCover(perHour(weather)));
        fmt(sb, "Avg apparent temperature", avgApparentTemperature(perHour(weather)));
        fmt(sb, "Avg visibility", avgVisibility(perHour(weather)));
        fmt(sb, "Total ozone", totalOzone(perHour(weather)));

        sb.append("Daily\n");
        fmt(sb, "Avg min temp", avgMinTemp(perDay(weather)));
        fmt(sb, "Avg max temp", avgMaxTemp(perDay(weather)));

        return sb.toString();
    }

    private Double avgCloudCover (Stream<HourlyDataPoint> stream) {
        return stream.collect(averagingDouble(DataPoint::getCloudCover));
    }

    private Double avgApparentTemperature (Stream<HourlyDataPoint> stream) {
        return stream.collect(averagingDouble(DataPoint::getApparentTemperature));
    }

    private Double avgVisibility (Stream<HourlyDataPoint> stream) {
        return stream.collect(averagingDouble(DataPoint::getVisibility));
    }

    private Double totalOzone (Stream<HourlyDataPoint> stream) {
        return stream.mapToDouble(DataPoint::getOzone).sum();
    }

    private Double avgMinTemp (Stream<DailyDataPoint> stream) {
        return stream.collect(averagingDouble(DailyDataPoint::getApparentTemperatureMin));
    }

    private Double avgMaxTemp (Stream<DailyDataPoint> stream) {
        return stream.collect(averagingDouble(t -> t.getApparentTemperatureMax()));
    }

    private void fmt(StringBuilder sb, String label, Serializable v) {
        sb.append(label + ": ");
        sb.append(v);
        sb.append("\n");
    }

    private Stream<HourlyDataPoint> perHour (Forecast weather) {
        return weather.getHourly().getData().stream();
    }

    private Stream<DailyDataPoint> perDay (Forecast weather) {
        return weather.getDaily().getData().stream();
    }
}