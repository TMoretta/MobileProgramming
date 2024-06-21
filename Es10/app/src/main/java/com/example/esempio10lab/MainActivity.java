package com.example.esempio10lab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tris1HeaderTV;
    private TextView tris2HeaderTV;
    private FrameLayout tris1FL;
    private FrameLayout tris2FL;
    private Button resetBtn;
    private TrisFragment tris1Fragment;
    private TrisFragment tris2Fragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tris1HeaderTV = findViewById(R.id.tris1HeaderTV);
        tris2HeaderTV = findViewById(R.id.tris2HeaderTV);
        tris1FL = findViewById(R.id.tris1FL);
        tris2FL = findViewById(R.id.tris2FL);
        resetBtn = findViewById(R.id.resetBtn);
        fm = getSupportFragmentManager();
        tris1Fragment = new TrisFragment(MainActivity.this,"X","O");
        tris2Fragment = new TrisFragment(MainActivity.this,"O","X");
        tris1Fragment.setOtherTris(tris2Fragment);
        tris2Fragment.setOtherTris(tris1Fragment);

        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.tris1FL,tris1Fragment,"tris1");
        ft.add(R.id.tris2FL,tris2Fragment,"tris2");
        ft.commit();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tris1Fragment.reset();
                tris2Fragment.reset();
            }
        });


    }
}