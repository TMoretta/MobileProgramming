package com.example.esempio6lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText titleET;
    private EditText authorET;
    private Button insertBtn;
    private ListView listView;
    private CustomAdapter customAdapter;
    private ArrayList<Book> books;
    private Bitmap coverBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleET = findViewById(R.id.titleET);
        insertBtn = findViewById(R.id.insertBtn);
        authorET = findViewById(R.id.authorET);
        listView = findViewById(R.id.listView);
        coverBitmap = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.book, null))).getBitmap();
        coverBitmap = Bitmap.createScaledBitmap(coverBitmap, 90, 90, true);

        books = new ArrayList<>();
        if (savedInstanceState != null && savedInstanceState.containsKey("titles") && savedInstanceState.containsKey("authors") && savedInstanceState.containsKey("covers")) {
            ArrayList<String> titles = savedInstanceState.getStringArrayList("titles");
            ArrayList<String> authors = savedInstanceState.getStringArrayList("authors");
            ArrayList<Bitmap> covers = savedInstanceState.getParcelableArrayList("covers", Bitmap.class);
            for (int i = 0; i < titles.size(); i++) {
                books.add(new Book(titles.get(i), authors.get(i), covers.get(i)));
            }
        }

        customAdapter = new CustomAdapter(MainActivity.this, books);
        listView.setAdapter(customAdapter);

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertBtnClicked();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                books.remove(position);
                customAdapter.notifyDataSetChanged();
            }
        });


    }

    public void insertBtnClicked() {

        String title = titleET.getText().toString();
        String author = authorET.getText().toString();

        if (title.trim().equals("")) {
            Toast.makeText(this, "Titolo non valido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (author.trim().equals("")) {
            Toast.makeText(this, "Autore non valido", Toast.LENGTH_SHORT).show();
            return;
        }

        books.add(new Book(title, author, coverBitmap));
        customAdapter.notifyDataSetChanged();
        titleET.setText("");
        authorET.setText("");

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> authors = new ArrayList<>();
        ArrayList<Bitmap> covers = new ArrayList<>();

        for (int i = 0; i < books.size(); i++) {
            titles.add(books.get(i).getTitle());
            authors.add(books.get(i).getAuthor());
            covers.add(books.get(i).getCover());
        }

        outState.putStringArrayList("titles", titles);
        outState.putStringArrayList("authors", authors);
        outState.putParcelableArrayList("covers", covers);

    }
}