package edu.gatech.cs2340.buzzmovieselector;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import android.widget.Button;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LoginActivity extends AppCompatActivity {

    private UserManager userManager;
    private EditText emailBox;
    private EditText passBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userManager = UserManager.getInstance();

        emailBox = (EditText) findViewById(R.id.email);
        passBox = (EditText) findViewById(R.id.password);

        Button signIn = (Button) findViewById(R.id.signIn);
        Button cancel = (Button) findViewById(R.id.cancel);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginButtonPressed(view);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View view) {
                onCancelButtonPressed(view);
            }
        });

    }

    /**
     * Upon clicking the lgoin button, this method facilitates the validation of user input
     * for username and password
     * @param v login button view
     */

    public void onLoginButtonPressed(View v) {
        String email = emailBox.getText().toString().trim();
        String password = passBox.getText().toString().trim();

        Firebase ref = userManager.getDatabase();
        ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Log.i("login", "User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
                Log.e("login", "error", firebaseError.toException());
                Toast.makeText(getApplicationContext(),
                        firebaseError.toException().getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Upon cancellation of registering for an account, this takes you to the wlecome page.
     * @param v cancel button view
     */

    public void onCancelButtonPressed(View v) {
        Intent intent = new Intent(this, WelcomePageActivity.class);
        startActivity(intent);
    }
}
