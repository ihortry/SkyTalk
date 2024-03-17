/**
 * @author Ihor Tryndey 3105023 , Oleksii Babii 3104904
 * @since 2024
 * @version 1.0
 */

package griffith;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChefConnect {
	
	private static final String RECIPE_API_URL = "https://api.edamam.com/search";
	private static final String APP_ID = "b9576f7d";
	private static final String APP_KEY = "d7a5d085107bf7dd9377ed2a4146576d";
	
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to Cooking Helper Chatbot! How can I assist you today?");

		while (true) {
			String userInput = scanner.nextLine().toLowerCase();
			if (userInput.equals("exit")) {
				System.out.println("Exiting Cooking Helper Chatbot. Have a great day!");
				break;
			} else {
				String response = generateResponse(userInput);
				System.out.println(response);
			}
		}

		scanner.close();
	}

	public static String generateResponse(String userInput) {
		Pattern recipePattern = Pattern.compile("\\b(recipe|cook|make|prepare)\\b");
		Matcher recipeMatcher = recipePattern.matcher(userInput);

		if (recipeMatcher.find()) {
			System.out.println("Enter the name of the dish: ");
			Scanner input = new Scanner(System.in);
			String dish = input.nextLine();
//			return suggestRecipe(dish);
		}

		Pattern nutritionPattern = Pattern.compile("\\b(nutrition|nutritional info)\\b");
		Matcher nutritionMatcher = nutritionPattern.matcher(userInput);

		if (nutritionMatcher.find()) {
			System.out.println("Enter the name of the dish: ");
			Scanner input = new Scanner(System.in);
			String dish = input.nextLine();
//          provideNutritionalInfo(dish);
			return ""; // Response handled within the method
		}

		return "I'm sorry, I didn't understand your request. Could you please provide more details?";
	}

    public static JsonNode getRootNode(String userInput) {
    	try {
    		// Make HTTP request to the recipe API
            userInput = URLEncoder.encode(userInput, StandardCharsets.UTF_8);
            URL url = new URL(RECIPE_API_URL + "?q=" + userInput + "&app_id=" + APP_ID + "&app_key=" + APP_KEY);
            //System.out.println(url);
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder responseBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                responseBuilder.append(scanner.nextLine());
            }
            scanner.close();

            // Parse JSON response
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(responseBuilder.toString());
    	} catch (Exception e) {
    		e.printStackTrace();
		}
		return null;
    	
    }

}
