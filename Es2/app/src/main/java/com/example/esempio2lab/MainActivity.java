package com.example.esempio2lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText firstNameET;
    private EditText lastNameET;
    private EditText phoneET;
    private Button insertBtn;
    private ListView listView;
    private Bitmap picture;
    private LinearLayout leftContainerLL;
    private RelativeLayout rightContainerRL;
    private DisplayMetrics dm;
    private ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNameET = findViewById(R.id.firstNameET);
        lastNameET = findViewById(R.id.lastnameET);
        phoneET = findViewById(R.id.phoneET);
        insertBtn = findViewById(R.id.insertBtn);
        listView = findViewById(R.id.listView);

        dm = getApplicationContext().getResources().getDisplayMetrics();
        if (dm.widthPixels > dm.heightPixels) {
            leftContainerLL = findViewById(R.id.leftContainerLL);
            rightContainerRL = findViewById(R.id.rightContainerRL);

            ViewGroup.LayoutParams lpLeft = leftContainerLL.getLayoutParams();
            ViewGroup.LayoutParams lpRight = rightContainerRL.getLayoutParams();
            lpLeft.width = (int) (0.40 * dm.widthPixels);
            lpRight.width = (int) (0.60 * dm.widthPixels);
            leftContainerLL.setLayoutParams(lpLeft);
            rightContainerRL.setLayoutParams(lpRight);
        }


        BitmapDrawable bd = (BitmapDrawable) ResourcesCompat.getDrawable(getApplicationContext().getResources(), R.drawable.picture, null);
        if (bd != null) {
            picture = bd.getBitmap();
            picture = Bitmap.createScaledBitmap(picture, 60, 60, false);
        }
        contacts = new ArrayList<>();
        if (savedInstanceState != null) {
            contacts = savedInstanceState.getParcelableArrayList("Contacts", Contact.class);
        }
        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), contacts);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Conferma di eliminazione");
                alert.setMessage("Sei sicuro di voler eliminare il contatto?");

                alert.setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        contacts.remove(contacts.get(position));
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Contatto eliminato", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Contatto non eliminato", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.create().show();
            }
        });

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameET.getText().toString();
                String lastName = lastNameET.getText().toString();
                String phone = phoneET.getText().toString();

                if (firstName.trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Nome non valido", Toast.LENGTH_SHORT).show();
                }

                if (lastName.trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Cognome non valido", Toast.LENGTH_SHORT).show();
                }

                if (phone.trim().equals("") || !phone.matches("^[0-9]{1,10}$")) {
                    Toast.makeText(getApplicationContext(), "Telefono non valido", Toast.LENGTH_SHORT).show();
                }

                if (!firstName.trim().equals("") && !lastName.trim().equals("") && !phone.trim().equals("") && phone.matches("^[0-9]{1,10}$")) {
                    contacts.add(new Contact(firstName, lastName, phone, picture));
                    adapter.notifyDataSetChanged();
                    firstNameET.setText("");
                    lastNameET.setText("");
                    phoneET.setText("");
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("Contacts", contacts);
    }

}