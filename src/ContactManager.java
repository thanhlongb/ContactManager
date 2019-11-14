import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ContactManager {
    private static String userInput = ""; //consider using StringBuffer/Builder
    private static ContactBuilder contactBook = new ContactBuilder();
    private static Scanner userInputScanner = new Scanner(System.in);
    public static void main(String[] args) throws NoSuchAlgorithmException {
        printUI();
        while (true) {
            System.out.print("Select a function (1-9): ");
            userInput = userInputScanner.nextLine();
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
        System.out.print("Enter your file path: ");
        String filePath = userInputScanner.nextLine();
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
        System.out.print("Contact name: ");
        String name = userInputScanner.nextLine();
        System.out.print("Contact phone number: ");
        String phone = userInputScanner.nextLine();
        System.out.print("Contact email: ");
        String email = userInputScanner.nextLine();
        System.out.print("Contact address: ");
        String address = userInputScanner.nextLine();
        try {
            contactBook.add(name, phone, email, address);
            System.out.println("Contact added.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void editContact() {
        int userOption = 0, contactID = 0;
        System.out.print("Enter ID of the contact you want to edit: ");
        try {
            contactID = userInputScanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input data type (supposed to be int).");
            return;
        }
        try {
            printContactInformation(contactBook.get(contactID));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.print("Select the field you want to edit (0 to 3): ");
        try {
            userOption = userInputScanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input data type (supposed to be int).");
            return;
        }
        System.out.print("Enter new value for the selected field: ");
        String newValue = userInputScanner.nextLine();
        switch (userOption) {
            case 0:
                contactBook.editName(contactID, newValue);
                break;
            case 1:
                contactBook.editPhone(contactID, newValue);
                break;
            case 2:
                contactBook.editEmail(contactID, newValue);
                break;
            case 3:
                contactBook.editAddress(contactID, newValue);
                break;
            default:
                break;
        }
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
        System.out.println(
                "Select your sort field:\n" +
                "[0] Sort by name\n" +
                "[1] Sort by phone number\n" +
                "[2] Sort by email\n" +
                "[3] Sort by address");
        int userOption = userInputScanner.nextInt();
        switch (userOption) {
            case 0: contactBook.sortByName(); break;
            case 1: contactBook.sortByPhone(); break;
            case 2: contactBook.sortByEmail(); break;
            case 3: contactBook.sortByAddress(); break;
            default: break;
        }
        System.out.println(
                "Select your sort order:\n" +
                        "[0] Ascending\n" +
                        "[1] Descending");
        int sortOrder = userInputScanner.nextInt();
        if (sortOrder == 1) contactBook.reverseOrder();
        System.out.println("Contacts sorted.");
    }
    private static void saveContactsToFile() {
        System.out.print("Enter your file path: ");
        String filePath = userInputScanner.nextLine();
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
}
