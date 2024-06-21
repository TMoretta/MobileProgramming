package com.example.esempio9lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridLayout gameGridGL;
    private Button showBtn;
    private Button resetBtn;
    private ImageView[][] cellsIV;
    private int N = 4;
    private Bitmap bombBitmap, counter0Bitmap, counter1Bitmap, counter2Bitmap, counter3Bitmap, counter4Bitmap, squareBitmap;
    private final int cellWidth = 150, cellHeight = 150;
    private View.OnClickListener cellClickListener;
    private int attemptsCounter;
    private boolean areBombShowed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameGridGL = findViewById(R.id.gameGridGL);
        showBtn = findViewById(R.id.showBtn);
        resetBtn = findViewById(R.id.resetBtn);

        squareBitmap = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.square, null))).getBitmap();
        bombBitmap = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.bomb, null))).getBitmap();
        counter0Bitmap = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.counter0, null))).getBitmap();
        counter1Bitmap = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.counter1, null))).getBitmap();
        counter2Bitmap = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.counter2, null))).getBitmap();
        counter3Bitmap = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.counter3, null))).getBitmap();
        counter4Bitmap = ((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.counter4, null))).getBitmap();
        squareBitmap = Bitmap.createScaledBitmap(squareBitmap, cellWidth, cellHeight, true);
        bombBitmap = Bitmap.createScaledBitmap(bombBitmap, cellWidth, cellHeight, true);
        counter0Bitmap = Bitmap.createScaledBitmap(counter0Bitmap, cellWidth, cellHeight, true);
        counter1Bitmap = Bitmap.createScaledBitmap(counter1Bitmap, cellWidth, cellHeight, true);
        counter2Bitmap = Bitmap.createScaledBitmap(counter2Bitmap, cellWidth, cellHeight, true);
        counter3Bitmap = Bitmap.createScaledBitmap(counter3Bitmap, cellWidth, cellHeight, true);
        counter4Bitmap = Bitmap.createScaledBitmap(counter4Bitmap, cellWidth, cellHeight, true);

        cellClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView cellIV = (ImageView) v;
                String tag = (String) cellIV.getTag();

                switch (tag) {
                    case "bomb":
                        cellIV.setImageBitmap(bombBitmap);
                        Toast.makeText(MainActivity.this, "Hai perso", Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < N; i++) {
                            for (int j = 0; j < N; j++) {
                                cellsIV[i][j].setClickable(false);
                            }
                        }
                        break;
                    case "counter0":
                        cellIV.setImageBitmap(counter0Bitmap);
                        attemptsCounter++;
                        break;
                    case "counter1":
                        cellIV.setImageBitmap(counter1Bitmap);
                        attemptsCounter++;
                        break;
                    case "counter2":
                        cellIV.setImageBitmap(counter2Bitmap);
                        attemptsCounter++;
                        break;
                    case "counter3":
                        cellIV.setImageBitmap(counter3Bitmap);
                        attemptsCounter++;
                        break;
                    case "counter4":
                        cellIV.setImageBitmap(counter4Bitmap);
                        attemptsCounter++;
                        break;
                }

                cellIV.setClickable(false);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(attemptsCounter == (N*N)-N) {
                            Toast.makeText(MainActivity.this, "Hai vinto", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < N; i++) {
                                for (int j = 0; j < N; j++) {
                                    cellsIV[i][j].setClickable(false);
                                    if(cellsIV[i][j].getTag().equals("bomb")) {
                                        cellsIV[i][j].setImageBitmap(bombBitmap);
                                    }
                                }
                            }
                        }
                    }
                });
            }
        };

        initializeGame();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeGame();
            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(areBombShowed) {
                    areBombShowed = false;
                } else {
                    areBombShowed = true;
                }
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if(cellsIV[i][j].getTag().equals("bomb")) {
                            if(areBombShowed) {
                                cellsIV[i][j].setImageBitmap(bombBitmap);
                            } else {
                                cellsIV[i][j].setImageBitmap(squareBitmap);
                            }
                        }
                    }
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.size3) {
            N = 3;
        } else if(id==R.id.size4) {
            N = 4;
        } else if(id==R.id.size5) {
            N = 5;
        } else if(id==R.id.size6) {
            N = 6;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initializeGame();
            }
        });

        return super.onOptionsItemSelected(item);
    }

    public void initializeGame() {

        //Matrice ausiliaria con interi al posto di immagini
        int[][] contents = new int[N][N];

        cellsIV = new ImageView[N][N];
        attemptsCounter = 0;
        areBombShowed = false;

        //Inserisco N bombe in modo random
        for (int i = 0; i < N; i++) {
            Random rnd = new Random();
            boolean isValid;
            do {
                isValid = true;
                int x = rnd.nextInt(N - 1);
                int y = rnd.nextInt(N - 1);

                if (contents[x][y] == 0) {
                    contents[x][y] = -1;
                } else {
                    isValid = false;
                }
            } while (!isValid);
        }
        //Aggiorno counter attorno alla bomba
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (contents[i][j] == -1) {
                    if (i - 1 >= 0 && j - 1 >= 0 && contents[i - 1][j - 1] != -1)
                        contents[i - 1][j - 1]++;
                    if (i - 1 >= 0 && contents[i - 1][j] != -1)
                        contents[i - 1][j]++;
                    if (i - 1 >= 0 && j + 1 <= N - 1 && contents[i - 1][j + 1] != -1)
                        contents[i - 1][j + 1]++;
                    if (j - 1 >= 0 && contents[i][j - 1] != -1)
                        contents[i][j - 1]++;
                    if (j + 1 <= N - 1 && contents[i][j + 1] != -1)
                        contents[i][j + 1]++;
                    if (i + 1 <= N - 1 && j - 1 >= 0 && contents[i + 1][j - 1] != -1)
                        contents[i + 1][j - 1]++;
                    if (i + 1 <= N - 1 && contents[i + 1][j] != -1)
                        contents[i + 1][j]++;
                    if (i + 1 <= N - 1 && j + 1 <= N - 1 && contents[i + 1][j + 1] != -1)
                        contents[i + 1][j + 1]++;
                }
            }
        }
        gameGridGL.removeAllViews();
        //Considerando la matrice con gli interi posso ora settare correttamente le imageView
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ImageView cell = new ImageView(MainActivity.this);
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                lp.width = cellWidth;
                lp.height = cellHeight;
                lp.rowSpec = GridLayout.spec(i);
                lp.columnSpec = GridLayout.spec(j);
                cell.setLayoutParams(lp);
                cell.setClickable(true);
                cell.setImageBitmap(squareBitmap);
                switch (contents[i][j]) {
                    case -1:
                        cell.setTag("bomb");
                        break;
                    case 0:
                        cell.setTag("counter0");
                        break;
                    case 1:
                        cell.setTag("counter1");
                        break;
                    case 2:
                        cell.setTag("counter2");
                        break;
                    case 3:
                        cell.setTag("counter3");
                        break;
                    case 4:
                        cell.setTag("counter4");
                        break;
                }
                cellsIV[i][j] = cell;
                cellsIV[i][j].setOnClickListener(cellClickListener);
                gameGridGL.addView(cellsIV[i][j]);
            }
        }

    }
}