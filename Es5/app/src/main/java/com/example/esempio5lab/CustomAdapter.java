package com.example.esempio5lab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private ArrayList<String> names;
    private Context context;

    public CustomAdapter(Context context, ArrayList<String> names) {
        this.names = names;
        this.context = context;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public String getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_list_view, parent, false);
        }
        TextView nameTV = v.findViewById(R.id.nameTV);
        String name = names.get(position);
        nameTV.setText(name);

        return v;
    }
}
