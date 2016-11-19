package com.michaelcavalli.projects.popularmoviesvectorform;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.michaelcavalli.projects.popularmoviesvectorform.REST.PopularMoviesObject;

import java.util.ArrayList;

/**
 * This adapter loads the movie info into the Recyclerview list
 */

class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private ArrayList<PopularMoviesObject.Movie> dataSet;   // Dataset of the adapter
    private moreMoviesCallback mainActivityCallback;                // Callback reference to main activity


    /**
     * Viewholder class for each item in our list
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle = null;
        ImageView posterImage = null;
        TextView movieOverview = null;
        TextView movieReleaseDate = null;
        TextView movieVoteAverage = null;

        ViewHolder (View v){
            super(v);
            movieTitle = (TextView) v.findViewById(R.id.movie_title);
            posterImage = (ImageView) v.findViewById(R.id.poster_view);
            movieOverview = (TextView) v.findViewById(R.id.movie_overview);
            movieReleaseDate = (TextView) v.findViewById(R.id.release_date);
            movieVoteAverage = (TextView) v.findViewById(R.id.vote_average);
        }

    }


    MovieAdapter(ArrayList<PopularMoviesObject.Movie> myMovieList, moreMoviesCallback c){
        // Make sure the callback interface is implemented
        try {
            mainActivityCallback =  c;
        } catch (ClassCastException e) {
            Log.e(LOG_TAG, e.toString() + ((Context)c).getString(R.string.callback_casing_fail));
        }
        // Set the dataset for this adapter
        dataSet = myMovieList;
    }

    /**
     * Creates the viewholder for the requested position and returns it.
     * @param parent The parent viewgroup
     * @param viewType The viewtype
     * @return The viewholder for this position
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Binds the data for this position to the individual views in the layout
     * @param holder The viewholder for this position
     * @param position The position requesting data to be binded
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the movie object once
        PopularMoviesObject.Movie movieAtPosition = dataSet.get(position);

        // Grab some context so we can get Strings easier
        Context c = (Context) mainActivityCallback;

        // Assign information to each field, grabbing the info from the movie object
        holder.movieTitle.setText(movieAtPosition.getTitle());
        holder.movieReleaseDate.setText(c.getString(R.string.release_date_text, movieAtPosition.getRelease_date()));
        holder.movieVoteAverage.setText(c.getString(R.string.vote_average_text, movieAtPosition.getVote_average()));
        holder.movieOverview.setText(movieAtPosition.getOverview());

        // Create the full poster path using the root in the strings file
        String fullPosterPath = c.getString(R.string.poster_root, movieAtPosition.getPosterPath());

        // Use glide to get the picture and put it into the ImageView
        Glide.with(c).load(fullPosterPath).into(holder.posterImage);

        // If we hit the end of the list, request more movies using the callback method
        if(position == dataSet.size()-1)
            mainActivityCallback.getMoreMovies();
    }

    /**
     * Returns the size of the data
     * @return data size
     */
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    /**
     * This method is used to add additional movie data to the dataset
     * @param newMovies the object containing new movies to be added
     */
    void addNewMovies(ArrayList<PopularMoviesObject.Movie> newMovies){
        // Find  the first new dataset position
        int firstNewPosition = dataSet.size();
        // Find how many new movies will be added
        int totalNewMovies = newMovies.size();
        // Add the new movies to the existing dataset
        dataSet.addAll(newMovies);
        // Notify the adapter of the new movies and their range
        notifyItemRangeInserted(firstNewPosition, totalNewMovies);
    }

    /**
     * Interface that must be implemented by the activity using this adapter
     */
    interface moreMoviesCallback {
        void getMoreMovies();
    }
}
