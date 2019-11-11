import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactBuilder {
    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    public ContactBuilder() {}
    public ContactBuilder(Scanner inputFile) {
        //implement this
    }
    public void view() {
        for (int i = 0; i < this.contacts.size(); i++) {
            System.out.printf("[%d] %s\n", i, this.contacts.get(i));
        }
    }
    public void add(String name, String phone, String email, String address) {
        //catch some error
        contacts.add(new Contact(name, phone, email, address));
    }
    public void edit() {
        //implement this
    }
    public void delete(int contactID) {
        //validate contactID
        contacts.remove(contactID);
    }
    public void search(String query) {
        //implement this
    }
    public void sort() {
        //implement this
    }
    public void save(PrintWriter file) {
        //implement this
    }
}
