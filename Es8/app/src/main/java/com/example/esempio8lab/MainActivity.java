package com.example.esempio8lab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ImageView imageView;
    private MyAdapter myAdapter;
    private ProgressBar progressBar;
    private ArrayList<String> texts;
    private ArrayList<Bitmap> images;
    private boolean isClickable;
    private int currentImagePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);
        texts = new ArrayList<>();
        images = new ArrayList<>();

        currentImagePosition = -1;
        isClickable = true;

        //Inizializzazione testi
        for (int i = 0; i <= 14; i++) {
            texts.add("Image " + i);
        }

        //Inizializzazione immagini
        Bitmap image = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.image0, null))).getBitmap();
        images.add(image);
        image = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.image1, null))).getBitmap();
        images.add(image);
        image = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.image2, null))).getBitmap();
        images.add(image);
        image = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.image3, null))).getBitmap();
        images.add(image);
        image = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.image4, null))).getBitmap();
        images.add(image);
        image = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.image5, null))).getBitmap();
        images.add(image);
        image = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.image6, null))).getBitmap();
        images.add(image);
        image = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.image7, null))).getBitmap();
        images.add(image);
        image = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.image8, null))).getBitmap();
        images.add(image);
        image = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.image9, null))).getBitmap();
        images.add(image);
        image = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.image10, null))).getBitmap();
        images.add(image);
        image = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.image11, null))).getBitmap();
        images.add(image);
        image = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.image12, null))).getBitmap();
        images.add(image);
        image = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.image13, null))).getBitmap();
        images.add(image);
        image = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.image14, null))).getBitmap();
        images.add(image);


        myAdapter = new MyAdapter(MainActivity.this, texts, images);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(!isClickable) {
                    return;
                }
                isClickable = false;

                if(position == currentImagePosition) {
                    return;
                }
                currentImagePosition = position;

                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.VISIBLE);
                            }
                        });

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(images.get(position));
                                progressBar.setVisibility(View.INVISIBLE);
                                isClickable = true;
                            }
                        });
                    }
                });
            }

        });

    }

}