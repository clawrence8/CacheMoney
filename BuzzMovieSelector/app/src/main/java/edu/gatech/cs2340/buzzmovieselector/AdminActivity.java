package edu.gatech.cs2340.buzzmovieselector;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class AdminActivity extends AppCompatActivity {


    private Spinner mStatusSpinner;
    private UserManager userManager;
    private Firebase database = new Firebase("https://buzz-movie-selector5.firebaseio.com/");
    private Firebase userTable = new Firebase("https://buzz-movie-selector5.firebaseio.com/Users");
    private String userTableURL = "https://buzz-movie-selector5.firebaseio.com/Users";
    private List<String> userList;
    private List<String> userStatusList;
    private Set<String> userSet;
    private String mStatus = "";
    private HashMap<String, String> firebaseUser;
//datasnapshot.getvalues


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        userManager = UserManager.getInstance();


        // Query Fire base for the userlist
        userTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap objectTempList = (HashMap<String, String>) dataSnapshot.getValue();
                userSet = objectTempList.keySet();
                userList = new ArrayList<String>();
                userStatusList = new ArrayList<String>();
                for(String name : userSet) {
                    if (name != null) {
                        userList.add(name);
                        firebaseUser = (HashMap<String, String>) dataSnapshot.child(name).getValue();
                        if (firebaseUser.get("status") != null) {
                            userStatusList.add(firebaseUser.get("status"));
                        } else {
                            userStatusList.add("Active");
                        }
                    }
                }
                changeView();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    /**
     * This method changes to our new list of user accounts
     */
    private void changeView() {
        setContentView(R.layout.activity_admin);

        View recyclerView = findViewById(R.id.adminList);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        ((RecyclerView) recyclerView).setLayoutManager(new LinearLayoutManager(this));

    }
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(userList, userStatusList));
    }



    /**
     * This is a special adapter for the spinner.
     * Allows us to dictate what the spinner defualt displays
     */
    public class CustomAdapter extends ArrayAdapter<String> {

        private int hidingItemIndex;

        public CustomAdapter(Context context, int textViewResourceId, List<String> objects, int hidingItemIndex) {
            super(context, textViewResourceId, objects);
            this.hidingItemIndex = hidingItemIndex;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View dropDownView = null;
            if (position == hidingItemIndex) {
                TextView dropDownDefault = new TextView(getContext());
                dropDownDefault.setVisibility(View.GONE);
                dropDownView = dropDownDefault;
            } else {
                dropDownView = super.getDropDownView(position, null, parent);
            }
            return dropDownView;
        }
    }




    /**
     * All lists need adapters, this is ours.
     */
    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<String> mNameValues;
        private final List<String> mStatusValues;


        public SimpleItemRecyclerViewAdapter(List<String> userNameItems, List<String> userStatusItems) {
            mNameValues = userNameItems;
            mStatusValues = userStatusItems;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.admin_list_item, parent, false);


            //mStatusSpinner = (Spinner) view.findViewById(R.id.user_status_spinner);
            // Create an ArrayAdapter using the string array and a default spinner layout



            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            List<String>  spinnerStringList = new ArrayList<String>();
            spinnerStringList.add(mStatusValues.get(position));
            spinnerStringList.add("Ban");
            spinnerStringList.add("Unlock");

            CustomAdapter dataAdapter = new CustomAdapter(AdminActivity.this, android.R.layout.simple_spinner_item, spinnerStringList, 0);

            holder.mItem = mNameValues.get(position);
            holder.mNameLabelTextView.setText(mNameValues.get(position));
            holder.mStatusLabelTextView.setText("Status:");
            holder.mStatusSpinner.setAdapter(dataAdapter);
            holder.mStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    mStatus = (String) adapterView.getSelectedItem().toString();
                    userTable.child(mNameValues.get(position)).child("status").setValue(mStatus);
                    if (mStatus.equals("Unlock")) {
                        userTable.child((mNameValues.get(position))).child("loginAttempts").setValue(0);
                        userTable.child((mNameValues.get(position))).child("status").setValue("Active");
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    //
                }
            });



        }

        @Override
        public int getItemCount() {

            return mNameValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mNameLabelTextView;
            public final TextView mStatusLabelTextView;
            public final Spinner mStatusSpinner;
            public final ArrayAdapter<CharSequence> adapter;


            public String mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mNameLabelTextView = (TextView) view.findViewById(R.id.nameLabelTextView);
                mStatusLabelTextView = (TextView) view.findViewById(R.id.statusLabelTextView);
                mStatusSpinner = (Spinner) view.findViewById(R.id.user_status_spinner);


                 adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.user_status_list, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner

                mStatusSpinner.setAdapter(adapter);

            }

            @Override
            public String toString() {
                return "Users Accounts";
                // return super.toString() + " '" + mContentView.getText() + "'";
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_account, menu);
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
            case R.id.logout:
                UserManager.getInstance().setCurrentUser(null);
                Intent intent = new Intent(this, WelcomePageActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


