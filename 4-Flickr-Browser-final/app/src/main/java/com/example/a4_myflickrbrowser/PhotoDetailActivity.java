package com.example.a4_myflickrbrowser;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        // enable ReturnButton (defined in BaseActivity)
        activateToolbar(true);

        // Retrieve Serializable Data from MainActivity when `LongClick`
        // `intent.putExtra(PHOTO_TRANSFER, mFlickrRecyclerViewAdapter.getPhoto(position));`
        Intent intent = getIntent();
        Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);

        if(photo != null) {
            TextView photoTitle = (TextView) findViewById(R.id.photo_title);
            Resources resources = getResources();
            String text = resources.getString(R.string.photo_title_text, photo.getTitle());
            photoTitle.setText(text);
//            photoTitle.setText("Title: " + photo.getTitle());

            TextView photoTags = (TextView) findViewById(R.id.photo_tags);
            photoTags.setText(resources.getString(R.string.photo_tags_text, photo.getTags()));
//            photoTags.setText("Tags: " + photo.getTags());

            TextView photoAuthor = (TextView) findViewById(R.id.photo_author);
            photoAuthor.setText(photo.getAuthor());

            ImageView photoImage = (ImageView) findViewById(R.id.photo_image);
            Picasso.get().load(photo.getLink())
                    .error(R.drawable.ic_broken_image)
                    .placeholder(R.drawable.ic_default_image)
                    .into(photoImage);
        }
    }
}