package loader;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.DataObjectHolder> {

    private Context mContext;
    private ArrayList<ContactData> mListContact;
    public static String CONTACT_DATA = "CONTACT_DATA";

    ContactsListAdapter(Context context, ArrayList<ContactData> contacts) {
        mContext = context;
        mListContact = contacts;
    }

    @Override
    public ContactsListAdapter.DataObjectHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        final View view = inflater.inflate(R.layout.contact_item, parent, false);
        return new ContactsListAdapter.DataObjectHolder(view);

    }

    @Override
    public void onBindViewHolder(final ContactsListAdapter.DataObjectHolder holder, final int position) {
        ContactData contactData = mListContact.get(position);
        String displayName = contactData.firstName + " " + contactData.lastName;
        holder.firstName.setText(displayName);
        Glide.with(mContext).load(R.drawable.default_avatar).into(holder.avatar);
        holder.itemView.setOnClickListener(v -> {
            Intent intentToContactMainActivity;
            intentToContactMainActivity = new Intent(mContext, ContactDetailsActivity.class);
            intentToContactMainActivity.putExtra(CONTACT_DATA, contactData);
            mContext.startActivity(intentToContactMainActivity);
        });

    }

    @Override
    public int getItemCount() {
        return mListContact.size();
    }

    class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView firstName;
        ImageView avatar;

        DataObjectHolder(final View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.contact_item_first_name);
            avatar = itemView.findViewById(R.id.contact_item_avatar);
        }
    }

    //Temporary method to find ContactData, maybe should be use
    ContactData findContactById(String contactId) {
        for (ContactData contactDataItem : mListContact) {
            if (contactDataItem.id.equalsIgnoreCase(contactId)) {
                return contactDataItem;
            }

        }
        return null;
    }
}
