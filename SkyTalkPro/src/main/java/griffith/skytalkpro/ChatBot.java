package griffith.skytalkpro;

// Class representing the ChatBot logic
class ChatBot {

    
    


    public String Output(String input) {
        // Simulate processing time
        try {
            Thread.sleep(1000); // Delay for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Simple echo bot for demonstration
        return input;
    }
}