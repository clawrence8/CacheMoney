package edu.gatech.cs2340.buzzmovieselector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

public class EditProfileActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private EditText mNameEditText;//the m is just a convention to denote instance data for the class
    private EditText mUsernameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mMajorEditText;
    private EditText mGenderEditText;
    private EditText mInterestEditText;
    private Button mDoneButton;
    private Button mCancelRegButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);


        User currentUser = UserManager.getInstance().getCurrentUser();

        mNameEditText = (EditText) findViewById(R.id.name_edit_text);
        mUsernameEditText = (EditText) findViewById(R.id.username_edit_text);
        mEmailEditText = (EditText) findViewById(R.id.email_edit_text);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit_text);
        mMajorEditText = (EditText) findViewById(R.id.major_edit_text);
        mGenderEditText = (EditText) findViewById(R.id.gender_edit_text);
        mInterestEditText = (EditText) findViewById(R.id.interest_edit_text);

        mNameEditText.setText(currentUser.getName());
        mUsernameEditText.setText(currentUser.getUsername());
        mEmailEditText.setText(currentUser.getEmail());
        mPasswordEditText.setText(currentUser.getPassword());
        mMajorEditText.setText(currentUser.getMajor());
        mGenderEditText.setText(currentUser.getGender());
        mInterestEditText.setText(currentUser.getInterests());

        mDoneButton = (Button) findViewById(R.id.doneProfileEditButton);
        mCancelRegButton = (Button) findViewById(R.id.cancel_registration_button);



        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mNameEditText.getText().toString().trim();
                String username = mUsernameEditText.getText().toString().trim();
                String email = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();
                String major = mMajorEditText.getText().toString().trim();
                String gender = mGenderEditText.getText().toString().trim();
                String interests = mInterestEditText.getText().toString().trim();
                User currentUser = UserManager.getInstance().getCurrentUser();

                currentUser.setName(name);
                currentUser.setUsername(username);
                currentUser.setEmail(email);
                currentUser.setPassword(password);
                currentUser.setMajor(major);
                currentUser.setGender(gender);
                currentUser.setInterests(interests);

                Intent intent = new Intent(EditProfileActivity.this, ProfilePageActivity.class);
                startActivity(intent);
            }
        });

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
