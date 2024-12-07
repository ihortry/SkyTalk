package griffith.skytalkpro.data_access;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LocationDAOImpl implements LocationDAO {
    @Override
    public Map<String, String> getLocationInfo(String ipAddress) {
        try {
            String urlStr = "https://ipinfo.io/" + ipAddress + "/json";
            String response = callAPI(urlStr);

            Map<String, String> locationInfo = new HashMap<>();
            String[] parts = response.split(",");
            for (String part : parts) {
                String[] keyValue = part.split(":");
                if (keyValue.length == 2) {
                    locationInfo.put(keyValue[0].replaceAll("\"", "").trim(), keyValue[1].replaceAll("\"", "").trim());
                }
            }
            return locationInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getCurrentIPAddress() {
        try {
            String urlStr = "https://api.ipify.org";
            return callAPI(urlStr).trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String callAPI(String urlStr) throws Exception {
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
}
