package com.example.esempio6lab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private ArrayList<Book> books;
    private Context context;

    public CustomAdapter(Context context, ArrayList<Book> books) {
        this.books = books;
        this.context = context;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Book getItem(int position) {
        return books.get(position);
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
        TextView titleTV = v.findViewById(R.id.titleTV);
        TextView authorTV = v.findViewById(R.id.authorTV);
        ImageView coverIV = v.findViewById(R.id.coverIV);
        titleTV.setText(books.get(position).getTitle());
        authorTV.setText(books.get(position).getAuthor());
        coverIV.setImageBitmap(books.get(position).getCover());

        return v;
    }
}
