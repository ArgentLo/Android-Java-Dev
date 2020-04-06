package com.example.a8my_earthquakereport;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

/**  Loading Earthquake info from url using AsyncTaskLoader
 *  (like AsyncTask but Loader is better,  Loader lives with the Activity ) */
public class EarthquakeLoader extends AsyncTaskLoader {

    private static final String LOG_TAG = EarthquakeLoader.class.getName();
    private String mUrl;

    public EarthquakeLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    // in background
    @Override
    public List<Earthquake> loadInBackground() {
        if (mUrl == null) {
            return null;
        } else {
            List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
            return earthquakes;
        }
    }

    // forceLoad(): to kick off the process.
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
