import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactManager {
    private static String userInput = ""; //consider using StringBuffer/Builder
    private static ContactBuilder contactBook = new ContactBuilder();
    private static String filePath = ""; //consider using StringBuffer/Builder
    private static Scanner userInputScanner = new Scanner(System.in);
    public static void main(String[] args) {
        printUI();
        while (true) {
            getUserInput();
            if (!isValidUserInput()) {
                System.out.println("Invalid user input.");
                continue;
            }
            operate();
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
        System.out.print("Select a function (1-9): ");
        userInput = userInputScanner.nextLine();
    }
    private static boolean isValidUserInput() {
        if (userInput.matches("[1-9]")) {
            return true;
        }
        return false;
    }
    private static void operate() {
        switch (Integer.parseInt(userInput)) {
            case 1: loadContactsFromFile(); break;
            case 2: viewAllContacts(); break;
            case 3: addNewContact(); break;
            case 4: editContact(); break;
            case 5: deleteContact(); break;
            case 6: searchContacts(); break;
            case 7: sortContacts(); break;
            case 8: saveContactsToFile(); break;
            case 9: quitProgram(); break;
            default: break;
        }
    }
    private static void loadContactsFromFile() {
        int loadedContactCount = 0;
        getFilePath();
        try {
            loadedContactCount = contactBook.load(filePath);
            System.out.printf("%d contacts loaded.\n", loadedContactCount);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void viewAllContacts() {
        ArrayList<Contact> allContacts = contactBook.getAll();
        if (allContacts.size() > 0) {
            for (int i = 0; i < allContacts.size(); i++) {
                System.out.printf("[%d] %s\n", i, allContacts.get(i));
            }
        } else {
            System.out.println("No contact, yet.");
        }
    }
    private static void addNewContact() {
        System.out.print("Enter new contact (format: name; phone; email; address): ");
        String contactRawInformation = userInputScanner.nextLine();
        try {
            contactBook.add(contactRawInformation);
            System.out.println("Contact added.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void editContact() {
//        //implement this
//        System.out.print("Enter ID of the contact you want to edit: ");
//        String contactID = userInputScanner.nextLine();
//        //validate
//        printContactInformation(contactBook.get(Integer.parseInt(contactID)));
//        int userOption = userInputScanner.nextInt();
//        String newValue = userInputScanner.nextLine();
//        switch (userOption) {
//            case 0:
//                contactBook.edit(contactID, newValue);
//                break;
//
//        }
    }
    private static void printContactInformation(Contact contact) {
        System.out.printf("[0] %s\n[1] %s\n[2] %s\n[3] %s\n",
                                            contact.getName(),
                                            contact.getPhone(),
                                            contact.getEmail(),
                                            contact.getAddress());
    }
    private static void deleteContact() {
        System.out.print("Enter ID of the contact you want to delete: ");
        String contactID = userInputScanner.nextLine();
        try {
            contactBook.delete(Integer.parseInt(contactID));
            System.out.println("Contact deleted.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void searchContacts() {
        System.out.print("Enter search query: ");
        String query = userInputScanner.nextLine();
        ArrayList<Integer> searchResults = contactBook.search(query);
        if (searchResults.size() > 0) {
            for (int contactID:searchResults) {
                System.out.printf("[%d] %s\n", contactID, contactBook.get(contactID));
            }
        } else {
            System.out.printf("No contact matched your query \"%s\"\n", query);
        }
    }
    private static void sortContacts() {
        //implement this
    }
    private static void saveContactsToFile() {
        getFilePath();
        try {
            contactBook.save(filePath);
            System.out.printf("%d contacts saved to file at \"%s\".\n", contactBook.size(),
                                                                        filePath);
        } catch(IOException e) {
            System.out.println("Error with your file path.");
        }
    }
    private static void quitProgram() {
        String exitMessage = "Exiting...";
        System.out.println(exitMessage);
        System.exit(0);
    }
    private static void getFilePath() {
        System.out.print("Enter your file path: ");
        filePath = userInputScanner.nextLine();
    }
}
