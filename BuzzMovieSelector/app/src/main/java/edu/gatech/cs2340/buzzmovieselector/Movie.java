package edu.gatech.cs2340.buzzmovieselector;

import java.io.Serializable;

/**
 * Created by julianeuman on 2/21/16.
 */
public class Movie implements Serializable {

    private String movieName;
    private String movieYear;
    private String movieLength;
    private String movieMpaRating;

    /**
     * Creates a new movie object with null inputs
     */
    public Movie() {
        this(null, null, null, null);

    }

    /**
     * Creates a new movie objects with corresponding movie attributes
     * @param name movie name
     * @param year year movie came out
     * @param movieLength movie duration
     * @param mpaRating film rating
     */
    public Movie(String name, String year, String movieLength, String mpaRating) {
        this.movieName = name;
        this.movieYear = year;
        this.movieLength = movieLength;
        this.movieMpaRating = mpaRating;
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

}
