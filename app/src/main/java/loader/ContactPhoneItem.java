package loader;

import java.io.Serializable;

public class ContactPhoneItem implements Serializable {

    public String number;
    String type;

    ContactPhoneItem(String number, String type) {
        this.number = number;
        this.type = type;
    }
}
