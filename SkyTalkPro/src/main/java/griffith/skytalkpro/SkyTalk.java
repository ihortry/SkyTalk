package griffith.skytalkpro;

/**
 * 	@author Ihor Tryndey 3105023 and Oleksii Babii 3104904
 *  @version 2.0
 *  @since 2024
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class SkyTalk extends Application {

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
    /*
     *  Declaration of chatbot object.
     */
    private ChatBot chatBot;

    /*
     *  Common options to ask the bot about weather
     */
    private List<String> predefinedOptions = Arrays.asList(
            "Option 1",
            "Option 2",
            "Option 3");



    /**
     * Method inside which occur creation of Chatbot instance
     * and addition all elements of window application
     */
    /**
     * Method inside which occur creation of Chatbot instance
     * and addition all elements of window application
     */
    @Override
    public void start(Stage primaryStage) {
        /*
         * Creation of instance ChatBot
         */
        chatBot = new ChatBot();

        /**
         * Creation of root layout
         */
        BorderPane root = new BorderPane();

        /*
         * Main taxt area inside which displays
         */
        VBox chatPane = new VBox();

        /**
         * VBox style setup
         */
        chatPane.setStyle("-fx-background-color: #f0f0f0;");
        chatPane.setPadding(new Insets(10));

        /*
         * ScrollPane addition to the main text area and style setup
         */
        ScrollPane scrollPane = new ScrollPane(chatPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background:transparent;");

        /*
         * Bind the scroll position to the bottom
         */
        scrollPane.vvalueProperty().bind(chatPane.heightProperty());

        /*
         * Text field for typing messages and its style setup
         */
        HBox inputBox = new HBox(5);
        inputBox.setPadding(new Insets(10));
        inputBox.setAlignment(Pos.CENTER);

        /*
         * Text field for user input and its style setup
         */
        TextField inputField = new TextField();
        inputField.setPromptText("Type here...");
        inputField.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12pt; -fx-background-color: #ffffff; -fx-background-radius: 10;");
        /*
         * Event listener for inputField(user messages)
         */
        inputField.setOnAction(event -> sendMessage(chatPane, inputField));

        /*
         * Button for sending user messages
         */
        Button sendButton = new Button("Send");
        /*
         * Style button
         */
        sendButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12pt; -fx-background-color: linear-gradient(to bottom, #4CAF50, #45a049); -fx-text-fill: white; -fx-background-radius: 10;");
        sendButton.setOnMouseEntered(e -> sendButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12pt; -fx-background-color: linear-gradient(to bottom, #45a049, #4CAF50); -fx-text-fill: white; -fx-background-radius: 10;"));
        sendButton.setOnMouseExited(e -> sendButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12pt; -fx-background-color: linear-gradient(to bottom, #4CAF50, #45a049); -fx-text-fill: white; -fx-background-radius: 10;"));
        sendButton.setOnAction(event -> sendMessage(chatPane, inputField));

        /*
         *  Common options to ask the bot about weather
         *  Creation five of them and position center
         */
        VBox optionsBox = new VBox(5);
        optionsBox.setAlignment(Pos.CENTER);

        /*
         * Margin addition to the buttons with options
         */
        Insets buttonMargin = new Insets(5);
        for (String option : predefinedOptions) {
            Button optionButton = new Button(option);
            optionButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12pt; -fx-background-color: linear-gradient(to bottom, #4CAF50, #45a049); -fx-text-fill: white; -fx-background-radius: 10;");
            optionButton.setOnMouseEntered(e -> optionButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12pt; -fx-background-color: linear-gradient(to bottom, #45a049, #4CAF50); -fx-text-fill: white; -fx-background-radius: 10;"));
            optionButton.setOnMouseExited(e -> optionButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12pt; -fx-background-color: linear-gradient(to bottom, #4CAF50, #45a049); -fx-text-fill: white; -fx-background-radius: 10;"));
            optionButton.setOnAction(event -> {
                inputField.setText(option);
                sendMessage(chatPane, inputField);
            });

            /*
             * Add margin to the option button
             */
            VBox.setMargin(optionButton, buttonMargin);
            optionsBox.getChildren().add(optionButton);



        }

        // Add input field and send button to the input box
        inputBox.getChildren().addAll(inputField, sendButton);
        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);

        // Set the layout components to the root layout
        root.setCenter(scrollPane);
        root.setBottom(inputBox);
        root.setRight(optionsBox);

        // Create the scene and set it to the stage
        Scene scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setTitle("SkyTalk");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Add welcome message for the robot
        String welcomeMessage = "Welcome to SkyTalk Chatbot!\n";
        addMessage(chatPane, "Bot", welcomeMessage, true);
        String instruction = "Enter up to 5 places you plan to visit and dates to plan your clothing\nrequirements.";
        addMessage(chatPane, "Bot", instruction, true);
        String example = "For example: London 25/04/2024, Paris 26/04/2024, Rome 27/04/2024:";
        addMessage(chatPane, "Bot", example, true);
    }

    public static void main(String[] args) {
        launch();
        //addMessage(chatPane, "You", , false);
    }

    public String input(String userInput) {
        System.out.println(userInput);
        return userInput;

    }

    private void sendMessage(VBox chatPane, TextField inputField) {
        /*
         * Get input from text field
         */

        String userInput = inputField.getText();
        addMessage(chatPane, "You", userInput, false);
        takeUserInput(userInput, chatPane, inputField);
        while (true) {

            if (userInput.equalsIgnoreCase("exit")) {

                addMessage(chatPane, "Bot", ChatBot.respond("Exiting SkyTalk Chatbot. Have a great day!"), true);
                break;
            } else {
                places = takeUserInput(userInput, chatPane, inputField);
                StringBuilder finalPlaces = new StringBuilder();
                finalPlaces.append("Final Places and dates:\n");
                for (String place : places.keySet()) {
                    finalPlaces.append("  " + place + ": " + places.get(place).format(formatter) + "\n");
                }


                addMessage(chatPane, "Bot", ChatBot.respond(finalPlaces.toString()), true);
                // System.out.print("\n");


                try {
                    addMessage(chatPane, "Bot", ChatBot.respond(generateResponse(places)) , true);
                } catch (IOException | ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                addMessage(chatPane, "Bot", ChatBot.respond("Type \"exit\" or enter new locations to continue\n"
                        + "(For example: London 25/04/2024, Paris 26/04/2024, Rome 27/04/2024):"), true);

            }
        }
        /*
         *  Add a delay before the bot responds
         */

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));

        /*
         * Addition of action listener for pause transitions
         */
        pauseTransition.setOnFinished(event -> {
            String response = chatBot.respond(userInput);
            addMessage(chatPane, "Bot", response, true);
        });
        pauseTransition.play();

        inputField.clear();
    }


    /*
     * 
     */


    public HashMap<String, ZonedDateTime> takeUserInput(String input, VBox chatPane, TextField inputField) {
        places = new HashMap<>();

        boolean validInput = false;

        while (!validInput) {

            String[] data = input.split(",");

            for (String placeWithDate : data) {
                String[] placeInfo = placeWithDate.trim().split(" ");
                if (placeInfo.length != 2) {
                    addMessage(chatPane, "Bot",
                            ChatBot.respond("Invalid input format. Please enter place and date separated by a space."),
                            true);
                    continue;
                }
                String placeName = placeInfo[0];
                String dateOfVisit = placeInfo[1];
                try {
                    // get the coordinates of the location given in order to find its timezone
                    double[] placeCoordinates = cityToCoordinate(placeName);
                    // get the timezone name of the location
                    LocalDate date = LocalDate.parse(dateOfVisit, formatter);
                    ZonedDateTime zonedDate = date.atStartOfDay(ZoneId.of(timezoneFromCoordinate(placeCoordinates)));
                    // ZoneId zoneId = ZoneId.of("UTC");
                    // long epoch = date.atStartOfDay(zoneId).toEpochSecond();

                    places.put(placeName, zonedDate);
                } catch (Exception e) {
                    addMessage(chatPane, "Bot", ChatBot.respond(
                            "Invalid date format for " + placeName + ". Please enter date in format dd/MM/yyyy."),
                            true);

                }
            }

            // TODO

            StringBuilder placesAndDates = new StringBuilder();
            placesAndDates.append("Places and dates:\n");
            for (String place : places.keySet()) {
                placesAndDates.append(" " + place + ": " + places.get(place).format(formatter) + "\n");
            }

            addMessage(chatPane, "Bot", ChatBot.respond(placesAndDates.toString()), true);
            if (places.size() >= MAXPLACES) {
                addMessage(chatPane, "Bot", ChatBot.respond("Maximum number of places reached."), true);
                validInput = true;
            } else {

                addMessage(chatPane, "Bot", ChatBot.respond("Do you want to add more places? (yes/no)"), true);
                String moreInput = gui.input();
                if (!moreInput.equals("yes")) {
                    validInput = true;
                } else {
                    addMessage(chatPane, "Bot", ChatBot.respond("Enter place you plan to visit and date: "), true);
                    input = gui.input();
                }
            }
        }

        return places;
    }

    public static String timezoneFromCoordinate(double[] coordinates) throws IOException {
        String urlStr = TIMEZONE_API_BASE_URL + "?latitude=" + coordinates[0] + "&longitude=" + coordinates[1];
        URL url = new URL(urlStr);
        // Make API call with adjusted date
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Parse JSON response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.toString());


// API call format
        /*
         * {
         * "latitude": 47.5162,
         * "longitude": 14.5501,
         * "location": "Austria",
         * "country_iso": "AT",
         * "iana_timezone": "Europe/Vienna",
         * "timezone_abbreviation": "CET",
         * "dst_abbreviation": "CEST",
         * "offset": "UTC+1",
         * "dst_offset": "UTC+2",
         * "current_local_datetime": "2023-09-19T18:06:11.57",
         * "current_utc_datetime": "2023-09-19T16:06:11.570Z"
         * }
         */

        return root.get("iana_timezone").asText();
    }

    public static double[] cityToCoordinate(String city) throws IOException, ParseException {
        System.out.println("Getting coordinates for " + city + "...");
        String urlStr = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=5&appid=" + WEATHER_API_KEY;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        JSONParser parser = new JSONParser();
        JSONArray cities = (JSONArray) parser.parse(new InputStreamReader(conn.getInputStream()));

        // Extract coordinates
        double[] coord = null;
        if (!cities.isEmpty()) {
            JSONArray coordinates = (JSONArray) ((JSONArray) ((JSONArray) cities.get(0)).get(0)).get(0);
            coord = new double[]{
                    Double.parseDouble(coordinates.get(0).toString()),
                    Double.parseDouble(coordinates.get(1).toString())
            };
        }

        conn.disconnect();
        return coord;
    }


    // getForecast() returns the weather conditions for a given coordinate and date
    public static HashMap<String, Double> getForecast(double[] coordinate, String date) {
        String urlStr = "https://api.openweathermap.org/data/3.0/onecall/day_summary?lat=" + coordinate[0] +
                "&lon=" + coordinate[1] + "&units=metric&date=" + date + "&appid=" + WEATHER_API_KEY;
        JSONObject response = new JSONObject();
        response.put("response",call(urlStr));
/* format:
 *
{
"lat":33,
"lon":35,
"tz":"+02:00",
"date":"2020-03-04",
"units":"standard",
"cloud_cover":{
	"afternoon":0
},
"humidity":{
	"afternoon":33
},
"precipitation":{
	"total":0
},
"temperature":{
	"min":286.48,
	"max":299.24,
	"afternoon":296.15,
	"night":289.56,
	"evening":295.93,
	"morning":287.59
},
"pressure":{
	"afternoon":1015
},
"wind":{
	"max":{
		"speed":8.7,
		"direction":120
	}
}
}

 */
        HashMap<String, Double> data = new HashMap<>();
        data.put("min_temp",(Double)
        (
            (JSONObject)response.get("temperature")
        ).get("min"));


        data.put("max_temp",(Double)
        (
            (JSONObject)response.get("temperature")
        ).get("max"));

        data.put("precipitation",(Double)(
            (JSONObject)response.get("precipitation")
        ).get("total"));

        data.put("cloud_cover", (Double)(
            (JSONObject)response.get("cloud_cover")
        ).get("afternoon"));
        return data;
    }

    public static String generateResponse(HashMap<String, ZonedDateTime> places) throws IOException, ParseException {
        StringBuilder builder = new StringBuilder();
        // for each city in the hashmap, get its coordinates
        for (String city : places.keySet()) {
            double[] coordinates = cityToCoordinate(city);
            ZonedDateTime time = places.get(city);
            //long timeUnix = time.toInstant().atZone(ZoneId.of(timezoneFromCoordinate(coordinates))).toEpochSecond();

            // Get year, month, and day
            int year = time.getYear();
            int month = time.getMonthValue();
            int day = time.getDayOfMonth();

            // Format them into year-month-day format
            String formattedDate = String.format("%04d-%02d-%02d", year, month, day);


            HashMap<String, Double> weather = getForecast(coordinates, formattedDate);

            double minTemp = weather.get("min_temp");
            double maxTemp = weather.get("max_temp");

            //if rainfall above 5cm, recommend umbrella
            //if cloud cover below 10%, recommend sunglasses
            boolean umbrellaNeeded = weather.get("precipitation") > 5;
            boolean sunglassesNeeded = weather.get("cloud_cover")<10;

            builder.append("temperature for " + city + " at " + time + ": \n");
            builder.append("minimum temperature: " + weather.get("min_temp") + "C | ");
            builder.append("maximum temperature: " + weather.get("max_temp") + "C \n");

            builder.append("cloud cover: " + weather.get("cloud_cover") + "%\n");
            builder.append("precipitation: " + weather.get("precipitation") + "cm\n");



            String clothingPlan = generateClothingPlan(minTemp,maxTemp,umbrellaNeeded,sunglassesNeeded);
            builder.append(clothingPlan);
        }
        return builder.toString();
    }

    public static String generateClothingPlan(double minTemperature, double maxTemperature, boolean umbrellaIsNeeded, boolean sunglassesIsNeeded) {

        if(minTemperature > maxTemperature) {
            return "Not defined";
        }
        StringBuilder plan = new StringBuilder();
        plan.append("My suggestion:\n" + "Since the lowest temperature during the entire trip will be " + minTemperature
                + " degrees\nCelsius and the highest " + maxTemperature + " degrees Celsius.\n" + " Put on a ");

// "Top" clothes
        if (minTemperature > 15) {
            if (maxTemperature < 25) {
                plan.append("T-Shirt ");
            } else {
                plan.append("Tank Top ");
            }
        } else {
            if (maxTemperature < 5) {
                if (minTemperature > -5) {
                    plan.append("Long Sleeves and Coat ");
                } else {
                    plan.append("Long Sleeves and Sweater ");
                }
            } else {
                if (maxTemperature - minTemperature > 10) {
                    plan.append("Long Sleeves, Jacket ");
                } else {
                    plan.append("Long Sleeves ");
                }
            }
        }

// Additional clothing options based on specific temperature ranges
        if (maxTemperature > 25) {
            plan.append("and Shorts.\n");
        } else if (maxTemperature >= 20 && maxTemperature <= 25) {
            plan.append("and Light trousers.\n");
        } else {
            plan.append("and Jeans.\n");
        }

        if (umbrellaIsNeeded) {
            plan.append(" There is a high chance of rain during your trip,\n so take an umbrella or a raincoat.рџЊ§");
        }

        if (sunglassesIsNeeded) {
            plan.append("Don't forget to bring your sunglasses, you'll need them.вј");
        }

        return plan.toString();
    }


    public void output(VBox chatPane,String str) {
        addMessage(chatPane, "Bot", str, false);
    }

    /**
     *	Method that adds a message to the chat pane
     * @param chatPane
     * @param sender
     * @param message
     * @param isBot
     */
    private void addMessage(VBox chatPane, String sender, String message, boolean isBot) {
        /*
         * Creation of StackPane for messages
         * and style it
         */
        StackPane messageContainer = new StackPane();
        messageContainer.setPadding(new Insets(5));
        messageContainer.setMaxWidth(300);

        /*
         * Creation of messageBox
         * and style it
         */
        HBox messageBox = new HBox(5);
        messageBox.setMaxWidth(300);
        messageBox.setSpacing(10);
        messageBox.setPadding(new Insets(5));
        messageBox.setAlignment(Pos.CENTER);

        /*
         * Customise the border style for bot and user messages
         */
        String borderColor = isBot ? "#0099FF" : "#4CAF50";
        String backgroundColor = isBot ? "#B3E5FC" : "#C8E6C9";
        String textColor = isBot ? "#000000" : "#000000";

        messageBox.setStyle("-fx-background-color: " + backgroundColor + "; -fx-border-radius: 10; -fx-padding: 5px; -fx-border-color: " + borderColor + "; -fx-border-width: 2px;");

        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(isBot ? "robot.png" : "user.png")));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);

        /*
         * Creation of textArea for message
         */
        TextArea messageText = new TextArea(message);
        messageText.setFont(Font.font("Arial", 12));
        /*
         * Make messageText not editable
         */
        messageText.setEditable(false);
        messageText.setWrapText(true);
        messageText.setMaxWidth(220);
        /*
         * Fix size for messageText
         */
        messageText.setMinHeight(60);
        messageText.setMaxHeight(60);
        messageText.setStyle("-fx-text-fill: " + textColor + ";");
        /*
         *  Swap positions of imageView and messageText
         */
        messageBox.getChildren().addAll(isBot ? imageView : messageText, isBot ? messageText : imageView);

        messageContainer.getChildren().add(messageBox);
        chatPane.getChildren().add(messageContainer);

        /*
         * positioned messageContainer
         */
        messageContainer.setAlignment(isBot ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);

        // Animation
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), messageContainer);

        translateTransition.setFromX(isBot ? -400 : 400);
        translateTransition.setToX(0);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), messageContainer);

        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        translateTransition.play();
        fadeTransition.play();
    }
}