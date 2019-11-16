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
  - email regex (line 71): https://stackoverflow.com/questions/46155/how-to-validate-an-email-address-in-javascript
*/

public class Contact {
    private static final String DELIMITER = "; ";
    private String name;
    private String phone;
    private String email;
    private String address;
    Contact () {
        // constructor not used but declared to be careful
        this.name = "";
        this.phone = "";
        this.email = "";
        this.address = "";
    }
    Contact (String rawInformation) throws IllegalArgumentException {
        String[] userInformation = rawInformation.split(DELIMITER);
        if (userInformation.length != 4) throw new IllegalArgumentException("Invalid contact information.");
        if (!isValidPhone(userInformation[1])) throw new IllegalArgumentException("Invalid phone number.");
        if (!isValidEmail(userInformation[2])) throw new IllegalArgumentException("Invalid email.");
        this.name = userInformation[0];
        this.phone = userInformation[1];
        this.email = userInformation[2];
        this.address = userInformation[3];
    }
    Contact (String name, String phone, String email, String address) throws IllegalArgumentException {
        if (!isValidPhone(phone)) throw new IllegalArgumentException("Invalid phone number.");
        if (!isValidEmail(email)) throw new IllegalArgumentException("Invalid email.");
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }
    public String getName() {
        return this.name;
    }
    public String getPhone() {
        return this.phone;
    }
    public String getEmail() {
        return this.email;
    }
    public String getAddress() {
        return this.address;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPhone(String phone) throws IllegalArgumentException {
        if (!isValidPhone(phone)) throw new IllegalArgumentException("Invalid phone number.");
        this.phone = phone;
    }
    public void setEmail(String email) throws IllegalArgumentException {
        if (!isValidEmail(email)) throw new IllegalArgumentException("Invalid email.");
        this.email = email;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    private static boolean isValidPhone(String phone) {
        return (phone.trim().matches("\\+?[0-9 ]{5,15}"));
    }
    private static boolean isValidEmail(String email) {
        return (email.matches("\\S+@\\S+\\.\\S+"));
    }

    @Override
    public String toString() {
        return String.format("%s%s%s%s%s%s%s", this.name, DELIMITER,
                                               this.phone, DELIMITER,
                                               this.email, DELIMITER,
                                               this.address);
    }
}
