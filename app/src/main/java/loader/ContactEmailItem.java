package loader;

import java.io.Serializable;

class ContactEmailItem implements Serializable {

    String address;
    String type;

    ContactEmailItem(String address, String type) {
        this.address = address;
        this.type = type;
    }
}
