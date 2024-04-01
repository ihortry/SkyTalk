/**
 * @author Ihor Tryndey 3105023 , Oleksii Babii 3104904
 * @since 2024
 * @version 1.0
 */
package griffith;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ChefConnectFormatTextTest {
	
	private Chatbot gui = new Chatbot();
	private String input;
	private String expectedOutput;
	
	public ChefConnectFormatTextTest(String input, String expectedOutput) {
		super();
		this.input = input;
		this.expectedOutput = expectedOutput;
	}
	@Parameters
	public static Collection<String[]> testConditions() {
	    String[][] expectedOutput = {
	            {
	                "Lorem Ipsum has been the industry's standard dummy text ever since the\r\n"
	                + "1500s, when an unknown printer took a galley of type and scrambled it\r\n"
	                + "to make a type specimen book. It has survived not only five centuries,\r\n"
	                + "but also the leap into electronic typesetting, remaining essentially\r\n"
	                + "unchanged. It was popularised in the 1960s with the release of\r\n"
	                + "Letraset sheets containing Lorem Ipsum passages, and more recently\r\n"
	                + "with desktop publishing software like Aldus PageMaker including\r\n"
	                + "versions of Lorem Ipsum.",
	                Chatbot.formatText("Lorem Ipsum has been the industry's"
	                        + " standard dummy text ever since the 1500s"
	                        + ", when an unknown printer took a galley"
	                        + " of type and scrambled it to make a type specimen"
	                        + " book. It has survived not only five centuries, but also"
	                        + " the leap into electronic typesetting, remaining essentially"
	                        + " unchanged. It was popularised in the 1960s with"
	                        + " the release of Letraset sheets containing Lorem Ipsum passages"
	                        + ", and more recently with desktop publishing software"
	                        + " like Aldus PageMaker including versions of Lorem Ipsum."
	            )
	            },
	            {
	                "Contrary to popular belief, Lorem Ipsum is not simply random text. It\r\n"
	                + "has roots in a piece of classical Latin literature from 45 BC, making\r\n"
	                + "it over 2000 years old. Richard McClintock, a Latin professor at\r\n"
	                + "Hampden-Sydney College in Virginia, looked up one of the more obscure\r\n"
	                + "Latin words, consectetur, from a Lorem Ipsum passage, and going\r\n"
	                + "through the cites of the word in classical literature, discovered the\r\n"
	                + "undoubtable source.",
	                Chatbot.formatText("Contrary to popular belief, Lorem Ipsum is not simply random text. "
	                		+ "It has roots in a piece of classical Latin literature from 45 BC, making it over "
	                		+ "2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in "
	                		+ "Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem "
	                		+ "Ipsum passage, and going through the cites of the word in classical literature, "
	                		+ "discovered the undoubtable source."
	                )
	            },
	            {
	                "The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes\r\n"
	                + "from a line in section 1.10.32.",
	                Chatbot.formatText("The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.")
	            }
	    };

	    return Arrays.asList(expectedOutput);
	}
	
	@Test
	public void formatTextTest() {
		assertEquals(expectedOutput, input);
	}

}