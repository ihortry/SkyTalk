package griffith.skytalkpro;

/**
 * 	@author Ihor Tryndey 3105023 and Oleksii Babii 3104904
 *  @version 2.0
 *  @since 2024
 */

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
import java.util.Arrays;
import java.util.List;


public class SkyTalk extends Application {
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
    }

    public static void main(String[] args) {
        launch();
    }

    private void sendMessage(VBox chatPane, TextField inputField) {
        /*
         * Get input from text field
         */

        String userInput = inputField.getText();
        /*
         * Append text area
         */
        addMessage(chatPane, "You", userInput, false);

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
         * Customize the border style for bot and user messages
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