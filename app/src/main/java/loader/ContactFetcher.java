package loader;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.content.CursorLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class ContactFetcher {

    private final Context mContext;

    ContactFetcher(Context c) {
        this.mContext = c;
    }

    ArrayList<ContactData> fetchAll() {

        String[] projectionFields = new String[] {
                ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME,
                };
        ArrayList<ContactData> listContacts = new ArrayList<>();
        CursorLoader cursorLoader = new CursorLoader(mContext, ContactsContract.Contacts.CONTENT_URI, projectionFields, null, null, null);

        Cursor c = cursorLoader.loadInBackground();

        final Map<String, ContactData> contactsMap = new HashMap<>(c.getCount());

        if (c.moveToFirst()) {

            int idIndex = c.getColumnIndex(ContactsContract.Contacts._ID);
            int nameIndex = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            do {
                String contactId = c.getString(idIndex);
                String fullDisplayName = c.getString(nameIndex);
                String[] splitedDisplayName = fullDisplayName.split("\\s+");
                String firstname = splitedDisplayName[0];
                String lastName = "";
                if (splitedDisplayName.length > 1) {
                    lastName = splitedDisplayName[1];
                }
                ContactData contactData = new ContactData(contactId, firstname, lastName);
                contactsMap.put(contactId, contactData);
                listContacts.add(contactData);
            } while (c.moveToNext());
        }

        c.close();

        matchContactNumbers(contactsMap);
        matchContactEmails(contactsMap);

        return listContacts;
    }

    private void matchContactNumbers(Map<String, ContactData> contactsMap) {
        final String[] numberProjection = new String[] {
                Phone.NUMBER, Phone.TYPE, Phone.CONTACT_ID,
                };

        Cursor phone = new CursorLoader(mContext, Phone.CONTENT_URI, numberProjection, null, null, null).loadInBackground();

        if (phone.moveToFirst()) {
            final int contactNumberColumnIndex = phone.getColumnIndex(Phone.NUMBER);
            final int contactTypeColumnIndex = phone.getColumnIndex(Phone.TYPE);
            final int contactIdColumnIndex = phone.getColumnIndex(Phone.CONTACT_ID);

            while (!phone.isAfterLast()) {
                final String number = phone.getString(contactNumberColumnIndex);
                final String contactId = phone.getString(contactIdColumnIndex);
                ContactData contactData = contactsMap.get(contactId);
                if (contactData == null) {
                    continue;
                }
                final int type = phone.getInt(contactTypeColumnIndex);
                String customLabel = "Custom";
                CharSequence phoneType = Phone.getTypeLabel(mContext.getResources(), type, customLabel);
                contactData.addNumber(number, phoneType.toString());
                phone.moveToNext();
            }
        }

        phone.close();
    }

    private void matchContactEmails(Map<String, ContactData> contactsMap) {
        final String[] emailProjection = new String[] {
                Email.DATA, Email.TYPE, Email.CONTACT_ID,
                };

        Cursor email = new CursorLoader(mContext, Email.CONTENT_URI, emailProjection, null, null, null).loadInBackground();

        if (email.moveToFirst()) {
            final int contactEmailColumnIndex = email.getColumnIndex(Email.DATA);
            final int contactTypeColumnIndex = email.getColumnIndex(Email.TYPE);
            final int contactIdColumnsIndex = email.getColumnIndex(Email.CONTACT_ID);

            while (!email.isAfterLast()) {
                final String address = email.getString(contactEmailColumnIndex);
                final String contactId = email.getString(contactIdColumnsIndex);
                final int type = email.getInt(contactTypeColumnIndex);
                String customLabel = "Custom";
                ContactData contactData = contactsMap.get(contactId);
                if (contactData == null) {
                    continue;
                }
                CharSequence emailType = Email.getTypeLabel(mContext.getResources(), type, customLabel);
                contactData.addEmail(address, emailType.toString());
                email.moveToNext();
            }
        }

        email.close();
    }
}
