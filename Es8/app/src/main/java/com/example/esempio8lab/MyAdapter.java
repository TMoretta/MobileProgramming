package com.example.esempio8lab;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private ArrayList<String> texts;
    private Context context;
    private ArrayList<Bitmap> images;

    public MyAdapter(Context context, ArrayList<String> texts, ArrayList<Bitmap> images) {
        this.context = context;
        this.texts = texts;
        this.images = images;
    }

    @Override
    public int getCount() {
        return texts.size();
    }

    @Override
    public String getItem(int position) {
        return texts.get(position);
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

        TextView itemTV = v.findViewById(R.id.itemTV);
        ImageView itemIV = v.findViewById(R.id.itemIV);
        String s = texts.get(position);
        itemTV.setText(s);
        itemIV.setImageBitmap(Bitmap.createScaledBitmap(images.get(position), 50, 50, true));

        return v;
    }
}
