import java.util.HashMap;
import java.util.Scanner;

public class SimpleChatBot {

    private static HashMap<String, String> responses = new HashMap<>();

    // Normalize input for better matching
    private static String normalize(String input) {
        return input.trim().toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");
    }

    // Load predefined responses (can be expanded)
    private static void trainBot() {
        responses.put("hi", "Hello! How can I assist you?");
        responses.put("hello", "Hi there! Need help?");
        responses.put("how are you", "I'm just a bot, but I'm doing great!");
        responses.put("what is your name", "I'm your virtual assistant.");
        responses.put("bye", "Goodbye! Have a great day!");
        responses.put("help", "Sure! You can ask about our services, timings, or support.");
        responses.put("who created you", "I was created by a Java developer for a project.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        trainBot();

        System.out.println("🤖 ChatBot: Hello! Type 'bye' to exit.");
        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();
            String cleanedInput = normalize(userInput);

            if (cleanedInput.equals("bye")) {
                System.out.println("🤖 ChatBot: " + responses.get("bye"));
                break;
            }

            String response = responses.getOrDefault(cleanedInput, "Sorry, I didn't understand that.");
            System.out.println("🤖 ChatBot: " + response);
        }

        scanner.close();
    }
}
