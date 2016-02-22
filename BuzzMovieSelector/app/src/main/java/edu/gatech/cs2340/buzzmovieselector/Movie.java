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

    public Movie() {
        this(null, null, null, null);

    }

    public Movie(String name, String year, String movieLength, String mpaRating) {
        this.movieName = name;
        this.movieYear = year;
        this.movieLength = movieLength;
        this.movieMpaRating = mpaRating;
    }



    public String getMovieName() {
        return this.movieName;
    }
    public String getMovieYear() {
        return this.movieYear;
    }
    public String getMovieLength() {
        return this.movieLength;
    }
    public String getMovieMpaRating() {
        return this.movieMpaRating;
    }
    public void setMovieName(String name) {
        this.movieName = name;
    }
    public void setMovieYear(String year) {
        this.movieYear = year;
    }
    public void setMovieLength(String mlength) {
        this.movieLength = mlength;
    }
    public void setMovieMpaRating(String rating) {
        this.movieMpaRating = rating;
    }

}
