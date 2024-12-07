package griffith.skytalkpro.data_access;

import griffith.skytalkpro.model.WeatherData;

public interface WeatherDAO {
    WeatherData getWeatherData(double[] coordinates, String date);
    double[] getCityCoordinates(String coordinates);
	String getTimezoneFromCoordinates(double[] coordinates) throws Exception;
	
}
