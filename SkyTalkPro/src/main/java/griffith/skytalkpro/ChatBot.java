package griffith.skytalkpro;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

// Class representing the ChatBot logic
class ChatBot {
    private static HashMap<String, LocalDate> places = new HashMap<>();
    private static final int MAXPLACES = 5;
    // Default values
    private static double minTemperature = 100;
    private static double maxTemperature = 0;
    private static boolean umbrellaIsNeeded = false;
    private static boolean sunglassesIsNeeded = false;
    //private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static Weather weather;
    //public String lastInput;
    private VBox chatPane;
    private SkyTalk skyTalk;

    public ChatBot(VBox chatPane) {
        this.chatPane = chatPane;

    }

    public void run() throws ParseException, org.json.simple.parser.ParseException {
        skyTalk = new SkyTalk();
        String input;

        while (true) {
            System.out.println("Intup: ");
            input = skyTalk.input();
            System.out.println("Intup: " + input);
            if (input.equalsIgnoreCase("exit")) {
                skyTalk.output("Exiting SkyTalk Chatbot. Have a great day!");
                Platform.exit(); // Terminate the JavaFX application
                break;
            } else {
                places = takeUserInput(input);
                StringBuilder finalPlaces = new StringBuilder();
                finalPlaces.append("Final Places and dates:\n");
                for (String place : places.keySet()) {
                    finalPlaces.append("  " + place + ": " + places.get(place).format(formatter)+"\n");
                }

                skyTalk.output(finalPlaces.toString());
                //System.out.print("\n");

                skyTalk.output(generateResponse(places));

                skyTalk.output("Type \"exit\" or enter new locations to continue\n"
                        + "(For example: London 25/06/2024, Paris 26/06/2024, Rome 27/06/2024):");
            }
        }
    }

    public  HashMap<String, LocalDate> takeUserInput(String input) {
        places = new HashMap<>();
        boolean validInput = false;
        while (!validInput) {

            String[] data = input.split(",");

            for (String placeWithDate : data) {
                String[] placeInfo = placeWithDate.trim().split(" ");
                //if placeInfo isnt an array size two [place, date]
                if (placeInfo.length != 2) {
                    skyTalk.output("Invalid input format. Please enter place and date separated by a space.");
                    continue;
                }
                String placeName = placeInfo[0];
                String dateOfVisit = placeInfo[1];
                try {
                    LocalDate date = LocalDate.parse(dateOfVisit, formatter);
                    places.put(placeName, date);
                } catch (Exception e) {
                    skyTalk.output("Invalid date format for " + placeName + ". Please enter date in format dd/MM/yyyy.");
                }
            }
            StringBuilder placesAndDates = new StringBuilder();
            placesAndDates.append("Places and dates:\n");
            for (String place : places.keySet()) {
                placesAndDates.append(" " + place + ": " + places.get(place).format(formatter) + "\n");
            }
            skyTalk.output(placesAndDates.toString());
            validInput = true;
            //    if (places.size() >= MAXPLACES) {
            //        output("Maximum number of places reached.");
            //        validInput = true;
            //    } else {
            //        output("Do you want to add more places? (yes/no)");

            //        String moreInput = input();
            //        if (!moreInput.equals("yes")) {
            //            validInput = true;
            //        } else {
            //            output("Enter place you plan to visit and date: ");
            //            input = input();
            //        }
            //    }
        }

        return places;
    }

    public String generateResponse(HashMap<String, LocalDate> places) throws ParseException, org.json.simple.parser.ParseException {
        // Reset variables
        minTemperature = 100;
        maxTemperature = 0;
        umbrellaIsNeeded = false;
        sunglassesIsNeeded = false;

        StringBuilder builder = new StringBuilder();
        // for each city in the hashmap, get its coordinates
        for (String city : places.keySet()) {
            builder.append("PLAN FOR: " + city);
            double[] coordinates = weather.cityToCoordinate(city);

            LocalDate time = places.get(city);
            //long timeUnix = time.toInstant().atZone(ZoneId.of(timezoneFromCoordinate(coordinates))).toEpochSecond();

            // Get year, month, and day
            int year = time.getYear();
            int month = time.getMonthValue();
            int day = time.getDayOfMonth();

            // Format them into year-month-day format
            String formattedDate = String.format("%04d-%02d-%02d", year, month, day);


            HashMap<String, Double> weatherData = weather.getForecast(coordinates, formattedDate);

            double minTemp = weatherData.get("min_temp");
            double maxTemp = weatherData.get("max_temp");

            //if rainfall above 5cm, recommend umbrella
            //if cloud cover below 10%, recommend sunglasses
            boolean umbrellaNeeded = weatherData.get("precipitation") > 5;
            boolean sunglassesNeeded = weatherData.get("cloud_cover")<10;

            builder.append("temperature for " + city + " at " + time + ": \n");
            builder.append("minimum temperature: " + weatherData.get("min_temp") + "C | ");
            builder.append("maximum temperature: " + weatherData.get("max_temp") + "C \n");

            builder.append("cloud cover: " + weatherData.get("cloud_cover") + "%\n");
            builder.append("precipitation: " + weatherData.get("precipitation") + "cm\n");



            String clothingPlan = ClothingRecommendation.generateClothingPlan(minTemp,maxTemp,umbrellaNeeded,sunglassesNeeded);
            builder.append(clothingPlan);
            builder.append("\n");
        }
        return builder.toString();

    }
}