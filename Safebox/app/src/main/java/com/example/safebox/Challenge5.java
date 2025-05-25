package com.example.safebox;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safebox.utils.Native;

public class Challenge5 extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge5);
        EditText editTextFlag = findViewById(R.id.flagInput);
        Button buttonSubmit = findViewById(R.id.unlockButton);
        Native ctfValidator = new Native();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = editTextFlag.getText().toString();
                if (ctfValidator.validateFlag(userInput)) {
                } else {
                }
            }
        });

    }
}
