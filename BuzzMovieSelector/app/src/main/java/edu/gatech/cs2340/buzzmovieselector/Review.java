package edu.gatech.cs2340.buzzmovieselector;

import java.io.Serializable;

/**
 * Created by Hannah on 3/2/16.
 */
public class Review implements Serializable {
    private String username;
    private String major;
    private String rating;
    private String comment;

    public Review() {
    }

    public Review(String username, String major, String rating, String comment) {
        this.username = username;
        this.major = major;
        this.rating = rating;
        this.comment = comment;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
