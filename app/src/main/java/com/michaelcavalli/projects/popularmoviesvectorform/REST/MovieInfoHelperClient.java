package com.michaelcavalli.projects.popularmoviesvectorform.REST;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.michaelcavalli.projects.popularmoviesvectorform.R;


/**
 * This class helps the main activity request information from the movie database by making requests
 * to the VolleyUtil class.  It also uses Gson to deserialize the JSON information into a
 * PopularMoviesObject, and returns it via a callback interface.
 */

public class MovieInfoHelperClient implements VolleyUtils.VolleyRequestCallback {
    private static final String LOG_TAG = MovieInfoHelperClient.class.getSimpleName();

    private static MovieInfoHelperClient onlyInstance = null;   // Singleton instance
    private static moviesCallback mainCallback;                 // Callback interface

    private MovieInfoHelperClient() {
        // Do nothing
    }

    /**
     * Insures we only have one instance of this class, and if it's null, it creates it.  Also stores
     * a reference to the callback.
     * @param context context for the callback
     * @return the singleton object
     */
    public static MovieInfoHelperClient getInstance(Context context) {
        if (onlyInstance == null) {
            onlyInstance = new MovieInfoHelperClient();
        }

        try {
            mainCallback = (moviesCallback) context;
        } catch (ClassCastException e) {
            Log.e(LOG_TAG, e.toString() + context.getString(R.string.callback_casing_fail));
        }

        return onlyInstance;
    }

    /**
     * This method checks for internet, then uses the VolleyUtil class to make a request for info
     * from the movie database. If there is no internet it uses the callback to notify of the error.
     * @param context the context of the main activity
     * @param page the page of movie info being requested
     */
    public void getPopularMovieData(Context context, int page) {

        // Check for internet
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        // If we have internet, make the request for movie info
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {

            // Build our Uri to send to Volley for the request
            Uri.Builder builder = new Uri.Builder();
            builder.scheme(context.getString(R.string.scheme))
                    .authority(context.getString(R.string.authority))
                    .appendPath(context.getString(R.string.firstAppend))
                    .appendPath(context.getString(R.string.movieAppend))
                    .appendPath(context.getString(R.string.popularAppend))
                    .appendQueryParameter(context.getString(R.string.sort_by_query), context.getString(R.string.movie_order))
                    .appendQueryParameter(context.getString(R.string.api_key_query), context.getString(R.string.api_key))
                    .appendQueryParameter(context.getString(R.string.page_query), Integer.toString(page));

            VolleyUtils.addNewVolleyRequest(builder.toString(), this, context);

        }
        // If we don't have internet, send the error callback
        else {
            mainCallback.errorCallback(context.getString(R.string.no_connection));
        }
    }

    /**
     * This method takes the response from the VolleyUtil request that was sent.
     * @param responseString The response string from the Volley request.
     */
    @Override
    public void volleyResponse(String responseString) {
        Gson gson = new GsonBuilder().create();
        PopularMoviesObject newMovieList = gson.fromJson(responseString, PopularMoviesObject.class);
        mainCallback.newMoviesObtained(newMovieList);


    }

    /**
     * This method is a callback that is called if Volley returns an error
     *
     */
    @Override
    public void volleyError() {
        mainCallback.errorCallback(((Context)mainCallback).getString(R.string.volley_error));
    }

    /**
     * This interface must be implemented by the activity using this client, for callbacks
     */
    public interface moviesCallback {
        void newMoviesObtained(PopularMoviesObject newMoviesObject);
        void errorCallback(String errorText);
    }

}
