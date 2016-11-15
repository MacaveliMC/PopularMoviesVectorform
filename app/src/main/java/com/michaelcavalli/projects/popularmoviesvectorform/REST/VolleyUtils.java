package com.michaelcavalli.projects.popularmoviesvectorform.REST;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * This class holds a static method that makes requests to the singleton queue holder class.
 * It also provides the callback interface to return information to the MovieInfoHelperClient
 */

class VolleyUtils {

    /**
     * This method asks the request queue holder class to add a new request to the request queue, and
     * provides a response listener.
     * @param url The URL for the request
     * @param vrCallBack The callback used to send the info back to the MovieInfoHelperClient
     * @param context Context for the request
     */
    static void addNewVolleyRequest(String url, final VolleyRequestCallback vrCallBack, final Context context ){

        // Create a new request, with new listener
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            /**
             * Positve response from Volley
             * @param response the string response from Volley
             */
            @Override
            public void onResponse(String response) {
                vrCallBack.volleyResponse(response);
            }
        }, new Response.ErrorListener() {
            /**
             * Error response from Volley
             * @param error the error response from Volley
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                vrCallBack.volleyError();
            }
        });

        // Grab the instance of the singleton class
        VolleyRequestQueueHolder vrQueueHolder = VolleyRequestQueueHolder.getInstance();

        // Ask to add the request
        vrQueueHolder.addRequest(stringRequest, context);

    }

    /**
     * This interface must be implemented by the MovieInfoHelperClient so movie info can be returned
     * to it.
     */
    interface VolleyRequestCallback{
        void volleyResponse(String responseString);
        void volleyError();
    }

}
