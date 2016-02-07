package edu.gatech.cs2340.buzzmovieselector;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

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


    public void onLoginButtonPressed(View v) {
        AuthenticationMeasure userman = new UserManager();
        EditText nameBox = (EditText) findViewById(R.id.username);
        EditText passBox = (EditText) findViewById(R.id.password);
        CharSequence text;
        if (userman.handleLoginAttempt(nameBox.getText().toString(), passBox.getText().toString())) {
            text = "Login Success!";
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast t = Toast.makeText(context, text, duration);
            t.show();
            Intent intent = new Intent(this, HomePageActivity.class);
            startActivity(intent);
        } else {
            text = "Login Failure!";
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast t = Toast.makeText(context, text, duration);
            t.show();
        }
    }

    public void onCancelButtonPressed(View v) {
        Intent intent = new Intent(this, WelcomePageActivity.class);
        startActivity(intent);
    }
}
