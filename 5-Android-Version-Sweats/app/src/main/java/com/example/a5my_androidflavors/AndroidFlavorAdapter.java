package com.example.a5my_androidflavors;
import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;


import java.util.ArrayList;


public class AndroidFlavorAdapter extends ArrayAdapter<AndroidFlavor> {

    /* "static final" : CONSTANT */
    private static final String LOG_TAG = AndroidFlavorAdapter.class.getSimpleName();

    /**This is our own custom constructor (it doesn't mirror a superclass constructor).
     *      The context is used to inflate the layout file,
     *      The list is the data we want to populate into the lists.
     *      @param context        The current context --> to inflate the layout.
     *      @param androidFlavors A List of objects to display in a list.
     */
    public AndroidFlavorAdapter(Activity context, ArrayList<AndroidFlavor> androidFlavors) {
        // "resource: 0" this is a custom adapter for two TextViews and an ImageView,
        // the adapter will NOT use the 2nd argument, so it can be any value. Here, we used 0.
        super(context, 0, androidFlavors);
    }

    /** Provides a view for an AdapterView (ListView, GridView, etc.)
     *  This `getView` method is how AdapterView interact with Adapter:
     *          e.g. "give me items at position 3, I'm gonna whip it out! "
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        AndroidFlavor currentAndroidFlavor = getItem(position);

        // Find the TextView and set text
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.version_name);
        nameTextView.setText(currentAndroidFlavor.getVersionName());
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.version_number);
        numberTextView.setText(currentAndroidFlavor.getVersionNumber());
        // Find the ImageView and set image
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.list_item_icon);
        iconView.setImageResource(currentAndroidFlavor.getImageResourceId());

        return listItemView;
    }
}
