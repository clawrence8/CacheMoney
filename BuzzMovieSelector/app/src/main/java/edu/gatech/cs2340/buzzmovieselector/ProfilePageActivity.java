package edu.gatech.cs2340.buzzmovieselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfilePageActivity extends AppCompatActivity {

    private TextView userNameTextView;
    private TextView genderLabelTextView;
    private TextView genderTextView;
    private TextView interestsLabelTextView;
    private TextView interestsTextView;
    private TextView majorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        UserManager userManager = UserManager.getInstance();
        User currentUser = userManager.retrieveCurrentUser();
        //initialize
        userNameTextView = (TextView)findViewById(R.id.userName);
        genderLabelTextView = (TextView) findViewById(R.id.genderLabel);
        genderTextView = (TextView) findViewById(R.id.userGender);
        interestsLabelTextView = (TextView) findViewById(R.id.movieInterestsLabel);
        interestsTextView = (TextView) findViewById(R.id.userMovieInterests);
        majorTextView = (TextView) findViewById(R.id.userMajor);

        // Pull user data
        userNameTextView.setText(currentUser.getName());
        genderTextView.setText(currentUser.getGender());
        interestsTextView.setText(currentUser.getInterests());
        majorTextView.setText(currentUser.getMajor());



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuHome:
                Intent homePageIntent = new Intent(ProfilePageActivity.this, HomePageActivity.class);
                startActivity(homePageIntent);
                return true;
            case R.id.menuEditProfile:
                Intent editProfileIntent = new Intent(ProfilePageActivity.this, EditProfileActivity.class);
                startActivity(editProfileIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
