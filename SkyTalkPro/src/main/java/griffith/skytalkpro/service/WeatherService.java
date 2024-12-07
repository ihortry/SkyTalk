package griffith.skytalkpro.service;

import griffith.skytalkpro.model.Place;
import griffith.skytalkpro.model.WeatherData;

import java.util.List;

public interface WeatherService {
    List<WeatherData> getWeatherForecasts(List<Place> places);
    String getTimezoneForPlace(Place place);
	double[] getCityCoordinates(String city);
}
