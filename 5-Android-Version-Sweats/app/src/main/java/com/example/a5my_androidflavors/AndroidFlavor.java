package com.example.a5my_androidflavors;

public class AndroidFlavor {

    /* init var */
    private String mVersionName;
    private String mVersionNumber;
    /** img represented by img_id*/
    private int mImageResourceId;

    /* constructor */
    public AndroidFlavor(String vName, String vNumber, int imgResourceId) {
        mVersionName = vName;
        mVersionNumber = vNumber;
        mImageResourceId = imgResourceId;
    }

    /* setter not needed for later use in Adapter */

    /* getter */
    public String getVersionName() { return mVersionName; }
    public String getVersionNumber() { return mVersionNumber; }
    public int getImageResourceId() { return mImageResourceId; }

}
