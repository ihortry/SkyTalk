package griffith;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class SkyTalkTest {
	SkyTalk skyTalk = new SkyTalk();

	@Test
	void testGenerateResponse() {
		HashMap<String, LocalDate> places1 = new HashMap<>();
		places1.put("london", LocalDate.of(2024, 4, 16));
		assertEquals("My suggestion:\n"
				+ "Since the lowest temperature during the entire trip will be 6.4 degrees\n"
				+ "Celsius and the highest 12.5 degrees Celsius.\n"
				+ " Put on a Long Sleeves and Jeans.\n"
				+ "",skyTalk.generateResponse(places1));
		
		HashMap<String, LocalDate> places2 = new HashMap<>();
		places2.put("london", LocalDate.of(2024, 4, 16));
		places2.put("cork", LocalDate.of(2024, 4, 17));
		
		assertEquals("My suggestion:\n"
				+ "Since the lowest temperature during the entire trip will be 3.7 degrees\n"
				+ "Celsius and the highest 12.9 degrees Celsius.\n"
				+ " Put on a Long Sleeves and Jeans.\n"
				+ " There is a high chance of rain during your trip,\n"
				+ " so take an umbrella or a raincoat.ðŸŒ§",skyTalk.generateResponse(places2));
		
		HashMap<String, LocalDate> places3 = new HashMap<>();
		places3.put("London", LocalDate.of(2024, 4, 16));
		places3.put("Cork", LocalDate.of(2024, 4, 17));
		places3.put("Dublin", LocalDate.of(2024, 4, 17));
		places3.put("Kyiv", LocalDate.of(2024, 4, 18));
		
		assertEquals("My suggestion:\n"
				+ "Since the lowest temperature during the entire trip will be 1.0 degrees\n"
				+ "Celsius and the highest 12.9 degrees Celsius.\n"
				+ " Put on a Long Sleeves, Jacket and Jeans.\n"
				+ " There is a high chance of rain during your trip,\n"
				+ " so take an umbrella or a raincoat.ðŸŒ§",skyTalk.generateResponse(places3));
		
		HashMap<String, LocalDate> places4 = new HashMap<>();
		assertEquals("Not defined",skyTalk.generateResponse(places4));
		
		
	}
}
