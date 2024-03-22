/**
 * @author Ihor Tryndey 3105023 , Oleksii Babii 3104904
 * @since 2024
 * @version 1.0
 */
package griffith;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;
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
			try {
				return suggestRecipe(dish);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Pattern nutritionPattern = Pattern.compile("\\b(nutrition|nutritional info)\\b");
		Matcher nutritionMatcher = nutritionPattern.matcher(userInput);

		if (nutritionMatcher.find()) {
			System.out.println("Enter the name of the dish: ");
			Scanner input = new Scanner(System.in);
			String dish = input.nextLine();
			System.out.println(provideNutritionalInfo(dish));
			return ""; // Response handled within the method
		}

		return "I'm sorry, I didn't understand your request. Could you please provide more details?";
	}

	public static String suggestRecipe(String userInput) throws IOException {
		JsonNode rootNode = getRootNode(userInput);
		JsonNode hitsNode = rootNode.get("hits");
		if (hitsNode != null && hitsNode.isArray() && hitsNode.size() > 0) {
			// Get the first recipe from the response
			JsonNode recipeNode = hitsNode.get(0).get("recipe");

			JsonNode labelNode = recipeNode.get("label");
			JsonNode urlNode = recipeNode.get("url");
			if (labelNode != null && urlNode != null) {
				String recipeName = labelNode.asText();
				String recipeUrl = urlNode.asText();
				JsonNode ingredientLines = recipeNode.get("ingredientLines");
				String ingredients = "";
				for (int i = 0; i < ingredientLines.size(); i++) {
					ingredients = ingredients + " - " + ingredientLines.get(i).asText() + "\n";
				}
				String cookingTime = recipeNode.get("totalTime").asText();

				return "Here's a recipe for " + recipeName + ".\n" + "Cooking time: " + cookingTime + " min\n"
						+ "Ingredients:\n" + ingredients + "\n" + provideNutritionalInfo(userInput) + "\n"
						+ "You can find it here: " + recipeUrl;
			} else {
				return "Sorry, I couldn't find the necessary information for the recipe.";
			}
		} else {
			return "Sorry, I couldn't find any recipes for your query.";
		}
	}

	public static String provideNutritionalInfo(String userInput) {

		JsonNode rootNode = getRootNode(userInput);
		JsonNode hitsNode = rootNode.get("hits");
		if (hitsNode != null && hitsNode.isArray() && hitsNode.size() > 0) {
			// Get the first recipe from the response
			JsonNode recipeNode = hitsNode.get(0).get("recipe");
			JsonNode labelNode = recipeNode.get("label");
			JsonNode urlNode = recipeNode.get("url");

			if (labelNode != null && urlNode != null) {
				String recipeName = labelNode.asText();
				double hundredGramParts = recipeNode.get("totalWeight").asDouble() / 100.0;
				JsonNode caloriesNode = recipeNode.get("calories");
				String calories = String.format("%.02f", caloriesNode.asDouble() / hundredGramParts);
				JsonNode totalNutrientsNode = recipeNode.get("totalNutrients");
				JsonNode fatQuantity = totalNutrientsNode.get("FAT").get("quantity");

				// String fatQuantity = fatNode.get("quantity").asText();
				String fat = String.format("%.02f", fatQuantity.asDouble() / hundredGramParts);
				JsonNode proteinQuantity = totalNutrientsNode.get("PROCNT").get("quantity");
				String protein = String.format("%.02f", proteinQuantity.asDouble() / hundredGramParts);

				return "Nutritional Information:\n" + " Calories: " + calories + " kcal\n" + " Protein: " + protein
						+ " g\n" + " Fat: " + fat + " g\n";
			} else {
				return "Sorry, I couldn't find the necessary nutritional information.";
			}

		} else {
			return "Sorry, I couldn't find any nutritional information for your query.";
		}

	}

	public static JsonNode getRootNode(String userInput) {
		try {
			// Make HTTP request to the recipe API
			userInput = URLEncoder.encode(userInput, StandardCharsets.UTF_8);
			URL url = new URL(RECIPE_API_URL + "?q=" + userInput + "&app_id=" + APP_ID + "&app_key=" + APP_KEY);
			// System.out.println(url);
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
