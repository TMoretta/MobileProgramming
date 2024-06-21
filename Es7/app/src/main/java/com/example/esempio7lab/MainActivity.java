package com.example.esempio7lab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView attemptsTV;
    private Button resetBtn;
    private ArrayList<ImageButton> cardsIB;
    private Bitmap card1Bm, card2Bm, card3Bm, card4Bm, card5Bm, card6Bm, card7Bm, card8Bm, cardBackBm;
    private ImageButton card1IB,card2IB,card3IB,card4IB,card5IB,card6IB,card7IB,card8IB,card9IB,card10IB,card11IB,card12IB,card13IB,card14IB,card15IB,card16IB;
    private int[] memory;
    private static int click;
    private static int attempts;
    private static int guessed;
    private int firstCardTag,secondCardTag;
    private boolean wrap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        attemptsTV = findViewById(R.id.attemptsTV);
        resetBtn = findViewById(R.id.resetBtn);
        card1IB = findViewById(R.id.card1IB);
        card2IB = findViewById(R.id.card2IB);
        card3IB = findViewById(R.id.card3IB);
        card4IB = findViewById(R.id.card4IB);
        card5IB = findViewById(R.id.card5IB);
        card6IB = findViewById(R.id.card6IB);
        card7IB = findViewById(R.id.card7IB);
        card8IB = findViewById(R.id.card8IB);
        card9IB = findViewById(R.id.card9IB);
        card10IB = findViewById(R.id.card10IB);
        card11IB = findViewById(R.id.card11IB);
        card12IB = findViewById(R.id.card12IB);
        card13IB = findViewById(R.id.card13IB);
        card14IB = findViewById(R.id.card14IB);
        card15IB = findViewById(R.id.card15IB);
        card16IB = findViewById(R.id.card16IB);

        cardsIB = new ArrayList<>();
        cardsIB.add(card1IB);
        cardsIB.add(card2IB);
        cardsIB.add(card3IB);
        cardsIB.add(card4IB);
        cardsIB.add(card5IB);
        cardsIB.add(card6IB);
        cardsIB.add(card7IB);
        cardsIB.add(card8IB);
        cardsIB.add(card9IB);
        cardsIB.add(card10IB);
        cardsIB.add(card11IB);
        cardsIB.add(card12IB);
        cardsIB.add(card13IB);
        cardsIB.add(card14IB);
        cardsIB.add(card15IB);
        cardsIB.add(card16IB);

        cardBackBm = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.card_back, null))).getBitmap();
        card1Bm = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.card1, null))).getBitmap();
        card2Bm = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.card2, null))).getBitmap();
        card3Bm = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.card3, null))).getBitmap();
        card4Bm = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.card4, null))).getBitmap();
        card5Bm = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.card5, null))).getBitmap();
        card6Bm = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.card6, null))).getBitmap();
        card7Bm = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.card7, null))).getBitmap();
        card8Bm = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.card8, null))).getBitmap();
        cardBackBm = Bitmap.createScaledBitmap(cardBackBm,80,95,false);
        card1Bm = Bitmap.createScaledBitmap(card1Bm,80,95,false);
        card2Bm = Bitmap.createScaledBitmap(card2Bm,80,95,false);
        card3Bm = Bitmap.createScaledBitmap(card3Bm,80,95,false);
        card4Bm = Bitmap.createScaledBitmap(card4Bm,80,95,false);
        card5Bm = Bitmap.createScaledBitmap(card5Bm,80,95,false);
        card6Bm = Bitmap.createScaledBitmap(card6Bm,80,95,false);
        card7Bm = Bitmap.createScaledBitmap(card7Bm,80,95,false);
        card8Bm = Bitmap.createScaledBitmap(card8Bm,80,95,false);

        //Initialization
        initializeGame();

        for(ImageButton ib : cardsIB) {
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ImageButton ib = (ImageButton) v;
                    //Card tag (da 0 a 15)
                    int tag = Integer.parseInt(Objects.toString(ib.getTag()))-1;

                    switch(memory[tag]) {
                        case 1:
                            ib.setImageBitmap(card1Bm);
                            break;
                        case 2:
                            ib.setImageBitmap(card2Bm);
                            break;
                        case 3:
                            ib.setImageBitmap(card3Bm);
                            break;
                        case 4:
                            ib.setImageBitmap(card4Bm);
                            break;
                        case 5:
                            ib.setImageBitmap(card5Bm);
                            break;
                        case 6:
                            ib.setImageBitmap(card6Bm);
                            break;
                        case 7:
                            ib.setImageBitmap(card7Bm);
                            break;
                        case 8:
                            ib.setImageBitmap(card8Bm);
                            break;
                    }

                    if(click==0) {
                        if(tag!=firstCardTag) {
                            if(wrap) {
                                cardsIB.get(firstCardTag).setImageBitmap(cardBackBm);
                            }
                        }
                        if(tag!=secondCardTag) {
                            if(wrap) {
                                cardsIB.get(secondCardTag).setImageBitmap(cardBackBm);
                            }
                        }

                        firstCardTag = tag;
                        cardsIB.get(firstCardTag).setClickable(false);
                        click = 1;
                    } else {
                        secondCardTag = tag;
                        cardsIB.get(firstCardTag).setClickable(true);
                        if(memory[secondCardTag] == memory[firstCardTag]) {
                            cardsIB.get(firstCardTag).setClickable(false);
                            cardsIB.get(secondCardTag).setClickable(false);
                            guessed++;

                            wrap = false;

                            if(guessed==8) {
                                Toast.makeText(MainActivity.this, "Gioco completato", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            wrap = true;
                        }
                        click = 0;
                        attempts++;
                        attemptsTV.setText(String.valueOf(attempts));
                    }

                }
            });
        }

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeGame();
            }
        });

    }

    public void initializeGame() {
        memory = new int[16];
        click = 0;
        attempts = 0;
        guessed = 0;
        attemptsTV.setText(String.valueOf(attempts));

        ArrayList<Integer> tempPos = new ArrayList<>();
        for(int i=0; i<16; i++) {
            tempPos.add(i);
        }

        Random rnd = new Random();
        for(int i=1 ; i<=8; i++) {
            int x = rnd.nextInt(tempPos.size()-1);
            memory[tempPos.get(x)] = i;
            tempPos.remove(x);
            if(i==8) {
                x = 0;
            } else {
                x = rnd.nextInt(tempPos.size()-1);
            }
            memory[tempPos.get(x)] = i;
            tempPos.remove(x);
        }

        for(ImageButton ib : cardsIB) {
            ib.setImageBitmap(cardBackBm);
        }

        for(ImageButton t : cardsIB) {
            t.setClickable(true);
        }

        wrap = false;
    }

}