import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactBuilder {
    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    public ContactBuilder() {}
    public void load(String inputFilePath) throws IOException {
        //try and catch
        String rawInformation;
        BufferedReader buffer = new BufferedReader(new FileReader(inputFilePath));
        while ((rawInformation = buffer.readLine()) != null) {
            this.contacts.add(new Contact(rawInformation));
        }
    }
    public int size() {
        return this.contacts.size();
    }
    public ArrayList<Contact> getAll() {
        return this.contacts;
    }
    public Contact get(int contactID) {
        //validate
        return this.contacts.get(contactID);
    }
    public void add(String rawInformation) {
        //do some more validations
        this.contacts.add(new Contact(rawInformation));
    }
    public void add(String name, String phone, String email, String address) {
        //catch some error
        this.contacts.add(new Contact(name, phone, email, address));
    }
    public void edit(int contactID, String newValue) {
        //catch some error

    }
    public void delete(int contactID) {
        //validate contactID
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
    public void sort(Contact.Field field) {
        //implement this
    }
    public void save(String outputFilePath) throws IOException {
        //try catch
        //override current file content
        FileWriter fileWriter = new FileWriter(outputFilePath);
        for (Contact contact:this.contacts) {
            fileWriter.write(contact.toString() + "\n");
        }
        fileWriter.close();
    }
}
