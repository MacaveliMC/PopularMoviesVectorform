package com.michaelcavalli.projects.popularmoviesvectorform;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

/**
 * About Activity that is reached by clicking on the info icon on the main app page.
 */

public class AboutActivity extends AppCompatActivity {
    private static final String LOG_TAG = AboutActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the view
        setContentView(R.layout.about_page);

        // Get the toolbar and set it as the supportActionBar
        Toolbar myToolBar = (Toolbar) findViewById(R.id.about_page_toolbar);
        setSupportActionBar(myToolBar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e){
            Log.v(LOG_TAG, this.getString(R.string.no_return_activity) + ": " + e.toString());
        }

    }
}
