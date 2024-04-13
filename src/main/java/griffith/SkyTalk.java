package griffith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class SkyTalk{

	private static final String BASE_URL = "http://api.weatherapi.com/v1";
	private static final String API_KEY = "7402bc32917148ce907223855241304";
	private static HashMap<String, LocalDate> places = new HashMap<>();
	private static final int MAXPLACES = 5;

	// Default values
	private static double minTemperature = 100;
	private static double maxTemperature = 0;

	// Each weather condition has its own unique code (Multilingual Condition list
	// URL: https://www.weatherapi.com/docs/conditions.json)
	private static int[] rainCodes = new int[] { 1063, 1066, 1069, 1072, 1087, 1114, 1150, 1153, 1171, 1180, 1183, 1186,
			1189, 1192, 1195, 1198, 1201, 1204, 1207, 1240, 1243, 1246, 1249, 1252, 1255, 1264, 1273, 1276 };
	private static int sunCode = 1000;

	private static boolean umbrellaIsNeeded = false;
	private static boolean sunglassesIsNeeded = false;
	private static Scanner scanner = new Scanner(System.in);
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static void main(String[] args) {

		System.out.println("Welcome to SkyTalk Chatbot!\n"
				+ "Enter up to 5 places you plan to visit and dates to plan your clothing\nrequirements.\n"
				+ "(For example: London 25/04/2024, Paris 26/04/2024, Rome 27/04/2024):");
		// Read user input and process accordingly
		
		String input;
	
		while (true) {
			input = scanner.nextLine();
			if (input.equalsIgnoreCase("exit")) {
				System.out.println("Exiting SkyTalk Chatbot. Have a great day!");
				break;
			} else {
				places = takeUserInput(input);
				StringBuilder finalPlaces = new StringBuilder();
				finalPlaces.append("Final Places and dates:\n");
				for (String place : places.keySet()) {
					finalPlaces.append("  " + place + ": " + places.get(place).format(formatter)+"\n");
				}
				
				System.out.println(finalPlaces.toString());
				//System.out.print("\n");
				
				System.out.println("Type \"exit\" or enter new locations to continue\n"
						+ "(For example: London 25/04/2024, Paris 26/04/2024, Rome 27/04/2024):");
				
			}
		}
		
	}
	
	public static HashMap<String, LocalDate> takeUserInput(String input) {
		places = new HashMap<>();
		
		boolean validInput = false;
		
		while (!validInput) {
			
			String[] data = input.split(",");

			for (String placeWithDate : data) {
				String[] placeInfo = placeWithDate.trim().split(" ");
				if (placeInfo.length != 2) {
					System.out.println("Invalid input format. Please enter place and date separated by a space.");
					continue;
				}
				String placeName = placeInfo[0];
				String dateOfVisit = placeInfo[1];
				try {
					LocalDate date = LocalDate.parse(dateOfVisit, formatter);
					places.put(placeName, date);
				} catch (Exception e) {
					System.out.println("Invalid date format for " + placeName + ". Please enter date in format dd/MM/yyyy.");
				}
			}
			StringBuilder placesAndDates = new StringBuilder();
			placesAndDates.append("Places and dates:\n");
			for (String place : places.keySet()) {
				placesAndDates.append(" " + place + ": " + places.get(place).format(formatter) + "\n");
			}
			System.out.println(placesAndDates.toString());
			if (places.size() >= MAXPLACES) {
				System.out.println("Maximum number of places reached.");
				validInput = true;
			} else {
				System.out.println("Do you want to add more places? (yes/no)");
				String moreInput = scanner.nextLine();
				if (!moreInput.equals("yes")) {
					validInput = true;
				} else {
					System.out.println("Enter place you plan to visit and date: ");
					input = scanner.nextLine();
				}
			}
		}
		
		return places;
	}
	public static String generateResponse(HashMap<String, LocalDate> places) {
		return "";
	}
	
	private static void getForecast(String location, LocalDate date) {
		try {
			// Adjust the start date to the current date
			String formattedForecastDate = date.toString();

			// Construct the URL with API key and adjusted date
			String urlStr = BASE_URL + "/forecast.json?key=" + API_KEY + "&q=" + URLEncoder.encode(location, "UTF-8")
					+ "&dt=" + formattedForecastDate;
			//System.out.println(urlStr);
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

			// Check if forecast node exists and is not empty
			JsonNode forecastNode = root.get("forecast");
			if (forecastNode != null && forecastNode.has("forecastday") && forecastNode.get("forecastday").isArray()) {
				// Access forecast data
				JsonNode forecastdayArray = forecastNode.get("forecastday");
				JsonNode firstForecastDay = forecastdayArray.get(0);
				JsonNode dayNode = firstForecastDay.get("day"); // Access the "day" node
				if (dayNode != null) {

					double currentMinTemp = dayNode.get("mintemp_c").asDouble();
					if (currentMinTemp < minTemperature) {
						minTemperature = currentMinTemp;
					}

					double currentmaxTemp = dayNode.get("maxtemp_c").asDouble();
					if (currentmaxTemp > maxTemperature) {
						maxTemperature = currentmaxTemp;
					}

					JsonNode condition = dayNode.get("condition");
					int currectCode = condition.get("code").asInt();

					for (int code : rainCodes) {
						if (currectCode == code) {
							umbrellaIsNeeded = true;
							break;
						}
					}

					if (currectCode == sunCode) {
						sunglassesIsNeeded = true;
					}

					// You can extract more data similarly and structure your return object
				} else {
					System.out.println("No forecast data found for the given date and location.");
				}
			} else {
				System.out.println("No forecast data found for the given date and location.");
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
	
	public static String generateClothingPlan(double minTemperature, double maxTemperature, boolean umbrellaIsNeeded,
			boolean sunglassesIsNeeded) {
		
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
			plan.append(" There is a high chance of rain during your trip,\n so take an umbrella or a raincoat.ðŸŒ§");
		}

		if (sunglassesIsNeeded) {
			plan.append("Don't forget to bring your sunglasses, you'll need them.â˜¼");
		}

		return plan.toString();
	}
		
}
