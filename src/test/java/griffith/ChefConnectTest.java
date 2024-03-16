package griffith;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChefConnectTest {
	ChefConnect chef = new ChefConnect();

	@Test
	void testProvideNutritionalInfo() {
		assertEquals("Nutritional Information for broccoli:\r\n"
				+ "protein: 3g\r\n"
				+ "fat: 0.5g\r\n"
				+ "calories: 50", chef.provideNutritionalInfo("nutritional info broccoli") );
		
		assertEquals("Nutritional Information for breast:\r\n"
				+ "protein: 20g\r\n"
				+ "fat: 5g\r\n"
				+ "calories: 150", chef.provideNutritionalInfo("nutritional info breast"));
		
		assertEquals("Nutritional Information for spaghetti:\r\n"
				+ "protein: 8g\r\n"
				+ "fat: 1g\r\n"
				+ "calories: 200", chef.provideNutritionalInfo("nutritional info spaghetti"));
	}

}
