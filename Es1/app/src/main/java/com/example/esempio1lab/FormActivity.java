package com.example.esempio1lab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FormActivity extends AppCompatActivity {

    private EditText nameET;
    private Button sendBtn;
    private int moves;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        nameET = findViewById(R.id.nameET);
        sendBtn = findViewById(R.id.sendBtn);

        Intent i = getIntent();
        moves = i.getIntExtra("BestMoves",0);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameET.getText().toString();

                if(!name.equals("")) {
                    SharedPreferences sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("BestPlayer",name);
                    editor.putInt("BestMoves",moves);
                    editor.apply();
                    onBackPressed();
                } else {
                    Toast.makeText(FormActivity.this, "Inserisci nome valido", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
