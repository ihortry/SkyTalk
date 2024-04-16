/**
 * 	@author Ihor Tryndey 3105023 and Oleksii Babii 3104904
 *  @version 2.0
 *  @since 2024
 */

package griffith.skytalkpro;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
    }

    public static void main(String[] args) {
        launch();
    }
}