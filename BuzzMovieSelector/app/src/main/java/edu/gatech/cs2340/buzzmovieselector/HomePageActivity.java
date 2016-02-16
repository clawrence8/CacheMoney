package edu.gatech.cs2340.buzzmovieselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


public class HomePageActivity extends AppCompatActivity implements OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("Account");
        list.add("Profile");
        list.add("License Agreement");
        list.add("About Us");
        list.add("Contact Us");
        list.add("Logout");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (item == "Logout") {
                    onLogoutItemPressed(view);
                } else if (item == "Profile") {
                    onProfileItemPressed(view);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        addListenerOnButton();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

//    public void onLogoutButtonClicked(View view) {
//
//    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onLogoutItemPressed(View view) {
        Intent intent = new Intent(this, WelcomePageActivity.class);
        startActivity(intent);
    }

    // This takes the user to their profile page
    public void onProfileItemPressed(View view) {
        Intent intent = new Intent(HomePageActivity.this, ProfilePageActivity.class);
        startActivity(intent);
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
