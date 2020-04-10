package com.example.a9my_petapp_v1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.example.a9my_petapp_v1.data.PetContract.PetEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class CatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    // insert Dummy Data for testing
    private void insertPet() {
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, "Bosco");
        values.put(PetEntry.COLUMN_PET_BREED, "WenSen");
        values.put(PetEntry.COLUMN_PET_GENDER, PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_PET_WEIGHT, 7);
        // Uri -> ContentResolver -> SQLiteOpenHelper.getWritableDatabase() -> Insert()
        Uri newUri = getContentResolver().insert(PetEntry.CONTENT_URI, values);
    }


    private void displayDatabaseInfo() {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
                PetEntry.COLUMN_PET_GENDER,
                PetEntry.COLUMN_PET_WEIGHT };

        // Perform a query on the provider using the ContentResolver.
        // Use the {@link PetEntry#CONTENT_URI} to access the pet data.
        Cursor cursor = getContentResolver().query(
                PetEntry.CONTENT_URI,   // The content URI of the words table
                projection,             // The columns to return for each row
                null,                   // Selection criteria
                null,                   // Selection criteria
                null);                  // The sort order for the returned rows

        TextView displayView = (TextView) findViewById(R.id.text_view_pet);

        if (cursor != null) {

            try {
                displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
                displayView.append(PetEntry._ID + " - " +
                        PetEntry.COLUMN_PET_NAME + " - " +
                        PetEntry.COLUMN_PET_BREED + " - " +
                        PetEntry.COLUMN_PET_GENDER + " - " +
                        PetEntry.COLUMN_PET_WEIGHT + "\n");

                // Figure out the index of each column
                int idColumnIndex = cursor.getColumnIndex(PetEntry._ID);
                int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);
                int breedColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED);
                int genderColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_GENDER);
                int weightColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT);

                // Iterate through all the returned rows in the cursor
                while (cursor.moveToNext()) {
                    // Use that index to extract the String or Int value of the word
                    // at the current row the cursor is on.
                    int currentID = cursor.getInt(idColumnIndex);
                    String currentName = cursor.getString(nameColumnIndex);
                    String currentBreed = cursor.getString(breedColumnIndex);
                    int currentGender = cursor.getInt(genderColumnIndex);
                    int currentWeight = cursor.getInt(weightColumnIndex);
                    // Display the values from each column of the current row in the cursor in the TextView
                    displayView.append(("\n" + currentID + " - " +
                            currentName + " - " +
                            currentBreed + " - " +
                            currentGender + " - " +
                            currentWeight));
                }
            } finally {
                // Always close the cursor --> Avoid Memory Leak.
                // This releases all its resources and makes it invalid.
                cursor.close();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
