package com.example.a8my_earthquakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /** a PRIVATE constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be ONLY accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed). */
    private QueryUtils() {
    }

    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {

        // Using all the helper functions defined below
        // Create URL object -> HTTP request -> HTTP connection -> receive and parse JSON -> return List {@link Earthquake}s
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Earthquake> earthquakes = extractFeatureFromJson(jsonResponse);

        return earthquakes;
    }


    /**
     *  1. Making HTTP Request here ( before 2. Start HTTP Connecting ) */
    private static String makeHttpRequest(URL url) throws IOException {
        // return raw json (as string) for further parsing
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET"); // Some `verbs` in HTTP Methods : GET, POST, PUT, DELETE, HEAD....
            urlConnection.connect(); // start Connecting

            // If the request was successful (response code 200)
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "makeHttpRequest: Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    //Returns URL object from URL String .
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Convert the {@link InputStream} into a String containing the whole JSON from the server. */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Earthquake} from parsing the given JSON response. */
    private static List<Earthquake> extractFeatureFromJson(String earthquakeJSON) {
        // check if null
        if (TextUtils.isEmpty(earthquakeJSON)){
            return null;
        }

        List<Earthquake> earthquakes = new ArrayList<>();
        // try parsing the JSON and catch Exception
        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

            for (int i = 0; i < earthquakeArray.length(); i++) {

                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");

                // Extract the value for the key called "mag", "place", "time", "url"
                double magnitude = properties.getDouble("mag");
                String location = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");

                // Create a new {@link Earthquake} object with extracted values.
                Earthquake earthquake = new Earthquake(magnitude, location, time, url);

                // Add the new {@link Earthquake} to the list of earthquakes.
                earthquakes.add(earthquake);
            }
        } catch (JSONException e){
            e.printStackTrace();
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return earthquakes;
    }
}
