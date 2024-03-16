/**
 * @author Ihor Tryndey 3105023 , Oleksii Babii 3104904
 * @since 2024
 * @version 1.0
 */

package griffith;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChefConnect {
    private static final Map<String, List<String>> ingredientMap = new HashMap<>();
    private static final Map<String, Map<String, String>> nutritionalInfo = new HashMap<>();
    
    public static void initializeIngredients() {
        // Initialize ingredients (for demonstration)
        ingredientMap.put("pasta", Arrays.asList("spaghetti", "penne", "linguine"));
        ingredientMap.put("chicken", Arrays.asList("breast", "thigh", "wing"));
        ingredientMap.put("vegetables", Arrays.asList("broccoli", "carrot", "bell pepper"));
    }

    public static void initializeNutritionalInfo() {
        // Initialize nutritional info (for demonstration)
        Map<String, String> pastaNutrition = new HashMap<>();
        pastaNutrition.put("calories", "200");
        pastaNutrition.put("protein", "8g");
        pastaNutrition.put("fat", "1g");
        nutritionalInfo.put("spaghetti", pastaNutrition);

        Map<String, String> chickenNutrition = new HashMap<>();
        chickenNutrition.put("calories", "150");
        chickenNutrition.put("protein", "20g");
        chickenNutrition.put("fat", "5g");
        nutritionalInfo.put("breast", chickenNutrition);

        Map<String, String> broccoliNutrition = new HashMap<>();
        broccoliNutrition.put("calories", "50");
        broccoliNutrition.put("protein", "3g");
        broccoliNutrition.put("fat", "0.5g");
        nutritionalInfo.put("broccoli", broccoliNutrition);
    }

	public static void main(String[] args) {
		initializeIngredients();
        initializeNutritionalInfo();
        
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

}
