package edu.gatech.cs2340.buzzmovieselector;

import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.firebase.client.Firebase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by julianeuman on 2/21/16.
 */
public class Movie implements Serializable {

    private String movieId;
    private String movieImdbId;
    private String movieName;
    private String movieYear;
    private String moviePoster;
    private String movieLength;
    private String movieMpaRating;
    private String movieGenre;
    private String movieDescription;
    private String movieRating;
    private double avgRating;
    private ArrayList<Map> movieReviews = new ArrayList<>();
    private String numstars;

    /**
     * Creates a new movie object with null inputs
     */
    public Movie() {
        this(null, null, null, null, null, null, null, null, null, null, 0);

    }

    /**
     * Creates a new movie objects with corresponding movie attributes
     * @param name movie name
     * @param year year movie came out
     * @param movieLength movie duration
     * @param mpaRating film rating
     */
    public Movie(String id, String imdbId, String name, String year, String movieLength, String mpaRating,
                 String movieDescription, String movieGenre, String movieRating, String moviePoster, double avgRating) {
        this.movieName = name;
        this.movieImdbId = imdbId;
        this.movieYear = year;
        this.movieId = id;
        this.movieLength = movieLength;
        this.movieMpaRating = mpaRating;
        this.movieDescription = movieDescription;
        this.movieGenre = movieGenre;
        this.movieRating = movieRating;
        this.moviePoster = moviePoster;
        this.avgRating = avgRating;
    }

    /**
     *
     * @return movie id
     */

    public String getMovieId() {
        return this.movieId;
    }

    /**
     *
     * @return movie imbdid
     */

    public String getMovieImbdId() {
        return this.movieImdbId;
    }

    /**
     *
     * @return movie name
     */

    public String getMovieName() {
        return this.movieName;
    }

    /**
     *
     * @return year movie was made
     */

    public String getMovieYear() {
        return this.movieYear;
    }

    /**
     *
     * @return poster url
     */

    public String getMoviePoster() {
        return this.moviePoster;
    }


    /**
     *
     * @return movie duration
     */

    public String getMovieLength() {
        return this.movieLength;
    }

    /**
     *
     * @return film rating
     */

    public String getMovieMpaRating() {
        return this.movieMpaRating;
    }

    /**
     *
     * @return movie description
     */

    public String getMovieDescription() {
        return this.movieDescription;
    }

    /**
     *
     * @return movie genre
     */

    public String getMovieGenre() {
        return this.movieGenre;
    }

    /**
     *
     * @return movie rating
     */

    public String getMovieRating() {
        return this.movieRating;
    }

    /**
     *
     * @return list of this movie's reviews
     */

    public ArrayList<Map> getMovieReviews() {
        return this.movieReviews;
    }

    /**
     *
     * @param id id of movie
     */

    public void setMovieId(String id) {
        this.movieId = id;
    }

    /**
     *
     * @param imdbId id of movie
     */

    public void setMovieImdbId(String imdbId) {
        this.movieImdbId = imdbId;
    }

    /**
     *
     * @param name name of movie
     */

    public void setMovieName(String name) {
        this.movieName = name;
    }

    /**
     *
     * @param year year movie was released
     */

    public void setMovieYear(String year) {
        this.movieYear = year;
    }

    /**
     *
     * @param mlength duration
     */

    public void setMovieLength(String mlength) {
        this.movieLength = mlength;
    }

    /**
     *
     * @param rating film rating
     */

    public void setMovieMpaRating(String rating) {
        this.movieMpaRating = rating;
    }

    /**
     *
     * @param description movie description
     */

    public void setMovieDescription(String description) {
        this.movieDescription = description;
    }

    /**
     *
     * @param genre movie genre
     */

    public void setMovieGenre(String genre) {
        this.movieGenre = genre;
    }

    /**
     *
     * @param rating movie user rating
     */

    public void setMovieRating(String rating) {
        this.movieRating = rating;
    }

    /**
     *
     * @param posterUrl movie poster url
     */

    public void setMoviePoster(String posterUrl) {
        this.moviePoster = posterUrl;
    }

    /**
     *
     * @param newReview new movie review
     */
    public void addReview(Map newReview) {
        this.movieReviews.add(newReview);
    }

    /**
     *
     * @return this movie's review number of stars
     */
    public String getNumstars() {
        return (String) this.getMovieReviews().get(0).get("numStars");
    }

    /**
     *
     * @return int avg rating
     */
    public double getAvgRating() {
        return avgRating;
    }

    /**
     *
     * @param avgRating the avg rating of this movie
     */
    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }
}
