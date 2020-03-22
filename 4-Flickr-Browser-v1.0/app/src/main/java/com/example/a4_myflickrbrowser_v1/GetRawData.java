package com.example.a4_myflickrbrowser_v1;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentNavigableMap;

enum DownloadStatus { IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK }

/** A General-Purpose Text Download class
 * (json, html, xml, ..... )*/

class GetRawData extends AsyncTask<String, Void, String> {

    private static final String TAG = "GetRawData";
    private DownloadStatus mDownloadStatus;
    private final OnDownloadComplete mCallback;

    interface OnDownloadComplete {
        void onDownloadComplete(String downloadedResults, DownloadStatus status);
    }

    public GetRawData(OnDownloadComplete Callback) {
        this.mDownloadStatus = DownloadStatus.IDLE;
        this.mCallback = Callback;
    }

    void runInSameThread(String s) {
        //  `doInBackground()` + `onPostExecute()`
        if(mCallback != null) {
            String downloadedResults = doInBackground(s);
            mCallback.onDownloadComplete(downloadedResults, mDownloadStatus);
//            mCallback.onDownloadComplete(doInBackground(s), mDownloadStatus);
        }
    }

    @Override
    protected void onPostExecute(String downloadedResults) {
        super.onPostExecute(downloadedResults);

        /** Pass the "Raw Results" (downloadedResults, mDownloadStatus) to the next part.
         *  Let the next part decide what to do with the "Raw Results"
         *          thro. `implements GetRawData.OnDownloadComplete`
         *  */

        if(mCallback != null) {
            mCallback.onDownloadComplete(downloadedResults, mDownloadStatus);
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        if(strings == null) {
            mDownloadStatus = DownloadStatus.NOT_INITIALISED;
            return null;
        }

        try {
            mDownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // The HTTP method (GET,POST,PUT...) default is GET.
            connection.connect();
            int response = connection.getResponseCode();
            Log.d(TAG, "doInBackground: Http Response Cose: " + response);

            // Result
            StringBuilder result = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            /** Read Line by Line:
             *  both while_loop and for_loop are fine */
//            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
//                // reader.readLine() will strip the "\n"; need to append it back.
//                result.append(line).append("\n");
//            }
            String line;
            while (null != (line = reader.readLine())) {
                result.append(line).append("\n");
            }

            mDownloadStatus = DownloadStatus.OK;
            return result.toString();

        } catch(MalformedURLException e) {
            Log.e(TAG, "doInBackground: Invalid URL " + e.getMessage() );
        } catch(IOException e) {
            Log.e(TAG, "doInBackground: IO Exception reading data: " + e.getMessage() );
        } catch(SecurityException e) {
            Log.e(TAG, "doInBackground: Security Exception. Needs permission? " + e.getMessage());
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            if(reader != null) {
                try {
                    reader.close();
                } catch(IOException e) {
                    Log.e(TAG, "doInBackground: Error closing stream " + e.getMessage() );
                }
            }
        }

        mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }

}

