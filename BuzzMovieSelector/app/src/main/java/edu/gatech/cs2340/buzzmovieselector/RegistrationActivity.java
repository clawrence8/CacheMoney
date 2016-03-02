package edu.gatech.cs2340.buzzmovieselector;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

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
                String name = mNameEditText.getText().toString().trim();
                String username = mUsernameEditText.getText().toString().trim();
                String email = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();
                String major = mMajorEditText.getText().toString().trim();
                String gender = mGenderEditText.getText().toString().trim();
                String interests = mInterestEditText.getText().toString().trim();

                if (username.equals("")) {
                    Snackbar.make(findViewById(R.id.registration_scroll_view),
                            R.string.registration_error, Snackbar.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Snackbar.make(findViewById(R.id.registration_scroll_view),
                            R.string.password_error, Snackbar.LENGTH_SHORT).show();
                } else if (email.equals("")) {
                    Snackbar.make(findViewById(R.id.registration_scroll_view),
                            R.string.email_error, Snackbar.LENGTH_SHORT).show();
                } else {
                    User newUser = new User(name, username, email, password, major, gender, interests);
                    UserManager.getInstance().setCurrentUser(newUser);
//
                    Firebase ref = UserManager.getInstance().getDatabase();
                    ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> result) {
                            Log.i("reg", "Successfully created user account with uid: " + result.get("uid"));
                            Intent homePageIntent = new Intent(RegistrationActivity.this, HomePageActivity.class);
                            startActivity(homePageIntent);

                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            // there was an error
                            Log.i("reg", firebaseError.toException().getMessage());
                            Toast.makeText(getApplicationContext(),
                                    firebaseError.toException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
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
