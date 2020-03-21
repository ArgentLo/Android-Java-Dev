package com.example.a3my_musicalstructure;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.bond.musicstructure.R;

public class ArtistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        setTitle(R.string.title_artist);

        /**
         * Set a Listener
         * -> the listener would be an object from an Abstract Class: "View.OnClickListener"
         * -> @Override the onClick method, since the abstract class provides nothing.        */

        Button btn_genre = (Button) findViewById(R.id.genre);
        Button btn_playlist = (Button) findViewById(R.id.playlist);

        btn_genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtistActivity.this, GenreActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtistActivity.this, PlaylistActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
