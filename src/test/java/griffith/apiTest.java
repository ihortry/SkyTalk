package griffith;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class apiTest {
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
	
	@Test
	void testSuggestRecipe() {
		assertEquals("Here's a recipe for Beef Wellington.\n"
				+ "Cooking time: 130.0 min\n"
				+ "Ingredients:\n"
				+ " - 1 pound beef tenderloin filet\n"
				+ " - Kosher salt and freshly ground black pepper\n"
				+ " - 2 tablespoons extra virgin olive oil\n"
				+ " - 1 pound mushrooms (see recipe note)\n"
				+ " - 4 thin slices ham (Parma ham if you can get it) or prosciutto\n"
				+ " - 2 tablespoons yellow mustard (we used Coleman's Original English Mustard)\n"
				+ " - 1 (7 to 8 1/2- ounce sheet) puff pastry , thawed (see recipe note)\n"
				+ " - 2 large egg yolks , beaten\n"
				+ "\n"
				+ "Nutritional Information:\n"
				+ " Calories: 187.14 kcal\n"
				+ " Protein: 22.50 g\n"
				+ " Fat: 9.67 g\n"
				+ "\n"
				+ "You can find it here: https://www.simplyrecipes.com/recipes/beef_wellington/"
				+ ""
				, chef.suggestRecipe("beef wellington"));
		
		assertEquals("Here's a recipe for Spaghetti Carbonara.\n"
				+ "Cooking time: 0.0 min\n"
				+ "Ingredients:\n"
				+ " - 50 grams Pecorino Romano (1.8 ounces) grated\n"
				+ " - 1 large egg\n"
				+ " - 2 tablespoons olive oil\n"
				+ " - fresh ground black pepper to taste\n"
				+ " - 170 grams Guanciale (6 ounces) Pancetta or Bacon will also work\n"
				+ " - 170 grams dried pasta (6 ounces) boiled according to package directions\n"
				+ " - 2 slow cooked eggs (optional)\n"
				+ "\n"
				+ "Nutritional Information:\n"
				+ " Calories: 347.97 kcal\n"
				+ " Protein: 14.20 g\n"
				+ " Fat: 21.47 g\n"
				+ "\n"
				+ "You can find it here: http://norecipes.com/blog/spaghetti-carbonara-recipe/"
				+ "", chef.suggestRecipe("Carbonara"));
		
		
		assertEquals("Here's a recipe for Slow-Cooker Bolognese.\n"
				+ "Cooking time: 505.0 min\n"
				+ "Ingredients:\n"
				+ " - 1 lb bulk Italian pork sausage\n"
				+ " - 1 lb lean (at least 80%) ground beef\n"
				+ " - 2 large onions, chopped (2 cups)\n"
				+ " - 2 carrots, finely chopped (1 cup)\n"
				+ " - 3 cloves garlic, finely chopped\n"
				+ " - 2 cans (14.5 oz each) diced tomatoes with basil, garlic and oregano, undrained\n"
				+ " - 1 can (6 oz) Muir Glen™ organic tomato paste\n"
				+ " - 1 tablespoon balsamic vinegar\n"
				+ " - 1 tablespoon packed brown sugar\n"
				+ " - 3/4 teaspoon salt\n"
				+ " - 1/2 teaspoon freshly ground pepper\n"
				+ " - 1/4 teaspoon crushed red pepper flakes\n"
				+ " - 16 oz uncooked spaghetti\n"
				+ " - 3/4 cup shaved Parmesan cheese (3 oz)\n"
				+ " - 1/2 cup chopped fresh basil leaves\n"
				+ "\n"
				+ "Nutritional Information:\n"
				+ " Calories: 166.67 kcal\n"
				+ " Protein: 8.54 g\n"
				+ " Fat: 7.80 g\n"
				+ "\n"
				+ "You can find it here: https://www.bettycrocker.com/recipes/slow-cooker-bolognese/e5e6ccc9-0adf-4635-88ad-6036ac80ec8f"
				+ "", chef.suggestRecipe("pasta bolognese slow cooker"));
	}
	
}
