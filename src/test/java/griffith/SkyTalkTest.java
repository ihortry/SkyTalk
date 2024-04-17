package griffith;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

class SkyTalkTest {
	SkyTalk skyTalk = new SkyTalk();

	@Test
	//testing the cityToCoordinate function
	void testCityToCoordinate(){
		double[] dublinCoordinates = {53.350140, -6.266155};
		double[] coords = skyTalk.cityToCoordinate("Dublin");
		
		//check that the coordinates the function returned are close enough to the actual coordinates
		//there will be slight differences depending on which exact point on the map the API picks as Dublin's "location", so we use a delta of 0.1
		assertEquals(dublinCoordinates[0],coords[0],0.1);
		assertEquals(dublinCoordinates[1],coords[1],0.1);
	}
	@Test
	//testing the timezoneToCoordinate function
	void testTimezoneFromCoordinate(){
		double[] dublinCoordinates = {53.350140, -6.266155};
		String dublinTimezone = "Europe/Dublin";
		String timezone = skyTalk.timezoneFromCoordinate(dublinCoordinates);
		
		//compare expected and result
		System.out.println("Expected timezone: " + dublinTimezone);
		System.out.println("Timezone received: " + timezone);
		assertEquals(dublinTimezone,timezone);

	}
	@Test
	void testGenerateResponse() {
		
	}
}
