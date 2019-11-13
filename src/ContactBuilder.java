import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactBuilder {
    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    public ContactBuilder() {}
    public int load(String inputFilePath) throws IOException {
        int addedContactCount = 0;
        File inputFile = new File(inputFilePath);
        Scanner inputFileScanner = new Scanner(inputFile);
        while (inputFileScanner.hasNextLine()) {
            String rawInformation = inputFileScanner.nextLine();
            try {
                this.contacts.add(new Contact(rawInformation));
            } catch (IllegalArgumentException e) {
                throw new IOException("Contact record at line " + (addedContactCount + 1) + " has invalid format.");
            }
            addedContactCount++;
        }
        inputFileScanner.close();
        return addedContactCount;
    }
    public int size() {
        return this.contacts.size();
    }
    public ArrayList<Contact> getAll() {
        return this.contacts;
    }
    public Contact get(int contactID) throws IllegalArgumentException {
        if (!isValidContactID(contactID)) throw new IllegalArgumentException("Invalid contact ID.");
        return this.contacts.get(contactID);
    }
    public void add(String name, String phone, String email, String address) throws IllegalArgumentException {
        this.contacts.add(new Contact(name, phone, email, address));
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
        ArrayList<Integer> searchResults = new ArrayList<Integer>();
        for (Contact contact:this.contacts) {
            if (contact.toString().matches(".*" + query + ".*")) {
                searchResults.add(this.contacts.indexOf(contact));
            }
        }
        return searchResults;
    }
    public void sort() {
        //implement this
    }
    public void save(String outputFilePath) throws IOException {
        FileWriter outputFile = new FileWriter(outputFilePath);
        for (Contact contact:this.contacts) {
            outputFile.write(contact.toString() + "\n");
        }
        outputFile.close();
    }
    private boolean isValidContactID(int contactID) {
        if (contactID < 0 || contactID > this.contacts.size()) {
            return false;
        }
        return true;
    }
}
