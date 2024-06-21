package com.example.esempio3lab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private Button decreaseBtn;
    private Button increaseBtn;
    private TextView counterTV;
    private Button startBtn;
    private Button finishBtn;
    private Button rankingBtn;
    private static int counter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        decreaseBtn = findViewById(R.id.decreaseBtn);
        increaseBtn = findViewById(R.id.increaseBtn);
        counterTV = findViewById(R.id.counterTV);
        startBtn = findViewById(R.id.startBtn);
        finishBtn = findViewById(R.id.finishBtn);
        rankingBtn = findViewById(R.id.rankingBtn);

        sharedPreferences = getSharedPreferences("Ranking", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        counter = 0;
        updateCounterTextView();
        increaseBtn.setEnabled(false);
        decreaseBtn.setEnabled(false);
        finishBtn.setEnabled(false);

        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                updateCounterTextView();
            }
        });

        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                updateCounterTextView();
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseBtn.setEnabled(true);
                decreaseBtn.setEnabled(true);
                finishBtn.setEnabled(true);
                startBtn.setEnabled(false);
                rankingBtn.setEnabled(false);
                counter = 0;
                updateCounterTextView();
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishBtn.setEnabled(false);
                startBtn.setEnabled(true);
                rankingBtn.setEnabled(true);

                String firstPlace = sharedPreferences.getString("firstPlace",null);
                int firstPlaceCounter = sharedPreferences.getInt("firstPlaceCounter",0);
                String secondPlace = sharedPreferences.getString("secondPlace",null);
                int secondPlaceCounter = sharedPreferences.getInt("secondPlaceCounter",0);
                String thirdPlace = sharedPreferences.getString("thirdPlace",null);
                int thirdPlaceCounter = sharedPreferences.getInt("thirdPlaceCounter",0);

                if(firstPlace==null || secondPlace==null || thirdPlace==null || counter>firstPlaceCounter || counter>secondPlaceCounter || counter>thirdPlaceCounter) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    View alertLayout = getLayoutInflater().inflate(R.layout.form,null);
                    EditText nameET = alertLayout.findViewById(R.id.nameET);
                    alert.setView(alertLayout);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String name = nameET.getText().toString();
                            if(!name.equals("")) {
                                if(firstPlace==null) {
                                    editor.putString("firstPlace",name);
                                    editor.putInt("firstPlaceCounter",counter);
                                } else if(secondPlace==null) {
                                    editor.putString("secondPlace",name);
                                    editor.putInt("secondPlaceCounter",counter);
                                } else if(thirdPlace==null) {
                                    editor.putString("thirdPlace",name);
                                    editor.putInt("thirdPlaceCounter",counter);
                                } else {
                                    //TODO posizionare in classifica
                                }
                                editor.apply();
                            } else {
                                Toast.makeText(MainActivity.this, "Nome non valido", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alert.show();
                }


            }
        });

        rankingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getApplicationContext(), RankingActivity.class);
                startActivity(i);
            }
        });


    }

    public void updateCounterTextView() {
        counterTV.setText(String.valueOf(counter));
    }

}