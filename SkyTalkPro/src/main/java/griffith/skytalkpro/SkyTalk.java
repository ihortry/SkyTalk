package griffith.skytalkpro;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import org.json.simple.parser.ParseException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



public class SkyTalk extends Application {
    //private static final String BASE_URL = "http://api.weatherapi.com/v1";
    //private static final String API_KEY = "7402bc32917148ce907223855241304";
    private static HashMap<String, LocalDate> places = new HashMap<>();
    private static final int MAXPLACES = 5;

    // Default values
    private static double minTemperature = 100;
    private static double maxTemperature = 0;

    // Each weather condition has its own unique code (Multilingual Condition list
    // URL: https://www.weatherapi.com/docs/conditions.json)
    //private static int[] rainCodes = new int[] { 1063, 1066, 1069, 1072, 1087, 1114, 1150, 1153, 1171, 1180, 1183, 1186,
    //1189, 1192, 1195, 1198, 1201, 1204, 1207, 1240, 1243, 1246, 1249, 1252, 1255, 1264, 1273, 1276 };
   // private static int sunCode = 1000;

    private static boolean umbrellaIsNeeded = false;
    private static boolean sunglassesIsNeeded = false;
    //private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    private static Weather weather;
    static VBox chatPane = new VBox();
    static ClothingRecommendation clothingRecommendation;

    static String lastInput;

    TextField inputField = new TextField();

    // Predefined options for the user to choose from
    private List<String> predefinedOptions = Arrays.asList(
            "Option 1",
            "Option 2",
            "Option 3"
    );

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();

        // Container for displaying chat messages
        //VBox chatPane = new VBox();
        chatPane.setStyle("-fx-background-color: #f0f0f0;");
        chatPane.setPadding(new Insets(10));
        ScrollPane scrollPane = new ScrollPane(chatPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background:transparent;");

        // Bind the scroll position to the bottom
        scrollPane.vvalueProperty().bind(chatPane.heightProperty());

        // Input box for typing messages
        HBox inputBox = new HBox(5);
        inputBox.setPadding(new Insets(10));
        inputBox.setAlignment(Pos.CENTER);

        // Text field for user input
        //TextField inputField = new TextField();
        inputField.setPromptText("Type here...");
        inputField.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12pt; -fx-background-color: #ffffff; -fx-background-radius: 10;");
        //inputField.setOnAction(event -> sendMessage(chatPane, inputField));
        inputField.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                handleInput(inputField);
                try {
                    sendMessage(chatPane, inputField);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (java.text.ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });


        // Button for sending messages
        Button sendButton = new Button("Send");
        sendButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12pt; -fx-background-color: linear-gradient(to bottom, #4CAF50, #45a049); -fx-text-fill: white; -fx-background-radius: 10;");
        sendButton.setOnMouseEntered(e -> sendButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12pt; -fx-background-color: linear-gradient(to bottom, #45a049, #4CAF50); -fx-text-fill: white; -fx-background-radius: 10;"));
        sendButton.setOnMouseExited(e -> sendButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12pt; -fx-background-color: linear-gradient(to bottom, #4CAF50, #45a049); -fx-text-fill: white; -fx-background-radius: 10;"));
        //sendButton.setOnAction(event -> sendMessage(chatPane, inputField));
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                handleInput(inputField);
                try {
                    sendMessage(chatPane, inputField);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (java.text.ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });


        VBox optionsBox = new VBox(5);
        optionsBox.setAlignment(Pos.CENTER);


        Insets buttonMargin = new Insets(5);
        for (String option : predefinedOptions) {
            Button optionButton = new Button(option);
            optionButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12pt; -fx-background-color: linear-gradient(to bottom, #4CAF50, #45a049); -fx-text-fill: white; -fx-background-radius: 10;");
            optionButton.setOnMouseEntered(e -> optionButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12pt; -fx-background-color: linear-gradient(to bottom, #45a049, #4CAF50); -fx-text-fill: white; -fx-background-radius: 10;"));
            optionButton.setOnMouseExited(e -> optionButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12pt; -fx-background-color: linear-gradient(to bottom, #4CAF50, #45a049); -fx-text-fill: white; -fx-background-radius: 10;"));
            optionButton.setOnAction(event -> {
                inputField.setText(option);
                try {
                    sendMessage(chatPane, inputField);
                } catch (ParseException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (java.text.ParseException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                String input;
            });


            // Add margin to the option button
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
        primaryStage.setTitle("Impressive ChatBot");
        primaryStage.setScene(scene);
        primaryStage.show();

        output("Welcome to SkyTalk Chatbot!");
        output("Enter up to 5 places you plan to visit and dates to plan your clothing requirements.");
        output("For example: London 25/04/2024, Paris 26/04/2024, Rome 27/04/2024");
    }

    public void handleInput(TextField inputField){
        lastInput = inputField.getText();
        System.out.println("Handle " + lastInput);
    }



    // Method to send a message
    private void sendMessage(VBox chatPane, TextField inputField) throws ParseException, java.text.ParseException {
        String userInput = inputField.getText();
        System.out.println(lastInput);
        addMessage(chatPane, "You", userInput, false);
        //lastInput = userInput;

        // Add a delay before the bot responds
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
        pauseTransition.play();

        inputField.clear();

        String input;

        while (true) {
            input = input();
            if (input.equalsIgnoreCase("exit")) {
                output("Exiting SkyTalk Chatbot. Have a great day!");
                Platform.exit(); // Terminate the JavaFX application
                break;
            } else {
                places = takeUserInput(input);
                StringBuilder finalPlaces = new StringBuilder();
                finalPlaces.append("Final Places and dates:\n");
                for (String place : places.keySet()) {
                    finalPlaces.append("  " + place + ": " + places.get(place).format(formatter)+"\n");
                }

                output(finalPlaces.toString());
                //System.out.print("\n");

                output(generateResponse(places));

                output("Type \"exit\" or enter new locations to continue\n"
                        + "(For example: London 25/04/2024, Paris 26/04/2024, Rome 27/04/2024):");

            }
        }
    }

    public static String input() {
        String temp = new String(lastInput);
        lastInput = null;
        return temp;

    }

    public static void output(String message) {
        addMessage(chatPane, "Bot", message, true);
    }

    // Method to add a message to the chat pane
    private static void addMessage(VBox chatPane, String sender, String message, boolean isBot) {
        StackPane messageContainer = new StackPane();
        messageContainer.setPadding(new Insets(5));
        messageContainer.setMaxWidth(300);

        HBox messageBox = new HBox(5);
        messageBox.setMaxWidth(300);
        messageBox.setSpacing(10);
        messageBox.setPadding(new Insets(5));
        messageBox.setAlignment(Pos.CENTER);

        // Customize the border style for bot and user messages
        String borderColor = isBot ? "#0099FF" : "#4CAF50";
        String backgroundColor = isBot ? "#B3E5FC" : "#C8E6C9";
        String textColor = isBot ? "#000000" : "#000000";

        messageBox.setStyle("-fx-background-color: " + backgroundColor + "; -fx-border-radius: 10; -fx-padding: 5px; -fx-border-color: " + borderColor + "; -fx-border-width: 2px;");

        ImageView imageView = new ImageView(new Image(SkyTalk.class.getResourceAsStream(isBot ? "robot.png" : "user.png")));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);

        TextArea messageText = new TextArea(message);
        messageText.setFont(Font.font("Arial", 12));
        messageText.setEditable(false);
        messageText.setWrapText(true);
        messageText.setMaxWidth(250);
        messageText.setMinHeight((1 + message.length()/30)*20);
       messageText.setMaxHeight(600);
        messageText.setStyle("-fx-text-fill: " + textColor + ";");

        messageBox.getChildren().addAll(isBot ? imageView : messageText, isBot ? messageText : imageView); // Swap positions of imageView and messageText

        messageContainer.getChildren().add(messageBox);
        chatPane.getChildren().add(messageContainer);

        // Align messageContainer to the appropriate side
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

    public static HashMap<String, LocalDate> takeUserInput(String input) {
        places = new HashMap<>();

        boolean validInput = false;

        while (!validInput) {

            String[] data = input.split(",");

            for (String placeWithDate : data) {
                String[] placeInfo = placeWithDate.trim().split(" ");
                //if placeInfo isnt an array size two [place, date]
                if (placeInfo.length != 2) {
                    output("Invalid input format. Please enter place and date separated by a space.");
                    continue;
                }
                String placeName = placeInfo[0];
                String dateOfVisit = placeInfo[1];
                try {
                    LocalDate date = LocalDate.parse(dateOfVisit, formatter);
                    places.put(placeName, date);
                } catch (Exception e) {
                    output("Invalid date format for " + placeName + ". Please enter date in format dd/MM/yyyy.");
                }
            }
            StringBuilder placesAndDates = new StringBuilder();
            placesAndDates.append("Places and dates:\n");
            for (String place : places.keySet()) {
                placesAndDates.append(" " + place + ": " + places.get(place).format(formatter) + "\n");
            }
            output(placesAndDates.toString());
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

    public static String generateResponse(HashMap<String, LocalDate> places) throws ParseException, java.text.ParseException {
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

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}

