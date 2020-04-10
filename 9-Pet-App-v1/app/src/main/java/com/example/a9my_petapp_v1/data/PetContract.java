package com.example.a9my_petapp_v1.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class PetContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private PetContract() {}


    // CONTENT_AUTHORITY: for simplicity, use "package name", b/c it's guaranteed to be unique on the
    // device
    public static final String CONTENT_AUTHORITY = "com.example.a9my_petapp_v1";

    // ContentScheme + CONTENT_AUTHORITY
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // PATH that allow user to access.
    // e.g. "content://com.example.android.pets/pets/" --> "pets" is a valid path. */
    public static final String PATH_PETS = "pets";


    // All Pet Table : BASE_CONTENT_URI / MIME-Type / schema info
    public static final class PetEntry implements BaseColumns {

        // The content URI to access the pet data in the provider
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

        /** The MIME type of the for a list / an item.
         *     --> different from CONTENT_URI, MIME-Type is for Android System to properly handle data.*/
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        /** Table Name & COLUMNS */
        public final static String TABLE_NAME = "pets";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PET_NAME ="name";
        public final static String COLUMN_PET_BREED = "breed";
        public final static String COLUMN_PET_WEIGHT = "weight";
        public final static String COLUMN_PET_GENDER = "gender";


        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        public static boolean isValidGender(int gender) {
            if (gender == GENDER_UNKNOWN || gender == GENDER_MALE || gender == GENDER_FEMALE) {
                return true;
            }
            return false;
        }
    }

}
