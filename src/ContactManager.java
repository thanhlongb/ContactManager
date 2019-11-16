/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Assignment 1
  Author: Bui Thanh Long
  ID: 3748575
  Created  date: 11/11/2019
  Last modified: 16/11/2019
  Acknowledgement: none
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.CancellationException;

public class ContactManager {
    private static String filePath = "";
    private static ContactBuilder contactBook = new ContactBuilder();
    private static Scanner userInputScanner = new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            operate();
            waitForUserToReadOutput();
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
        int userOption = promptUserOption(options, false);
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
        int loadedContactCount;
        System.out.print(">> Enter your file path: ");
        filePath = userInputScanner.nextLine();
        try {
            loadedContactCount = contactBook.load(filePath);
            System.out.printf("%d contacts loaded.\n", loadedContactCount);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void viewAllContacts() {
        if (contactBook.isEmpty()) {
            System.out.println("Doesn't have any contact to display.");
        } else {
            ArrayList<Contact> allContacts = contactBook.getAll();
            System.out.println("Here are all the contacts you have:");
            for (int i = 0; i < allContacts.size(); i++) {
                System.out.printf("[%d] %s\n", i, allContacts.get(i));
            }
        }
    }
    private static void addNewContact() {
        Contact newContact;
        try {
            newContact = promptNewContact();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            contactBook.add(newContact);
            System.out.println("Contact added.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void editContact() {
        if (contactBook.isEmpty()) {
            System.out.println("Doesn't have any contact to edit.");
            return;
        }
        int contactID;
        try {
            contactID = promptContactID();
        } catch (CancellationException e) {
            System.out.println(e.getMessage());
            return;
        }
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
        int userOption;
        try {
            userOption = promptUserOption(options, true);
        } catch (CancellationException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.print(">> Enter new value for the selected field: ");
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
        if (contactBook.isEmpty()) {
            System.out.println("Doesn't have any contact to delete.");
            return;
        }
        int contactID;
        try {
            contactID = promptContactID();
        } catch (CancellationException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            contactBook.delete(contactID);
            System.out.println("Contact deleted.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void searchContacts() {
        if (contactBook.isEmpty()) {
            System.out.println("Doesn't have any contact to search.");
            return;
        }
        System.out.print(">> Enter search query: ");
        String query = userInputScanner.nextLine();
        ArrayList<Integer> searchResults = contactBook.search(query);
        if (searchResults.size() > 0) {
            System.out.printf("%d contacts match your search query \"%s\":\n", searchResults.size(), query);
            for (int contactID:searchResults) {
                System.out.printf("[%d] %s\n", contactID, contactBook.get(contactID));
            }
        } else {
            System.out.printf("No contact matched your query \"%s\"\n", query);
        }
    }
    private static void sortContacts() {
        if (contactBook.isEmpty()) {
            System.out.println("Doesn't have any contact to sort.");
            return;
        }
        String[] options = {
                "Sort by name",
                "Sort by phone number",
                "Sort by email",
                "Sort by address"};
        System.out.println("Select which data field to sort:");
        int sortDataField;
        try {
            sortDataField = promptUserOption(options, true);
        } catch (CancellationException e) {
            System.out.println(e.getMessage());
            return;
        }
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
        int sortOrder;
        try {
            sortOrder = promptUserOption(options, true);
        } catch (CancellationException e) {
            System.out.println(e.getMessage());
            return;
        }
        if (sortOrder == 1) contactBook.reverseOrder();
        System.out.println("Contacts sorted.");
    }
    private static void saveContactsToFile() {
        if (contactBook.isEmpty()) {
            System.out.println("Doesn't have any contact to save to file.");
            return;
        }
        if (filePath.matches("")) {
            System.out.print(">> Enter your output file path: ");
            filePath = userInputScanner.nextLine();
        }
        try {
            contactBook.save(filePath);
            System.out.printf("%d contacts saved to file \"%s\".\n", contactBook.size(), filePath);
        } catch(IOException e) {
            System.out.println("Error occurred with your file path.");
        }
    }
    private static void quitProgram() {
        userInputScanner.close();
        String exitMessage = "Exiting... Good bye!";
        System.out.println(exitMessage);
        System.exit(0);
    }
    private static void waitForUserToReadOutput() {
        System.out.print(">> Press ENTER to continue...");
        userInputScanner.nextLine();
    }
    private static Contact promptNewContact() throws IllegalArgumentException {
        System.out.print(">> Contact name: ");
        String name = userInputScanner.nextLine();
        System.out.print(">> Contact phone number: ");
        String phone = userInputScanner.nextLine();
        System.out.print(">> Contact email: ");
        String email = userInputScanner.nextLine();
        System.out.print(">> Contact address: ");
        String address = userInputScanner.nextLine();
        return new Contact(name, phone, email, address);
    }
    private static int promptContactID() throws CancellationException {
        int contactID;
        while (true) {
            System.out.print(">> Enter contact ID (use '-1' to cancel): ");
            try {
                contactID = userInputScanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("You supposed to enter a number.");
                continue;
            } finally {
                userInputScanner.nextLine();
            }
            if (contactID == -1) throw new CancellationException("Operation cancelled.");
            if (contactID >= 0 && contactID < contactBook.size()) {
                break;
            } else {
                System.out.println("You've entered an invalid contact ID, please try again.");
            }
        }
        return contactID;
    }
    private static int promptUserOption(String[] options, boolean isCancelable) throws CancellationException {
        int userOption;
        for (int i = 0; i < options.length; i++) {
            System.out.printf("[%d] %s\n", i, options[i]);
        }
        while (true) {
            System.out.printf(">> Enter your option%s: ", (isCancelable) ? " (use '-1' to cancel)" : "");
            try {
                userOption = userInputScanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("You supposed to enter a number.");
                continue;
            } finally {
                userInputScanner.nextLine();
            }
            if (isCancelable && userOption == -1) throw new CancellationException("Operation cancelled.");
            if (userOption >= 0 && userOption < options.length) {
                break;
            } else {
                System.out.println("You've entered an invalid option, please try again.");
            }
        }
        return userOption;
    }
}
