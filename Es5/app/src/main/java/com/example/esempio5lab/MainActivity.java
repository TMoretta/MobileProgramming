package com.example.esempio5lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SwitchCompat optionsSC;
    private Button list1Btn;
    private ListView list1LV;
    private Button list2Btn;
    private ListView list2LV;
    private CustomAdapter adapter1;
    private CustomAdapter adapter2;
    private ArrayList<String> names1;
    private ArrayList<String> names2;
    private Option option;
    enum Option {
        DELETE,
        MOVE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        optionsSC = findViewById(R.id.optionsSC);
        list1Btn = findViewById(R.id.list1Btn);
        list1LV = findViewById(R.id.list1LV);
        list2Btn = findViewById(R.id.list2Btn);
        list2LV = findViewById(R.id.list2LV);

        names1 = new ArrayList<>();
        names2 = new ArrayList<>();

        if(savedInstanceState!=null) {
            if(savedInstanceState.containsKey("names1") && savedInstanceState.containsKey("names2")) {
                names1 = savedInstanceState.getStringArrayList("names1");
                names2 = savedInstanceState.getStringArrayList("names2");
            }
        }

        adapter1 = new CustomAdapter(MainActivity.this, names1);
        adapter2 = new CustomAdapter(MainActivity.this, names2);
        list1LV.setAdapter(adapter1);
        list2LV.setAdapter(adapter2);

        option = Option.DELETE;

        optionsSC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    option = Option.MOVE;
                } else {
                    option = Option.DELETE;
                }
            }
        });

        list1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                View alertLayout = getLayoutInflater().inflate(R.layout.form,null);
                alertBuilder.setView(alertLayout);
                EditText nameET = alertLayout.findViewById(R.id.nameET);
                Button okBtn = alertLayout.findViewById(R.id.okBtn);
                AlertDialog alertDialog = alertBuilder.create();
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = nameET.getText().toString();
                        if(name.trim().equals("")) {
                            Toast.makeText(MainActivity.this, "inserisci un nome valido", Toast.LENGTH_SHORT).show();
                        } else {
                            names1.add(name);
                            adapter1.notifyDataSetChanged();
                            alertDialog.dismiss();
                        }
                    }
                });
                alertDialog.show();
            }
        });

        list2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                View alertLayout = getLayoutInflater().inflate(R.layout.form,null);
                alertBuilder.setView(alertLayout);
                EditText nameET = alertLayout.findViewById(R.id.nameET);
                Button okBtn = alertLayout.findViewById(R.id.okBtn);
                AlertDialog alertDialog = alertBuilder.create();
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = nameET.getText().toString();
                        if(name.trim().equals("")) {
                            Toast.makeText(MainActivity.this, "inserisci un nome valido", Toast.LENGTH_SHORT).show();
                        } else {
                            names2.add(name);
                            adapter2.notifyDataSetChanged();
                            alertDialog.dismiss();
                        }
                    }
                });
                alertDialog.show();
            }
        });

        list1LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(option == Option.DELETE) {
                    names1.remove(position);
                    adapter1.notifyDataSetChanged();
                }
                if(option == Option.MOVE) {
                    names2.add(names1.get(position));
                    names1.remove(position);
                    adapter1.notifyDataSetChanged();
                    adapter2.notifyDataSetChanged();
                }
            }
        });

        list2LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(option == Option.DELETE) {
                    names2.remove(position);
                    adapter2.notifyDataSetChanged();
                }
                if(option == Option.MOVE) {
                    names1.add(names2.get(position));
                    names2.remove(position);
                    adapter1.notifyDataSetChanged();
                    adapter2.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("names1", names1);
        outState.putStringArrayList("names2", names2);
    }
}