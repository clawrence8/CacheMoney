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

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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



        mCreateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = mNameEditText.getText().toString().trim();
                final String username = mUsernameEditText.getText().toString().trim();
                final String email = mEmailEditText.getText().toString().trim();
                final String password = mPasswordEditText.getText().toString().trim();
                //final String major = mMajorEditText.getText().toString().trim();
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
                } else {
                    User newUser = new User(name, username, email, password, mMajor, gender, interests);
                    UserManager.getInstance().addUser(newUser);
//
                    //Firebase ref = UserManager.getInstance().getUserTable();
                    //Firebase firebaseUser = ref.child(username);
                    //firebaseUser.setValue(newUser);

                    Intent homePageIntent = new Intent(RegistrationActivity.this, HomePageActivity.class);
                    startActivity(homePageIntent);

//                    firebaseUser.child("name").setValue(name);
//                    firebaseUser.child("email").setValue(email);
//                    firebaseUser.child("password").setValue(password);
//                    firebaseUser.child("username").setValue(username);
//                    firebaseUser.child("major").setValue(major);
//                    firebaseUser.child("gender").setValue(password);
//                    firebaseUser.child("interests").setValue(interests);

//                    Map<String, Object> map = ref.getAuth().getProviderData();
//                    map.put("name", name);
//                    map.put("username", username);
//                    map.put("major", major);
//                    map.put("gender", gender);
//                    map.put("interests", interests);

                    /*ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
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
                    }); end email auth */
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

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
         mMajor = (String) parent.getSelectedItem().toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
