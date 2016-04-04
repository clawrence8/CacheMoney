package edu.gatech.cs2340.buzzmovieselector;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

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
}