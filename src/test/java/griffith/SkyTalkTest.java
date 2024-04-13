package griffith;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class SkyTalkTest {
	SkyTalk skyTalk = new SkyTalk();

	@Test
	void testGenerateClothingPlan() {
		assertEquals("My suggestion:\n"
				+ "Since the lowest temperature during the entire trip will be 5.0 degrees\n"
				+ "Celsius and the highest 10.0 degrees Celsius.\n"
				+ " Put on a Long Sleeves and Jeans.\n"
				+ " There is a high chance of rain during your trip,\n"
				+ " so take an umbrella or a raincoat.🌧Don't forget to bring your sunglasses, you'll need them.☼", skyTalk.generateClothingPlan(5, 10, true, true));
		
		assertEquals("My suggestion:\n"
				+ "Since the lowest temperature during the entire trip will be 22.0 degrees\n"
				+ "Celsius and the highest 32.0 degrees Celsius.\n"
				+ " Put on a Tank Top and Shorts.\n"
				+ "Don't forget to bring your sunglasses, you'll need them.☼", skyTalk.generateClothingPlan(22, 32, false, true));
		
		assertEquals("My suggestion:\n"
				+ "Since the lowest temperature during the entire trip will be 10.0 degrees\n"
				+ "Celsius and the highest 14.0 degrees Celsius.\n"
				+ " Put on a Long Sleeves and Jeans.\n"
				+ "",skyTalk.generateClothingPlan(10, 14, false, false));
		
		assertEquals("My suggestion:\n"
				+ "Since the lowest temperature during the entire trip will be 10.0 degrees\n"
				+ "Celsius and the highest 20.0 degrees Celsius.\n"
				+ " Put on a Long Sleeves and Light trousers.\n"
				+ " There is a high chance of rain during your trip,\n"
				+ " so take an umbrella or a raincoat.🌧", skyTalk.generateClothingPlan(10, 20, true, false));
		
		assertEquals("Not defined", skyTalk.generateClothingPlan(20, 1, true, false));
	}

}
