package edu.gatech.cs2340.buzzmovieselector;

import org.junit.Before;
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

    private User user1;
    private User user2;
    private User user3;
    private Movie movieA;
    private Movie movieB;
    private Movie movieC;
    private Movie movie1;
    private Movie movie2;
    private Movie movie3;
    private UserManager manager;
    private static Map<String, User> usersList;
    public static final int TIMEOUT = 200;

    @Before
    public void setup() {
        usersList = new HashMap<>();
        user1 = new User( "bob", "bBob", "bob@gmail.com", "ob", "CS",
                "Male", "Marvel");
        user2 = new User( "sally", "", "", "", "",
                "", "");
        user3 = null;

        movieA = new Movie();
        movieB = new Movie();
        movieC = new Movie();

        movie1 = new Movie();
        movie2 = new Movie();
        movie3 = new Movie();

        //TODO see testValidUser method
        //manager = UserManager.getInstance();
    }
    //Clayton's JUnit
    @Test
    public void correctMovieCompare() {


        movieA.setMovieName("Avengers");
        movieA.setMajorRating(3.3);

        movieB.setMovieName("Buzz");
        movieB.setMajorRating(4.8);

        movieC.setMovieName("Cache Money");
        movieC.setMajorRating(3.3);

        //Check when rating is different
        assertEquals(-1, movieB.compareTo(movieA));
        assertEquals(1, movieC.compareTo(movieB));

        //check when rating is same
        assertTrue(movieA.compareTo(movieC) < 0);
        assertTrue(movieC.compareTo(movieA) > 0);
        assertTrue(movieA.compareTo(movieA) == 0);

    }

    //Hannah's JUnit
    @Test
    public void checkMovieAddItem()
    {
        movie1.setMovieName("Zootopia");
        Movies.addItem(movie1);

        movie2.setMovieName("James Bond");
        Movies.addItem(movie2);

        movie3.setMovieName("Zootopia");


        assertTrue(Movies.contains(movie1));
        assertTrue(Movies.contains(movie2));
        assertFalse(Movies.contains(movie3));

    }

    //Chez's J-units


    @Test(timeout = TIMEOUT)
    public void testValidUser() {
        /*
         * Since the following code uses a UserManager and UserManager uses Firebase, we
         * need to do Firebase.setAndroidContext(<Context>) in UserManager to actually run this.
         * Since UserManager doesn't extend an Android class, we don't have a context to pass to
         * Firebase. So we might not be able to test this.
         */

//        manager.addUser(user2);
//        manager.addUser(user3);
//
//    	assertFalse(manager.getUserList().containsValue(user3));
//    	assertTrue(manager.getUserList().containsValue(user2));
//    	assertFalse(manager.getUserList().containsValue(user1));

    }
}