package loader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class ContactListActivity extends AppCompatActivity {

    ArrayList<ContactData> listOfContacts;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity_main);
        setTitle(R.string.tContactList);

        listOfContacts = new ContactFetcher(this).fetchAll();
        Collections.sort(listOfContacts, (s1, s2) -> s1.firstName.compareToIgnoreCase(s2.firstName));
        mRecyclerView = findViewById(R.id.contact_recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        ContactsListAdapter adapterContacts = new ContactsListAdapter(this, listOfContacts);
        mRecyclerView.setAdapter(adapterContacts);

    }

}
