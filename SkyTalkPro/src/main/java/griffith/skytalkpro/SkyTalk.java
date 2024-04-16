/**
 * 	@author Ihor Tryndey 3105023 and Oleksii Babii 3104904
 *  @version 2.0
 *  @since 2024
 */

package griffith.skytalkpro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SkyTalk.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}