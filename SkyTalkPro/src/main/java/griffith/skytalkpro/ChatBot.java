package griffith.skytalkpro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// Class representing the ChatBot logic
class ChatBot {

    
    


    public String Output(String input) {
        // Simulate processing time
        try {
            Thread.sleep(1000); // Delay for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Simple echo bot for demonstration
        return input;
    }
}