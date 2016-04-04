package edu.gatech.cs2340.buzzmovieselector;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class BuzzMovieSelectorJUnits {
    //Clayton's JUnit
    @Test
    public void correctMovieCompare() {
        Movie a = new Movie();
        Movie b = new Movie();
        Movie c = new Movie();

        a.setMovieName("Avengers");
        a.setMajorRating(3.3);

        b.setMovieName("Buzz");
        b.setMajorRating(4.8);

        c.setMovieName("Cache Money");
        c.setMajorRating(3.3);

        //Check when rating is different
        assertEquals(-1, b.compareTo(a));
        assertEquals(1, c.compareTo(b));

        //check when rating is same
        assertTrue(a.compareTo(c) < 0);
        assertTrue(c.compareTo(a) > 0);
        assertTrue(a.compareTo(a) == 0);

    }

    //Hannah's JUnit
    public void checkMovieAddItem()
    {
        List<Movie> movieList = new ArrayList<>();
        Map<String, Movie> movieItems = new HashMap<>();

        Movie movie1 = new Movie();
        Movie movie2 = new Movie();
        Movie movie3 = new Movie();

        movie1.setMovieName("Zootopia");
        Movies.addItem(movie1);

        movie2.setMovieName("James Bond");
        Movies.addItem(movie2);

        movie3.setMovieName("Zootopia");
        Movies.addItem(movie3);

        assertTrue(movieList.contains(movie1));
        assertTrue(movieItems.containsKey(movie1.getMovieName()));

        assertTrue(movieList.contains(movie2));
        assertTrue(movieItems.containsKey(movie2.getMovieName()));

        assertFalse(movieList.contains(movie3));
        assertFalse(movieItems.containsKey(movie3.getMovieName()));
    }
}