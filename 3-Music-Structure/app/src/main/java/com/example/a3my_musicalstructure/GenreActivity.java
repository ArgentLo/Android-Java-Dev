package com.example.a3my_musicalstructure;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.bond.musicstructure.R;

public class GenreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        setTitle(R.string.title_genre);

        Button btn_artist = (Button) findViewById(R.id.artist);
        Button btn_playlist = (Button) findViewById(R.id.playlist);

        btn_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenreActivity.this, ArtistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenreActivity.this, PlaylistActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
