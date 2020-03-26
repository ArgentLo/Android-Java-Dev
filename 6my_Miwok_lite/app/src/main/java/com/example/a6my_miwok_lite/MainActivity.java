package com.example.a6my_miwok_lite;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        /** OnClickListener for Numbers Activity View*/
        TextView numbers = (TextView) findViewById(R.id.numbers);
        numbers.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(numbersIntent);
            }
        });

        /** OnClickListener for other Views */
        TextView family = (TextView) findViewById(R.id.family);
        TextView colors = (TextView) findViewById(R.id.colors);
        TextView phrases = (TextView) findViewById(R.id.phrases);

        family.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent familyIntent = new Intent(MainActivity.this,
                        FamilyActivity.class);
                startActivity(familyIntent);
            }
        });

        colors.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent colorsIntent = new Intent(MainActivity.this,
                        ColorsActivity.class);
                startActivity(colorsIntent);
            }
        });

        phrases.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phasesIntent = new Intent(MainActivity.this,
                        PhrasesActivity.class);
                startActivity(phasesIntent);
            }
        });
    }
}
