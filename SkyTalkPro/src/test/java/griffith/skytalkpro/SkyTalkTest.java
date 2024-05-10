package griffith.skytalkpro;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SkyTalkTest {
    //initialise new instance of Weather for testing weather-related functions
    static Weather weather = new Weather();
    //example cities
    static double[] dublinCoordinates = {53.33306, -6.24889};
    static double[] addisAbabaCoordinates={9.005401, 38.763611};
    static double[] tokyoCoordinates={35.652832, 139.839478};
    static double[] seattleCoordinates={47.60383, -122.33006};

    @Test
    public void testTimeZoneFromCoordinate(){
        try{
            String dublinTimezone = "Europe/Dublin";
            String addisAbabaTimezone="Africa/Addis_Ababa";
            String tokyoTimezone="Asia/Tokyo";
            String seattleTimezone="America/Los_Angeles";
            
            //test Dublin
            String timezone = weather.timezoneFromCoordinate(dublinCoordinates);
            System.out.println(timezone);
            assertEquals(dublinTimezone,timezone);

            //test Addis Ababa
            timezone = weather.timezoneFromCoordinate(addisAbabaCoordinates);
            System.out.println(timezone);
            assertEquals(addisAbabaTimezone,timezone);

            //test Tokyo
            timezone = weather.timezoneFromCoordinate(tokyoCoordinates);
            System.out.println(timezone);
            assertEquals(tokyoTimezone,timezone);

            //test Seattle
            timezone = weather.timezoneFromCoordinate(seattleCoordinates);
            System.out.println(timezone);
            assertEquals(seattleTimezone,timezone);

        }catch(Exception e){

        }

    }

    public void integratedTest(){
        //TODO
        //test that sendMessage() returns '"Type \"exit\" or enter new locations to continue\n (For example: London 25/06/2024, Paris 26/06/2024, Rome 27/06/2024):"
        //test that takeUserInput() returns a places hashmap
        //test generateClothingPlan()

        //test timezoneFromCoordinate()
        //test cityToCoordinate()
        //test getForecast()
    }
}
