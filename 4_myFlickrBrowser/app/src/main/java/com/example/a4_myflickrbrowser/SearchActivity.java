package com.example.a4_myflickrbrowser;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class SearchActivity extends BaseActivity {

    private static final String TAG = "SearchActivity";
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        activateToolbar(true);
        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: starts.");
        // Inflater:
        //      inflate a [Menu Obj: menu] from [XML structure: R.menu.menu_search]
        getMenuInflater().inflate(R.menu.menu_search, menu);

        // Boilerplate code here: SearchManager -> FindItem(menu) -> setSearchableInfo
        //      1. SearchManager from Android does a lot "Search Assistance" jobs:
        //      - voice search
        //      - search auto-complete ......
        //      2. findItem( find items (menu...) ) ; findViewById( find Layout )
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        mSearchView.setSearchableInfo(searchableInfo);

        // setIconified( false )-> open Text Block for typing (no SearchIcon) when SearchActivity's open.
        mSearchView.setIconified(false);


        /** android SearchView hsa 3 interfaces (here we play with 2 of them) :
         *      1.interface    SearchView.OnCloseListener
         *      2.interface    SearchView.OnQueryTextListener:  Callbacks for changes to the query text.
         *      3.interface	   SearchView.OnSuggestionListener: Callback interface for selection events on suggestions.
         *
         *  -> return true:  if we handle the Query by listener ourselves.
         *  -> finish():  close the SearchActivity and return back to the Prev. Activity (in our case, MainActivity)
         *  -> sharedPreferences:  like "bundles", key-value pairs allow transforming Data btw activities.
         *  */

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: called");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                // We can use same key "FLICKR_QUERY" for SearchActivity and MainActivity.
                // b/c SearchActivity and MainActivity both Extend BaseActivity
                sharedPreferences.edit().putString(FLICKR_QUERY, query).apply();
                // clearFocus when we using External KeyBoard on Emulator.
                mSearchView.clearFocus();
                finish();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                finish();
                return false;
            }
        });





        return true;
    }
}
