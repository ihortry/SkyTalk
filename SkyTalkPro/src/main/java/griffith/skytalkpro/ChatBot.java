package griffith.skytalkpro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javafx.application.Application;
import javafx.stage.Stage;


/*
 * Class representing the ChatBot logic
 */
class ChatBot {


    private static final String TIMEZONE_API_BASE_URL = "https://api.geotimezone.com/public/timezone";
    private static final String WEATHER_API_KEY = "d28881d996383f0a2d6a65bb445a882d";
    private static HashMap<String, ZonedDateTime> places = new HashMap<>();
    private static final int MAXPLACES = 5;

    // private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

    public static void main(String[] args) {

        respond("Welcome to SkyTalk Chatbot!\n"
                + "Enter up to 5 places you plan to visit and dates to plan your clothing\nrequirements.\n"
                + "(For example: London 25/04/2024, Paris 26/04/2024, Rome 27/04/2024):");
        // Read user input and process accordingly

//        String input;
//
//        while (true) {
//            input = gui.input();
//            if (input.equalsIgnoreCase("exit")) {
//                gui.output("Exiting SkyTalk Chatbot. Have a great day!");
//                break;
//            } else {
//                places = takeUserInput(input);
//                StringBuilder finalPlaces = new StringBuilder();
//                finalPlaces.append("Final Places and dates:\n");
//                for (String place : places.keySet()) {
//                    finalPlaces.append("  " + place + ": " + places.get(place).format(formatter) + "\n");
//                }
//
//                gui.output(finalPlaces.toString());
//                // System.out.print("\n");
//
//                gui.output(generateResponse(places));
//
//                gui.output("Type \"exit\" or enter new locations to continue\n"
//                        + "(For example: London 25/04/2024, Paris 26/04/2024, Rome 27/04/2024):");
//
//            }
//        }

    }
//
//    public static HashMap<String, ZonedDateTime> takeUserInput(String input) {
//        places = new HashMap<>();
//
//        boolean validInput = false;
//
//        while (!validInput) {
//
//            String[] data = input.split(",");
//
//            for (String placeWithDate : data) {
//                String[] placeInfo = placeWithDate.trim().split(" ");
//                if (placeInfo.length != 2) {
//                    gui.output("Invalid input format. Please enter place and date separated by a space.");
//                    continue;
//                }
//                String placeName = placeInfo[0];
//                String dateOfVisit = placeInfo[1];
//                try {
//                    // get the coordinates of the location given in order to find its timezone
//                    double[] placeCoordinates = cityToCoordinate(placeName);
//                    // get the timezone name of the location
//                    LocalDate date = LocalDate.parse(dateOfVisit, formatter);
//                    ZonedDateTime zonedDate = date.atStartOfDay(ZoneId.of(timezoneFromCoordinate(placeCoordinates)));
//                    // ZoneId zoneId = ZoneId.of("UTC");
//                    // long epoch = date.atStartOfDay(zoneId).toEpochSecond();
//
//                    places.put(placeName, zonedDate);
//                } catch (Exception e) {
//                    gui.output("Invalid date format for " + placeName + ". Please enter date in format dd/MM/yyyy.");
//                }
//            }
//
//            // TODO
//
//            StringBuilder placesAndDates = new StringBuilder();
//            placesAndDates.append("Places and dates:\n");
//            for (String place : places.keySet()) {
//                placesAndDates.append(" " + place + ": " + places.get(place).format(formatter) + "\n");
//            }
//            gui.output(placesAndDates.toString());
//            if (places.size() >= MAXPLACES) {
//                gui.output("Maximum number of places reached.");
//                validInput = true;
//            } else {
//                gui.output("Do you want to add more places? (yes/no)");
//                String moreInput = gui.input();
//                if (!moreInput.equals("yes")) {
//                    validInput = true;
//                } else {
//                    gui.output("Enter place you plan to visit and date: ");
//                    input = gui.input();
//                }
//            }
//        }
//
//        return places;
//    }
//
//    public static String timezoneFromCoordinate(double[] coordinates) {
//        String url = TIMEZONE_API_BASE_URL + "?latitude=" + coordinates[0] + "&longitude=" + coordinates[1];
//        String response = call(url);
//
//        // API call format
//        /*
//         * {
//         * "latitude": 47.5162,
//         * "longitude": 14.5501,
//         * "location": "Austria",
//         * "country_iso": "AT",
//         * "iana_timezone": "Europe/Vienna",
//         * "timezone_abbreviation": "CET",
//         * "dst_abbreviation": "CEST",
//         * "offset": "UTC+1",
//         * "dst_offset": "UTC+2",
//         * "current_local_datetime": "2023-09-19T18:06:11.57",
//         * "current_utc_datetime": "2023-09-19T16:06:11.570Z"
//         * }
//         */
//        JSONObject timezoneData = new JSONObject(response);
//        return timezoneData.getString("iana_timezone");
//    }
//
//    public static double[] cityToCoordinate(String city) {
//        System.out.println("Getting coordinates for " + city + "...");
//        String urlStr = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=5&appid=" + WEATHER_API_KEY;
//        String response = call(urlStr);
//        JSONArray cities = new JSONArray(response.toString());
//        JSONObject firstCity = cities.getJSONObject(0);
//        // the coordinate will be an array of two elements, the first being the latitude
//        // and the second being the longitude
//        double[] coord = {
//                firstCity.getDouble("lat"),
//                firstCity.getDouble("lon")
//        };
//
//        return coord;
//    }
//
//    // getForecast() returns the weather conditions for a given coordinate and date
//    public static HashMap<String, Double> getForecast(double[] coordinate, String date) {
//        String urlStr = "https://api.openweathermap.org/data/3.0/onecall/day_summary?lat="+coordinate[0]+"&lon="+coordinate[1]+"&units=metric&date="+date+"&appid="+WEATHER_API_KEY;
//        JSONObject response = new JSONObject(call(urlStr));
//		/* format:
//		 *
//		{
//		"lat":33,
//		"lon":35,
//		"tz":"+02:00",
//		"date":"2020-03-04",
//		"units":"standard",
//		"cloud_cover":{
//			"afternoon":0
//		},
//		"humidity":{
//			"afternoon":33
//		},
//		"precipitation":{
//			"total":0
//		},
//		"temperature":{
//			"min":286.48,
//			"max":299.24,
//			"afternoon":296.15,
//			"night":289.56,
//			"evening":295.93,
//			"morning":287.59
//		},
//		"pressure":{
//			"afternoon":1015
//		},
//		"wind":{
//			"max":{
//				"speed":8.7,
//				"direction":120
//			}
//		}
//		}
//
//		 */
//        HashMap<String, Double> data = new HashMap<>();
//        data.put("min_temp",response.getJSONObject("temperature").getDouble("min"));
//        data.put("max_temp",response.getJSONObject("temperature").getDouble("max"));
//        data.put("wind_speed",response.getJSONObject("wind").getJSONObject("max").getDouble("speed"));
//        data.put("precipitation",response.getJSONObject("precipitation").getDouble("total"));
//        data.put("cloud_cover", response.getJSONObject("cloud_cover").getDouble("afternoon"));
//        return data;
//    }
//
//    public static String generateResponse(HashMap<String, ZonedDateTime> places) {
//        StringBuilder builder = new StringBuilder();
//        // for each city in the hashmap, get its coordinates
//        for (String city : places.keySet()) {
//            double[] coordinates = cityToCoordinate(city);
//            ZonedDateTime time = places.get(city);
//            //long timeUnix = time.toInstant().atZone(ZoneId.of(timezoneFromCoordinate(coordinates))).toEpochSecond();
//
//            // Get year, month, and day
//            int year = time.getYear();
//            int month = time.getMonthValue();
//            int day = time.getDayOfMonth();
//
//            // Format them into year-month-day format
//            String formattedDate = String.format("%04d-%02d-%02d", year, month, day);
//
//
//            HashMap<String, Double> weather = getForecast(coordinates, formattedDate);
//
//            double minTemp = weather.get("min_temp");
//            double maxTemp = weather.get("max_temp");
//
//            //if rainfall above 5cm, recommend umbrella
//            //if cloud cover below 10%, recommend sunglasses
//            boolean umbrellaNeeded = weather.get("precipitation") > 5;
//            boolean sunglassesNeeded = weather.get("cloud_cover")<10;
//
//            builder.append("temperature for " + city + " at " + time + ": \n");
//            builder.append("minimum temperature: " + weather.get("min_temp") + "C | ");
//            builder.append("maximum temperature: " + weather.get("max_temp") + "C \n");
//
//            builder.append("cloud cover: " + weather.get("cloud_cover") + "%\n");
//            builder.append("precipitation: " + weather.get("precipitation") + "cm\n");
//            builder.append("wind speed: " + weather.get("wind_speed") + "km/h \n");
//
//
//
//            String clothingPlan = generateClothingPlan(minTemp,maxTemp,umbrellaNeeded,sunglassesNeeded);
//            builder.append(clothingPlan);
//        }
//        return builder.toString();
//    }
//
//    public static String generateClothingPlan(double minTemperature, double maxTemperature, boolean umbrellaIsNeeded, boolean sunglassesIsNeeded) {
//
//        if(minTemperature > maxTemperature) {
//            return "Not defined";
//        }
//        StringBuilder plan = new StringBuilder();
//        plan.append("My suggestion:\n" + "Since the lowest temperature during the entire trip will be " + minTemperature
//                + " degrees\nCelsius and the highest " + maxTemperature + " degrees Celsius.\n" + " Put on a ");
//
//        // "Top" clothes
//        if (minTemperature > 15) {
//            if (maxTemperature < 25) {
//                plan.append("T-Shirt ");
//            } else {
//                plan.append("Tank Top ");
//            }
//        } else {
//            if (maxTemperature < 5) {
//                if (minTemperature > -5) {
//                    plan.append("Long Sleeves and Coat ");
//                } else {
//                    plan.append("Long Sleeves and Sweater ");
//                }
//            } else {
//                if (maxTemperature - minTemperature > 10) {
//                    plan.append("Long Sleeves, Jacket ");
//                } else {
//                    plan.append("Long Sleeves ");
//                }
//            }
//        }
//
//        // Additional clothing options based on specific temperature ranges
//        if (maxTemperature > 25) {
//            plan.append("and Shorts.\n");
//        } else if (maxTemperature >= 20 && maxTemperature <= 25) {
//            plan.append("and Light trousers.\n");
//        } else {
//            plan.append("and Jeans.\n");
//        }
//
//        if (umbrellaIsNeeded) {
//            plan.append(" There is a high chance of rain during your trip,\n so take an umbrella or a raincoat.рџЊ§");
//        }
//
//        if (sunglassesIsNeeded) {
//            plan.append("Don't forget to bring your sunglasses, you'll need them.вј");
//        }
//
//        return plan.toString();
//    }
//

    public static String respond(String input) {
        /*
         * Simulation of processing time
         */
        try {
            /*
             * Delay the bot respond for a second after user input display
             */
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
         * return string result of the bot respond
         */
        return "You said: " + input;
    }
}
