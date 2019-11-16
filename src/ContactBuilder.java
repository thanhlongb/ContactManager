/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Assignment 1
  Author: Bui Thanh Long
  ID: 3748575
  Created  date: 11/11/2019
  Last modified: 16/11/2019
  Acknowledgement:
  - Comparator implementation (line 88, 96, 104, 112): https://stackoverflow.com/questions/10396970/sort-a-list-that-contains-a-custom-class
*/

import java.io.*;
import java.util.*;

public class ContactBuilder {
    private ArrayList<Contact> contacts;
    public ContactBuilder() {
        this.contacts = new ArrayList<>();
    }
    public int load(String inputFilePath) throws IOException {
        int addedContactCount = 0;
        Scanner inputFileScanner = new Scanner(new File(inputFilePath));
        while (inputFileScanner.hasNextLine()) {
            String rawInformation = inputFileScanner.nextLine();
            Contact newContact;
            try {
                newContact = new Contact(rawInformation);
            } catch (IllegalArgumentException e) {
                throw new IOException("Contact record at line " + (addedContactCount + 1) + " has invalid format.");
            }
            try {
                this.add(newContact);
            } catch (IllegalArgumentException e) {
                // contact duplication found,
                // skipping this contact record
                continue;
            }
            addedContactCount++;
        }
        inputFileScanner.close();
        return addedContactCount;
    }
    public int size() {
        return (this.contacts.size());
    }
    public boolean isEmpty() {
        return (this.contacts.size() == 0);
    }
    public Contact get(int contactID) throws IllegalArgumentException {
        if (!isValidContactID(contactID)) throw new IllegalArgumentException("Invalid contact ID.");
        return this.contacts.get(contactID);
    }
    public ArrayList<Contact> getAll() {
        return this.contacts;
    }
    public void add(Contact newContact) throws IllegalArgumentException {
        if (isDuplicate(newContact)) throw new IllegalArgumentException("Can't add due to duplication.");
        this.contacts.add(newContact);
    }
    public void editName(int contactID, String newValue) throws IllegalArgumentException {
        this.contacts.get(contactID).setName(newValue);
    }
    public void editPhone(int contactID, String newValue) throws IllegalArgumentException {
        this.contacts.get(contactID).setPhone(newValue);
    }
    public void editEmail(int contactID, String newValue) throws IllegalArgumentException {
        this.contacts.get(contactID).setEmail(newValue);
    }
    public void editAddress(int contactID, String newValue) throws IllegalArgumentException {
        this.contacts.get(contactID).setAddress(newValue);
    }
    public void delete(int contactID) throws IllegalArgumentException {
        if (!isValidContactID(contactID)) throw new IllegalArgumentException("Invalid contact ID.");
        this.contacts.remove(contactID);
    }
    public ArrayList<Integer> search(String query) {
        ArrayList<Integer> searchResults = new ArrayList<>();
        for (Contact contact:this.contacts) {
            if (contact.toString().matches(".*" + query + ".*")) {
                searchResults.add(this.contacts.indexOf(contact));
            }
        }
        return searchResults;
    }
    public void sortByName() {
        Comparator<Contact> contactName =  new Comparator<Contact>() {
            public int compare(Contact contact1, Contact contact2) {
                return contact1.getName().compareTo(contact2.getName());
            }
        };
        Collections.sort(this.contacts, contactName);
    }
    public void sortByPhone() {
        Comparator<Contact> contactPhone =  new Comparator<Contact>() {
            public int compare(Contact contact1, Contact contact2) {
                return contact1.getPhone().compareTo(contact2.getPhone());
            }
        };
        Collections.sort(this.contacts, contactPhone);
    }
    public void sortByEmail() {
        Comparator<Contact> contactEmail =  new Comparator<Contact>() {
            public int compare(Contact contact1, Contact contact2) {
                return contact1.getEmail().compareTo(contact2.getEmail());
            }
        };
        Collections.sort(this.contacts, contactEmail);
    }
    public void sortByAddress() {
        Comparator<Contact> contactAddress =  new Comparator<Contact>() {
            public int compare(Contact contact1, Contact contact2) {
                return contact1.getAddress().compareTo(contact2.getAddress());
            }
        };
        Collections.sort(this.contacts, contactAddress);
    }
    public void reverseOrder() {
        Collections.reverse(this.contacts);
    }
    public void save(String outputFilePath) throws IOException {
        FileWriter outputFile = new FileWriter(outputFilePath);
        for (Contact contact:this.contacts) {
            outputFile.write(contact.toString() + "\n");
        }
        outputFile.close();
    }
    private boolean isValidContactID(int contactID) {
        return (contactID >= 0 && contactID < this.contacts.size());
    }
    private boolean isDuplicate(Contact newContact) {
        for (Contact contact:this.contacts) {
            if (contact.toString().matches(newContact.toString())) {
                return true;
            }
        }
        return false;
    }
}
