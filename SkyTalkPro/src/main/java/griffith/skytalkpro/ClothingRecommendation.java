package griffith.skytalkpro;

public class ClothingRecommendation {

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
            plan.append(" There is a high chance of rain during your trip,\n so take an umbrella or a raincoat.🌧");
        }

        if (sunglassesIsNeeded) {
            plan.append("Don't forget to bring your sunglasses, you'll need them.☼");
        }

        return plan.toString();
    }
    
}
