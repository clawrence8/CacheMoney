package edu.gatech.cs2340.buzzmovieselector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by julianeuman on 2/21/16.
 */
public class ItemListActivity extends AppCompatActivity {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    //private boolean mTwoPane;
    private RequestQueue queue;

    /**
     * This holds the movie information extracted from the intent
     */
    private String response;
    private String query;
    private ArrayList<Movie> movies;
    private String typeButton;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        queue = Volley.newRequestQueue(this);

        typeButton = getIntent().getStringExtra("button");

        if (typeButton.equals("search")) {
            query = getIntent().getStringExtra("query");
            Log.i("queryExtra", query);
            url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?q=" + query.trim()
                    + "&page_limit=10&page=1&apikey=yedukp76ffytfuy24zsqk7f5";

        } else if (typeButton.equals("newReleases")) {
            url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/opening.json?limit=16&country=us&apikey=yedukp76ffytfuy24zsqk7f5";

        } else if (typeButton.equals("newDVD")) {
            url = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?page_limit=16&page=1&country=us&apikey=yedukp76ffytfuy24zsqk7f5";

        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {
                        //handle a valid response coming back.  Getting this string mainly for debug
                        response = resp.toString();
                        Log.i("response", response);
                        //printing first 500 chars of the response.  Only want to do this for debug
//                        TextView view = (TextView) findViewById(R.id.movieTitle);
//                        view.setText(response.substring(0, 500));


                        //Now we parse the information.  Looking at the format, everything encapsulated in RestResponse object
                        JSONArray obj1 = null;
                        try {
                            obj1 = resp.getJSONArray("movies");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        assert obj1 != null;


                        movies = new ArrayList<>();
                        for (int i = 0; i < obj1.length(); i++) {

                            try {
                                //for each array element, we have to create an object
                                JSONObject jsonObject = obj1.getJSONObject(i);
                                Movie m = new Movie();

                                assert jsonObject != null;
                                m.setMovieName(jsonObject.optString("title"));
                                m.setMovieMpaRating(jsonObject.optString("mpaa_rating"));
                                m.setMovieYear(jsonObject.optString("year"));
                                m.setMovieLength(jsonObject.optString("runtime"));

////                                //save the object for later
                                movies.add(m);
////
////
                            } catch (JSONException e) {
                                Log.d("VolleyApp", "Failed to get JSON object");
                                e.printStackTrace();
                            }
                        }
                        //once we have all data, then go to list screen

                        changeView(movies);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        response = "JSon Request Failed!!";
                        //show error on phone
                        TextView view = (TextView) findViewById(R.id.movieTitle);
                        view.setText(response);
                    }
                });
        //this actually queues up the async response with Volley
        queue.add(jsObjRequest);
    }






        /**
         * This method changes to our new list view of the states, but we have to pass the
         * state array into the intent so the new screen gets the data.
         *
         * @param movies the list of state objects we created from the JSon response
         */
        private void changeView(ArrayList<Movie> movies) {

            setContentView(R.layout.activity_search);

            View recyclerView = findViewById(R.id.movieList);
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView);
            ((RecyclerView) recyclerView).setLayoutManager(new LinearLayoutManager(this));



//        if (findViewById(R.id.item_detail_container) != null) {
//            // The detail container view will be present only in the
//            // large-screen layouts (res/values-w900dp).
//            // If this view is present, then the
//            // activity should be in two-pane mode.
//            mTwoPane = true;
//        }

        //Here we extract the objects out of the intent
        //Note that to pass them, they have to be serializable
            Movies.removeAll();
            for (Movie m : movies) {
                Movies.addItem(m);
         }


    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(Movies.ITEMS));
    }

    /**
     * All lists need adapters, this is ours.
     */
    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Movie> mValues;

        public SimpleItemRecyclerViewAdapter(List<Movie> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);

            holder.mNameView.setText(mValues.get(position).getMovieName());
            holder.mLengthView.setText(mValues.get(position).getMovieLength());
            holder.mMPAView.setText(mValues.get(position).getMovieMpaRating());
            holder.mYearView.setText(mValues.get(position).getMovieYear());


            //for when you are clicking on the individual movie
//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, ItemDetailActivity.class);
//                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.getName());
//
//                        context.startActivity(intent);
//                }
//            });
        }

        @Override
        public int getItemCount() {

            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mNameView;
            public final TextView mLengthView;
            public final TextView mMPAView;
            public final TextView mYearView;

            public Movie mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mNameView = (TextView) view.findViewById(R.id.movieTitle);
                mLengthView = (TextView) view.findViewById(R.id.movieLength);
                mMPAView = (TextView) view.findViewById(R.id.mpaRating);
                mYearView = (TextView) view.findViewById(R.id.movieYear);
            }

            @Override
            public String toString() {
                return "Movies";
               // return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movies.clear();
    }
}
