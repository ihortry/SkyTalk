package griffith.skytalkpro.data_access;

import griffith.skytalkpro.model.WeatherData;
import io.github.cdimascio.dotenv.Dotenv;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherDAOImpl implements WeatherDAO {
	private static final Dotenv dotenv = Dotenv.load();

    // Retrieve API keys and base URLs from .env
    private static final String TIMEZONE_API_BASE_URL = dotenv.get("TIMEZONE_API_BASE_URL");
    private static final String WEATHER_API_KEY = dotenv.get("WEATHER_API_KEY");

    private static final JSONParser parser = new JSONParser();
    
    

    @Override
    public WeatherData getWeatherData(double[] coordinates, String date) {
        try {
            String urlStr = "https://api.openweathermap.org/data/3.0/onecall/day_summary?lat=" + coordinates[0] +
                            "&lon=" + coordinates[1] + "&units=metric&date=" + date + "&appid=" + WEATHER_API_KEY;
            JSONObject response = (JSONObject) parser.parse(callAPI(urlStr));

            JSONObject temperature = (JSONObject) response.get("temperature");
            JSONObject precipitation = (JSONObject) response.get("precipitation");
            JSONObject cloudCover = (JSONObject) response.get("cloud_cover");

            double minTemp = (Double) temperature.get("min");
            double maxTemp = (Double) temperature.get("max");
            double precipitationTotal = (Double) precipitation.get("total");
            double cloudCoverAfternoon = (Double) cloudCover.get("afternoon");

            return new WeatherData(minTemp, maxTemp, precipitationTotal, cloudCoverAfternoon);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private static String callAPI(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }


    public String getTimezoneFromCoordinates(double[] coordinates) throws Exception {
        String url = TIMEZONE_API_BASE_URL + "?latitude=" + coordinates[0] + "&longitude=" + coordinates[1];
        String response = callAPI(url);

        JSONObject timezoneData = (JSONObject) parser.parse(response);
        return timezoneData.get("iana_timezone").toString(); // Return the IANA timezone
    }


    @Override
    public double[] getCityCoordinates(String city) {
        try {
            String urlStr = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=1&appid=" + WEATHER_API_KEY;
            String response = callAPI(urlStr);
            JSONArray cities = (JSONArray) parser.parse(response);

            if (cities.isEmpty()) {
                throw new Exception("No results found for city: " + city);
            }

            JSONObject firstCity = (JSONObject) cities.get(0);
            return new double[]{
                (Double) firstCity.get("lat"),
                (Double) firstCity.get("lon")
            };
        } catch (Exception e) {
            System.err.println("Error in DAO for city " + city + ": " + e.getMessage());
            return null;
        }
    }


}
