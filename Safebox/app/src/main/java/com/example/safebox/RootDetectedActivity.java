package com.example.safebox;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class RootDetectedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root_detected);
        
        // Prevent going back
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }
    
    @Override
    public void onBackPressed() {
        // Disable back button
        finishAffinity();
    }
} 