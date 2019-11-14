import javassist.bytecode.DuplicateMemberException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ContactManager {
    private static String filePath = "";
    private static ContactBuilder contactBook = new ContactBuilder();
    private static Scanner userInputScanner = new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            operate();
            System.out.print("Press ENTER to continue...");
            userInputScanner.nextLine();
        }
    }
    private static void operate() {
        String[] options = {
            "Load contacts from file",
            "View all contacts",
            "Add new contact",
            "Edit a contact",
            "Delete a contact",
            "Search contact list",
            "Sort contact list",
            "Save contacts to file",
            "Quit"
        };
        System.out.println("Select the operation you want to execute:");
        int userOption = promptUserOption(options);
        switch (userOption) {
            case 0: loadContactsFromFile(); break;
            case 1: viewAllContacts(); break;
            case 2: addNewContact(); break;
            case 3: editContact(); break;
            case 4: deleteContact(); break;
            case 5: searchContacts(); break;
            case 6: sortContacts(); break;
            case 7: saveContactsToFile(); break;
            case 8: quitProgram(); break;
            default: break;
        }
    }
    private static void loadContactsFromFile() {
        int loadedContactCount = 0;
        System.out.print("Enter your file path: ");
        filePath = userInputScanner.nextLine();
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
            System.out.println("Doesn't have any contact to display.");
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
        } catch (DuplicateMemberException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void editContact() {
        int contactID = promptContactID();
        Contact selectedContact;
        try {
            selectedContact = contactBook.get(contactID);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        String[] options = {
                String.format("Name: %s", selectedContact.getName()),
                String.format("Phone number: %s", selectedContact.getPhone()),
                String.format("Email: %s", selectedContact.getEmail()),
                String.format("Address: %s", selectedContact.getAddress())
        };
        System.out.println("Select the data field to be changed: ");
        int userOption = promptUserOption(options);
        System.out.print("Enter new value for the selected field: ");
        String newValue = userInputScanner.nextLine();
        try {
            switch (userOption) {
                case 0: contactBook.editName(contactID, newValue); break;
                case 1: contactBook.editPhone(contactID, newValue); break;
                case 2: contactBook.editEmail(contactID, newValue); break;
                case 3: contactBook.editAddress(contactID, newValue); break;
                default: break;
            }
            System.out.println("Contact edited.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void deleteContact() {
        int contactID = promptContactID();
        try {
            contactBook.delete(contactID);
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
        String[] options = {
                "Sort by name",
                "Sort by phone number",
                "Sort by email",
                "Sort by address"};
        System.out.println("Select which data field to sort:");
        int sortDataField = promptUserOption(options);
        switch (sortDataField) {
            case 0: contactBook.sortByName(); break;
            case 1: contactBook.sortByPhone(); break;
            case 2: contactBook.sortByEmail(); break;
            case 3: contactBook.sortByAddress(); break;
            default: break;
        }
        options = new String[]{
                "Ascending",
                "Descending"};
        System.out.println("Select sort order: ");
        int sortOrder = promptUserOption(options);
        if (sortOrder == 1) contactBook.reverseOrder();
        System.out.println("Contacts sorted.");
    }
    private static void saveContactsToFile() {
        if (filePath.matches("")) {
            System.out.print("Enter your output file path: ");
            filePath = userInputScanner.nextLine();
        }
        try {
            contactBook.save(filePath);
            System.out.printf("%d contacts saved to file: \"%s\".\n", contactBook.size(),
                                                                      filePath);
        } catch(IOException e) {
            System.out.println("Error with your file path.");
        }
    }
    private static void quitProgram() {
        String exitMessage = "Exiting... Good bye!";
        System.out.println(exitMessage);
        System.exit(0);
    }
    private static int promptContactID() {
        int contactID = -1;
        while (true) {
            System.out.print("Enter contact ID: ");
            try {
                contactID = userInputScanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            } finally {
                userInputScanner.nextLine();
            }
            if (contactBook.isValidContactID(contactID)) {
                break;
            } else {
                System.out.println("You've entered an invalid contact ID, please try again.");
            }
        }
        return contactID;
    }
    private static int promptUserOption(String[] options) {
        int userOption = -1;
        for (int i = 0; i < options.length; i++) {
            System.out.printf("[%d] %s\n", i, options[i]);
        }
        while (true) {
            System.out.print("Enter your option: ");
            try {
                userOption = userInputScanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            } finally {
                userInputScanner.nextLine();
            }
            if (userOption >= 0 && userOption < options.length) {
                break;
            } else {
                System.out.println("You've entered an invalid option, please try again.");
            }
        }
        return userOption;
    }
}
