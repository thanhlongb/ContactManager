import java.util.Scanner;

public class ContactManager {
    private static String userInput;
    public static void main(String[] args) {
        printUI();
        while (true) {
            getUserInput();
            if (!isValidUserInput()) {
                System.out.println("Invalid user input.");
                continue;
            }
        }
    }
    private static void printUI() {
        final String options =
                "1. Load contacts from file\n" +
                "2. View all contacts\n" +
                "3. Add new contact\n" +
                "4. Edit a contact\n" +
                "5. Delete a contact\n" +
                "6. Search contact list\n" +
                "7. Sort contact list\n" +
                "8. Save contacts to file\n" +
                "9. Quit";
        System.out.println(options);
    }
    private static void getUserInput() {
        Scanner userInputScanner = new Scanner(System.in);
        System.out.print("Select a function (1-9): ");
        userInput = userInputScanner.nextLine();
    }
    private static boolean isValidUserInput() {
        if (userInput.matches("[1-9]")) {
            return true;
        }
        return false;
    }
}
