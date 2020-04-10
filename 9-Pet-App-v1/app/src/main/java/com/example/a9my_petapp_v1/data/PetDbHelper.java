package com.example.a9my_petapp_v1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.a9my_petapp_v1.data.PetContract.PetEntry;


/**
 * Database helper for Pets app:
 *      - Manages database creation
 *      - Version management.
 */
public class PetDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "PetDbHelper";

    // Name of the database file */
    private static final String DATABASE_NAME = "shelter.db";

    // Database version -> when database schema changed, increment the database version and use OnUpgrade();
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public PetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // onCreate() called, when DB is created for the first time.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Standard SQL: CREAT TABLE tableName (col type)
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + PetEntry.TABLE_NAME + " ("
                + PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                + PetEntry.COLUMN_PET_BREED + " TEXT, "
                + PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
                + PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
