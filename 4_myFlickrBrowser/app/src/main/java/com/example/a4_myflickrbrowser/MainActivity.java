package com.example.a4_myflickrbrowser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements GetFlickrJsonData.OnDataAvailable,
                                               RecyclerItemClickListener.OnRecyclerClickListener{


    private static final String TAG = "MainActivity";
    private FlickrRecyclerViewAdapter mFlickrRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // setLayoutManager() is required by RecyclerView;
        // Not like `ListView`, `RecyclerView` is more flexible, allowing LinearLayoutManager and more.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ItemClickListen { SingleClick ; LongPress }
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));

        mFlickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(this, new ArrayList<Photo>());
        recyclerView.setAdapter(mFlickrRecyclerViewAdapter);

//        GetRawData getRawData = new GetRawData(this);
//        getRawData.execute("https://api.flickr.com/services/feeds/photos_public.gne?tags=android,nougat,sdk&tagmode=any&format=json&nojsoncallback=1");

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume starts");
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String queryResult = sharedPreferences.getString(FLICKR_QUERY, "");

        GetFlickrJsonData getFlickrJsonData = new GetFlickrJsonData(this,
                "https://api.flickr.com/services/feeds/photos_public.gne", "en-us", true);

        if(queryResult.length() > 0) {
            getFlickrJsonData.execute(queryResult);
        } else {
            getFlickrJsonData.execute("Barcelona");
        }

        Log.d(TAG, "onResume ends");
    }

    @Override
    public void onDataAvailable(List<Photo> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: starts");
        if(status == DownloadStatus.OK) {
            mFlickrRecyclerViewAdapter.loadNewData(data);
        } else {
            // download or processing failed
            Log.e(TAG, "onDataAvailable failed with status " + status);
        }

        Log.d(TAG, "onDataAvailable: ends");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /** Overriding `OnRecyclerClickListener`
     *  define what to do when LongPress() or SingleClick()  */

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Single Tap at position" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(this, "Long Press Detected.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PhotoDetailActivity.class);
        /** "Serializable": (for simple object (String, Int), No Serializable requirement)
         *  (from Oracle Doc: Serializable means " Obj --> Byte Stream --> retrieve back as Obj ")
         *  To allow Photo object to pass thro diff. Activity by Intent:
         *      need a way to store/retrieve data --> complex obj needs to meet "Serializable" requirement.
         *      when defining Photo object: implement + (optional) provide SrializationID (for diff. JAVA ver.)
         *          class Photo implements Serializable() {
         *              private static final long serialVersionUID = 1L; }                         */
        intent.putExtra(PHOTO_TRANSFER, (Serializable) mFlickrRecyclerViewAdapter.getPhoto(position));
        startActivity(intent);
    }
}
