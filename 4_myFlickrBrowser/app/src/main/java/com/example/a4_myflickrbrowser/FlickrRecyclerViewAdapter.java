package com.example.a4_myflickrbrowser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageViewHolder> {

    private static final String TAG = "FlickrRecyclerViewAdapt";
    private List<Photo> mPhotosList;
    private Context mContext;

    public FlickrRecyclerViewAdapter(Context context, List<Photo> photosList) {
        mContext = context;
        mPhotosList = photosList;
    }

    /** `static class AAA` is just a NEW TOP-Level Class(a new file) "AAA.java"
     *      but using `static class` allows to create "inner class" more concise,
     *      especially when `ViewHolder()` always appears together with `RecyclerView.Adapter`!
     */
    static class FlickrImageViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "FlickrImageViewHolder";
        ImageView thumbnail = null;
        TextView title = null;

        // Constructor
        public FlickrImageViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "FlickrImageViewHolder: starts");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Called by the layout manager when it needs a new view
        Log.d(TAG, "onCreateViewHolder: new view requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);
        return new FlickrImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlickrImageViewHolder holder, int position) {
        // Called by the layout manager when it wants new data in an existing row

        Photo photoItem = mPhotosList.get(position);
        Log.d(TAG, "onBindViewHolder: " + photoItem.getTitle() + " --> " + position);
        // Use Picasso here : auto handling when downloaded IMG is broken.
        Picasso.get().load(photoItem.getImage())
                .error(R.drawable.ic_broken_image)
                .placeholder(R.drawable.ic_default_image)
                .into(holder.thumbnail);

        holder.title.setText(photoItem.getTitle());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called");
        return ((mPhotosList != null) && (mPhotosList.size() !=0) ? mPhotosList.size() : 0);
    }

    void loadNewData(List<Photo> newPhotos) {
        mPhotosList = newPhotos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position) {
        return ((mPhotosList != null) && (mPhotosList.size() !=0) ? mPhotosList.get(position) : null);
    }
}
