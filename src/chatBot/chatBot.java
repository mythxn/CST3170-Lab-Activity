package chatBot;

import java.util.Scanner;

public class chatBot {

    private static final Scanner scannerObj = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            // get user input, clean it up and setup array
            System.out.print("\nPlease enter your query: ");
            String userInput = scannerObj.nextLine().replaceAll("[\\-+?'.^:,]", "").toLowerCase();
            String[] inputArray = userInput.split(" ");

            // stop look if users says 'stop'
            if (userInput.equals("stop")) break;

            // pattern identification, order response
            else if (userInput.contains("ill have a") ||
                    userInput.contains("wheres the") ||
                    userInput.contains("can i have a"))
            System.out.println("Preparing your order: " + inputArray[inputArray.length - 1]);

            else if (userInput.contains("please")) System.out.println("Preparing your order: " + inputArray[0]);

            // wildcard response
            else System.out.println("sorry, could you say that again?");
        }
    }
}
