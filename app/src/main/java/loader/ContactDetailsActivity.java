package loader;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Objects;

public class ContactDetailsActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar mToolbar;

    private TextView mFirstName;
    private TextView mLastName;
    private TextView mEmail;
    private TextView mPhone;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState, @Nullable final PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details);
        inflateViews();
        inflateToolbar();
        ContactData contactData = (ContactData) Objects.requireNonNull(getIntent().getExtras()).get(ContactsListAdapter.CONTACT_DATA);
        if (contactData != null) {
            mFirstName.setText(contactData.firstName);
            mLastName.setText(contactData.lastName);

            //TODO get all mails from contact data, not only primary mail
            if (!contactData.listEmails.isEmpty()) {
                mEmail.setText(contactData.listEmails.get(0).address);
            }

            //TODO get all numbers from contact data, not only primary number
            if (!contactData.listOfNumbers.isEmpty()) {
                mPhone.setText(contactData.listOfNumbers.get(0).number);
            }

        }

    }

    void inflateViews() {
        mFirstName = findViewById(R.id.contact_display_screen_name);
        mLastName = findViewById(R.id.contact_display_screen_lastName);
        mEmail = findViewById(R.id.contact_display_screen_email);
        mPhone = findViewById(R.id.contact_display_screen_phone);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(this, ContactListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void inflateToolbar() {
        mToolbar = findViewById(R.id.contact_details_toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.tContactDetails);
    }

}
