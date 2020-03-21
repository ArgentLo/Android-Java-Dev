package com.example.a3my_musicalstructure;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.bond.musicstructure.R;

public class PlaylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle(R.string.title_playlist);
        Button btn_artist = (Button) findViewById(R.id.artist);
        Button btn_genre = (Button) findViewById(R.id.genre);

        btn_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaylistActivity.this, ArtistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaylistActivity.this, GenreActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
