package edu.gatech.cs2340.buzzmovieselector;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
import com.firebase.client.Firebase;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
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
    private RatingBar mRatingBar;
    private TextView mUsernameTextView;
    private TextView mMajorTextView;
    private TextView mCommentTextView;
    private TextView mRatingTextView;
    private EditText mComment;
    private String url;
    private String genrelist = "";
    private Movie m;
    private RecyclerView mRatingsRecyclerView;
    private Firebase database = new Firebase("https://buzz-movie-selector5.firebaseio.com/");


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
        Intent intent = getIntent();
        final String movieId = intent.getStringExtra("id");
        setContentView(R.layout.activity_movie);
        Firebase.setAndroidContext(this);
        url = "http://api.rottentomatoes.com/api/public/v1.0/movies/" + movieId + ".json?apikey=yedukp76ffytfuy24zsqk7f5";

        queue = Volley.newRequestQueue(this);

        mTitle = (TextView) findViewById(R.id.movieTitleTextView);
        mMPARating = (TextView) findViewById(R.id.mpaRatingTextView);
        mLength = (TextView) findViewById(R.id.movieLengthTextView);
        mYear = (TextView) findViewById(R.id.movieYearTextView);
        mGenre = (TextView) findViewById(R.id.movieGenreTextView);
        mDescription = (TextView) findViewById(R.id.movieDescriptionTextView);
//        mUserRating = (TextView) findViewById(R.id.userRatingTextView);
        mPoster = (ImageView) findViewById(R.id.moviePosterImageView);
        mUsernameTextView = (TextView) findViewById(R.id.rating_username);
        mMajorTextView = (TextView) findViewById(R.id.rating_major);
        mCommentTextView = (TextView) findViewById(R.id.rating_comment);
        mRatingTextView = (TextView) findViewById(R.id.rating_str_stars);


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

                        m = new Movie();

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
//                        m.setMovieImdbId(imdb);
                        m.setMovieName(resp.optString("title"));
                        m.setMovieDescription(resp.optString("synopsis"));
                        m.setMovieGenre(genrelist);
                        m.setMovieLength(resp.optString("runtime"));
                        m.setMovieMpaRating(resp.optString("mpaa_rating"));
                        m.setMovieYear(resp.optString("year"));

//                        Firebase revs = database.child("movies").child(movieId).child("movieReviews");
//                        Query q = revs.orderByKey();
//                        q.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                if(dataSnapshot.getChildrenCount() > 0) {
//                                    for(int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
//                                        DataSnapshot individualReview = dataSnapshot.child(Integer.toString(i));
//                                        Review rev = individualReview.getValue(Review.class);
//                                        Map newReview = new HashMap<String,String>();
//                                        newReview.put("username",rev.getUsername());
//                                        newReview.put("major",rev.getMajor());
//                                        newReview.put("numStars",rev.getRating());
//                                        newReview.put("comment",rev.getComment());
//                                        m.addReview(newReview);
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(FirebaseError firebaseError) {
//
//                            }
//                        });

                        mTitle.setText(m.getMovieName());
                        mDescription.setText("Description: " + m.getMovieDescription());
                        mMPARating.setText("Rated " + m.getMovieMpaRating());
                        mGenre.setText("Genres: " + m.getMovieGenre());
                        mLength.setText("Runtime: " + m.getMovieLength() + " minutes");
                        mYear.setText(m.getMovieYear());

//                        database.child("movies").child(m.getMovieId()).setValue(m);
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

        //Query query database.;

        mRatingBar = (RatingBar) findViewById(R.id.rating_bar);
        mComment = (EditText) findViewById(R.id.commentEditText);

        /**
         * This updates reviews and adds to database
         */
        mSubmitReview = (Button) findViewById(R.id.submit_review_button);
        mSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float givenStars = mRatingBar.getRating();
                String comm = mComment.getText().toString();
                User curr = UserManager.getInstance().retrieveCurrentUser();
//                String currUsername = curr.getUsername();
                String currUsername = "halequin";
//                String currMajor = curr.getMajor();
                String currMajor = "CS";
                String s = Float.toString(givenStars);

                Map<String, String> newReview = new HashMap<String, String>();
                newReview.put("username", currUsername);
                newReview.put("major", currMajor);
                newReview.put("numStars", s);
                newReview.put("comment", comm);

                m.addReview(newReview);

                database.child("movies").child(m.getMovieId()).setValue(m);

                //complete
                Toast.makeText(MovieActivity.this, "Review submitted!", Toast.LENGTH_SHORT).show();
                mComment.setText("");
                mRatingBar.setRating(0F);

//                Map<String, Object> revs = new HashMap<String, Object>();
//                revs.put("review", newReview);
//                database.child("movies").child(m.getMovieId()).push().setValue(revs);

//                database.addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                        DataSnapshot movieObj = dataSnapshot.child("movies");
//                        Movie movie = movieObj.getValue(Movie.class);
//                        if(movie.getMovieReviews() != null) {
//                            ArrayList<Map> thisReviews = movie.getMovieReviews();
//                            if(thisReviews.size() != 0) {
//                                Map firstReviews = thisReviews.get(0);
//                                mUsernameTextView.setText("Username: " + firstReviews.get("username").toString());
//                                mMajorTextView.setText("Major: " + firstReviews.get("major").toString());
//                                mCommentTextView.setText(firstReviews.get("comment").toString());
//                                mRatingTextView.setText("Rating: " + firstReviews.get("numStars").toString());
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                });
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
