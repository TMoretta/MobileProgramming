package com.example.esempio10lab;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class TrisFragment extends Fragment {

    private GridLayout trisGL;
    private String symbol;
    private String otherSymbol;
    private static String turn;
    private TrisFragment otherTris;
    private ArrayList<TextView> cellsTV;
    private Context context;

    public TrisFragment(Context context, String symbol, String otherSymbol) {
        this.context = context;
        this.symbol = symbol;
        this.otherSymbol = otherSymbol;
        turn = "UNDEFINED";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tris, container, false);
        trisGL = v.findViewById(R.id.trisGL);
        cellsTV = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            cellsTV.add((TextView) trisGL.getChildAt(i));
        }

        View.OnClickListener cellClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (turn.equals("UNDEFINED")) {
                    turn = symbol;
                }

                TextView cell = (TextView) v;
                if (turn.equals(symbol)) {
                    cell.setText(symbol);
                    cell.setClickable(false);
                    int index = trisGL.indexOfChild(cell);
                    TextView otherCell = (TextView) otherTris.trisGL.getChildAt(index);
                    otherCell.setText(symbol);
                    otherCell.setClickable(false);

                    int[][] winConditions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

                    for (int i = 0; i <= 7; i++) {

                        //Controllo se ho vinto
                        if (((TextView) trisGL.getChildAt(winConditions[i][0])).getText().toString().equals(symbol) &&
                                ((TextView) trisGL.getChildAt(winConditions[i][1])).getText().toString().equals(symbol) &&
                                ((TextView) trisGL.getChildAt(winConditions[i][2])).getText().toString().equals(symbol)) {

                            Toast.makeText(context, "Player " + symbol + " ha vinto", Toast.LENGTH_SHORT).show();

                            for (TextView tv : cellsTV) {
                                tv.setClickable(false);
                            }
                            ArrayList<TextView> otherCellsTV = new ArrayList<>();
                            for (int j = 0; j < 9; j++) {
                                otherCellsTV.add((TextView) otherTris.trisGL.getChildAt(i));
                            }
                            for (TextView tv : otherCellsTV) {
                                tv.setClickable(false);
                            }
                        }
                    }

                } else {
                    return;
                }

                if (turn.equals(symbol)) {
                    turn = otherSymbol;
                } else {
                    turn = symbol;
                }

            }
        };

        for (TextView cell : cellsTV) {
            cell.setOnClickListener(cellClickListener);
        }

        return v;
    }

    public void setOtherTris(TrisFragment otherTris) {
        this.otherTris = otherTris;
    }

    public void reset() {
        turn = "UNDEFINED";
        for (TextView cell : cellsTV) {
            cell.setClickable(true);
            cell.setText("");
        }
    }
}
