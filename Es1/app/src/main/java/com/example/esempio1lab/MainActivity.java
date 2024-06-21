package com.example.esempio1lab;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView movesTV;
    private RelativeLayout root;
    private GridLayout gameGridGL;
    private Button resetBtn;
    private TextView[] numbersTV;
    private Button[] buttonsBtn;
    private TextView bestPlayerTV;
    private TextView bestMovesTV;
    private int[][] numbers;
    private Button recapBtn;
    private int[][] initialNumbers;
    private int moves;
    private ArrayList<Integer> movesStory;
    private boolean completed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = findViewById(R.id.root);
        bestPlayerTV = findViewById(R.id.bestPlayerTV);
        bestMovesTV = findViewById(R.id.bestMovesTV);
        movesTV = findViewById(R.id.movesTV);
        recapBtn = findViewById(R.id.recapBtn);
        gameGridGL = findViewById(R.id.gameGridGL);
        resetBtn = findViewById(R.id.resetBtn);
        numbersTV = new TextView[9];
        buttonsBtn = new Button[12];
        numbersTV[0] = findViewById(R.id.n1TV);
        numbersTV[1] = findViewById(R.id.n2TV);
        numbersTV[2] = findViewById(R.id.n3TV);
        numbersTV[3] = findViewById(R.id.n4TV);
        numbersTV[4] = findViewById(R.id.n5TV);
        numbersTV[5] = findViewById(R.id.n6TV);
        numbersTV[6] = findViewById(R.id.n7TV);
        numbersTV[7] = findViewById(R.id.n8TV);
        numbersTV[8] = findViewById(R.id.n9TV);
        buttonsBtn[0] = findViewById(R.id.up1Btn);
        buttonsBtn[1] = findViewById(R.id.up2Btn);
        buttonsBtn[2] = findViewById(R.id.up3Btn);
        buttonsBtn[3] = findViewById(R.id.left1Btn);
        buttonsBtn[4] = findViewById(R.id.left2Btn);
        buttonsBtn[5] = findViewById(R.id.left3Btn);
        buttonsBtn[6] = findViewById(R.id.right1Btn);
        buttonsBtn[7] = findViewById(R.id.right2Btn);
        buttonsBtn[8] = findViewById(R.id.right3Btn);
        buttonsBtn[9] = findViewById(R.id.down1Btn);
        buttonsBtn[10] = findViewById(R.id.down2Btn);
        buttonsBtn[11] = findViewById(R.id.down3Btn);

        initializeGame();

        for (Button button : buttonsBtn) {
            button.setOnClickListener(gameButtonsListener);
        }

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeGame();
            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences("Pref",Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.apply();
        String bestPlayer = sharedPreferences.getString("BestPlayer",null);
        int bestMoves = sharedPreferences.getInt("BestMoves",-1);
        if(bestPlayer!=null && bestMoves!=-1) {
            bestPlayerTV.setText(bestPlayer);
            bestMovesTV.setText(String.valueOf(bestMoves));
            bestPlayerTV.invalidate();
            bestMovesTV.invalidate();
        }

        recapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(Button b : buttonsBtn) {
                    b.setEnabled(true);
                }

                moves = 0;
                movesTV.setText(String.valueOf(moves));

                //Reimpostare condizione array iniziale
                int counter = 0;
                for (int i=0; i<3; i++) {
                    for(int j=0; j<3; j++) {
                        numbers[i][j] = initialNumbers[i][j];
                        numbersTV[counter].setText(String.valueOf(numbers[i][j]));
                        counter++;
                    }
                }

                root.invalidate();

                for(Integer moveId : movesStory) {
                    Button b = findViewById(moveId);
                    b.performClick();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }

    public void initializeGame() {

        movesStory = new ArrayList<>();

        recapBtn.setVisibility(View.INVISIBLE);

        for(Button b : buttonsBtn) {
            b.setEnabled(true);
        }

        moves = 0;
        movesTV.setText(String.valueOf(moves));

        ArrayList<Integer> tempNumbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            tempNumbers.add(i);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("Pref",Context.MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
                sharedPreferences = getSharedPreferences("Pref",Context.MODE_PRIVATE);
                String bestPlayer = sharedPreferences.getString("BestPlayer",null);
                int bestMoves = sharedPreferences.getInt("BestMoves",-1);
                if(bestPlayer!=null && bestMoves!=-1) {
                    bestPlayerTV.setText(bestPlayer);
                    bestMovesTV.setText(String.valueOf(bestMoves));
                    bestPlayerTV.invalidate();
                    bestMovesTV.invalidate();
                }
            }
        });

        Random rnd = new Random();
        numbers = new int[3][3];

//        int counter = 0;
//        for (int i=0; i<3; i++) {
//            for(int j=0; j<3; j++) {
//                int index = rnd.nextInt(tempNumbers.size());
//                numbers[i][j] = tempNumbers.get(index);
//                tempNumbers.remove(index);
//                numbersTV[counter].setText(String.valueOf(numbers[i][j]));
//                counter++;
//            }
//        }
//        initialNumbers = numbers;

        //TESTING
        int counter = 0;
        for (int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                numbers[i][j] = counter + 1;
                numbersTV[counter].setText(String.valueOf(numbers[i][j]));
                counter++;
            }
        }

        initialNumbers = new int[3][3];
        for (int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                initialNumbers[i][j] = numbers[i][j];
            }
        }
    }


    View.OnClickListener gameButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int temp;
            moves++;
            movesTV.setText(String.valueOf(moves));
            for(Button b : buttonsBtn) {
                b.setEnabled(true);
            }

            if(v.getId() == R.id.up1Btn) {
                temp = numbers[0][0];
                numbers[0][0] = numbers[1][0];
                numbers[1][0] = numbers[2][0];
                numbers[2][0] = temp;
                if(!completed) {
                    movesStory.add(R.id.up1Btn);
                }
            }
            else
            if(v.getId() == R.id.up2Btn) {
                temp = numbers[0][1];
                numbers[0][1] = numbers[1][1];
                numbers[1][1] = numbers[2][1];
                numbers[2][1] = temp;
                if(!completed) {
                    movesStory.add(R.id.up2Btn);
                }
            }
            else
            if(v.getId() == R.id.up3Btn) {
                temp = numbers[0][2];
                numbers[0][2] = numbers[1][2];
                numbers[1][2] = numbers[2][2];
                numbers[2][2] = temp;
                if(!completed) {
                    movesStory.add(R.id.up3Btn);
                }
            }
            else
            if(v.getId() == R.id.left1Btn) {
                temp = numbers[0][0];
                numbers[0][0] = numbers[0][1];
                numbers[0][1] = numbers[0][2];
                numbers[0][2] = temp;
                if(!completed) {
                    movesStory.add(R.id.left1Btn);
                }
            }
            else
            if(v.getId() == R.id.left2Btn) {
                temp = numbers[1][0];
                numbers[1][0] = numbers[1][1];
                numbers[1][1] = numbers[1][2];
                numbers[1][2] = temp;
                if(!completed) {
                    movesStory.add(R.id.left2Btn);
                }
            }
            else
            if(v.getId() == R.id.left3Btn) {
                temp = numbers[2][0];
                numbers[2][0] = numbers[2][1];
                numbers[2][1] = numbers[2][2];
                numbers[2][2] = temp;
                if(!completed) {
                    movesStory.add(R.id.left3Btn);
                }
            }
            else
            if(v.getId() == R.id.right1Btn) {
                temp = numbers[0][2];
                numbers[0][2] = numbers[0][1];
                numbers[0][1] = numbers[0][0];
                numbers[0][0] = temp;
                if(!completed) {
                    movesStory.add(R.id.right1Btn);
                }
            }
            else
            if(v.getId() == R.id.right2Btn) {
                temp = numbers[1][2];
                numbers[1][2] = numbers[1][1];
                numbers[1][1] = numbers[1][0];
                numbers[1][0] = temp;
                if(!completed) {
                    movesStory.add(R.id.right2Btn);
                }
            }
            else
            if(v.getId() == R.id.right3Btn) {
                temp = numbers[2][2];
                numbers[2][2] = numbers[2][1];
                numbers[2][1] = numbers[2][0];
                numbers[2][0] = temp;
                if(!completed) {
                    movesStory.add(R.id.right3Btn);
                }
            }
            else
            if(v.getId() == R.id.down1Btn) {
                temp = numbers[2][0];
                numbers[2][0] = numbers[1][0];
                numbers[1][0] = numbers[0][0];
                numbers[0][0] = temp;
                if(!completed) {
                    movesStory.add(R.id.down1Btn);
                }
            }
            else
            if(v.getId() == R.id.down2Btn) {
                temp = numbers[2][1];
                numbers[2][1] = numbers[1][1];
                numbers[1][1] = numbers[0][1];
                numbers[0][1] = temp;
                if(!completed) {
                    movesStory.add(R.id.down2Btn);
                }
            }
            else
            if(v.getId() == R.id.down3Btn) {
                temp = numbers[2][2];
                numbers[2][2] = numbers[1][2];
                numbers[1][2] = numbers[0][2];
                numbers[0][2] = temp;
                if(!completed) {
                    movesStory.add(R.id.down3Btn);
                }
            }


            int counter = 0;
            for (int i=0; i<3; i++) {
                for(int j=0; j<3; j++) {
                    numbersTV[counter].setText(String.valueOf(numbers[i][j]));
                    counter++;
                }
            }

            completed = true;
            counter = 0;
            for (int i=0; i<3; i++) {
                for(int j=0; j<3; j++) {
                    if(numbers[i][j] != (counter+1)) {
                        completed = false;
                        break;
                    }
                    counter++;
                }
            }

            if(completed) {
                Toast.makeText(MainActivity.this,"Hai completato il gioco",Toast.LENGTH_LONG).show();

                for(Button b : buttonsBtn) {
                    b.setEnabled(false);
                }

                if(!bestMovesTV.getText().toString().equals("")) {
                    int m = Integer.parseInt(bestMovesTV.getText().toString());

                    if(moves<m) {
                        Intent i = new Intent();
                        i.setClass(MainActivity.this,FormActivity.class);
                        i.putExtra("BestMoves",moves);
                        startActivity(i);
                    }
                } else {
                    Intent i = new Intent();
                    i.setClass(MainActivity.this,FormActivity.class);
                    i.putExtra("BestMoves",moves);
                    startActivity(i);
                }
//                recapBtn.setVisibility(View.VISIBLE);

            }
        }
    };


}