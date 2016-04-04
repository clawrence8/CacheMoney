package edu.gatech.cs2340.buzzmovieselector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * The user is taken to this activity when they want to view recommended movies based on major
 * @author Clayton Lawrence
 */
public class RecommendationActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Object[] movieArray = new Object[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_search);

        mRecyclerView = (RecyclerView) findViewById(R.id.movieList);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        fetchMovieRecs();

    }

    public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {
        private Object[] mRecommendations = movieArray;


        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mNameView;
            public final TextView mLengthView;
            public final TextView mMPAView;
            public final TextView mYearView;
            public final ImageView mPosterView;

            public Movie mItem;

            public ViewHolder(View view) {
                super(view);

                mView = view;
                mPosterView = (ImageView) view.findViewById(R.id.imageView);
                mNameView = (TextView) view.findViewById(R.id.movieTitle);
                mLengthView = (TextView) view.findViewById(R.id.movieLength);
                mMPAView = (TextView) view.findViewById(R.id.mpaRating);
                mYearView = (TextView) view.findViewById(R.id.movieYear);
            }
        }

        public RecAdapter(Object[] rec) {
            mRecommendations = rec;
        }

        @Override
        public RecAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mItem = (Movie) mRecommendations[position];

            holder.mNameView.setText(holder.mItem.getMovieName());
            holder.mLengthView.setText("Runtime: " + holder.mItem.getMovieLength() + " minutes");
            holder.mMPAView.setText("Rated " + holder.mItem.getMovieMpaRating());
            holder.mYearView.setText(holder.mItem.getMovieYear());
            new DownloadImageTask(holder.mPosterView).execute(holder.mItem.getMoviePoster());
        }

        @Override
        public int getItemCount() {
            return mRecommendations.length;
        }
    }

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
                //Log.e("Error", e.getMessage(), e);
                e.printStackTrace();
            }
            return mIcon11;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    /**
     * Fetches movies for the user based on their major
     * @return an array of Movies sorted from highest rated to lowest rated
     */
    //TODO make this method work for any major that the user selects
    public void fetchMovieRecs() {

        Firebase movieTable = UserManager.getInstance().getDatabase().child("movies");
        movieTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable movies = dataSnapshot.getChildren();
                Iterator iterator = movies.iterator();
                PriorityQueue<Movie> pq = new PriorityQueue();
                while (iterator.hasNext()) {
                    try {
                        int sum = 0;
                        int counter = 0;
                        int avg = 0;
                        //snapshot points to current movie
                        DataSnapshot snapshot = ((DataSnapshot) iterator.next());
                        HashMap<String, String> snapShotMap = ((HashMap) snapshot.getValue());
                        Object finesseList = snapShotMap.get("movieReviews");
                        //Grab list of reviews for current movie
                        ArrayList reviewList = (ArrayList) finesseList;
                        for (Object item : reviewList) {
                            HashMap<String, String> review = (HashMap) item;
                            String major = review.get("major");
                            //Filters out reviews based on current user's major
                            if (major.equals(UserManager.getInstance().getCurrentUser().getMajor())) {
                                double rating = Double.parseDouble(review.get("numStars"));
                                sum += rating;
                                counter++;
                            }


                        }
                        //calculate avg review for ___ majors
                        if (counter > 0) {
                            avg = sum / counter;
                            Movie movie = new Movie(snapShotMap);
                            movie.setMajorRating(avg);
                            pq.add(movie);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        Log.e("error", "index out of bounds \n", e);
                    }

                }

                movieArray = pq.toArray();
                onFetchFinish();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    /**
     * Callback to continue building the RecyclerView once we have the list of movie recommendations
     */
    public void onFetchFinish() {
        mAdapter = new RecAdapter(movieArray);
        mRecyclerView.setAdapter(mAdapter);
    }

}
