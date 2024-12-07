package griffith.skytalkpro;

import griffith.skytalkpro.model.Place;
import griffith.skytalkpro.model.WeatherData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SkyTalkTest {
    private static ChatBot catbot;

    private static final double[] DUBLIN_COORDINATES = {53.33306, -6.24889};
    private static final double[] ADDIS_ABABA_COORDINATES = {9.005401, 38.763611};

    @BeforeAll
    public static void setup() {
  
                 // Initialize the ChatBot instance
    }

    @Test
    public void testCityToCoordinate() {
        try {
            double delta = 0.1;

            double[] coordinates = Weather.cityToCoordinate("Dublin");
            assertNotNull(coordinates);
            assertEquals(DUBLIN_COORDINATES[0], coordinates[0], delta);
            assertEquals(DUBLIN_COORDINATES[1], coordinates[1], delta);

            coordinates = Weather.cityToCoordinate("Addis Ababa");
            assertNotNull(coordinates);
            assertEquals(ADDIS_ABABA_COORDINATES[0], coordinates[0], delta);
            assertEquals(ADDIS_ABABA_COORDINATES[1], coordinates[1], delta);

        } catch (Exception e) {
            fail("City to coordinate conversion failed: " + e.getMessage());
        }
    }

    @Test
    public void testGetForecastData() {
        try {
            WeatherData data = Weather.getForecastData(DUBLIN_COORDINATES, "2024-06-01");
            assertNotNull(data);
            assertTrue(data.getMinTemp() > -50);
            assertTrue(data.getMaxTemp() < 50);
        } catch (Exception e) {
            fail("Weather data retrieval failed: " + e.getMessage());
        }
    }

    @Test
    public void testTakeUserInput() {
        String input = "Dublin 06/06/2024, Paris 07/06/2024";

        List<Place> places = catbot.takeUserInput(input);
        assertEquals(2, places.size());
        assertEquals("Dublin", places.get(0).getName());
        assertEquals(LocalDate.of(2024, 6, 6), places.get(0).getDate());
        assertEquals("Paris", places.get(1).getName());
        assertEquals(LocalDate.of(2024, 7, 7), places.get(1).getDate());
    }

    @Test
    public void testGenerateClothingPlan() {
        String recommendation = ClothingRecommendation.generateClothingPlan(5, 15, true, false);
        assertNotNull(recommendation);
        assertTrue(recommendation.contains("umbrella"));
    }

    @Test
    public void testGenerateResponse() {
        String input = "Dublin 06/06/2024";
        List<Place> places = catbot.takeUserInput(input);
        String response = catbot.generateResponse(places);
        assertNotNull(response);
    }
}
