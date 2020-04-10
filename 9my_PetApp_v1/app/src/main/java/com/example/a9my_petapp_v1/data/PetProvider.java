package com.example.a9my_petapp_v1.data;

import com.example.a9my_petapp_v1.data.PetContract.PetEntry;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/** ContentProvider: needs to implement:
 *      - CRUD Operation: insert(), query(), update(), delete()
 *      - getType() method: return proper MIME-Type for Android System  */

public class PetProvider extends ContentProvider {

    private static final String TAG = "PetProvider";

    // URI matcher code for the content URI (entire pets table)
    private static final int PETS = 100;
    // URI matcher code for the content URI (single pet)
    private static final int PET_ID = 101;
    // UriMatcher: match ContentURI to corresponding code (100/101 here)
    // (UriMatcher.NO_MATCH): it's common to use "NO_MATCH:-1" as code for root URI.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // "content://com.example.android.pets/pets" maps to code "100".
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS, PETS);
        // Wildcard "#": for any integer.
        // "content://com.example.android.pets/pets/3" maps to code "101".
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS + "/#", PET_ID);
    }

    /**
     * Database helper object
     */
    private PetDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new PetDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    /**
     * ContentValues: is used to store a set of values that `ContentResolver` can process.
     * e.g. in EditorActivity:
     *      ContentValues values = new ContentValues();
     *      values.put(PetEntry.COLUMN_PET_NAME, nameString);
     *      values.put(PetEntry.COLUMN_PET_GENDER, mGender);
     *      values.put(PetEntry.COLUMN_PET_WEIGHT, weight);
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return insertPet(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a pet into the database with the given content values.
     * Return the new content URI for that specific row in the database.
     */
    private Uri insertPet(Uri uri, ContentValues values) {
        // Get PetName and check null; User needs to provide Pet Name.
        String name = values.getAsString(PetEntry.COLUMN_PET_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }
        // Check that the gender is valid
        Integer gender = values.getAsInteger(PetEntry.COLUMN_PET_GENDER);
        if (gender == null || !PetEntry.isValidGender(gender)) {
            throw new IllegalArgumentException("Pet requires valid gender");
        }
        // If the weight is provided, check that it's greater than or equal to 0 kg
        Integer weight = values.getAsInteger(PetEntry.COLUMN_PET_WEIGHT);
        if (weight != null && weight < 0) {
            throw new IllegalArgumentException("Pet requires valid weight");
        }

        // Get Writable database to Insert
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(PetEntry.TABLE_NAME, null, values);
        // if id is -1 : the insertion Failed
        if (id == -1) {
            Log.e(TAG, "insertPet: Failed to insert row for " + uri);
            return null;
        }
        // otherwise, Succeeded
        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                // Delete all rows that match the selection and selection args
                return database.delete(PetEntry.TABLE_NAME, selection, selectionArgs);
            case PET_ID:
                // Delete a single row given by the ID in the URI
                selection = PetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return database.delete(PetEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return updatePet(uri, contentValues, selection, selectionArgs);
            case PET_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = PetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatePet(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // check the provided name value is not null.
        if (values.containsKey(PetEntry.COLUMN_PET_NAME)) {
            String name = values.getAsString(PetEntry.COLUMN_PET_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }

        // check that the gender value is valid.
        if (values.containsKey(PetEntry.COLUMN_PET_GENDER)) {
            Integer gender = values.getAsInteger(PetEntry.COLUMN_PET_GENDER);
            if (gender == null || !PetEntry.isValidGender(gender)) {
                throw new IllegalArgumentException("Pet requires valid gender");
            }
        }

        // check that the weight value is valid.
        if (values.containsKey(PetEntry.COLUMN_PET_WEIGHT)) {
            // Check that the weight is greater than or equal to 0 kg
            Integer weight = values.getAsInteger(PetEntry.COLUMN_PET_WEIGHT);
            if (weight != null && weight < 0) {
                throw new IllegalArgumentException("Pet requires valid weight");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Get writable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        return database.update(PetEntry.TABLE_NAME, values, selection, selectionArgs);
    }


    // provide correct MIME-type for Android System to select right way to handle this URI.
    // Especially "Intent with URI", Android System needs to know which Apps is capable to handle:
    //      "it this an Database?  is this PDF file? ...."
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                // return "vnd.android.cursor.dir/com.example.android.pets/pets"
                return PetEntry.CONTENT_LIST_TYPE;
            case PET_ID:
                // return "vnd.android.cursor.item/com.example.android.pets/pets"
                return PetEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
