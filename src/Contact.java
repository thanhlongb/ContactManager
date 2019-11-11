//find a way to validate properties
//when they are passed into class
public class Contact {
    private String name;
    private String phone;
    private String email;
    private String address;
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
    public void edit(String name, String phone, String email, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
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
        return String.format("%s %s %s %s\n", this.name,
                                              this.phone,
                                              this.email,
                                              this.address);
    }


}
