package com.example.a3_myyoutubeplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    /** we can implements `View.OnClickListener` TOGETHER in this class,
     *  then use `this` to set listener "button.setOnClickListener(this);"
     *  (we used to define View.OnClickListener separately, both ways are fine.)
     *  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSingle = (Button) findViewById(R.id.btnPlaySingle);
        Button btnStandalone = (Button) findViewById(R.id.btnStandalone);
        btnSingle.setOnClickListener(this);
        btnStandalone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // use Intent to call our other Activities.
        // Note that "standalone_activity" uses Intent to call external "YouTube APP"
        Intent intent = null;

        switch (view.getId()) {
            case R.id.btnPlaySingle:
                intent = new Intent(this, YoutubeActivity.class);
                break;
            case R.id.btnStandalone:
                Log.d(TAG, "onClick: StandaloneActivity creating...");
                intent = new Intent(this, StandaloneActivity.class);
                Log.d(TAG, "onClick: StandaloneActivity finished...");
                break;
            default:
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
