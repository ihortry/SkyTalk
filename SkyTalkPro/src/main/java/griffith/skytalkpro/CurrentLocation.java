package griffith.skytalkpro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CurrentLocation {
    public static String getCurrentLocation() {
        try {
            URL url = new URL("https://ipinfo.io/json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse JSON response to get location
            String jsonResponse = response.toString();
            String[] lines = jsonResponse.split("\n");
            for (String l : lines) {
                if (l.contains("loc")) {
                    return l.split(":")[1].replace("\"", "").trim();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String location = getCurrentLocation();
        System.out.println("Current location: " + location);
    }
}