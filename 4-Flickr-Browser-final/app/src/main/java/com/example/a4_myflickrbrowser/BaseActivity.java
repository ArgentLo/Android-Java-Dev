package com.example.a4_myflickrbrowser;

import android.util.Log;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {

    /**
     * Create our own BaseActivity class for adding to ActivityBar.
     * -   ToolBar
     * -   return button "<-" .
     * <p>
     * The Followings can be put in individual activity (which uses ToolBar);
     * but by `extends BaseActivity` here, code would be concise.
     */

    private static final String TAG = "BaseActivity";
    static final String FLICKR_QUERY = "FLICKR_QUERY";
    static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";

    // Adding ToolBar and return button "<-" to ActivityBar
    void activateToolbar(boolean enableHomeButton) {
        Log.d(TAG, "activateToolbar: starts");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            if (toolbar != null) {
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
            }
        }

        // set return button "<-" on ActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(enableHomeButton);
        }

    }
}
