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
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private UserManager userManager;
    private EditText usernameBox;
    private EditText passBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userManager = UserManager.getInstance();

        usernameBox = (EditText) findViewById(R.id.username);
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
     *
     * @param v login button view
     */

    public void onLoginButtonPressed(View v) {
        final String username = usernameBox.getText().toString().trim();
        final String password = passBox.getText().toString().trim();

        final Firebase ref = userManager.getUserTable();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                               @Override
                                               public void onDataChange(DataSnapshot dataSnapshot) {
                                                   if (!username.equals("") && !password.equals("")) {
                                                       if (dataSnapshot.child(username).exists() &&
                                                               dataSnapshot.child(username)
                                                                       .child("password").getValue()
                                                                       .toString().equals(password)) {
                                                           HashMap<String, String> firebaseUser = (HashMap<String, String>) dataSnapshot.child(username).getValue();


                                                           User newUser = new User(firebaseUser.get("name"),
                                                                   firebaseUser.get("username"),
                                                                   firebaseUser.get("email"),
                                                                   firebaseUser.get("password"),
                                                                   firebaseUser.get("major"),
                                                                   firebaseUser.get("gender"),
                                                                   firebaseUser.get("interests"));

                                                           UserManager.getInstance().setCurrentUser(newUser);

                                                           Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                                                           startActivity(intent);

                                                       }
                                                   }

                                               }

                                               @Override
                                               public void onCancelled(FirebaseError firebaseError) {

                                               }
                                           }

        );
        /*ref.authWithPassword(username, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("provider", authData.getProvider());
                    if (authData.getProviderData().containsKey("username")) {
                        map.put("username", authData.getProviderData().get("username").toString());
                    }
                    ref.child("users").child(authData.getUid()).setValue(map);

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
        }); end username auth */

    }

    /**
     * Upon cancellation of registering for an account, this takes you to the wlecome page.
     *
     * @param v cancel button view
     */

    public void onCancelButtonPressed(View v) {
        Intent intent = new Intent(this, WelcomePageActivity.class);
        startActivity(intent);
    }
}
