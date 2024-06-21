package com.example.esempio3lab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RankingActivity extends AppCompatActivity {

    private TextView firstPlaceTV;
    private TextView secondPlaceTV;
    private TextView thirdPlaceTV;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        firstPlaceTV = findViewById(R.id.firstPlaceTV);
        secondPlaceTV = findViewById(R.id.secondPlaceTV);
        thirdPlaceTV = findViewById(R.id.thirdPlaceTV);

        sharedPreferences = getSharedPreferences("Ranking", Context.MODE_PRIVATE);
        String firstPlace = sharedPreferences.getString("firstPlace",null);
        String secondPlace = sharedPreferences.getString("secondPlace",null);
        String thirdPlace = sharedPreferences.getString("thirdPlace",null);

        if(firstPlace!=null) {
            firstPlaceTV.setText(firstPlace);
            secondPlaceTV.setText(secondPlace);
            thirdPlaceTV.setText(thirdPlace);
        } else if(secondPlace!=null) {
            secondPlaceTV.setText(secondPlace);
            thirdPlaceTV.setText(thirdPlace);
        } else if(thirdPlace!=null) {
            thirdPlaceTV.setText(thirdPlace);
        }

    }
}
