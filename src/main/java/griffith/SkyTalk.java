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


public class SkyTalk{

	private static final String BASE_URL = "http://api.weatherapi.com/v1";
	private static final String API_KEY = "7402bc32917148ce907223855241304";
	private static HashMap<String, LocalDate> places = new HashMap<>();
	private static final int MAXPLACES = 5;
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
			
			} catch (IOException e) {
				e.printStackTrace();
		}
			
	}
		
}
