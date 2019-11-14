public class Contact {
    public static final String DELIMITER = "; ";
    private String name = "";
    private String phone = "";
    private String email = "";
    private String address = "";
    Contact () {};
    Contact (String rawInformation) throws IllegalArgumentException {
        String[] userInformation = rawInformation.split(DELIMITER);
        if (userInformation.length != 4) throw new IllegalArgumentException("E1. Invalid contact information.");
        if (!isValidPhone(userInformation[1])) throw new IllegalArgumentException("E2. Invalid phone number.");
        if (!isValidEmail(userInformation[2])) throw new IllegalArgumentException("E3. Invalid email.");
        this.name = userInformation[0];
        this.phone = userInformation[1];
        this.email = userInformation[2];
        this.address = userInformation[3];
    }
    Contact (String name, String phone, String email, String address) throws IllegalArgumentException {
        if (!isValidPhone(phone)) throw new IllegalArgumentException("E4. Invalid phone number.");
        if (!isValidEmail(email)) throw new IllegalArgumentException("E5. Invalid email.");
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
        if (!isValidPhone(phone)) throw new IllegalArgumentException("E6. Invalid phone number.");
        this.phone = phone;
    }
    public void setEmail(String email) throws IllegalArgumentException {
        if (!isValidEmail(email)) throw new IllegalArgumentException("E7. Invalid email.");
        this.email = email;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    private static boolean isValidPhone(String phone) {
        if (phone.matches("[0-9]{10,11}")) {
            return true;
        }
        return false;
    }
    private static boolean isValidEmail(String email) {
        //ref: https://stackoverflow.com/questions/46155/how-to-validate-an-email-address-in-javascript
        if (email.matches("\\S+@\\S+\\.\\S+")) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s%s%s%s%s%s%s", this.name, DELIMITER,
                                               this.phone, DELIMITER,
                                               this.email, DELIMITER,
                                               this.address);
    }
}
