package edu.gatech.cs2340.buzzmovieselector;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mNameEditText;//the m is just a convention to denote instance data for the class
    private EditText mUsernameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private String mMajor = "";
    private Spinner mMajorSpinner;
    private EditText mGenderEditText;
    private EditText mInterestEditText;
    private Button mCreateProfileButton;
    private Button mCancelRegButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mNameEditText = (EditText) findViewById(R.id.name_edit_text);
        mUsernameEditText = (EditText) findViewById(R.id.username_edit_text);
        mEmailEditText = (EditText) findViewById(R.id.email_edit_text);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit_text);
        //mMajorEditText = (EditText) findViewById(R.id.major_edit_text);
        mGenderEditText = (EditText) findViewById(R.id.gender_edit_text);
        mInterestEditText = (EditText) findViewById(R.id.interest_edit_text);

        mCreateProfileButton = (Button) findViewById(R.id.create_profile_button);
        mCancelRegButton = (Button) findViewById(R.id.cancel_registration_button);

        mMajorSpinner = (Spinner) findViewById(R.id.major_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.major_list, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner

        mMajorSpinner.setAdapter(adapter);
        mMajorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mMajor = (String) adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //
            }
        });


        mCreateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = mNameEditText.getText().toString().trim();
                final String username = mUsernameEditText.getText().toString().trim();
                final String email = mEmailEditText.getText().toString().trim();
                final String password = mPasswordEditText.getText().toString().trim();
                final String gender = mGenderEditText.getText().toString().trim();
                final String interests = mInterestEditText.getText().toString().trim();

                if (username.equals("")) {
                    Snackbar.make(findViewById(R.id.registration_scroll_view),
                            R.string.registration_error, Snackbar.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Snackbar.make(findViewById(R.id.registration_scroll_view),
                            R.string.password_error, Snackbar.LENGTH_SHORT).show();
                } else if (email.equals("")) {
                    Snackbar.make(findViewById(R.id.registration_scroll_view),
                            R.string.email_error, Snackbar.LENGTH_SHORT).show();
                } else if (mMajor.equals("Chose Major:") || mMajor.equals("")) {
                    //TODO fix error message
                    Snackbar.make(findViewById(R.id.registration_scroll_view),
                            R.string.email_error, Snackbar.LENGTH_SHORT).show();
                } else {
                    User newUser = new User(name, username, email, password, mMajor, gender, interests);
                    UserManager.getInstance().addUser(newUser);

                    Intent homePageIntent = new Intent(RegistrationActivity.this, HomePageActivity.class);
                    startActivity(homePageIntent);


                }


            }
        });

        mCancelRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent welcomePageIntent = new Intent(RegistrationActivity.this,
                        WelcomePageActivity.class);
                startActivity(welcomePageIntent);
            }
        });


    }
}
