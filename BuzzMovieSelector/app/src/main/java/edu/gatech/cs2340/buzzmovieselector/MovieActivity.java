package edu.gatech.cs2340.buzzmovieselector;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Rating;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
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

import java.io.InputStream;
import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID =  "movie_id";
    private RequestQueue queue;
    private ImageView mPoster;
    private String response;
    private String response2;
    private TextView mTitle;
    private TextView mMPARating;
    private TextView mLength;
    private TextView mYear;
    private TextView mGenre;
    private TextView mDescription;
    private TextView mUserRating;
    private String url;
    private String genrelist = "";
    private String mImageUrl;
    private RatingBar ratingBar;
    private Movie m;

    public static Intent newIntent(Context packageContext, String movieId) {
        Intent intent = new Intent(packageContext, MovieActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final String movieId = intent.getStringExtra("id");
        setContentView(R.layout.activity_movie);
        url = "http://api.rottentomatoes.com/api/public/v1.0/movies/" + movieId + ".json?apikey=yedukp76ffytfuy24zsqk7f5";

        queue = Volley.newRequestQueue(this);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        mTitle = (TextView) findViewById(R.id.movieTitleTextView);
        mMPARating = (TextView) findViewById(R.id.mpaRatingTextView);
        mLength = (TextView) findViewById(R.id.movieLengthTextView);
        mYear = (TextView) findViewById(R.id.movieYearTextView);
        mGenre = (TextView) findViewById(R.id.movieGenreTextView);
        mDescription = (TextView) findViewById(R.id.movieDescriptionTextView);
        mUserRating = (TextView) findViewById(R.id.userRatingTextView);
        mPoster = (ImageView) findViewById(R.id.moviePosterImageView);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);

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
//                        JSONObject obj = null;
//                        try {
//                            obj = resp.getJSONObject("movie");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        assert obj != null;

                        m = new Movie();

//                        JSONObject altID = resp.optJSONObject("alternate_ids");
//                        String imdb = "tt" + altID.optString("imdb");
//
//                        imdbUrl = "http://api.themoviedb.org/3/movie/" + imdb + "?api_key=ed43075e51488f197ff55424cd6e69ee";

                        JSONObject posters = resp.optJSONObject("posters");
                        String mImageUrl = posters.optString("thumbnail");
                        new DownloadImageTask(mPoster).execute(mImageUrl);

                        JSONArray genres = resp.optJSONArray("genres");
                        for (int i = 0; i < genres.length(); i++)
                        {
                            if(i == genres.length()-1)
                            {
                                genrelist += genres.optString(i);

                            } else {
                                genrelist += genres.optString(i) +", ";
                            }
                        }

                        m.setMovieId(movieId);
//                        m.setMovieImdbId(imdb);
                        m.setMovieName(resp.optString("title"));
                        m.setMovieDescription(resp.optString("synopsis"));
                        m.setMovieGenre(genrelist);
                        m.setMovieLength(resp.optString("runtime"));
                        m.setMovieMpaRating(resp.optString("mpaa_rating"));
                        m.setMovieYear(resp.optString("year"));

                        mTitle.setText(m.getMovieName());
                        mDescription.setText("Description: " + m.getMovieDescription());
                        mMPARating.setText("Rated " + m.getMovieMpaRating());
                        mGenre.setText("Genres: " + m.getMovieGenre());
                        mLength.setText("Runtime: " + m.getMovieLength() + " minutes");
                        mYear.setText(m.getMovieYear());

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


//        //get higher quality poster
//        JsonObjectRequest jsPosterRequest = new JsonObjectRequest
//                (Request.Method.GET, imdbUrl, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject respo) {
//                        //handle a valid response coming back.  Getting this string mainly for debug
//                        response2 = respo.toString();
//                        Log.i("response", response2);
//
//                        String mImageUrl = "https://image.tmdb.org/t/p/w185" + respo.optString("poster_path");
//
//                        new DownloadImageTask(mPoster).execute(mImageUrl);
//
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        response = "JSon Request Failed!!";
//                        //show error on phone
//                        TextView view = (TextView) findViewById(R.id.movieTitle);
//                        view.setText(response);
//                    }
//                });
//        //this actually queues up the async response with Volley
//        queue.add(jsPosterRequest);



        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });

    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
