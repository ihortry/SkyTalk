package griffith.skytalkpro;

import griffith.skytalkpro.model.Place;
import griffith.skytalkpro.model.WeatherData;
import javafx.application.Platform;
import javafx.scene.layout.VBox;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class ChatBot {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private VBox chatPane;
    private SkyTalk skyTalk;
 

    public ChatBot(VBox chatPane, SkyTalk skyTalk) {
        this.chatPane = chatPane;
        this.skyTalk = skyTalk; // Initialize SkyTalk reference
    }

    public void run() throws ParseException, org.json.simple.parser.ParseException {
        skyTalk = new SkyTalk();
        String input;

        while (true) {
            input = skyTalk.input();
            if (input.equalsIgnoreCase("exit")) {
                skyTalk.output("Exiting SkyTalk Chatbot. Have a great day!");
                Platform.exit();
                break;
            } else {
                List<Place> places = takeUserInput(input);
                skyTalk.output("Final Places and dates:\n" + places);

                String response = generateResponse(places);
                skyTalk.output(response);

                skyTalk.output("Type \"exit\" or enter new locations to continue\n"
                        + "(For example: London 25/06/2024, Paris 26/06/2024, Rome 27/06/2024):");
            }
        }
    }

    public List<Place> takeUserInput(String input) {
        List<Place> places = new ArrayList<>();
        String[] data = input.split(",");

        for (String placeWithDate : data) {
            String[] placeInfo = placeWithDate.trim().split(" ");
            if (placeInfo.length == 2) {
                try {
                    String placeName = placeInfo[0];
                    LocalDate date = LocalDate.parse(placeInfo[1], formatter);
                    places.add(new Place(placeName, date));
                } catch (Exception e) {
                    skyTalk.output("Invalid input format or date. Please use the format: Place DD/MM/YYYY.");
                }
            } else {
                skyTalk.output("Invalid input format. Use: Place DD/MM/YYYY.");
            }
        }
        return places;
    }

    public String generateResponse(List<Place> places) {
        StringBuilder builder = new StringBuilder();

        for (Place place : places) {
            try {
                builder.append("Plan for ").append(place.getName()).append(" on ").append(place.getDate()).append(":\n");

                // Fetch coordinates
                double[] coordinates = Weather.cityToCoordinate(place.getName());
                if (coordinates == null) {
                    builder.append("Unable to fetch coordinates for ").append(place.getName()).append(".\n");
                    continue;
                }

                // Fetch weather data
                WeatherData weatherData = Weather.getForecastData(coordinates, place.getDate().toString());
                if (weatherData == null) {
                    builder.append("Unable to fetch weather data for ").append(place.getName()).append(".\n");
                    continue;
                }

                builder.append(weatherData.toString()).append("\n");

                // Generate clothing recommendations
                boolean umbrellaNeeded = weatherData.getPrecipitation() > 5;
                boolean sunglassesNeeded = weatherData.getCloudCover() < 10;
                builder.append(ClothingRecommendation.generateClothingPlan(
                        weatherData.getMinTemp(),
                        weatherData.getMaxTemp(),
                        umbrellaNeeded,
                        sunglassesNeeded
                )).append("\n");

            } catch (Exception e) {
                builder.append("An error occurred for ").append(place.getName()).append(": ").append(e.getMessage()).append("\n");
            }
        }

        return builder.toString();
    }
    public void runForCurrentLocation(double[] coordinates) {
        try {
            // Assuming today's date for current location suggestions
            String currentDate = LocalDate.now().toString();
            WeatherData weatherData = Weather.getForecastData(coordinates, currentDate);
            if (weatherData != null) {
                skyTalk.output("Weather Suggestions for Your Current Location:\n" + weatherData.toString());

                boolean umbrellaNeeded = weatherData.getPrecipitation() > 5;
                boolean sunglassesNeeded = weatherData.getCloudCover() < 10;
                skyTalk.output(ClothingRecommendation.generateClothingPlan(
                        weatherData.getMinTemp(),
                        weatherData.getMaxTemp(),
                        umbrellaNeeded,
                        sunglassesNeeded
                ));
            } else {
                skyTalk.output("Unable to fetch weather data for your current location.");
            }
        } catch (Exception e) {
            skyTalk.output("An error occurred while fetching weather data: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
