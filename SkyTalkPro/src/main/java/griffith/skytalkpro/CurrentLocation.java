package griffith.skytalkpro;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CurrentLocation {

    public static void main(String[] args) {
        try {

            String ipAddress = getCurrentIPAddress();
            Map<String, String> locationInfo = getLocationInfo(ipAddress);
            String cityName = locationInfo.get("city");
            System.out.println("Current City: " + cityName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String getCurrentIPAddress() throws IOException {
        URL url = new URL("https://api.ipify.org");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String ipAddress = reader.readLine();
        reader.close();
        return ipAddress;
    }
    private static Map<String, String> getLocationInfo(String ipAddress) throws IOException {
        URL url = new URL("https://ipinfo.io/" + ipAddress + "/json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        Map<String, String> locationInfo = new HashMap<>();
        String[] parts = response.toString().split(",");
        for (String part : parts) {
            String[] keyValue = part.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].replaceAll("\"", "").trim();
                String value = keyValue[1].replaceAll("\"", "").trim();
                locationInfo.put(key, value);
            }
        }
        return locationInfo;
    }
}


