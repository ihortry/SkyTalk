package griffith.skytalkpro;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
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
import java.util.Arrays;
import java.util.List;


public class SkyTalk extends Application {

    static VBox chatPane = new VBox();
    ChatBot chatbot = new ChatBot(chatPane);
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
        ChatBot chatbot = new ChatBot(chatPane);

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
        output("For example: London 25/06/2024, Paris 26/06/2024, Rome 27/06/2024");
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
        chatbot.run();
    }

    public static String input() {
        String temp = new String(lastInput);
        lastInput = null;
        return temp;
    }

    public void output(String message) {
        addMessage(chatPane, "Bot", message, true);
    }

    // Method to add a message to the chat pane
    public  void addMessage(VBox chatPane, String sender, String message, boolean isBot) {
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
    public static void main(String[] args) {
        launch(args);
    }
}

