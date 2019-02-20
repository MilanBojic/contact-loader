package loader;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactData implements Serializable {

    public String id;
    String firstName;
    String lastName;
    ArrayList<ContactEmailItem> listEmails;
    ArrayList<ContactPhoneItem> listOfNumbers;

    ContactData(String id, String name, String lastName) {
        this.id = id;
        this.firstName = name;
        this.lastName = lastName;
        this.listEmails = new ArrayList<>();
        this.listOfNumbers = new ArrayList<>();
    }

    @Override
    public String toString() {
        String result = firstName;
        if (listOfNumbers.size() > 0) {
            ContactPhoneItem number = listOfNumbers.get(0);
            result += " (" + number.number + " - " + number.type + ")";
        }
        if (listEmails.size() > 0) {
            ContactEmailItem email = listEmails.get(0);
            result += " [" + email.address + " - " + email.type + "]";
        }
        return result;
    }

    void addEmail(String address, String type) {
        listEmails.add(new ContactEmailItem(address, type));
    }

    void addNumber(String number, String type) {
        listOfNumbers.add(new ContactPhoneItem(number, type));
    }

}
