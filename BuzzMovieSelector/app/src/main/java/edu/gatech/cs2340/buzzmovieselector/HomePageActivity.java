package edu.gatech.cs2340.buzzmovieselector;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.android.volley.RequestQueue;


public class HomePageActivity extends AppCompatActivity {

    private SearchView searchView;
    private EditText tempSearch;
    private Button tempButton;
    private Button newReleases;
    private Button newDvd;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        tempButton = (Button) findViewById(R.id.temp_button);

        tempSearch = (EditText) findViewById(R.id.temp_search);

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempQuery = tempSearch.getText().toString();
                String buttonPressed = "search";
                Log.i("temp", tempQuery);
                Intent intent = new Intent(HomePageActivity.this, ItemListActivity.class);
                intent.putExtra("query", tempQuery);
                intent.putExtra("button", buttonPressed);
                if (tempQuery != null && tempQuery != "") {
                    startActivity(intent);
                }
            }
        });

        newReleases = (Button) findViewById(R.id.newReleases);
        newDvd = (Button) findViewById(R.id.newDVD);

        newReleases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonPressed = "newReleases";
                Intent intent = new Intent(HomePageActivity.this, ItemListActivity.class);
                intent.putExtra("button", buttonPressed);
                startActivity(intent);
            }
        });

        newDvd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonPressed = "newDVD";
                Intent intent = new Intent(HomePageActivity.this, ItemListActivity.class);
                intent.putExtra("button", buttonPressed);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome_page, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_movie_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.menu_my_profile:

                Intent profileIntent = new Intent(HomePageActivity.this, ProfilePageActivity.class);
                startActivity(profileIntent);
                return true;

            case R.id.logout:
                UserManager.getInstance().setCurrentUser(null);
                Intent intent = new Intent(this, WelcomePageActivity.class);
                startActivity(intent);
                return true;

            case R.id.menu_movie_search:
                Log.i("click", "click");

        }

        return super.onOptionsItemSelected(item);
    }


}