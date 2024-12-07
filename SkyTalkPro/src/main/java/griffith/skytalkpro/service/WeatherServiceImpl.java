package griffith.skytalkpro.service;

import griffith.skytalkpro.data_access.WeatherDAO;
import griffith.skytalkpro.model.Place;
import griffith.skytalkpro.model.WeatherData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherServiceImpl implements WeatherService {
    private final WeatherDAO weatherDAO;

    public WeatherServiceImpl(WeatherDAO weatherDAO) {
        this.weatherDAO = weatherDAO;
    }


    private final Map<String, double[]> coordinatesCache = new HashMap<>();

    @Override
    public List<WeatherData> getWeatherForecasts(List<Place> places) {
        List<WeatherData> forecasts = new ArrayList<>();
        for (Place place : places) {
            try {
                double[] coordinates = coordinatesCache.computeIfAbsent(
                    place.getName(),
                    weatherDAO::getCityCoordinates
                );
                WeatherData forecast = weatherDAO.getWeatherData(coordinates, place.getDate().toString());
                forecasts.add(forecast);
            } catch (Exception e) {
                System.err.println("Failed to fetch forecast for " + place.getName() + ": " + e.getMessage());
            }
        }
        return forecasts;
    }

    @Override
    public String getTimezoneForPlace(Place place) {
        try {
            double[] coordinates = coordinatesCache.computeIfAbsent(
                place.getName(),
                weatherDAO::getCityCoordinates
            );
            return weatherDAO.getTimezoneFromCoordinates(coordinates);
        } catch (Exception e) {
            System.err.println("Failed to fetch timezone for " + place.getName() + ": " + e.getMessage());
            return "Unknown timezone";
        }
    }
    

    @Override
    public double[] getCityCoordinates(String city) {
        try {
            return weatherDAO.getCityCoordinates(city);
        } catch (Exception e) {
            System.err.println("Error fetching coordinates for city " + city + ": " + e.getMessage());
            return null;
        }
    }


}
