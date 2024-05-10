package griffith.skytalkpro;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.HashMap;

import org.junit.Test;

public class SkyTalkTest {
    // initialise new instance of Weather for testing weather-related functions,
    // ClothingRecommendation for clothing recommendation methods
    //and Skytalk for Skytalk methods
    static Weather weather = new Weather();
    static ClothingRecommendation clothingRecommendation = new ClothingRecommendation();
    static SkyTalk skytalk;
    // example cities
    static double[] dublinCoordinates = { 53.33306, -6.24889 };
    static double[] addisAbabaCoordinates = { 9.005401, 38.763611 };
    static double[] tokyoCoordinates = { 35.652832, 139.839478 };
    static double[] seattleCoordinates = { 47.60383, -122.33006 };

    // test timezoneFromCoordinate()
    @Test
    public void testTimeZoneFromCoordinate() {
        try {
            String dublinTimezone = "Europe/Dublin";
            String addisAbabaTimezone = "Africa/Addis_Ababa";
            String tokyoTimezone = "Asia/Tokyo";
            String seattleTimezone = "America/Los_Angeles";

            // test Dublin
            String timezone = weather.timezoneFromCoordinate(dublinCoordinates);
            System.out.println(timezone);
            assertEquals(dublinTimezone, timezone);

            // test Addis Ababa
            timezone = weather.timezoneFromCoordinate(addisAbabaCoordinates);
            System.out.println(timezone);
            assertEquals(addisAbabaTimezone, timezone);

            // test Tokyo
            timezone = weather.timezoneFromCoordinate(tokyoCoordinates);
            System.out.println(timezone);
            assertEquals(tokyoTimezone, timezone);

            // test Seattle
            timezone = weather.timezoneFromCoordinate(seattleCoordinates);
            System.out.println(timezone);
            assertEquals(seattleTimezone, timezone);

        } catch (Exception e) {
            fail("Something went wrong!");
        }

    }

    // test cityToCoordinate()
    @Test
    public void testCityToCoordinate() {
        // set delta to 0.1 to account for tiny differences in coordinates for the same
        // city/location,
        // that may occur due to the website i used for the expected coordinates picking
        // a slightly different spot on the map
        double delta = 0.1;
        try {
            // test Dublin
            double[] coordinates = weather.cityToCoordinate("Dublin");
            System.out.println(coordinates[0] + ", " + coordinates[1]);

            // testing latitude
            assertEquals(dublinCoordinates[0], coordinates[0], delta);
            // testing longitude
            assertEquals(dublinCoordinates[1], coordinates[1], delta);

            // test Addis Ababa
            coordinates = weather.cityToCoordinate("Addis Ababa");
            System.out.println(coordinates[0] + ", " + coordinates[1]);

            // testing latitude
            assertEquals(addisAbabaCoordinates[0], coordinates[0], delta);
            // testing longitude
            assertEquals(addisAbabaCoordinates[1], coordinates[1], delta);

            // test Tokyo
            coordinates = weather.cityToCoordinate("Tokyo");
            System.out.println(coordinates[0] + ", " + coordinates[1]);

            // testing latitude
            assertEquals(tokyoCoordinates[0], coordinates[0], delta);
            // testing longitude
            assertEquals(tokyoCoordinates[1], coordinates[1], delta);

            // test Seattle
            coordinates = weather.cityToCoordinate("Seattle");
            System.out.println(coordinates[0] + ", " + coordinates[1]);

            // testing latitude
            assertEquals(seattleCoordinates[0], coordinates[0], delta);
            // testing longitude
            assertEquals(seattleCoordinates[1], coordinates[1], delta);

        } catch (Exception e) {
            fail("Something went wrong!");
        }
    }

    // test getForecast()
    @Test
    public void testGetForecast() {
        try {
            String date = "2024-06-01";
            HashMap<String, Double> forecast;
            double[] dublinExpectedForecast = {0.15,15.29,55.86,7.03,17.85};
            double[] addisAbabaExpectedForecast = {20.05,13.9,24.56,2.58,27.15};
            double[] tokyoExpectedForecast = {0.75,18.3,94.09,3.28,23.62};
            double[] seattleExpectedForecast = {0.93,9.75,68.92,4.99,19.38};

            double delta = 0.01;

            // test Dublin
            forecast = weather.getForecast(dublinCoordinates, date);
            int i = 0;
            for (HashMap.Entry<String, Double> entry : forecast.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                System.out.println("Key: " + key + ", Value: " + value);
                assertEquals(dublinExpectedForecast[i],value,delta);
                i++;
            }

            // test Addis Ababa
            forecast = weather.getForecast(addisAbabaCoordinates, date);
            i = 0;
            for (HashMap.Entry<String, Double> entry : forecast.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                System.out.println("Key: " + key + ", Value: " + value);
                assertEquals(addisAbabaExpectedForecast[i],value,delta);
                i++;
            }
            // test Tokyo
            forecast = weather.getForecast(tokyoCoordinates, date);
            i = 0;
            for (HashMap.Entry<String, Double> entry : forecast.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                System.out.println("Key: " + key + ", Value: " + value);
                assertEquals(tokyoExpectedForecast[i],value,delta);
                i++;
            }
            // test Seattle
            forecast = weather.getForecast(seattleCoordinates, date);
            i = 0;
            for (HashMap.Entry<String, Double> entry : forecast.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                System.out.println("Key: " + key + ", Value: " + value);
                assertEquals(seattleExpectedForecast[i],value,delta);
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail("Something went wrong!");
        }
    }

    /* i wasn't able to write a test for skytalk as trying to initialise it or use its method would give me this error:
    java.lang.ExceptionInInitializerError
        at griffith.skytalkpro/griffith.skytalkpro.SkyTalk.addMessage(SkyTalk.java:272)
        at griffith.skytalkpro/griffith.skytalkpro.SkyTalk.output(SkyTalk.java:246)
        at griffith.skytalkpro/griffith.skytalkpro.SkyTalk.takeUserInput(SkyTalk.java:332)
        at griffith.skytalkpro/griffith.skytalkpro.SkyTalkTest.testUserInput(SkyTalkTest.java:175)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:45)
        at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15)
        at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:42)
        at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:20)
        at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:263)
        at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:68)
        at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:47)
        at org.junit.runners.ParentRunner$3.run(ParentRunner.java:231)
        at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:60)
        at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:229)
        at org.junit.runners.ParentRunner.access$000(ParentRunner.java:50)
        at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:222)
        at org.junit.runners.ParentRunner.run(ParentRunner.java:300)
        at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:93)
        at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:40)
        at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:529)
        at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:757)
        at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:452)
        at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:210)
Caused by: java.lang.IllegalStateException: Toolkit not initialized
        at javafx.graphics/com.sun.javafx.application.PlatformImpl.runLater(PlatformImpl.java:437)
        at javafx.graphics/com.sun.javafx.application.PlatformImpl.runLater(PlatformImpl.java:432)
        at javafx.graphics/com.sun.javafx.application.PlatformImpl.setPlatformUserAgentStylesheet(PlatformImpl.java:725)
        at javafx.graphics/com.sun.javafx.application.PlatformImpl.setDefaultPlatformUserAgentStylesheet(PlatformImpl.java:687)
        at javafx.controls/javafx.scene.control.Control.<clinit>(Control.java:99)
        ... 27 more
     * 
     */


    // @Test
    // public void testUserInput(){
    //     // test that takeUserInput() returns a places hashmap
    //     String input = "Dublin 06/06/2024";
    //     HashMap<String, LocalDate> place = skytalk.takeUserInput(input);
    //     for (HashMap.Entry<String, LocalDate> entry : place.entrySet()) {
    //         String key = entry.getKey();
    //         LocalDate value = entry.getValue();
    //         System.out.println("Key: " + key + ", Value: " + value.toString());
    //     }

    // }

    @Test
    public void testGenerateClothingPlan(){
        String recommendation = clothingRecommendation.generateClothingPlan(5,12,true,false);
        System.out.println(recommendation);
        assertEquals("My suggestion:  \nSince the lowest temperature during the entire trip will be 5.0 degrees \nCelsius and the highest 12.0 degrees Celsius.\n Put on a Long Sleeves and Jeans.\n There is a high chance of rain during your trip,\n so take an umbrella or a raincoat.🌧",recommendation);
    }
}
