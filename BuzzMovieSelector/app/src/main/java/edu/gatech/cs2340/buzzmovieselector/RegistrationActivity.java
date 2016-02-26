package edu.gatech.cs2340.buzzmovieselector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mNameEditText;//the m is just a convention to denote instance data for the class
    private EditText mUsernameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mMajorEditText;
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
        mMajorEditText = (EditText) findViewById(R.id.major_edit_text);
        mGenderEditText = (EditText) findViewById(R.id.gender_edit_text);
        mInterestEditText = (EditText) findViewById(R.id.interest_edit_text);

        mCreateProfileButton = (Button) findViewById(R.id.create_profile_button);
        mCancelRegButton = (Button) findViewById(R.id.cancel_registration_button);

        mCreateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mNameEditText.getText().toString();
                String username = mUsernameEditText.getText().toString();
                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                String major = mMajorEditText.getText().toString();
                String gender = mGenderEditText.getText().toString();
                String interests = mInterestEditText.getText().toString();

                User newUser = new User(name, username, email, password, major, gender, interests);
                UserManager.getInstance().addUser(newUser);

                Intent homePageIntent = new Intent(RegistrationActivity.this, HomePageActivity.class);
                startActivity(homePageIntent);
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
