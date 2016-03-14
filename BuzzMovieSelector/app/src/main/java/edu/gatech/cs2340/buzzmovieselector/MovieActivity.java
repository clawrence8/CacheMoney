package edu.gatech.cs2340.buzzmovieselector;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by halequin
 */
public class MovieActivity extends AppCompatActivity {

    /**
     * Movie information utilized to display onto the screen
     */
    public static final String EXTRA_MOVIE_ID =  "movie_id";
    private RequestQueue queue;
    private ImageView mPoster;
    private String response;
    private TextView mTitle;
    private TextView mMPARating;
    private TextView mLength;
    private TextView mYear;
    private TextView mGenre;
    private TextView mDescription;
    private Button mSubmitReview;
    private Button mViewMoreCommentsButton;
    private RatingBar mRatingBar;
    private double ratingSum;
    private TextView mUsernameTextView;
    private TextView mMajorTextView;
    private TextView mCommentTextView;
    private TextView mRatingTextView;
    private TextView mUserRatingTextView;
    private EditText mComment;
    private String url;
    private String genrelist = "";
    private double numReviews;
    private Movie m;
    private String mRating;
    private Firebase database = new Firebase("https://buzz-movie-selector5.firebaseio.com/");
    private Firebase movieTable = new Firebase("https://buzz-movie-selector5.firebaseio.com/movies");


    /**
     * Creates new intent to launch Movie Activity to display a selected movie's details
     *
     * @param packageContext the context of the application
     * @param movieId extra that defines which movie to display
     * @return
     */
    public static Intent newIntent(Context packageContext, String movieId) {
        Intent intent = new Intent(packageContext, MovieActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        return intent;
    }

    /**
     * Set up method to manage what happens when the activity starts
     *
     * @param savedInstanceState bundle of info from when this activity is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        Intent intent = getIntent();
        final String movieId = intent.getStringExtra("id");
        final String itemListPosterUrl = intent.getStringExtra("listUrl");
        setContentView(R.layout.activity_movie);
        url = "http://api.rottentomatoes.com/api/public/v1.0/movies/" + movieId + ".json?apikey=yedukp76ffytfuy24zsqk7f5";

        m = new Movie();
        queue = Volley.newRequestQueue(this);

        mTitle = (TextView) findViewById(R.id.movieTitleTextView);
        mMPARating = (TextView) findViewById(R.id.mpaRatingTextView);
        mLength = (TextView) findViewById(R.id.movieLengthTextView);
        mYear = (TextView) findViewById(R.id.movieYearTextView);
        mGenre = (TextView) findViewById(R.id.movieGenreTextView);
        mDescription = (TextView) findViewById(R.id.movieDescriptionTextView);
        mUserRatingTextView = (TextView) findViewById(R.id.userRatingTextView);
        mPoster = (ImageView) findViewById(R.id.moviePosterImageView);
        mUsernameTextView = (TextView) findViewById(R.id.rating_username);
        mMajorTextView = (TextView) findViewById(R.id.rating_major);
        mCommentTextView = (TextView) findViewById(R.id.rating_comment);
        mRatingTextView = (TextView) findViewById(R.id.rating_str_stars);
        mRatingBar = (RatingBar) findViewById(R.id.rating_bar);
        mComment = (EditText) findViewById(R.id.commentEditText);

        /**
         * Make request to get Movie info from Rotten Tomatoes API
         */
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {
                        //handle a valid response coming back.  Getting this string mainly for debug
                        response = resp.toString();
                        Log.i("response", response);

                        JSONObject posters = resp.optJSONObject("posters");
                        String mImageUrl = posters.optString("thumbnail");
                        new DownloadImageTask(mPoster).execute(mImageUrl);

                        JSONArray genres = resp.optJSONArray("genres");
                        for (int i = 0; i < genres.length(); i++) {
                            if (i == genres.length() - 1) {
                                genrelist += genres.optString(i);

                            } else {
                                genrelist += genres.optString(i) + ", ";
                            }
                        }

                        m.setMovieId(movieId);
                        m.setMovieName(resp.optString("title"));
                        m.setMovieDescription(resp.optString("synopsis"));
                        m.setMovieGenre(genrelist);
                        m.setMovieLength(resp.optString("runtime"));
                        m.setMovieMpaRating(resp.optString("mpaa_rating"));
                        m.setMovieYear(resp.optString("year"));
                        m.setMoviePoster(itemListPosterUrl);
                        mUserRatingTextView.setText("Average Rating: No user ratings yet");
                        mRatingTextView.setText("No user reviews yet. Leave one now!");

                        movieTable.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                DataSnapshot reviews = dataSnapshot.child(movieId).child("movieReviews");
                                if (reviews != null) {
                                    Iterable<DataSnapshot> reviewsIter = reviews.getChildren();
                                    for (DataSnapshot i : reviewsIter) {
                                        String numStars = (String) i.child("numStars").getValue();
                                        String username = (String) i.child("username").getValue();
                                        String major = (String) i.child("major").getValue();
                                        String comment = (String) i.child("comment").getValue();

                                        Map newReview = new HashMap<String, String>();
                                        newReview.put("username", username);
                                        newReview.put("major", major);
                                        newReview.put("numStars", numStars);
                                        newReview.put("comment", comment);
                                        m.addReview(newReview);
                                        ratingSum += Double.parseDouble(numStars);
                                        numReviews++;

                                        mUserRatingTextView.setText("Average Rating: " + String.valueOf(ratingSum / numReviews) + " stars");
                                        mRatingTextView.setText(numStars + " stars");
                                        mCommentTextView.setText("\"" + comment + "\"");
                                        mUsernameTextView.setText("User: " + username);
                                        mMajorTextView.setText("Major: " + major);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });

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

        /**
         * This updates reviews and adds to database
         */
        mSubmitReview = (Button) findViewById(R.id.submit_review_button);
        mSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double rating;
                float givenStars = mRatingBar.getRating();
                String comm = mComment.getText().toString();
                User curr = UserManager.getInstance().getCurrentUser();
                String currUsername = curr.getUsername();
                String currMajor = curr.getMajor();
                String s = Float.toString(givenStars);

                if (comm.equals("")) {
                    Snackbar.make(findViewById(R.id.activity_movie_scrollview), "Please leave a comment", Snackbar.LENGTH_SHORT).show();
                } else if (mRatingBar.getRating() == 0.0) {
                    Snackbar.make(findViewById(R.id.activity_movie_scrollview), "Please submit a rating of at least one star", Snackbar.LENGTH_SHORT).show();
                } else {

                    Map<String, String> newReview = new HashMap<String, String>();
                    newReview.put("username", currUsername);
                    newReview.put("major", currMajor);
                    newReview.put("numStars", s);
                    newReview.put("comment", comm);

                    ratingSum += givenStars;
                    numReviews++;
                    double avg = ratingSum / numReviews;
                    DecimalFormat df = new DecimalFormat("#.#");
                    mUserRatingTextView.setText("Average Rating: " + df.format(avg) + " stars");
                    m.setAvgRating(avg);

                    m.addReview(newReview);

                    database.child("movies").child(m.getMovieId()).setValue(m);

                    //review submission complete
                    Toast.makeText(MovieActivity.this, "Review submitted!", Toast.LENGTH_SHORT).show();
                    mComment.setText("");
                    mRatingBar.setRating(0F);

                    if (mUsernameTextView.getText().equals("")) {
                        mRatingTextView.setText(s + " stars");
                        mCommentTextView.setText("\"" + comm + "\"");
                        mUsernameTextView.setText("User: " + currUsername);
                        mMajorTextView.setText("Major: " + currMajor);                    }
                }
            }
        });

        mViewMoreCommentsButton = (Button) findViewById(R.id.view_more_comments_button);
        mViewMoreCommentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * This functions as an AsyncTask to download images to appear in our app's views
     * based on a given url
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
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
        @Override
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
