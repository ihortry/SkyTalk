package griffith.skytalkpro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Weather {
    //API keys
    private static final String TIMEZONE_API_BASE_URL = "https://api.geotimezone.com/public/timezone";
    private static final String WEATHER_API_KEY = "d28881d996383f0a2d6a65bb445a882d";

    private static HashMap<String, ZonedDateTime> places = new HashMap<>();

    private static final int MAXPLACES = 5;

    // private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    //parser for converting API responses into JSONObjects
    private static JSONParser parser = new JSONParser();

    // helper method to get the response of an API call

	private static String call(String urlStr) {
		try {
			// convert string to URL object
			URL url = new URL(urlStr);
			// try connecting to the URL
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			// read the input from the API call
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			// append the data to the response string
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);

			}
			// close the reader
			in.close();
			// return the output as a string
			return response.toString();
		} catch (Exception e) {
			e.getStackTrace();
			return null;
		}

	}

    public static String timezoneFromCoordinate(double[] coordinates) throws ParseException, org.json.simple.parser.ParseException {
		String url = TIMEZONE_API_BASE_URL + "?latitude=" + coordinates[0] + "&longitude=" + coordinates[1];
		String response = call(url);

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
        
		JSONObject timezoneData =  (JSONObject)parser.parse(response);

		return timezoneData.get("iana_timezone").toString();
	}

	public static double[] cityToCoordinate(String city) throws ParseException, org.json.simple.parser.ParseException {
		System.out.println("Getting coordinates for " + city + "...");
		String urlStr = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=5&appid=" + WEATHER_API_KEY;
		String response = call(urlStr);
		JSONArray cities = (JSONArray)parser.parse(response);
		JSONObject firstCity = (JSONObject)cities.get(0);
		// the coordinate will be an array of two elements, the first being the latitude
		// and the second being the longitude
		double[] coord = {
				(Double)firstCity.get("lat"),
				(Double)firstCity.get("lon")
		};

		return coord;
	}

    // getForecast() returns the weather conditions for a given coordinate and date
	public static HashMap<String, Double> getForecast(double[] coordinate, String date) throws org.json.simple.parser.ParseException {
		String urlStr = "https://api.openweathermap.org/data/3.0/onecall/day_summary?lat="+coordinate[0]+"&lon="+coordinate[1]+"&units=metric&date="+date+"&appid="+WEATHER_API_KEY;
		System.out.println(urlStr);
		//convert the API response string into a JSON Object
        JSONObject response = (JSONObject)parser.parse(call(urlStr));
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
		}                
                 
		 */
		HashMap<String, Double> data = new HashMap<>();
        //convert all the individual fields to their own JSONObjects
        JSONObject temperature = (JSONObject)response.get("temperature");
        JSONObject precipitation = (JSONObject)response.get("precipitation");
        JSONObject wind = (JSONObject)response.get("wind");
        JSONObject cloud_cover = (JSONObject)response.get("cloud_cover");

        //"speed" is nested in the "max" field of the "wind" field so we need to extract max first
        JSONObject wind_max = (JSONObject)wind.get("max");

        //cast Objects to Doubles and put them all into the hashmap
		data.put("min_temp",(Double)temperature.get("min"));
		data.put("max_temp",(Double)temperature.get("max"));
		data.put("wind_speed",(Double)wind_max.get("speed"));
		data.put("precipitation",(Double)precipitation.get("total"));
		data.put("cloud_cover",(Double)cloud_cover.get("afternoon"));

		return data;
	}

    
}
