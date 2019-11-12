public class Contact {
    private String name = "";
    private String phone = "";
    private String email = "";
    private String address = "";
    Contact (String rawInformation) throws IllegalArgumentException {
        String[] userInformation = rawInformation.split("; ");
        if (userInformation.length != 4) throw new IllegalArgumentException("Invalid contact information.");
        if (!isValidName(userInformation[0])) throw new IllegalArgumentException("Invalid name.");
        if (!isValidPhone(userInformation[1])) throw new IllegalArgumentException("Invalid phone number.");
        if (!isValidEmail(userInformation[2])) throw new IllegalArgumentException("Invalid email.");
        if (!isValidAddress(userInformation[3])) throw new IllegalArgumentException("Invalid address.");
        this.name = userInformation[0];
        this.phone = userInformation[1];
        this.email = userInformation[2];
        this.address = userInformation[3];
    }
    Contact (String name, String phone, String email, String address) throws IllegalArgumentException {
        if (!isValidName(name)) throw new IllegalArgumentException("Invalid name.");
        if (!isValidPhone(phone)) throw new IllegalArgumentException("Invalid phone number.");
        if (!isValidEmail(email)) throw new IllegalArgumentException("Invalid email.");
        if (!isValidAddress(address)) throw new IllegalArgumentException("Invalid address.");
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
    public void setName(String name) throws IllegalArgumentException {
        if (!isValidName(name)) throw new IllegalArgumentException("Invalid name.");
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
    public void setAddress(String address) throws IllegalArgumentException {
        if (!isValidAddress(address)) throw new IllegalArgumentException("Invalid address.");
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
        return String.format("%s;%s;%s;%s", this.name,
                                            this.phone,
                                            this.email,
                                            this.address);
    }


}
