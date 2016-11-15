package com.michaelcavalli.projects.popularmoviesvectorform.REST;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * This class holds a reference to the VolleyRequestQueueHolder, and can add new requests to that
 * queue. It's a singleton class that can only have on instantiation.
 */

class VolleyRequestQueueHolder {
    private static VolleyRequestQueueHolder onlyRequestHolder = null;   // Singleton instance
    private RequestQueue moviesRequestQueue;                            // Queue holding all requests

    private VolleyRequestQueueHolder(){}

    /**
     * Checks to see if the class is already instantiated and returns it, or instantiates it and
     * returns it.
     * @return A reference to the instantiation
     */
    static VolleyRequestQueueHolder getInstance(){
        if(onlyRequestHolder == null){
            onlyRequestHolder = new VolleyRequestQueueHolder();
        }

        return onlyRequestHolder;
    }

    /**
     * This method adds a new request to the queue.
     * @param newRequest This is the new request
     * @param context this is the context for the request
     */
    void addRequest(StringRequest newRequest, Context context  ){
        if(moviesRequestQueue == null){
            moviesRequestQueue = Volley.newRequestQueue(context);
        }
        moviesRequestQueue.add(newRequest);
    }


}
