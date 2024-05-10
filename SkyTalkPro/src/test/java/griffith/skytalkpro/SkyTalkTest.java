package griffith.skytalkpro;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Test;

public class SkyTalkTest {
    // initialise new instance of Weather for testing weather-related functions, and
    // ClothingRecommendation for clothing recommendation methods
    static Weather weather = new Weather();
    static ClothingRecommendation clothingRecommendation = new ClothingRecommendation();
    // example cities
    static double[] dublinCoordinates = { 53.33306, -6.24889 };
    static double[] addisAbabaCoordinates = { 9.005401, 38.763611 };
    static double[] tokyoCoordinates = { 35.652832, 139.839478 };
    static double[] seattleCoordinates = { 47.60383, -122.33006 };

    // test timezoneFromCoordinate()
    @Test
    public void testTimeZoneFromCoordinate() {
        try {
            String dublinTimezone = "Europe/Dublin";
            String addisAbabaTimezone = "Africa/Addis_Ababa";
            String tokyoTimezone = "Asia/Tokyo";
            String seattleTimezone = "America/Los_Angeles";

            // test Dublin
            String timezone = weather.timezoneFromCoordinate(dublinCoordinates);
            System.out.println(timezone);
            assertEquals(dublinTimezone, timezone);

            // test Addis Ababa
            timezone = weather.timezoneFromCoordinate(addisAbabaCoordinates);
            System.out.println(timezone);
            assertEquals(addisAbabaTimezone, timezone);

            // test Tokyo
            timezone = weather.timezoneFromCoordinate(tokyoCoordinates);
            System.out.println(timezone);
            assertEquals(tokyoTimezone, timezone);

            // test Seattle
            timezone = weather.timezoneFromCoordinate(seattleCoordinates);
            System.out.println(timezone);
            assertEquals(seattleTimezone, timezone);

        } catch (Exception e) {
            fail("Something went wrong!");
        }

    }

    // test cityToCoordinate()
    @Test
    public void testCityToCoordinate() {
        // set delta to 0.1 to account for tiny differences in coordinates for the same
        // city/location,
        // that may occur due to the website i used for the expected coordinates picking
        // a slightly different spot on the map
        double delta = 0.1;
        try {
            // test Dublin
            double[] coordinates = weather.cityToCoordinate("Dublin");
            System.out.println(coordinates[0] + ", " + coordinates[1]);

            // testing latitude
            assertEquals(dublinCoordinates[0], coordinates[0], delta);
            // testing longitude
            assertEquals(dublinCoordinates[1], coordinates[1], delta);

            // test Addis Ababa
            coordinates = weather.cityToCoordinate("Addis Ababa");
            System.out.println(coordinates[0] + ", " + coordinates[1]);

            // testing latitude
            assertEquals(addisAbabaCoordinates[0], coordinates[0], delta);
            // testing longitude
            assertEquals(addisAbabaCoordinates[1], coordinates[1], delta);

            // test Tokyo
            coordinates = weather.cityToCoordinate("Tokyo");
            System.out.println(coordinates[0] + ", " + coordinates[1]);

            // testing latitude
            assertEquals(tokyoCoordinates[0], coordinates[0], delta);
            // testing longitude
            assertEquals(tokyoCoordinates[1], coordinates[1], delta);

            // test Seattle
            coordinates = weather.cityToCoordinate("Seattle");
            System.out.println(coordinates[0] + ", " + coordinates[1]);

            // testing latitude
            assertEquals(seattleCoordinates[0], coordinates[0], delta);
            // testing longitude
            assertEquals(seattleCoordinates[1], coordinates[1], delta);

        } catch (Exception e) {
            fail("Something went wrong!");
        }
    }

    // test getForecast()
    @Test
    public void testGetForecast() {
        try {
            String date = "2024-06-01";
            HashMap<String, Double> forecast;
            double[] dublinExpectedForecast = {0.15,15.29,55.86,7.03,17.85};
            double[] addisAbabaExpectedForecast = {20.05,13.9,24.56,2.58,27.15};
            double[] tokyoExpectedForecast = {0.75,18.3,94.09,3.28,23.62};
            double[] seattleExpectedForecast = {0.93,9.75,68.92,4.99,19.38};

            double delta = 0.01;

            // test Dublin
            forecast = weather.getForecast(dublinCoordinates, date);
            int i = 0;
            for (HashMap.Entry<String, Double> entry : forecast.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                System.out.println("Key: " + key + ", Value: " + value);
                assertEquals(dublinExpectedForecast[i],value,delta);
                i++;
            }

            // test Addis Ababa
            forecast = weather.getForecast(addisAbabaCoordinates, date);
            i = 0;
            for (HashMap.Entry<String, Double> entry : forecast.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                System.out.println("Key: " + key + ", Value: " + value);
                assertEquals(addisAbabaExpectedForecast[i],value,delta);
                i++;
            }
            // test Tokyo
            forecast = weather.getForecast(tokyoCoordinates, date);
            i = 0;
            for (HashMap.Entry<String, Double> entry : forecast.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                System.out.println("Key: " + key + ", Value: " + value);
                assertEquals(tokyoExpectedForecast[i],value,delta);
                i++;
            }
            // test Seattle
            forecast = weather.getForecast(seattleCoordinates, date);
            i = 0;
            for (HashMap.Entry<String, Double> entry : forecast.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                System.out.println("Key: " + key + ", Value: " + value);
                assertEquals(seattleExpectedForecast[i],value,delta);
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail("Something went wrong!");
        }
    }

    public void integratedTest() {
        // TODO
        // test that sendMessage() returns '"Type \"exit\" or enter new locations to
        // continue\n (For example: London 25/06/2024, Paris 26/06/2024, Rome
        // 27/06/2024):"
        // test that takeUserInput() returns a places hashmap
        // test generateClothingPlan()

    }
}
