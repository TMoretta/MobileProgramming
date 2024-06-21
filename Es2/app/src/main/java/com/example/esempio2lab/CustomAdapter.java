package com.example.esempio2lab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<Contact> contacts;

    public CustomAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;

    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Contact getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        if(v==null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_list_view, parent, false);
        }

        TextView firstLastNameTV = v.findViewById(R.id.firstLastNameTV);
        TextView phoneTV = v.findViewById(R.id.phoneTV);
        ImageView pictureIV = v.findViewById(R.id.pictureIV);

        String s = contacts.get(position).getFirstName() + " " + contacts.get(position).getLastName();
        firstLastNameTV.setText(s);
        s = contacts.get(position).getPhone();
        phoneTV.setText(s);
        pictureIV.setImageBitmap(contacts.get(position).getPicture());

        return v;
    }
}
