package griffith.skytalkpro;

//class for the clothing recommendation method
public class ClothingRecommendation {

    public static String generateClothingPlan(double minTemperature, double maxTemperature, boolean umbrellaIsNeeded,
                                              boolean sunglassesIsNeeded) {

        //return error string if minimum temperature is higher than maximum temperature
        if(minTemperature > maxTemperature) {
            return "Not defined";
        }
        //build a string with the minimum and maximum temperatures
        StringBuilder plan = new StringBuilder();
        plan.append("My suggestion:\n" + "Since the lowest temperature during the entire trip will be " + minTemperature
                + " degrees\nCelsius and the highest " + maxTemperature + " degrees Celsius.\n" + " Put on a ");

        // "Top" clothes
        //decides which clothing string to append to the builder based on the temperature
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

        //if it's going to rain, recommend an umbrella
        if (umbrellaIsNeeded) {
            plan.append(" There is a high chance of rain during your trip,\n so take an umbrella or a raincoat.🌧");
        }

        //if cloud cover is low, recommend sunglasses
        if (sunglassesIsNeeded) {
            plan.append("Don't forget to bring your sunglasses, you'll need them.☼");
        }

        return plan.toString();
    }
    
}
