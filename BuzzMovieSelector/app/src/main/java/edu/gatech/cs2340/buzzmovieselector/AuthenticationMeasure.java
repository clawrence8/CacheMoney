package edu.gatech.cs2340.buzzmovieselector;

/**
 * Created by naudghebre on 2/5/16.
 */

/**
 * Interface that checks if a login attempt is valid
 */
public interface AuthenticationMeasure {
    boolean handleLoginAttempt(String name, String password);
}
