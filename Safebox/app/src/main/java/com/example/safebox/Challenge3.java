package com.example.safebox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Challenge3 extends AppCompatActivity {

    private final int leftTouch = 1, rightTouch = 2, upTouch = 4, downTouch = 0;
    public int left = 0, right = 0, up = 0, down = 0;

    // 4l, 2r, 5u, 1d, 1l, 1u, 1r
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge3);

        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button clear = findViewById(R.id.button5);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right += 1;
                if (down > 0) down -= 1;
                nextLevel();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                down += 1;
                if (up > 0) up -= 1;
                nextLevel();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                left += 1;
                if (right > 0) right -= 1;
                nextLevel();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up += 1;
                if (left > 0) left -= 1;
                nextLevel();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                left = 0;
                right = 0;
                up = 0;
                down = 0;
            }
        });
    }

    private boolean puzzleUnlocked()
    {
        return leftTouch == up && downTouch == left && rightTouch == down && upTouch == right;
    }

    private void nextLevel()
    {
        if(puzzleUnlocked())
        {
            Intent intent = new Intent(this, Challenge4.class);
            startActivity(intent);
            finish();
        }
    }
}
