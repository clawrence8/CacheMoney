package edu.gatech.cs2340.buzzmovieselector;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleAdapter;

import java.util.List;

public class SearchActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        View recyclerView = findViewById(R.id.movieItem);
        assert recyclerView != null;
        //setupRecyclerView((RecyclerView) recyclerView);


    }

//    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
//        recyclerView.setAdapter(new SimpleItemRecyclerView Adapter(States.ITEMS));
//    }


}
