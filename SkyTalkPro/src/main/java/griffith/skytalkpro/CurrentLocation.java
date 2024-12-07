package griffith.skytalkpro;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CurrentLocation {

    public static double[] getLocationFromIP() {
        try {
            String url = "https://ipapi.co/json/";
            System.out.println("Connecting to: " + url);
            
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            System.out.println("Response received: " + response);

            JSONObject json = (JSONObject) new JSONParser().parse(response.toString());
            if (json.containsKey("latitude") && json.containsKey("longitude")) {
                double latitude = Double.parseDouble(json.get("latitude").toString());
                double longitude = Double.parseDouble(json.get("longitude").toString());
                System.out.println("Parsed coordinates: " + latitude + ", " + longitude);
                return new double[]{latitude, longitude};
            } else {
                System.err.println("Invalid response: Missing latitude/longitude keys.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error fetching location: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}



