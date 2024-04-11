package griffith;

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
		
		return new HashMap<>();
	}

}
