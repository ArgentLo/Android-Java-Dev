package com.example.a2_mytopdownloader;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // INIT VAR
    private static final String TAG = "MainActivity";
    private ListView listApps;
    private String feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
    private int feedLimit = 10; // Length of our Ranking
    private String cachedUrl = "INIT_URL";
    public static final String STATE_URL = "feedUrl";
    public static final String STATE_LIMIT = "feedLimit";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listApps = (ListView) findViewById(R.id.xmlListView);

        if (savedInstanceState != null) {
            feedUrl = savedInstanceState.getString(STATE_URL);
            feedLimit = savedInstanceState.getInt(STATE_LIMIT);
        }

        downloadUrl(String.format(feedUrl, feedLimit));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_URL, feedUrl);
        outState.putInt(STATE_LIMIT, feedLimit);
        super.onSaveInstanceState(outState);
    }

    /** for Menu Layout selected */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feeds_menu, menu);
        if (feedLimit == 10) {
            menu.findItem(R.id.mnu10).setChecked(true);
        } else if (feedLimit == 25) {
            menu.findItem(R.id.mnu25).setChecked(true);
        } else if (feedLimit == 50) {
            menu.findItem(R.id.mnu50).setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.mnuFree:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
                break;
            case R.id.mnuPaid:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml";
                break;
            case R.id.mnuSongs:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml";
                break;
            case R.id.mnuTv:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topTvSeasons/limit=%d/xml";
                break;
            case R.id.mnuMovies:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topMovies/limit=%d/xml";
                break;
            case R.id.mnu10:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    feedLimit = 10;
                } break;
            case R.id.mnu25:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    feedLimit = 25;
                } break;
            case R.id.mnu50:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    feedLimit = 50;
                } break;
            case R.id.mnuRefresh:
                cachedUrl = "INVALIDATED";
                Toast.makeText(this, "Refreshing RSS Contents ......", Toast.LENGTH_LONG).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        downloadUrl(String.format(feedUrl, feedLimit));
        return true;
    }

    /**
     * Helper for Downloading given URL
     */
    private void downloadUrl(String urlToDownload) {
        if (!urlToDownload.equalsIgnoreCase(cachedUrl)) {
            Log.d(TAG, "downloadUrl: starting AsyncTask...");
            DownloadData downloadData = new DownloadData();
            downloadData.execute(urlToDownload);
            cachedUrl = urlToDownload;
            Log.d(TAG, "downloadUrl: Done");
        } else {
            Log.d(TAG, "downloadUrl: URL not changed, Download not required.");
        }
    }

    /**
     * Download being done in background as "AsyncTask"
     */
    private class DownloadData extends AsyncTask<String, Void, String> {
        public static final String TAG = "DownloadData";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // String s input here is the whole downloaded XML
            // Need to be Parsed here.
//            Log.d(TAG, "onPostExecute: the parameter is " + s);
            ParseApplications parseApplications = new ParseApplications();
            parseApplications.parse(s);

//            ArrayAdapter<FeedEntry> arrayAdapter = new ArrayAdapter<FeedEntry>(
//                    MainActivity.this, R.layout.list_item, parseApplications.getApplications());
//            listApps.setAdapter(arrayAdapter);
            FeedAdapter<FeedEntry> feedAdapter = new FeedAdapter<FeedEntry>(
                    MainActivity.this, R.layout.list_record, parseApplications.getApplications());
            listApps.setAdapter(feedAdapter);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: starts with " + strings[0]);
            String rssFeed = downloadXML(strings[0]);
            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: ERROR downloading");
            }
            return rssFeed;
        }

        private String downloadXML(String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            /** Try download and Catch all possible Exception (IOException),
             *  since anything can go wrong while downloading:
             *      - URL can be unreachable;
             *      - Phone Internet not available;  .... */
            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // response code: e.g. "404":  Page Not Found
                int responseCode = connection.getResponseCode();
                // (very common combo) Get/Read InputStream --> Buffer
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charsRead;
                char[] inputBuffer = new char[500]; // 500 length for buffer, given only small XML.
                while (true) {
                    charsRead = reader.read(inputBuffer);
                    if (charsRead < 0) {
                        break; // has reached the end of XML.
                    }
                    if (charsRead > 0) {
                        // copyValueOf(Array, offset, length_to_copy)
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }
                }
                reader.close();
                return xmlResult.toString();
            } catch (MalformedURLException e) {
                Log.e(TAG, "downloadXML: Invalid URL" + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "downloadXML: IO Exception reading data" + e.getMessage());
            } catch (SecurityException e) {
                Log.e(TAG, "downloadXML: Security Exception. Permission Problem? "
                        + e.getMessage());
            }
            // return null if try failed.
            return null;
        }
    }
}
