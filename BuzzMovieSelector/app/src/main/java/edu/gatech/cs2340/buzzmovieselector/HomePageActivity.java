package edu.gatech.cs2340.buzzmovieselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


public class HomePageActivity extends AppCompatActivity /*TODO: Delete 'implements...'once profile is implemented */
implements OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        /*TODO: Delete from here once profile is implemented */
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("Account");
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                int a = 0;
            }
        });
        /*TODO: To Here*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.menu_my_profile:
                //Chez, uncomment the code below to get to your profile page.
                //Intent profileIntent = new Intent(HomePageActivity.this, ProfilePageActivity.class);
                //startActivity(profileIntent);
                return true;

            case R.id.logout:
                Intent intent = new Intent(this, WelcomePageActivity.class);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }



    /*TODO: Delete from here once the profile is implemented*/
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

    @Override
    public void onNothingSelected(AdapterView<?> view) {

    }
    /*TODO: To here*/


}
