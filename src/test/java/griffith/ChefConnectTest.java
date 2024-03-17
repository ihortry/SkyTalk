package griffith;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChefConnectTest {
	ChefConnect chef = new ChefConnect();
	@Test
	void testProvideNutritionalInfo() {
		assertEquals("Nutritional Information:\n"
				+ " Calories: 156.87 kcal\n"
				+ " Protein: 0.09 g\n"
				+ " Fat: 0.08 g\n",chef.provideNutritionalInfo("apple"));
		
		assertEquals("Nutritional Information:\n"
				+ " Calories: 199.21 kcal\n"
				+ " Protein: 17.23 g\n"
				+ " Fat: 12.90 g\n", chef.provideNutritionalInfo("Boiled chicken breast"));
		
		assertEquals("Sorry, I couldn't find any nutritional information for your query.", chef.provideNutritionalInfo(""));
	}
}
