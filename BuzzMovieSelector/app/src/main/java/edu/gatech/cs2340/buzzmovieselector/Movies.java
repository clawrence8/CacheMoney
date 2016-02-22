package edu.gatech.cs2340.buzzmovieselector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by julianeuman on 2/21/16.
 */
public class Movies {
    /**
     * An array of State objects.
     */
    public static final List<Movie> ITEMS = new ArrayList<>();

    /**
     * A map of states  by Name.
     */
    public static final Map<String, Movie> ITEM_MAP = new HashMap<>();

    public static void addItem(Movie item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getMovieName(), item);
    }


}