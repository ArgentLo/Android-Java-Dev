package com.example.a8my_earthquakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public static final String LOCATION_SEPARATOR = " of ";

    public EarthquakeAdapter(Context context, List<Earthquake> earthquake) {
        super(context, 0, earthquake);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // [getView + ViewHolder]: not required by ListView but necessary in the new RecycleView.
        // Check if convertView is null, then inflate it with a new XML layout.
        // otherwise, (if != null) there is an existing convertView that we can reuse,
        ViewHolder viewHolder;
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
            viewHolder = new ViewHolder(listItemView);
            listItemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listItemView.getTag();
        }

        // Get item at the current position.
        Earthquake currentEarthquake = getItem(position);

        // magnitudeView
        String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());
        viewHolder.magnitudeView.setText(formattedMagnitude);
        // magnitudeView BackgroundColor
        GradientDrawable magnitudeCircle = (GradientDrawable) viewHolder.magnitudeView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);


        String originalLocation = currentEarthquake.getLocation();
        String primaryLocation;
        String locationOffset;

        // Check whether the originalLocation string contains the " of " text
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            // Split the string into different parts (as an array of Strings) "Cairo, Egypt".
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            // Location offset should be "5km N " + " of " --> "5km N of"
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            // the default location offset to say "Near the".
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        // locationView
        viewHolder.primaryLocationView.setText(primaryLocation);
        viewHolder.locationOffsetView.setText(locationOffset);

        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        String formattedDate = formatDate(dateObject);
        viewHolder.dateView.setText(formattedDate);

        String formattedTime = formatTime(dateObject);
        viewHolder.timeView.setText(formattedTime);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

    private class ViewHolder{

        final TextView magnitudeView;
        final TextView primaryLocationView;
        final TextView locationOffsetView;
        final TextView dateView;
        final TextView timeView;

        ViewHolder (View listItemView) {
            this.magnitudeView = listItemView.findViewById(R.id.magnitude);
            this.primaryLocationView = (TextView) listItemView.findViewById(R.id.primary_location);
            this.locationOffsetView = (TextView) listItemView.findViewById(R.id.location_offset);
            this.dateView = (TextView) listItemView.findViewById(R.id.date);
            this.timeView = (TextView) listItemView.findViewById(R.id.time);
        }

    }

    /**  Format raw data: Magnitude, Date, Time  */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
