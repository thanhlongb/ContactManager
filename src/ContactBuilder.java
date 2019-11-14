import java.io.*;
import java.util.*;

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
        if (!isValidContactID(contactID)) throw new IllegalArgumentException("E8. Invalid contact ID.");
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
        if (!isValidContactID(contactID)) throw new IllegalArgumentException("E9. Invalid contact ID.");
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
    //are these consider repetitive?
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
        if (contactID < 0 || contactID > this.contacts.size()) {
            return false;
        }
        return true;
    }
}
