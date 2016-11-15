package com.michaelcavalli.projects.popularmoviesvectorform.REST;

import java.util.ArrayList;

/**
 * This class holds a list of all the movie info retrieved from the movie database
 */

public class PopularMoviesObject {

    // List of movie info
    private ArrayList<Movie> results;

    public PopularMoviesObject(){
        results = new ArrayList<>();
    }

    // Returns the list of movies
    public ArrayList<Movie> returnMovieList(){
        return results;
    }

    // The movie class, where each set of movie data is pushed into
    public class Movie {
        private String title = null;
        private String poster_path = null;
        private String overview = null;
        private String release_date = null;
        private String vote_average = null;

        public String getTitle(){
            return title;
        }

        public String getPosterPath(){
            return poster_path;
        }

        public String getOverview(){
            return overview;
        }

        public String getRelease_date(){
            return release_date;
        }

        public String getVote_average(){
            return vote_average;
        }
    }

}
