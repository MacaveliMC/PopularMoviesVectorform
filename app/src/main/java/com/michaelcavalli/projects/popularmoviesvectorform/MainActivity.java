package com.michaelcavalli.projects.popularmoviesvectorform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.michaelcavalli.projects.popularmoviesvectorform.REST.MovieInfoHelperClient;
import com.michaelcavalli.projects.popularmoviesvectorform.REST.PopularMoviesObject;

import java.util.ArrayList;

/**
 * This is the main activity of the application that opens first.  It shows a toolbar with the title
 * and an info icon that leads to the about activity.  It also shows a list of movies with their
 * basic information.
 */

public class MainActivity extends AppCompatActivity implements MovieInfoHelperClient.moviesCallback, MovieAdapter.moreMoviesCallback {

    private ProgressBar mainProgressBar;        // Progress spinner for movie content loading
    MovieInfoHelperClient myHelperClient;       // Client used to retrieve movie data using Volley

    private RecyclerView myRecyclerView;        // Recyclerview holds our list of movies
    private MovieAdapter myAdapter;             // Adapter that loads the movies
    RecyclerView.LayoutManager myLayoutManager; // Layout manager for the Recyclerview

    // This int keeps track of what pages we've retrieved and loaded from the movie database
    int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find our important views
        mainProgressBar = (ProgressBar) findViewById(R.id.main_progress_bar);
        myRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);

        // Prepare the Recyclerview for use
        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setHasFixedSize(true);

        // Ask our client to retrieve movie data
        myHelperClient = MovieInfoHelperClient.getInstance(this);
        myHelperClient.getPopularMovieData(this, page);

    }

    /**
     * This method is the callback method for the MovieInfoHelperClient.
     * @param newMoviesObject the object containing a new list of movies to be
     *                        added to our list.
     */
    @Override
    public void newMoviesObtained(PopularMoviesObject newMoviesObject) {
        // Grab the list of new movies from the newMoviesObject
        ArrayList<PopularMoviesObject.Movie> myResults = newMoviesObject.returnMovieList();

        // If we're on page one, create the adapter with the first page movie data
        if(page == 1) {
            myAdapter = new MovieAdapter(myResults, this);
            myRecyclerView.setAdapter(myAdapter);
            // Make the progress bar invisible, and the Recyclerview visible
            mainProgressBar.setVisibility(View.INVISIBLE);
            myRecyclerView.setVisibility(View.VISIBLE);
        }
        // If we're not on page one, add the new movie data to the adapter
        else {
            myAdapter.addNewMovies(myResults);
        }
    }

    /**
     * This is the error callback method.  If there is no internet, it will set the appropriate error
     * view and apply the error message, while making the progress bar invisible.
     */
    @Override
    public void errorCallback(String errorText) {

        // Set the progress bar invisible
        mainProgressBar.setVisibility(View.INVISIBLE);

        // Get view reference, set text, and make visible
        TextView errorTextView = (TextView) findViewById(R.id.no_connection);
        errorTextView.setText(errorText);
        errorTextView.setVisibility(View.VISIBLE);
    }

    /**
     * This method is called when the user hits the bottom of the Recyclerview.  It requests the
     * next page of movies from the MovieInfoHelperClient
     */
    @Override
    public void getMoreMovies() {
        page++; // increase page count
        myHelperClient.getPopularMovieData(this, page);
    }

    /**
     * This is the method called when the user clicks on the info icon in the toolbar.  It opens
     * the about activity.
     * @param view The view clicked (info icon)
     */
    public void openAboutPage(View view){
        Intent aboutPageIntent = new Intent(this, AboutActivity.class);
        startActivity(aboutPageIntent);
    }


}
