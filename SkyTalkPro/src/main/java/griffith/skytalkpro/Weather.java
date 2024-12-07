package griffith.skytalkpro;

import org.json.simple.JSONObject;

import griffith.skytalkpro.data_access.WeatherDAO;
import griffith.skytalkpro.data_access.WeatherDAOImpl;
import griffith.skytalkpro.model.WeatherData;

public class Weather {
    private static final WeatherDAO weatherDAO = new WeatherDAOImpl();

    public static WeatherData getForecastData(double[] coordinates, String date) throws Exception {
        return weatherDAO.getWeatherData(coordinates, date);
    }

    public static double[] cityToCoordinate(String city) throws Exception {
        return weatherDAO.getCityCoordinates(city);
    }

 
    		


}

// API call format
		/*
		 * {
		 * "latitude": 47.5162,
		 * "longitude": 14.5501,
		 * "location": "Austria",
		 * "country_iso": "AT",
		 * "iana_timezone": "Europe/Vienna",
		 * "timezone_abbreviation": "CET",
		 * "dst_abbreviation": "CEST",
		 * "offset": "UTC+1",
		 * "dst_offset": "UTC+2",
		 * "current_local_datetime": "2023-09-19T18:06:11.57",
		 * "current_utc_datetime": "2023-09-19T16:06:11.570Z"
		 * }
		 */
        //put response into JSONObject
/* format:
		 *                    
		{
		"lat":33,
		"lon":35,
		"tz":"+02:00",
		"date":"2020-03-04",
		"units":"standard",
		"cloud_cover":{
			"afternoon":0
		},
		"humidity":{
			"afternoon":33
		},
		"precipitation":{
			"total":0
		},
		"temperature":{
			"min":286.48,
			"max":299.24,
			"afternoon":296.15,
			"night":289.56,
			"evening":295.93,
			"morning":287.59
		},
		"pressure":{
			"afternoon":1015
		},
		"wind":{
			"max":{
				"speed":8.7,
				"direction":120
			}
		}
		}  */