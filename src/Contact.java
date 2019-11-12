//find a way to validate properties
//when they are passed into class
public class Contact {
    public enum Field {NAME, PHONE, EMAIL, ADDRESS};
    private String name = "";
    private String phone = "";
    private String email = "";
    private String address = "";
    Contact (String rawInformation) {
        //do some more validations
        String[] userInformation = rawInformation.split("; ");
        this.name = userInformation[0];
        this.phone = userInformation[1];
        this.email = userInformation[2];
        this.address = userInformation[3];
    }
    Contact (String name, String phone, String email, String address) {
        //validate these values
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
        if (isValidName(name)) {
            this.name = name;
        }
    }
    public void setPhone(String phone) {
        if (isValidPhone(phone)) {
            this.phone = phone;
        }
    }
    public void setEmail(String email) {
        if (isValidEmail(email)) {
            this.email = email;
        }
    }
    public void setAddress(String address) {
        if (isValidAddress(address)) {
            this.address = address;
        }
    }
    private static boolean isValidName(String name) {
        if (name.matches("([A-Za-z]+\\s?)+")) {
            return true;
        }
        return false;
    }
    private static boolean isValidPhone(String phone) {
        if (phone.matches("[0-9]{10,11}")) {
            return true;
        }
        return false;
    }
    private static boolean isValidEmail(String email) {
        if (email.matches("[a-z]+[\\w.]+\\w+@[\\w+.?]+[A-za-z]+")) {
            return true;
        }
        return false;
    }
    private static boolean isValidAddress(String address) {
        if (address.matches("[\\w+, \\/]+")) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s; %s; %s; %s",this.name,
                                              this.phone,
                                              this.email,
                                              this.address);
    }


}
