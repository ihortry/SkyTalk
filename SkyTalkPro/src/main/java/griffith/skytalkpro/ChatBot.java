package griffith.skytalkpro;

/*
 * Class representing the ChatBot logic
 */
class ChatBot {
    public String respond(String input) {
        /*
         * Simulation of processing time
         */
        try {
        	/*
        	 * Delay the bot respond for a second after user input display
        	 */
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
         * return string result of the bot respond
         */
        return "You said: " + input;
    }
}