package com.example.esempio4lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> words;
    private LinearLayout wordContainerLL;
    private LinearLayout tryContainerLL;
    private EditText tryLetterET;
    private Button tryBtn;
    private TextView attemptsTV;
    private Button hintBtn;
    private Button resetBtn;
    private TextView wrongLettersTV;
    private String word;
    private static int ATTEMPTS_LIMIT = 12;
    private static int guessedLettersCounter = 0;
    private EditText[] lettersOfWordET;
    private char[] guessedLetters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] temp;
        temp = getResources().getStringArray(R.array.words);
        words = new ArrayList<>(Arrays.asList(temp));

        wordContainerLL = findViewById(R.id.wordContainerLL);
        tryContainerLL = findViewById(R.id.tryContainerLL);
        tryLetterET = findViewById(R.id.tryLetterET);
        tryBtn = findViewById(R.id.tryBtn);
        attemptsTV = findViewById(R.id.attemptsTV);
        resetBtn = findViewById(R.id.resetBtn);
        hintBtn = findViewById(R.id.hintBtn);
        wrongLettersTV = findViewById(R.id.wrongLettersTV);

        if (savedInstanceState != null && savedInstanceState.containsKey("AttemptsLimit")) {
            ATTEMPTS_LIMIT = savedInstanceState.getInt("AttemptsLimit");
            if(ATTEMPTS_LIMIT<=5) {
                hintBtn.setEnabled(false);
            }
        }
        String s = "Tentativi: " + ATTEMPTS_LIMIT;
        attemptsTV.setText(s);

        if (savedInstanceState != null && savedInstanceState.containsKey("Word")) {
            word = savedInstanceState.getString("Word");
        } else {
            Random random = new Random();
            word = words.get(random.nextInt(words.size() - 1));
        }
        Log.d("MyTag", "Parola: " + word);

        guessedLetters = new char[word.length()];

        lettersOfWordET = new EditText[word.length()];
        for (int i = 0; i < word.length(); i++) {
            lettersOfWordET[i] = new EditText(getApplicationContext());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(100, 200);
            lettersOfWordET[i].setLayoutParams(lp);
            lettersOfWordET[i].setFocusable(false);
            lettersOfWordET[i].setInputType(InputType.TYPE_NULL);
            wordContainerLL.addView(lettersOfWordET[i]);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey("GuessedLetters") && savedInstanceState.containsKey("GuessedLettersCounter")) {
            guessedLettersCounter = savedInstanceState.getInt("GuessedLettersCounter");
            guessedLetters = savedInstanceState.getCharArray("GuessedLetters");

            for (int i = 0; i < guessedLettersCounter; i++) {

                char letter = guessedLetters[i];
                if (word.contains(String.valueOf(letter))) {
                    int j = 0;
                    for (int z = 0; z < word.length(); z++) {
                        int temp2 = word.indexOf(letter, z);
                        if (temp2 != -1) {
                            lettersOfWordET[temp2].setText(String.valueOf(letter));
                            z = temp2;
                        } else {
                            break;
                        }
                    }
                    wordContainerLL.invalidate();
                }

            }

            if (guessedLettersCounter == word.length()) {
                tryLetterET.setEnabled(false);
                tryBtn.setEnabled(false);
            }

        }

        tryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = tryLetterET.getText().toString().toUpperCase();
                if (!s.equals("")) {
                    char letter = s.charAt(0);
                    if (letter >= 'A' && letter <= 'Z') {
                        if (word.contains(String.valueOf(letter))) {
                            int j = 0;
                            for (int i = 0; i < word.length(); i++) {
                                int temp = word.indexOf(letter, i);
                                if (temp != -1) {
                                    lettersOfWordET[temp].setText(String.valueOf(letter));
                                    i = temp;
                                    guessedLetters[guessedLettersCounter] = letter;
                                    guessedLettersCounter++;
                                } else {
                                    break;
                                }
                            }
                            wordContainerLL.invalidate();
                            tryLetterET.setText("");
                        } else {
                            ATTEMPTS_LIMIT--;
                            attemptsTV.setText("Tentativi: " + String.valueOf(ATTEMPTS_LIMIT));
                            tryLetterET.setText("");
                            String t = wrongLettersTV.getText().toString();
                            t += " " + letter;
                            wrongLettersTV.setText(t);
                        }

                        if(ATTEMPTS_LIMIT<=5) {
                            hintBtn.setEnabled(false);
                        }

                        if (guessedLettersCounter == word.length()) {
                            tryLetterET.setEnabled(false);
                            tryBtn.setEnabled(false);

                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                            alert.setMessage("Hai vinto!");
                            alert.setPositiveButton("Ok",null);
                            alert.create().show();
                        }

                        if (ATTEMPTS_LIMIT == 0) {
                            tryLetterET.setEnabled(false);
                            tryBtn.setEnabled(false);

                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                            alert.setMessage("Hai perso!");
                            alert.setPositiveButton("Ok",null);
                            alert.create().show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Inserisci una lettera", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Inserisci una lettera", Toast.LENGTH_SHORT).show();
                }

            }
        });

        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ATTEMPTS_LIMIT -= 5;
                attemptsTV.setText(String.valueOf(ATTEMPTS_LIMIT));

                boolean isGood = true;
                Random rnd = new Random();
                char hintLetter;
                do {
                    isGood = true;
                    hintLetter = word.charAt(rnd.nextInt(word.length()-1));
                    for(int i=0; i<guessedLettersCounter; i++) {
                        if(hintLetter == guessedLetters[i]) {
                            isGood = false;
                            break;
                        }
                    }
                }while(!isGood);

                String hintStr = "La parola contiene la lettera " + Character.toUpperCase(hintLetter);

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage(hintStr);
                alert.setPositiveButton("Ok",null);
                alert.create().show();

                if(ATTEMPTS_LIMIT<=5) {
                    hintBtn.setEnabled(false);
                }
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("Sei sicuro di voler resettare il gioco?");
                alert.setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ATTEMPTS_LIMIT = 12;
                        attemptsTV.setText("Tentativi: " + String.valueOf(ATTEMPTS_LIMIT));
                        tryLetterET.setEnabled(true);
                        tryBtn.setEnabled(true);
                        wrongLettersTV.setText("");
                        guessedLettersCounter = 0;

                        Random random = new Random();
                        word = words.get(random.nextInt(words.size() - 1));

                        wordContainerLL.removeAllViews();

                        lettersOfWordET = new EditText[word.length()];
                        for (int i = 0; i < word.length(); i++) {
                            lettersOfWordET[i] = new EditText(getApplicationContext());
                            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(100, 200);
                            lettersOfWordET[i].setLayoutParams(lp);
                            lettersOfWordET[i].setFocusable(false);
                            lettersOfWordET[i].setInputType(InputType.TYPE_NULL);
                            wordContainerLL.addView(lettersOfWordET[i]);
                        }

                        Log.d("MyTag", "Parola: " + word);
                    }
                });
                alert.setNegativeButton("No", null);
                alert.create().show();

            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("Word", word);
        outState.putInt("AttemptsLimit", ATTEMPTS_LIMIT);
        String wrongLetters = wrongLettersTV.getText().toString();
        outState.putString("WrongLetters", wrongLetters);
        outState.putInt("GuessedLettersCounter", guessedLettersCounter);
        outState.putCharArray("GuessedLetters", guessedLetters);

        for (char c : guessedLetters) {
            Log.d("MyTag", "Lettera: " + c);
        }

    }
}