package com.example.safebox;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Challenge4 extends AppCompatActivity {

    private static final byte[] OBFUSCATED_FLAG =  new byte[] {
            0x16, 0x3B, 0x7A, 0x36, 0x3B, 0x2F, 0x2E, 0x3B, 0x28, 0x33
    };

    private static final byte XOR_KEY = 0x5A;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge4);
        EditText editTextFlag = findViewById(R.id.flagInput);
        Button buttonSubmit = findViewById(R.id.unlockButton);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = editTextFlag.getText().toString();
                if (validateFlag(userInput)) {
                    Toast.makeText(Challenge4.this, "✅ Correct Flag!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Challenge4.this, "❌ Incorrect Flag", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private static boolean validateFlag(String submittedFlag) {
        if (submittedFlag == null) return false;

        byte[] decoded = new byte[OBFUSCATED_FLAG.length];
        for (int i = 0; i < OBFUSCATED_FLAG.length; i++) {
            decoded[i] = (byte) (OBFUSCATED_FLAG[i] ^ XOR_KEY);
        }
        String actualFlag = new String(decoded);
        return submittedFlag.equals(actualFlag);
    }
}
